package main.java.com.library.server.service.impl;

import main.java.com.library.server.database.impl.BaseDao;
import main.java.com.library.server.entity.impl.Book;
import main.java.com.library.server.entity.impl.BorrowRecord;

/**
 * @author PC
 */
public class BorrowService extends BaseService<BorrowRecord> {
    public BorrowService() {
        super(new BaseDao<>(BorrowRecord.class));
    }

    public boolean borrowBook(BorrowRecord borrowRecord) {
        BookService bookService = new BookService();
        Book book = bookService.get(borrowRecord.getBookID());

        if (book.isAvailable()) {
            return super.update(borrowRecord);
        }
        return false;
    }

}
