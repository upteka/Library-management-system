package main.java.com.library.server.database.impl;

import main.java.com.library.server.model.Book;

public class BookDAO extends BaseDAO<Book> {
    public BookDAO() {
        super(Book.class);
    }
}
