package main.java.com.library.server.entity.service.impl;

import main.java.com.library.server.database.impl.BaseDao;
import main.java.com.library.server.entity.impl.BorrowRecord;

public class BorrowService extends BaseService<BorrowRecord> {
    public BorrowService() {
        super(new BaseDao<>(BorrowRecord.class));
    }
}