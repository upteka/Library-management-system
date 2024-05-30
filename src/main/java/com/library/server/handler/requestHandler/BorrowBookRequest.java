package main.java.com.library.server.handler.requestHandler;

import main.java.com.library.server.model.BorrowRecord;

import java.io.Serial;
import java.io.Serializable;

public class BorrowBookRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private BorrowRecord borrowRecord;

    // 默认构造函数
    public BorrowBookRequest() {
    }

    // 带参数的构造函数
    public BorrowBookRequest(BorrowRecord borrowRecord) {
        this.borrowRecord = borrowRecord;
    }

    // Getter 和 Setter 方法
    public BorrowRecord getBorrowRecord() {
        return borrowRecord;
    }

    public void setBorrowRecord(BorrowRecord borrowRecord) {
        this.borrowRecord = borrowRecord;
    }

    @Override
    public String toString() {
        return "BorrowBookRequest{" +
                "borrowRecord=" + borrowRecord +
                '}';
    }
}