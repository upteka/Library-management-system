package main.java.com.library.server.service;

import main.java.com.library.server.database.impl.BaseDAO;
import main.java.com.library.server.model.Book;

import java.util.List;

public class BookService {
    private final BaseDAO<Book> bookDAO = new BaseDAO<>(Book.class);

    public boolean add(Book book) {
        return bookDAO.add(book);
    }

    public boolean delete(String bookID) {
        return bookDAO.delete(bookID);
    }

    public Book get(String bookID) {
        return bookDAO.get(bookID);
    }

    public List<Book> getAll() {
        return bookDAO.getAll();
    }

    public boolean update(Book book) {
        return bookDAO.update(book);
    }

}