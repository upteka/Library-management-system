package main.java.com.library.server.database.impl;

import main.java.com.library.server.entity.impl.BorrowRecord;


public class BorrowRecordDao extends BaseDao<BorrowRecord> {
    public BorrowRecordDao() {
        super(BorrowRecord.class);
    }
}