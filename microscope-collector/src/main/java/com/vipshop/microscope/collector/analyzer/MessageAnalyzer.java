package com.vipshop.microscope.collector.analyzer;

import com.vipshop.microscope.analyzer.AnalyzeEngine;
import com.vipshop.microscope.trace.gen.Span;
import com.vipshop.microscope.trace.metrics.MetricData;

import java.util.Map;

/**
 * Message Analyze API.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class MessageAnalyzer {

    private AnalyzeEngine engine = new AnalyzeEngine();

    private MessageAnalyzer() {
    }

    public static MessageAnalyzer getMessageAnalyzer() {
        return MessageAnalyzerHolder.messageAnalyzer;
    }

    public void analyze(Span span) {
        engine.analyze(span);
    }

    public void analyze(Map<String, Object> metrics) {
        engine.analyze(metrics);
    }

    public void analyze(MetricData metrics) {

    }

    private static class MessageAnalyzerHolder {
        private static MessageAnalyzer messageAnalyzer = new MessageAnalyzer();
    }

}
