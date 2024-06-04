package main.java.com.library.server.service.impl;

import main.java.com.library.common.entity.impl.Book;
import main.java.com.library.common.entity.impl.BorrowRecord;
import main.java.com.library.server.database.impl.BaseDao;

import java.time.Duration;
import java.time.Instant;

/**
 * @author upteka
 */
public class BorrowService extends BaseService<BorrowRecord> {
    private static final int MAX_BORROW_DAYS = 30;

    public BorrowService() {
        super(new BaseDao<>(BorrowRecord.class));
    }

    public String borrowBook(BorrowRecord borrowRecord) {
        BookService bookService = new BookService();


        Instant now = Instant.now();
        borrowRecord.setBorrowDate(now);

        Book book = bookService.get(borrowRecord.getBookID());
        if (!isValidReturnDate(borrowRecord.getReturnDate(), now)) {
            return "Failed to borrow book. Invalid return date.";
        } else if (book.isAvailable() || book.getCount() != 0) {
            return super.update(borrowRecord);
        } else {
            return "Failed to borrow book. Book is not available.";
        }

    }

    private boolean isValidReturnDate(Instant returnDate, Instant borrowDate) {
        if (returnDate == null) {
            return false;
        }
        Duration duration = Duration.between(borrowDate, returnDate);
        return !returnDate.isBefore(borrowDate) && duration.toDays() <= MAX_BORROW_DAYS;
    }

}

