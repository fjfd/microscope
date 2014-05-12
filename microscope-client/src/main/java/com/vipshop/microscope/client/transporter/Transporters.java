package com.vipshop.microscope.client.transporter;

import com.vipshop.microscope.client.Tracer;

/**
 * Transporter factory
 */
public class Transporters {

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
        new DisruptorQueueTransporter().transport();
    }

    private static void startLog4j2FileTransporter() {
        new Log4j2FileTransporter().transport();
    }

    private static void startLog4jFileTransporter() {
        new Log4jFileTransporter().transport();
    }
}
