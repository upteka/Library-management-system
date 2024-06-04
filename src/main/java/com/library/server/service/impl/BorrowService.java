package main.java.com.library.server.service.impl;

import main.java.com.library.common.entity.impl.Book;
import main.java.com.library.common.entity.impl.BorrowRecord;
import main.java.com.library.server.database.impl.BaseDao;

/**
 * @author upteka
 */
public class BorrowService extends BaseService<BorrowRecord> {
    public BorrowService() {
        super(new BaseDao<>(BorrowRecord.class));
    }

    public boolean borrowBook(BorrowRecord borrowRecord) {
        BookService bookService = new BookService();
        Book book = bookService.get(borrowRecord.getBookID());

        if (book.isAvailable() || book.getCount() != 0) {
            return super.update(borrowRecord).equals("success");
        }
        return false;
    }
}

