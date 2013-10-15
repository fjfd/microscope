package com.vipshop.microscope.collector.decode;

import java.io.ByteArrayInputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.SerializationUtils;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TIOStreamTransport;

import com.vipshop.microscope.thrift.Span;

public class Encoder {
	
	private static final TProtocolFactory protocolFactory = new TBinaryProtocol.Factory();
	
    /**
     * Read {@code Span} object to from byte array.
     * 
     * @param thriftSpan span object
     * @return 
     * @throws TException
     */
    private Span stringToSpan(final String msg) throws TException {
		byte[] tmp = Base64.decodeBase64(msg);
		final ByteArrayInputStream buf = new ByteArrayInputStream(tmp);
		final TProtocol proto = protocolFactory.getProtocol(new TIOStreamTransport(buf));
		Span span = new Span();
		span.read(proto);
		return span;
    }
    
    public Span decodeToSpan(final String msg) throws TException {
    	return stringToSpan(msg);
    }
    
    public static void main(String[] args) throws TException {
    	Span span = new Span();
    	span.setName("test");
    	
    	byte[] data = SerializationUtils.serialize(span);
    	
    	Span span2 = (Span) SerializationUtils.deserialize(data);
    	System.out.println(span2);

	}
    
}
