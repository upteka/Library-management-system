package main.java.com.library.server.service.impl;

import main.java.com.library.common.entity.impl.Book;
import main.java.com.library.server.database.impl.BaseDao;

import java.util.Optional;
import java.util.logging.Logger;

public class BookService extends BaseService<Book> {

    private static final Logger logger = Logger.getLogger(BookService.class.getName());
    private final BaseDao<Book> dao;

    public BookService() {
        super(new BaseDao<>(Book.class));
        this.dao = new BaseDao<>(Book.class);
    }

    @Override
    public String add(Book book) {
        try {
            String validationResult = validateBook(book);
            if (!validationResult.equals("Valid")) {
                return validationResult;
            }

            // Check if the book already exists
            Book existingBook = dao.get(book.getId());
            logger.info("Checking if book with id " + book.getId() + " already exists.");

            if (existingBook != null) {
                logger.info("Book with id " + book.getId() + " already exists. Updating count.");
                int newCount = Optional.ofNullable(existingBook.getCount()).orElse(0) + Optional.ofNullable(book.getCount()).orElse(0);
                int newAvailableCount = Optional.ofNullable(existingBook.getAvailableCount()).orElse(0) + Optional.ofNullable(book.getCount()).orElse(0);

                existingBook.setCount(newCount);
                existingBook.setAvailableCount(newAvailableCount);
                existingBook.setStatus(newAvailableCount > 0 ? "available" : "unavailable");

                if (update(existingBook).equals("Success")) {
                    return "Success: Existing book updated successfully with increased count.";
                } else {
                    logger.severe("Failed to update existing book: " + existingBook);
                    return "Error: Failed to update existing book.";
                }
            } else {
                if (super.add(book).equals("Success")) {
                    logger.info("New book added successfully.");
                    return "Success: Book added successfully.";
                } else {
                    logger.severe("Failed to add new book: " + book);
                    return "Error: Failed to add book.";
                }
            }
        } catch (Exception e) {
            logger.severe("Exception occurred while adding a book: " + e.getMessage());
            return "Error: An unexpected error occurred. " + e.getMessage();
        }
    }

    private String validateBook(Book book) {
        if (book.getTitle() == null || book.getAuthor() == null || book.getISBN() == null || book.getCount() == null || book.getPublisher() == null) {
            return "Error: Missing required field(s). Title, Author, Count, Publisher, and ISBN must be provided.";
        }
        return "Valid";
    }
}