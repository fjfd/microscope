package com.vipshop.microscope.client.exception;

import com.vipshop.microscope.client.storage.Storage;
import com.vipshop.microscope.client.storage.Storages;

/**
 * Exception data build API.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class Exceptions {

    /**
     * A ExceptionDataBuilder factory return a singleton {@code ExceptionDataBuilder}
     *
     * @author Xu Fei
     * @version 1.0
     */
    private static class ExceptionDataBuilderHolder {
        private static ExceptionDataBuilder builder = new ExceptionDataBuilder();
    }

    private static ExceptionDataBuilder getBuilder() {
        return ExceptionDataBuilderHolder.builder;
    }

    private static final Storage storage = Storages.getStorage();

    /**
     * Record exception
     *
     * @param t exception
     */
    public static void record(final Throwable t) {
        storage.add(getBuilder().build(t));
    }

    /**
     * Record exception and debug info
     *
     * @param t     exception
     * @param debug debug info
     */
    public static void record(final Throwable t, String debug) {
        storage.add(getBuilder().build(t, debug));
    }

}
