package main.java.com.library.server.database.impl;

import main.java.com.library.common.entity.impl.Book;

public class BookDao extends BaseDao<Book> {
    public BookDao(Class<Book> bookClass) {
        super(Book.class);
    }

}
