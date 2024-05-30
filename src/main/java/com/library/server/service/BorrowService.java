package main.java.com.library.server.service;

import main.java.com.library.server.database.BookDAO;
import main.java.com.library.server.database.BorrowRecordDAO;
import main.java.com.library.server.model.Book;
import main.java.com.library.server.model.BorrowRecord;

import java.util.List;


public class BorrowService {
    private BorrowRecordDAO borrowRecordDAO;
    private BookDAO bookDAO;

    public BorrowService() {
        borrowRecordDAO = new BorrowRecordDAO();
        bookDAO = new BookDAO();
    }

    public boolean addBorrowRecord(BorrowRecord record) {
        Book book = bookDAO.getBook(record.getBookID());
        if (book == null || "borrowed".equals(book.getStatus())) {
            return false; // 书籍不存在或已被借出
        }
        if (borrowRecordDAO.addBorrowRecord(record)) {
            book.setStatus("borrowed");
            return bookDAO.updateBook(book);
        }
        return false;
    }

    public boolean returnBook(String recordID) {
        BorrowRecord record = borrowRecordDAO.getBorrowRecord(recordID);
        if (record == null) {
            return false; // 借阅记录不存在
        }
        if (borrowRecordDAO.returnBook(recordID)) {
            Book book = bookDAO.getBook(record.getBookID());
            book.setStatus("available");
            return bookDAO.updateBook(book);
        }
        return false;
    }

    public boolean deleteBorrowRecord(String recordID) {
        BorrowRecord record = borrowRecordDAO.getBorrowRecord(recordID);
        if (record == null) {
            return false; // 借阅记录不存在
        }
        if (borrowRecordDAO.deleteBorrowRecord(recordID)) {
            Book book = bookDAO.getBook(record.getBookID());
            if (book != null) {
                book.setStatus("available");
                return bookDAO.updateBook(book);
            }
        }
        return false;
    }

    public BorrowRecord getBorrowRecord(String recordID) {
        return borrowRecordDAO.getBorrowRecord(recordID);
    }

    public List<BorrowRecord> getAllBorrowRecords() {
        return borrowRecordDAO.getAllBorrowRecords();
    }
}