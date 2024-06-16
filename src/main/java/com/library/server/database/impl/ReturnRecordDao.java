package main.java.com.library.server.database.impl;

import main.java.com.library.common.entity.impl.ReturnRecord;


public class ReturnRecordDao extends BaseDao<ReturnRecord> {
    public ReturnRecordDao(Class<ReturnRecord> returnRecordClass) {
        super(ReturnRecord.class);
    }
}
