package com.vipshop.microscope.trace.transport;

import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.storage.DisruptorQueueStorage;
import com.vipshop.microscope.trace.storage.StorageHolder;

/**
 * Transporter factory
 */
public class TransporterHolder {

    private static int key = Tracer.STORAGE_TYPE;

    public static void startTransporter() {
        startTransporter(key);
    }

    private static void startTransporter(int key) {
        switch (key) {
            case 1:
                startQueueTransporter();
                break;
            case 2:
                startDisruptorTransporter();
                break;
            case 3:
                startLog4j2FileTransporter();
                break;
            case 4:
                startLog4jFileTransporter();
                break;
            default:
                startQueueTransporter();
                break;
        }
    }

    private static void startQueueTransporter() {
        new ArrayBlockingQueueTransporter().transport();
    }

    private static void startDisruptorTransporter() {
        ((DisruptorQueueStorage) StorageHolder.getStorage()).transport();
    }

    private static void startLog4j2FileTransporter() {
        // TODO flume transport
    }

    private static void startLog4jFileTransporter() {
        // TODO flume transport
    }
}
