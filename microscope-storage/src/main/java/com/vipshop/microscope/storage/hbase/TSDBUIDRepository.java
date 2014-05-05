package com.vipshop.microscope.storage.hbase;

import org.springframework.stereotype.Repository;

@Repository
public class TSDBUIDRepository extends AbstractRepository {

    public void initialize() {
        super.create(TSDBUIDTable.TABLE_NAME, new String[]{TSDBUIDTable.CF_NAME, TSDBUIDTable.CF_ID});
    }

    public void drop() {
        super.drop(TSDBUIDTable.TABLE_NAME);
    }

}
