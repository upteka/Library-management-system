package main.java.com.library.server.service;

import main.java.com.library.server.database.BookDAO;
import main.java.com.library.server.model.Book;

import java.util.List;

public class BookService {
    private BookDAO bookDAO;

    public BookService() {
        bookDAO = new BookDAO();
    }

    public boolean addBook(Book book) {
        return bookDAO.addBook(book);
    }

    public boolean deleteBook(String bookId) {
        return bookDAO.deleteBook(bookId);
    }

    public boolean updateBook(Book book) {
        Book existingBook = bookDAO.getBook(book.getBookID());
        if (existingBook != null) {

            if (book.getTitle() != null) existingBook.setTitle(book.getTitle());
            if (book.getAuthor() != null) existingBook.setAuthor(book.getAuthor());
            if (book.getISBN() != null) existingBook.setISBN(book.getISBN());
            if (book.getStatus() != null) existingBook.setStatus(book.getStatus());

            return bookDAO.updateBook(existingBook);
        }
        return false;
    }

    public Book getBook(String identifier) {
        return bookDAO.getBook(identifier);
    }

    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }
}