package com.vipshop.microscope.trace.transport;

public class DisruptorQueueTransporter implements Transporter {

    @Override
    public void transport() {
        throw new UnsupportedOperationException("transport operations is defined in DisruptorQueueStorage");
    }
}
