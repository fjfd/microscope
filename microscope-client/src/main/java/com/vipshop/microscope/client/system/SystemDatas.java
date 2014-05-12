package com.vipshop.microscope.client.system;

import com.vipshop.microscope.client.storage.Storage;
import com.vipshop.microscope.client.storage.Storages;

/**
 * System data build API.
 *
 * @author Xu Fei
 * @version 1.0
 */
public class SystemDatas {

    /**
     * A SystemDataBuilder factory return a singleton {@code SystemDataBuilder}
     *
     * @author Xu Fei
     * @version 1.0
     */
    private static class SystemDataBuilderHolder{
        private static SystemDataBuilder builder = new SystemDataBuilder();
    }

    private static SystemDataBuilder getBuilder() {
        return SystemDataBuilderHolder.builder;
    }

    private static final Storage storage = Storages.getStorage();

    public static void record() {
        storage.add(getBuilder().build());
    }
}
