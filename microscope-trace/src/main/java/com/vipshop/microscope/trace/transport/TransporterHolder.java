package com.vipshop.microscope.trace.transport;

import com.vipshop.microscope.trace.Tracer;
import com.vipshop.microscope.trace.stoarge.StorageHolder;

public class TransporterHolder {

    public static void startTransporter() {
        if (Tracer.DEFAULT_STORAGE == 1) {
            startQueueTransporter();
        }

        if (Tracer.DEFAULT_STORAGE == 2) {
            startDisruptorTransporter();
        }
    }
	
	private static void startQueueTransporter() {
		new QueueTransporter().transport();
	}

    private static void startDisruptorTransporter() {
        StorageHolder.getStorage();
    }

}
