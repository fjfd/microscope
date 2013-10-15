package com.vipshop.microscope.collector.processor;

import org.springframework.stereotype.Component;

import com.vipshop.microscope.collector.builder.BuildProcessor;
import com.vipshop.microscope.collector.decode.Encoder;
import com.vipshop.microscope.collector.metric.Metric;
import com.vipshop.microscope.collector.processor.storage.HbaseStorageProcessor;
import com.vipshop.microscope.collector.processor.storage.StorageProcessor;
import com.vipshop.microscope.thrift.LogEntry;

@Component
public abstract class AbstraceMessageProcessor implements MessageProcessor {

	protected static final Encoder encoder = new Encoder();

	protected static final Metric metric = new Metric();

	protected static final StorageProcessor storageProcessor = new HbaseStorageProcessor();

	protected static final BuildProcessor buildProcessor = new BuildProcessor();

	public AbstraceMessageProcessor() {
		
	}

	@Override
	public void process(LogEntry logEntry) {
		this.stat(logEntry);
		this.store(logEntry.getMessage());
	}

}
