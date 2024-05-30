package main.java.com.library.server.service;

import main.java.com.library.server.database.BookDAO;
import main.java.com.library.server.database.BorrowRecordDAO;
import main.java.com.library.server.database.ReturnRecordDAO;
import main.java.com.library.server.model.Book;
import main.java.com.library.server.model.BorrowRecord;
import main.java.com.library.server.model.ReturnRecord;

import java.util.Date;
import java.util.List;

public class ReturnService {
    private final ReturnRecordDAO returnRecordDAO;
    private final BorrowRecordDAO borrowRecordDAO;
    private final BookDAO bookDAO;

    public ReturnService() {
        returnRecordDAO = new ReturnRecordDAO();
        borrowRecordDAO = new BorrowRecordDAO();
        bookDAO = new BookDAO();
    }

    public boolean returnBook(String recordID) {
        BorrowRecord borrowRecord = borrowRecordDAO.getBorrowRecord(recordID);
        if (borrowRecord == null) {
            return false; // 借阅记录不存在
        }

        // 创建还书记录
        ReturnRecord returnRecord = new ReturnRecord(
                java.util.UUID.randomUUID().toString(),
                recordID,
                new Date()
        );

        if (returnRecordDAO.addReturnRecord(returnRecord)) {
            Book book = bookDAO.getBook(borrowRecord.getBookID());
            book.setStatus("available");
            return bookDAO.updateBook(book);
        }
        return false;
    }

    public List<ReturnRecord> getAllReturnRecords() {
        return returnRecordDAO.getAllReturnRecords();
    }

    public ReturnRecord getReturnRecord(String returnID) {
        return returnRecordDAO.getReturnRecord(returnID);
    }
}