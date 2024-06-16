package main.java.com.library.server.database.impl;

import main.java.com.library.common.entity.impl.BorrowRecord;


public class BorrowRecordDao extends BaseDao<BorrowRecord> {
    public BorrowRecordDao(Class<BorrowRecord> borrowRecordClass) {
        super(BorrowRecord.class);
    }
}
