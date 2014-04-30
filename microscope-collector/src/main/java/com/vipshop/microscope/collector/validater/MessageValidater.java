package com.vipshop.microscope.collector.validater;

import com.vipshop.microscope.trace.gen.Span;
import com.vipshop.microscope.validater.ValidateEngine;

import java.util.HashMap;

/**
 * Validate message from client.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class MessageValidater {

    private ValidateEngine validateEngine = new ValidateEngine();

    public Span validateMessage(Span span) {
        return validateEngine.validate(span);
    }

    public HashMap<String, Object> validateMessage(HashMap<String, Object> metrics) {
        return validateEngine.validate(metrics);
    }
}
