package com.vipshop.microscope.client.transporter;

import com.vipshop.microscope.client.storage.DisruptorQueueStorage;
import com.vipshop.microscope.client.storage.Storages;

public class DisruptorQueueTransporter implements Transporter {

    DisruptorQueueTransporter(){}

    @Override
    public void transport() {
        ((DisruptorQueueStorage) Storages.getStorage()).transport();
    }
}
