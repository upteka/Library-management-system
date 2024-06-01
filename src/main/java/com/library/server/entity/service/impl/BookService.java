package main.java.com.library.server.entity.service.impl;

import main.java.com.library.server.database.impl.BaseDao;
import main.java.com.library.server.entity.impl.Book;

public class BookService extends BaseService<Book> {
    public BookService() {
        super(new BaseDao<>(Book.class));
    }
}