/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vipshop.microscope.trace.metrics.opentsdb;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.codahale.metrics.Clock;
import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Snapshot;
import com.codahale.metrics.Timer;

/**
 * @author Sean Scanlon <sean.scanlon@gmail.com>
 */
@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("rawtypes")
public class OpenTsdbReporterTest {

    private OpenTsdbReporter reporter;

    @Mock
    private OpenTsdb opentsdb;

    @Mock
    private MetricRegistry registry;

	@Mock
    private Gauge gauge;

    @Mock
    private Counter counter;

    @Mock
    private Clock clock;

    private final long timestamp = 1000198;

    private ArgumentCaptor<Set> captor;

    @Before
    public void setUp() throws Exception {
        captor = ArgumentCaptor.forClass(Set.class);
        reporter = OpenTsdbReporter.forRegistry(registry)
                .withClock(clock)
                .prefixedWith("prefix")
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .filter(MetricFilter.ALL)
                .withTags(Collections.singletonMap("foo", "bar"))
                .build(opentsdb);

        when(clock.getTime()).thenReturn(timestamp * 1000);
    }

    @SuppressWarnings("unchecked")
	@Test
    public void testReportGauges() {
        when(gauge.getValue()).thenReturn(1L);
        reporter.report(this.map("gauge", gauge), this.<Counter>map(), this.<Histogram>map(), this.<Meter>map(), this.<Timer>map());
        verify(opentsdb).send(captor.capture());

        final Set<OpenTsdbMetric> metrics = captor.getValue();
        assertEquals(1, metrics.size());
        OpenTsdbMetric metric = metrics.iterator().next();
        assertEquals("prefix.gauge", metric.getMetric());
        assertEquals(1L, metric.getValue());
        assertEquals((Long) timestamp, metric.getTimestamp());
    }

    @SuppressWarnings("unchecked")
	@Test
    public void testReportCounters() {

        when(counter.getCount()).thenReturn(2L);
        reporter.report(this.<Gauge>map(), this.map("counter", counter), this.<Histogram>map(), this.<Meter>map(), this.<Timer>map());
        verify(opentsdb).send(captor.capture());

        final Set<OpenTsdbMetric> metrics = captor.getValue();
        assertEquals(1, metrics.size());
        OpenTsdbMetric metric = metrics.iterator().next();
        assertEquals("prefix.counter.count", metric.getMetric());
        assertEquals((Long) timestamp, metric.getTimestamp());
        assertEquals(2L, metric.getValue());
    }

    @SuppressWarnings("unchecked")
	@Test
    public void testReportHistogram() {

        final Histogram histogram = mock(Histogram.class);
        when(histogram.getCount()).thenReturn(1L);

        final Snapshot snapshot = mock(Snapshot.class);
        when(snapshot.getMax()).thenReturn(2L);
        when(snapshot.getMean()).thenReturn(3.0);
        when(snapshot.getMin()).thenReturn(4L);
        when(snapshot.getStdDev()).thenReturn(5.0);
        when(snapshot.getMedian()).thenReturn(6.0);
        when(snapshot.get75thPercentile()).thenReturn(7.0);
        when(snapshot.get95thPercentile()).thenReturn(8.0);
        when(snapshot.get98thPercentile()).thenReturn(9.0);
        when(snapshot.get99thPercentile()).thenReturn(10.0);
        when(snapshot.get999thPercentile()).thenReturn(11.0);

        when(histogram.getSnapshot()).thenReturn(snapshot);

        reporter.report(this.<Gauge>map(), this.<Counter>map(), this.map("histogram", histogram), this.<Meter>map(), this.<Timer>map());

        verify(opentsdb).send(captor.capture());

        final Set<OpenTsdbMetric> metrics = captor.getValue();
        assertEquals(11, metrics.size());

        final OpenTsdbMetric metric = metrics.iterator().next();
        assertEquals((Long) timestamp, metric.getTimestamp());

        final Map<String, Object> histMap = new HashMap<String, Object>();
        for (OpenTsdbMetric m : metrics) {
            histMap.put(m.getMetric(), m.getValue());
        }

        assertEquals(histMap.get("prefix.histogram.count"), 1L);
        assertEquals(histMap.get("prefix.histogram.max"), 2L);
        assertEquals(histMap.get("prefix.histogram.mean"), 3.0);
        assertEquals(histMap.get("prefix.histogram.min"), 4L);

        assertEquals(histMap.get("prefix.histogram.stddev"), 5.0);
        assertEquals(histMap.get("prefix.histogram.p50"), 6.0);
        assertEquals(histMap.get("prefix.histogram.p75"), 7.0);
        assertEquals(histMap.get("prefix.histogram.p95"), 8.0);
        assertEquals(histMap.get("prefix.histogram.p98"), 9.0);
        assertEquals(histMap.get("prefix.histogram.p99"), 10.0);
        assertEquals(histMap.get("prefix.histogram.p999"), 11.0);

    }


    @SuppressWarnings("unchecked")
	@Test
    public void testReportTimers() {

        final Timer timer = mock(Timer.class);
        when(timer.getCount()).thenReturn(1L);

        final Snapshot snapshot = mock(Snapshot.class);
        when(snapshot.getMax()).thenReturn(2L);
        when(snapshot.getMean()).thenReturn(3.0);
        when(snapshot.getMin()).thenReturn(4L);
        when(snapshot.getStdDev()).thenReturn(5.0);
        when(snapshot.getMedian()).thenReturn(6.0);
        when(snapshot.get75thPercentile()).thenReturn(7.0);
        when(snapshot.get95thPercentile()).thenReturn(8.0);
        when(snapshot.get98thPercentile()).thenReturn(9.0);
        when(snapshot.get99thPercentile()).thenReturn(10.0);
        when(snapshot.get999thPercentile()).thenReturn(11.0);

        when(timer.getSnapshot()).thenReturn(snapshot);

        reporter.report(this.<Gauge>map(), this.<Counter>map(), this.<Histogram>map(), this.<Meter>map(), this.map("timer", timer));

        verify(opentsdb).send(captor.capture());

        final Set<OpenTsdbMetric> metrics = captor.getValue();
        assertEquals(11, metrics.size());

        final OpenTsdbMetric metric = metrics.iterator().next();
        assertEquals((Long) timestamp, metric.getTimestamp());

        final Map<String, Object> timerMap = new HashMap<String, Object>();
        for (OpenTsdbMetric m : metrics) {
            timerMap.put(m.getMetric(), m.getValue());
        }

        assertEquals(timerMap.get("prefix.timer.count"), 1L);
        assertEquals(timerMap.get("prefix.timer.max"), 2L);
        assertEquals(timerMap.get("prefix.timer.mean"), 3.0);
        assertEquals(timerMap.get("prefix.timer.min"), 4L);

        assertEquals(timerMap.get("prefix.timer.stddev"), 5.0);
        assertEquals(timerMap.get("prefix.timer.p50"), 6.0);
        assertEquals(timerMap.get("prefix.timer.p75"), 7.0);
        assertEquals(timerMap.get("prefix.timer.p95"), 8.0);
        assertEquals(timerMap.get("prefix.timer.p98"), 9.0);
        assertEquals(timerMap.get("prefix.timer.p99"), 10.0);
        assertEquals(timerMap.get("prefix.timer.p999"), 11.0);

    }

    private <T> SortedMap<String, T> map() {
        return new TreeMap<String, T>();
    }

    private <T> SortedMap<String, T> map(String name, T metric) {
        final TreeMap<String, T> map = new TreeMap<String, T>();
        map.put(name, metric);
        return map;
    }
}
