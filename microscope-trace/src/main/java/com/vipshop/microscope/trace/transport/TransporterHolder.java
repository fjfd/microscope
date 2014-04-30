package com.vipshop.microscope.trace.transport;

import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.storage.DisruptorQueueStorage;
import com.vipshop.microscope.trace.storage.StorageHolder;

public class TransporterHolder {

    public static void startTransporter() {
        if (Tracer.DEFAULT_STORAGE == 1) {
            startQueueTransporter();
        }

        if (Tracer.DEFAULT_STORAGE == 2) {
            startDisruptorTransporter();
        }

        if (Tracer.DEFAULT_STORAGE == 3) {
            startLog4j2FileTransporter();
        }
    }

    private static void startQueueTransporter() {
        new ArrayBlockingQueueTransporter().transport();
    }

    private static void startDisruptorTransporter() {
        ((DisruptorQueueStorage) StorageHolder.getStorage()).transport();
    }

    private static void startLog4j2FileTransporter() {
        // TODO
    }

}
