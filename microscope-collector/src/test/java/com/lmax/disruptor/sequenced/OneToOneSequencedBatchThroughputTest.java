/*
 * Copyright 2011 LMAX Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lmax.disruptor.sequenced;

import static com.lmax.disruptor.RingBuffer.createMultiProducer;
import static com.lmax.disruptor.RingBuffer.createSingleProducer;
import static com.lmax.disruptor.support.PerfTestUtil.failIfNot;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.support.PerfTestUtil;
import com.lmax.disruptor.support.ValueAdditionEventHandler;
import com.lmax.disruptor.support.ValueEvent;
import com.lmax.disruptor.util.DaemonThreadFactory;

/**
 * <pre>
 * UniCast a series of items between 1 publisher and 1 event processor.
 *
 * +----+    +-----+
 * | P1 |--->| EP1 |
 * +----+    +-----+
 *
 * Disruptor:
 * ==========
 *              track to prevent wrap
 *              +------------------+
 *              |                  |
 *              |                  v
 * +----+    +====+    +====+   +-----+
 * | P1 |--->| RB |<---| SB |   | EP1 |
 * +----+    +====+    +====+   +-----+
 *      claim      get    ^        |
 *                        |        |
 *                        +--------+
 *                          waitFor
 *
 * P1  - Publisher 1
 * RB  - RingBuffer
 * SB  - SequenceBarrier
 * EP1 - EventProcessor 1
 *
 * </pre>
 */
public final class OneToOneSequencedBatchThroughputTest extends AbstractPerfTestDisruptor {
    public static final int BATCH_SIZE = 10;
    private static final int BUFFER_SIZE = 1024 * 64;
    private static final long ITERATIONS = 1000L * 1000L * 100L;
    private final ExecutorService executor = Executors.newSingleThreadExecutor(DaemonThreadFactory.INSTANCE);
    private final long expectedResult = PerfTestUtil.accumulatedAddition(ITERATIONS) * BATCH_SIZE;

    private RingBuffer<ValueEvent> ringBuffer;
    private final SequenceBarrier sequenceBarrier;
    private final ValueAdditionEventHandler handler;
    private final BatchEventProcessor<ValueEvent> batchEventProcessor;

    public OneToOneSequencedBatchThroughputTest(ProducerType type, WaitStrategy strategy) {
        if (type.equals(ProducerType.SINGLE)) {
            this.ringBuffer = createSingleProducer(ValueEvent.EVENT_FACTORY, BUFFER_SIZE, strategy);
        }

        if (type.equals(ProducerType.MULTI)) {
            this.ringBuffer = createMultiProducer(ValueEvent.EVENT_FACTORY, BUFFER_SIZE, strategy);
        }

        sequenceBarrier = ringBuffer.newBarrier();
        handler = new ValueAdditionEventHandler();
        batchEventProcessor = new BatchEventProcessor<ValueEvent>(ringBuffer, sequenceBarrier, handler);
        ringBuffer.addGatingSequences(batchEventProcessor.getSequence());
    }

    @Override
    protected int getRequiredProcessorCount() {
        return 2;
    }

    @Override
    protected long runDisruptorPass() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        long expectedCount = batchEventProcessor.getSequence().get() + ITERATIONS * BATCH_SIZE;
        handler.reset(latch, expectedCount);
        executor.submit(batchEventProcessor);
        long start = System.currentTimeMillis();

        final RingBuffer<ValueEvent> rb = ringBuffer;

        for (long i = 0; i < ITERATIONS; i++) {
            long hi = rb.next(BATCH_SIZE);
            long lo = hi - (BATCH_SIZE - 1);
            for (long l = lo; l <= hi; l++) {
                rb.get(l).setValue(i);
            }
            rb.publish(lo, hi);
        }

        latch.await();
        long opsPerSecond = (BATCH_SIZE * ITERATIONS * 1000L) / (System.currentTimeMillis() - start);
        waitForEventProcessorSequence(expectedCount);
        batchEventProcessor.halt();

        failIfNot(expectedResult, handler.getValue());

        return opsPerSecond;
    }

    private void waitForEventProcessorSequence(long expectedCount) throws InterruptedException {
        while (batchEventProcessor.getSequence().get() != expectedCount) {
            Thread.sleep(1);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("ProducerType.SINGLE, use yield wait strategy");
        OneToOneSequencedBatchThroughputTest test1 = new OneToOneSequencedBatchThroughputTest(ProducerType.SINGLE, new YieldingWaitStrategy());
        test1.testImplementations();

        System.out.println("ProducerType.SINGLE, use sleep wait strategy");
        OneToOneSequencedBatchThroughputTest test2 = new OneToOneSequencedBatchThroughputTest(ProducerType.SINGLE, new SleepingWaitStrategy());
        test2.testImplementations();

        System.out.println("ProducerType.SINGLE, use block wait strategy");
        OneToOneSequencedBatchThroughputTest test3 = new OneToOneSequencedBatchThroughputTest(ProducerType.SINGLE, new BlockingWaitStrategy());
        test3.testImplementations();

        System.out.println("ProducerType.SINGLE, use busy spin wait strategy");
        OneToOneSequencedBatchThroughputTest test4 = new OneToOneSequencedBatchThroughputTest(ProducerType.SINGLE, new BusySpinWaitStrategy());
        test4.testImplementations();

        System.out.println("ProducerType.MULTI, use yield wait strategy");
        OneToOneSequencedBatchThroughputTest test5 = new OneToOneSequencedBatchThroughputTest(ProducerType.MULTI, new YieldingWaitStrategy());
        test5.testImplementations();

        System.out.println("ProducerType.MULTI, use sleep wait strategy");
        OneToOneSequencedBatchThroughputTest test6 = new OneToOneSequencedBatchThroughputTest(ProducerType.MULTI, new SleepingWaitStrategy());
        test6.testImplementations();

        System.out.println("ProducerType.MULTI, use block wait strategy");
        OneToOneSequencedBatchThroughputTest test7 = new OneToOneSequencedBatchThroughputTest(ProducerType.MULTI, new BlockingWaitStrategy());
        test7.testImplementations();

        System.out.println("ProducerType.MULTI, use busy spin wait strategy");
        OneToOneSequencedBatchThroughputTest test8 = new OneToOneSequencedBatchThroughputTest(ProducerType.MULTI, new BusySpinWaitStrategy());
        test8.testImplementations();
    }
}
