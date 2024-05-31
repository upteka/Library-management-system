package main.java.com.library.server.service;

import main.java.com.library.server.database.impl.BaseDAO;
import main.java.com.library.server.model.ReturnRecord;

import java.util.List;

public class ReturnService {
    private final BaseDAO<ReturnRecord> ReturnRecordDAO = new BaseDAO<>(ReturnRecord.class);

    public boolean add(ReturnRecord ReturnRecord) {
        return ReturnRecordDAO.add(ReturnRecord);
    }

    public boolean delete(String ReturnRecordID) {
        return ReturnRecordDAO.delete(ReturnRecordID);
    }

    public ReturnRecord get(String ReturnRecordID) {
        return ReturnRecordDAO.get(ReturnRecordID);
    }

    public List<ReturnRecord> getAll() {
        return ReturnRecordDAO.getAll();
    }

    public boolean update(ReturnRecord ReturnRecord) {
        return ReturnRecordDAO.update(ReturnRecord);
    }

}