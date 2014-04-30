package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * GCLog store handler.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class GCLogStorageHandler implements EventHandler<GCLogEvent> {

    public static final Logger logger = LoggerFactory.getLogger(GCLogStorageHandler.class);

    @Override
    public void onEvent(final GCLogEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println(event.getResult());
    }

}