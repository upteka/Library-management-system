package main.java.com.library.server.entity.service.impl;

import main.java.com.library.server.database.impl.BaseDao;
import main.java.com.library.server.entity.impl.ReturnRecord;

public class ReturnService extends BaseService<ReturnRecord> {
    public ReturnService() {
        super(new BaseDao<>(ReturnRecord.class));
    }
}