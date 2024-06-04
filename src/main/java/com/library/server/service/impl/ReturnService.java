package main.java.com.library.server.service.impl;

import main.java.com.library.common.entity.impl.Book;
import main.java.com.library.common.entity.impl.BorrowRecord;
import main.java.com.library.common.entity.impl.ReturnRecord;
import main.java.com.library.server.database.impl.BaseDao;

import java.time.Instant;
import java.util.Date;

public class ReturnService extends BaseService<ReturnRecord> {

    public ReturnService() {
        super(new BaseDao<>(ReturnRecord.class));
    }

    public String returnBook(ReturnRecord returnRecord) {
        BorrowService borrowService = new BorrowService();
        BorrowRecord borrowRecord = borrowService.get(returnRecord.getborrowID());

        if (borrowRecord == null) {
            return "Failed to find borrow record"; // 未能找到匹配的借阅记录
        }

        BookService bookService = new BookService();
        Book book = bookService.get(borrowRecord.getBookID());

        if (book == null) {
            return "Failed to find book"; // 未能找到匹配的书籍
        }
        // 更新借阅记录的归还时间
        borrowRecord.setReturnDate(Instant.now());
        borrowService.update(borrowRecord);

        // 添加新的还书记录
        returnRecord.setReturnDate(new Date());
        return super.add(returnRecord); // 触发器将自动更新 availableCount 和 status
    }

}