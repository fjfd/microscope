package com.vipshop.microscope.trace.span;

import java.util.Iterator;
import java.util.List;

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
		annotation.setType(AnnotationType.SR);
		return annotation;
	}
	
	/**
	 * Add a KV annotation
	 * 
	 * @param annotations
	 * @param key
	 * @param message
	 */
	public static void KVAnnotation(List<Annotation> annotations, String key, String message) {
		if (hasKVAnnotation(annotations)) {
			Annotation annotation = getKVAnnotation(annotations);
			annotation.setEndPoint(EndPointBuilder.buildAddKV(annotation, key, message));
		} else {
			annotations.add(newKVAnnotation(key, message));
		}
	}
	
	/**
	 * Returns a KV annotation.
	 * 
	 * @param spanName the name of span
	 * @param message  the message 
	 * @return Annotation object
	 */
	private static Annotation newKVAnnotation(String key, String message) {
		Annotation annotation = new Annotation();
		annotation.setTimestamp(TimeUtil.currentTimeMicros());
		annotation.setEndPoint(EndPointBuilder.buildNewKV(annotation, key, message));
		annotation.setType(AnnotationType.KV);
		return annotation;
	}

	private static boolean hasKVAnnotation(List<Annotation> annotations) {
		for (Iterator<Annotation> iterator = annotations.iterator(); iterator.hasNext();) {
			Annotation annotation = iterator.next();
			if (annotation.getType().equals(AnnotationType.KV)) {
				return true;
			}
		}
		return false;
	}
	
	private static Annotation getKVAnnotation(List<Annotation> annotations) {
		for (Iterator<Annotation> iterator = annotations.iterator(); iterator.hasNext();) {
			Annotation annotation = iterator.next();
			if (annotation.getType().equals(AnnotationType.KV)) {
				return annotation;
			}
		}
		return null;
	}

}
