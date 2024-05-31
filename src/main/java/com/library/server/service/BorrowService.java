package main.java.com.library.server.service;

import main.java.com.library.server.database.impl.BaseDAO;
import main.java.com.library.server.model.BorrowRecord;

import java.util.List;

public class BorrowService {
    private final BaseDAO<BorrowRecord> BorrowRecordDAO = new BaseDAO<>(BorrowRecord.class);

    public boolean add(BorrowRecord BorrowRecord) {
        return BorrowRecordDAO.add(BorrowRecord);
    }

    public boolean delete(String BorrowRecordID) {
        return BorrowRecordDAO.delete(BorrowRecordID);
    }

    public BorrowRecord get(String BorrowRecordID) {
        return BorrowRecordDAO.get(BorrowRecordID);
    }

    public List<BorrowRecord> getAll() {
        return BorrowRecordDAO.getAll();
    }

    public boolean update(BorrowRecord BorrowRecord) {
        return BorrowRecordDAO.update(BorrowRecord);
    }
}