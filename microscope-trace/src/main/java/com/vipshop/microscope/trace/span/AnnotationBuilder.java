package com.vipshop.microscope.trace.span;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import com.vipshop.microscope.common.util.TimeUtil;
import com.vipshop.microscope.thrift.Annotation;
import com.vipshop.microscope.thrift.AnnotationType;
import com.vipshop.microscope.thrift.BinaryAnnotation;
import com.vipshop.microscope.thrift.zipkinCoreConstants;

/**
 * AnnotationBuilder is a builder which build
 * Annotation object and return to span.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class AnnotationBuilder {
	
	private static final String UTF_8 = "UTF-8";
	
	/**
	 * Return a client send annotation.
	 * 
	 * @param spanName the name of span
	 * @return Annotation object
	 */
	public static Annotation clientSendAnnotation(String spanName) {
		Annotation annotation = new Annotation();
		annotation.setTimestamp(TimeUtil.currentTimeMicros());
		annotation.setHost(EndpointBuilder.newEndpoint(spanName));
		annotation.setValue(zipkinCoreConstants.CLIENT_SEND);
		return annotation;
	}
	
	/**
	 * Returns a client receive annotation.
	 * 
	 * @param spanName the name of span
	 * @return Annotation object
	 */
	public static Annotation clientReceAnnotation(String spanName) {
		Annotation annotation = new Annotation();
		annotation.setTimestamp(TimeUtil.currentTimeMicros());
		annotation.setHost(EndpointBuilder.newEndpoint(spanName));
		annotation.setValue(zipkinCoreConstants.CLIENT_RECV);
		return annotation;
	}
	
	/**
	 * Returns a server send annotation.
	 * 
	 * @param spanName the name of span
	 * @return Annotation object
	 */
	public static Annotation serverSendAnnotation(String spanName) {
		Annotation annotation = new Annotation();
		annotation.setTimestamp(TimeUtil.currentTimeMicros());
		annotation.setHost(EndpointBuilder.newEndpoint(spanName));
		annotation.setValue(zipkinCoreConstants.SERVER_SEND);
		return annotation;
	}
	
	/**
	 * Returns a server receive annotation.
	 * 
	 * @param spanName the name of span 
	 * @return Annotation object
	 */
	public static Annotation serverReceAnnotation(String spanName) {
		Annotation annotation = new Annotation();
		annotation.setTimestamp(TimeUtil.currentTimeMicros());
		annotation.setHost(EndpointBuilder.newEndpoint(spanName));
		annotation.setValue(zipkinCoreConstants.SERVER_RECV);
		return annotation;
	}
	
	/**
	 * Returns a message annotation.
	 * 
	 * @param spanName the name of span
	 * @param message  the message 
	 * @return Annotation object
	 */
	public static Annotation messageAnnotation(String spanName, String message) {
		Annotation annotation = new Annotation();
		annotation.setTimestamp(TimeUtil.currentTimeMicros());
		annotation.setHost(EndpointBuilder.newEndpoint(spanName));
		annotation.setValue(message);
		return annotation;
	}
	
	/**
	 * Returns a binary annotation.
	 * 
	 * @param spanName the name of span
	 * @param key annotation key
	 * @param value annotation value
	 * @throws IllegalStateException if encode is not supported
	 * @return BinaryAnnotation ojbect
	 */
	public static BinaryAnnotation keyValueAnnotation(String spanName, String key, String value) {
        BinaryAnnotation binaryAnnotation = new BinaryAnnotation();
        binaryAnnotation.setKey(key);
        try {
			binaryAnnotation.setValue(ByteBuffer.wrap(value.getBytes(UTF_8)));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		}
        binaryAnnotation.setAnnotation_type(AnnotationType.STRING);
        binaryAnnotation.setHost(EndpointBuilder.newEndpoint(spanName));
        return binaryAnnotation;
	}
	
	/**
	 * Duration is currently not supported in the ZipkinUI
	 * so also add it as part of the annotation name.
	 * 
	 * @param spanName
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static Annotation eventAnnotation(String spanName, long startDate, long endDate) {
		final Annotation annotation = new Annotation();
		final int duration = (int) (endDate - startDate);
		annotation.setTimestamp(startDate * 1000);
		annotation.setHost(EndpointBuilder.newEndpoint(spanName));
		annotation.setDuration(duration * 1000);
		annotation.setValue(spanName + "=" + duration + "ms");
		return annotation;
	}
	
}
