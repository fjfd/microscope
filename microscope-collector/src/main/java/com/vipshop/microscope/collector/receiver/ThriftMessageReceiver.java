package com.vipshop.microscope.collector.receiver;

import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;

import com.vipshop.microscope.collector.consumer.MessageConsumer;
import com.vipshop.microscope.common.span.Codec;
import com.vipshop.microscope.common.thrift.LogEntry;
import com.vipshop.microscope.common.thrift.ResultCode;
import com.vipshop.microscope.common.thrift.Send;
import com.vipshop.microscope.common.thrift.Span;
import com.vipshop.microscope.common.thrift.ThriftCategory;
import com.vipshop.microscope.common.thrift.ThriftServer;

/**
 * Use {@code NoneBlockingThriftServer} receive spans.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class ThriftMessageReceiver implements MessageReceiver {
	
	private ThriftServer thriftServer;
	
	private ThriftCategory category;
	
	public ThriftMessageReceiver(MessageConsumer consumer, int port, ThriftCategory category) {
		this.thriftServer = new ThriftServer(new ThriftReceiveHandler(consumer), port);
		this.category = category;
	}
	
	public void start() throws TTransportException {
		thriftServer.startServer(category);
	}
	
	/**
	 * Thrift handler.
	 * 
	 * @author Xu Fei
	 * @version 1.0
	 */
	static class ThriftReceiveHandler implements Send.Iface {
		
		final Codec encoder = new Codec();
		
		final MessageConsumer consumer;

		public ThriftReceiveHandler(MessageConsumer consumer) {
			this.consumer = consumer;
		}
		
		@Override
		public ResultCode send(List<LogEntry> messages) throws TException {
			for (LogEntry logEntry : messages) {
				Span span = null;
				try {
					span = encoder.decodeToSpan(logEntry.getMessage());
				} catch (TException e) {
					return null;
				} 
				consumer.publish(span);
			}
			return ResultCode.OK;
		}
	}
	
}
