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
	
	/**
	 * Return a client send annotation.
	 * 
	 * @param spanName the name of span
	 * @return Annotation object
	 */
	public static Annotation clientSendAnnotation() {
		Annotation annotation = new Annotation();
		annotation.setTimestamp(TimeUtil.currentTimeMicros());
		annotation.setEndPoint(EndPointBuilder.build());
		annotation.setType(AnnotationType.CS);
		return annotation;
	}
	
	/**
	 * Returns a client receive annotation.
	 * 
	 * @param spanName the name of span
	 * @return Annotation object
	 */
	public static Annotation clientReceAnnotation() {
		Annotation annotation = new Annotation();
		annotation.setTimestamp(TimeUtil.currentTimeMicros());
		annotation.setEndPoint(EndPointBuilder.build());
		annotation.setType(AnnotationType.CR);
		return annotation;
	}
	
	/**
	 * Returns a server send annotation.
	 * 
	 * @param spanName the name of span
	 * @return Annotation object
	 */
	public static Annotation serverSendAnnotation() {
		Annotation annotation = new Annotation();
		annotation.setTimestamp(TimeUtil.currentTimeMicros());
		annotation.setEndPoint(EndPointBuilder.build());
		annotation.setType(AnnotationType.SS);
		return annotation;
	}
	
	/**
	 * Returns a server receive annotation.
	 * 
	 * @param spanName the name of span 
	 * @return Annotation object
	 */
	public static Annotation serverReceAnnotation() {
		Annotation annotation = new Annotation();
		annotation.setTimestamp(TimeUtil.currentTimeMicros());
		annotation.setEndPoint(EndPointBuilder.build());
		annotation.setType(AnnotationType.SR);
		return annotation;
	}
	
	/**
	 * Returns a message annotation.
	 * 
	 * @param spanName the name of span
	 * @param message  the message 
	 * @return Annotation object
	 */
	public static Annotation messageAnnotation(String message) {
		Annotation annotation = new Annotation();
		annotation.setTimestamp(TimeUtil.currentTimeMicros());
		annotation.setLogicPoint(LogicPointBuilder.build(message));
		annotation.setType(AnnotationType.MSG);
		return annotation;
	}
	
	/**
	 * Returns a key/value annotation.
	 * 
	 * @param spanName the name of span
	 * @param key annotation key
	 * @param value annotation value
	 * @throws IllegalStateException if encode is not supported
	 * @return BinaryAnnotation ojbect
	 */
	public static Annotation keyValueAnnotation(String key, String value) {
		Annotation annotation = new Annotation();
		annotation.setTimestamp(TimeUtil.currentTimeMicros());
		annotation.setLogicPoint(LogicPointBuilder.build(key, value));
		annotation.setType(AnnotationType.KV);
		return annotation;
	}
	
}
