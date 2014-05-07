package com.vipshop.microscope.collector.disruptor;

import com.lmax.disruptor.EventHandler;
import com.vipshop.microscope.collector.storager.MessageStorager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logs store handler.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class AppLogStoreHandler implements EventHandler<AppLogEvent> {

    public static final Logger logger = LoggerFactory.getLogger(AppLogStoreHandler.class);

    private final MessageStorager messageStorager = MessageStorager.getMessageStorager();

    @Override
    public void onEvent(final AppLogEvent event, long sequence, boolean endOfBatch) throws Exception {
        messageStorager.saveAppLog(event.getResult());
    }


}