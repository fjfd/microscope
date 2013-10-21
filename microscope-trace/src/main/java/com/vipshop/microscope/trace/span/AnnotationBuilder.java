package com.vipshop.microscope.trace.span;

import com.vipshop.microscope.common.util.TimeUtil;
import com.vipshop.microscope.thrift.Annotation;
import com.vipshop.microscope.thrift.AnnotationType;

/**
 * AnnotationBuilder is a builder which build
 * Annotation object and return to span.
 * 
 * @author Xu Fei
 * @version 1.0
 */
public class AnnotationBuilder {
	
//	private static final String UTF_8 = "UTF-8";
	
	/**
	 * Return a client send annotation.
	 * 
	 * @param spanName the name of span
	 * @return Annotation object
	 */
	public static Annotation clientSendAnnotation(String spanName) {
		Annotation annotation = new Annotation();
		annotation.setTimestamp(TimeUtil.currentTimeMicros());
		annotation.setEndPoint(EndPointBuilder.build());
		annotation.setLogicPoint(LogicPointBuilder.build());
		annotation.setType(AnnotationType.CS);
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
		annotation.setEndPoint(EndPointBuilder.build());
		annotation.setLogicPoint(LogicPointBuilder.build());
		annotation.setType(AnnotationType.CR);
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
		annotation.setEndPoint(EndPointBuilder.build());
		annotation.setLogicPoint(LogicPointBuilder.build());
		annotation.setType(AnnotationType.SS);
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
		annotation.setEndPoint(EndPointBuilder.build());
		annotation.setLogicPoint(LogicPointBuilder.build());
		annotation.setType(AnnotationType.CR);
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
		annotation.setEndPoint(EndPointBuilder.build());
		annotation.setLogicPoint(LogicPointBuilder.build());
		annotation.setType(AnnotationType.CR);
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
	public static Annotation keyValueAnnotation(String spanName, String key, String value) {
		Annotation annotation = new Annotation();
		annotation.setTimestamp(TimeUtil.currentTimeMicros());
		annotation.setEndPoint(EndPointBuilder.build());
		annotation.setLogicPoint(LogicPointBuilder.build());
		annotation.setType(AnnotationType.CR);
		return annotation;
//        Annotation binaryAnnotation = new Annotation();
//        binaryAnnotation.setKey(key);
//        try {
//			binaryAnnotation.setValue(ByteBuffer.wrap(value.getBytes(UTF_8)));
//		} catch (UnsupportedEncodingException e) {
//			throw new IllegalStateException(e);
//		}
//        binaryAnnotation.setAnnotation_type(AnnotationType.STRING);
//        binaryAnnotation.setHost(EndpointBuilder.newEndpoint(spanName));
//        return binaryAnnotation;
	}
	
}
