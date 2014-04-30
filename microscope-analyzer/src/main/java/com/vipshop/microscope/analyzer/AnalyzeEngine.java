package com.vipshop.microscope.analyzer;

import com.vipshop.microscope.analyzer.processor.TopAnalyzer;
import com.vipshop.microscope.analyzer.processor.TraceAnalyzer;
import com.vipshop.microscope.common.util.ThreadPoolUtil;
import com.vipshop.microscope.storage.StorageRepository;
import com.vipshop.microscope.trace.gen.Span;

import java.util.Map;
import java.util.concurrent.ExecutorService;

public class AnalyzeEngine {

    private final StorageRepository storager = StorageRepository.getStorageRepository();

    private final TopAnalyzer topAnalyzer = new TopAnalyzer();
    private final ExecutorService topExecutor = ThreadPoolUtil.newSingleThreadExecutor("top-analyzer");

    private final TraceAnalyzer traceAnalyzer = new TraceAnalyzer();
    private final ExecutorService traceExecutor = ThreadPoolUtil.newSingleThreadExecutor("trace-analyzer");

    public AnalyzeEngine() {
    }

    public void analyze(final Span span) {

        topExecutor.execute(new Runnable() {
            @Override
            public void run() {
                topAnalyzer.analyze(span, storager);
            }
        });

        traceExecutor.execute(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                traceAnalyzer.analyze(span);
            }
        });
    }

    public void analyze(Map<String, Object> metrics) {

    }
}
