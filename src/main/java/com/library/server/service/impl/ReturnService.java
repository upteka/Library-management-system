package main.java.com.library.server.service.impl;

import main.java.com.library.common.entity.impl.Book;
import main.java.com.library.common.entity.impl.BorrowRecord;
import main.java.com.library.common.entity.impl.ReturnRecord;
import main.java.com.library.server.database.impl.BaseDao;

import java.sql.SQLException;
import java.time.Instant;

public class ReturnService extends BaseService<ReturnRecord> {


    public ReturnService() {
        super(new BaseDao<>(ReturnRecord.class));
    }

    public String returnBook(ReturnRecord returnRecord) throws SQLException {
        BorrowService borrowService = new BorrowService();
        BorrowRecord borrowRecord = borrowService.get(returnRecord.getBorrowID());

        if (borrowRecord == null) {
            return "Failed to find borrow record";
        }

        BookService bookService = new BookService();
        Book book = bookService.get(borrowRecord.getBookID());

        if (book == null) {
            return "Failed to find book";
        }

        // 更新借阅记录的归还时间
        borrowRecord.setReturnDate(Instant.now());
        borrowService.update(borrowRecord);

        // 服务端设置还书记录的归还时间
        returnRecord.setReturnDate(Instant.now());
        String addResult = super.add(returnRecord);

        if ("Success".equals(addResult)) {
            return "Book returned successfully";
        } else {
            return "Failed to add return record";
        }
    }
}