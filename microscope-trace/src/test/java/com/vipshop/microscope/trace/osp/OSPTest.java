package com.vipshop.microscope.trace.osp;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.vipshop.microscope.common.trace.Category;
import com.vipshop.microscope.trace.Tracer;

public class OSPTest {
	
	static class TaskB implements Runnable {
		CountDownLatch taskBCountDownLatch = null;
		String traceId;
		String spanId;
		int step;

		public TaskB(int step, CountDownLatch taskBCountDownLatch) {
			this.taskBCountDownLatch = taskBCountDownLatch;
			this.step = step;
		}

		public void send(String traceId, String spanId) {
			this.traceId = traceId;
			this.spanId = spanId;

			Thread thread = new Thread(this);
			thread.start();
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			// taskB clientSend
			Tracer.clientSend(traceId, spanId, "TaskB_" + step, Category.Method);

			System.out.println("TaskB_" + step);
			System.out.println("+++++++++++++++++++ task B ++++++++++++++++++++++++++");
			this.traceId = Tracer.getTraceId();
			this.spanId = Tracer.getSpanId();
			System.out.println(traceId);
			System.out.println(spanId);
			Tracer.clientReceive();

			taskBCountDownLatch.countDown();
		}
	}

	static class TaskA implements Runnable {
		String traceId;
		String spanId;
		int step;
		CountDownLatch mainTaskCountDownLatch;

		public TaskA(int step, CountDownLatch mainTaskCountDownLatch) {
			this.mainTaskCountDownLatch = mainTaskCountDownLatch;
			this.step = step;
		}

		public void send(String traceId, String spanId) {
			this.traceId = traceId;
			this.spanId = spanId;

			Thread thread = new Thread(this);

			thread.start();

			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
			}

		}

		public void run() {
			// taskA clientSend
			Tracer.clientSend(traceId, spanId, "TaskA_" + step, Category.Method);

			System.out.println("TaskA_" + step);
			System.out.println("+++++++++++++++++++ task A ++++++++++++++++++++++++++");
			this.traceId = Tracer.getTraceId();
			this.spanId = Tracer.getSpanId();
			System.out.println(traceId);
			System.out.println(spanId);

			// taskA call taskB
			CountDownLatch taskBCountDownLatch = new CountDownLatch(1);
			TaskB taskB = new TaskB(step, taskBCountDownLatch);
			taskB.send(traceId, spanId);
			try {
				taskBCountDownLatch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Tracer.clientReceive();

			mainTaskCountDownLatch.countDown();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		// mainTask clientSend
		Tracer.clientSend("MainTask", Category.Method);

		String traceId = Tracer.getTraceId();
		String spanId = Tracer.getSpanId();

		System.out.println("++++++++++++++++ main +++++++++++++++++++++++++++ ");

		System.out.println(traceId);
		System.out.println(spanId);

		// mainTask call taskA
		CountDownLatch taskA1CountDownLatch = new CountDownLatch(1);
		TaskA taskA = new TaskA(1, taskA1CountDownLatch);
		taskA.send(traceId, spanId);
		taskA1CountDownLatch.await();// 执行完成,模拟同步通讯

		CountDownLatch taskA2CountDownLatch = new CountDownLatch(1);
		TaskA taskA2 = new TaskA(2, taskA2CountDownLatch);
		taskA2.send(traceId, spanId);
		taskA2CountDownLatch.await();

		TimeUnit.SECONDS.sleep(1);

		Tracer.clientSend("sencond-main-method", Category.Method);
		TimeUnit.MILLISECONDS.sleep(100);
		String traceId1 = Tracer.getTraceId();
		String spanId1 = Tracer.getSpanId();

		System.out.println("++++++++++++++++ sendcond +++++++++++++++++++++++++++ ");

		System.out.println(traceId1);
		System.out.println(spanId1);
		Tracer.clientReceive();

		Tracer.clientReceive();
		TimeUnit.SECONDS.sleep(5);
	}
}
