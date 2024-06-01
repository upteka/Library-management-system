package main.java.com.library.server.database.impl;

import main.java.com.library.server.entity.impl.Book;

public class BookDao extends BaseDao<Book> {
    public BookDao() {
        super(Book.class);
    }
}
