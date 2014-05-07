package com.vipshop.microscope.trace.transport;

import com.vipshop.microscope.trace.storage.DisruptorQueueStorage;
import com.vipshop.microscope.trace.storage.StorageHolder;

public class DisruptorQueueTransporter implements Transporter {

    DisruptorQueueTransporter(){}

    @Override
    public void transport() {
        ((DisruptorQueueStorage) StorageHolder.getStorage()).transport();
    }
}
