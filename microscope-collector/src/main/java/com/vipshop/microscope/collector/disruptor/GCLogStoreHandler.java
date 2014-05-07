package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventHandler;
import com.vipshop.microscope.collector.storager.MessageStorager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * GC Log store handler.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class GCLogStoreHandler implements EventHandler<GCLogEvent> {

    public static final Logger logger = LoggerFactory.getLogger(GCLogStoreHandler.class);

    private final MessageStorager messageStorager = MessageStorager.getMessageStorager();

    @Override
    public void onEvent(final GCLogEvent event, long sequence, boolean endOfBatch) throws Exception {
        messageStorager.saveGCLog(event.getResult());
    }

}