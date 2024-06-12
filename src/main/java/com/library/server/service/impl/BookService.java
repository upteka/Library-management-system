package main.java.com.library.server.service.impl;

import main.java.com.library.common.entity.impl.Book;
import main.java.com.library.server.database.impl.BaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class BookService extends BaseService<Book> {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
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
            Book existingBook = dao.getByField("ISBN", book.getISBN());
            logger.info("Checking if book with id " + book.getId() + " already exists.");

            if (existingBook != null) {
                logger.info("Book with ISBN " + book.getISBN() + " already exists. Updating count.");
                int newCount = Optional.ofNullable(existingBook.getCount()).orElse(0) + Optional.ofNullable(book.getCount()).orElse(0);
                int newAvailableCount = Optional.ofNullable(existingBook.getAvailableCount()).orElse(0) + Optional.ofNullable(book.getCount()).orElse(0);

                existingBook.setCount(newCount);
                existingBook.setAvailableCount(newAvailableCount);
                existingBook.setStatus(newAvailableCount > 0 ? "available" : "unavailable");

                // Update existing book
                String updateResult = update(existingBook);
                if (updateResult.equals("Success")) {
                    logger.info("Existing book updated successfully with increased count.");
                    return "Success: Existing book updated successfully with increased count.";
                } else {
                    logger.error("Failed to update existing book: {}", existingBook);
                    return updateResult;  // Return the error message from update method
                }

            } else {
                // Add new book
                String addResult = super.add(book);
                if (addResult.equals("Success")) {
                    logger.info("New book added successfully.");
                    return "Success: Book added successfully.";
                } else {
                    logger.error("Failed to add new book: {}", book);
                    return addResult;  // Return the error message from super.add method
                }
            }
        } catch (Exception e) {
            logger.error("Exception occurred while adding a book: {}", e.getMessage());
            return "Error: An unexpected error occurred. " + e.getMessage();
        }
    }

    public String update(Book book) {
        try {
            String validationResult = validateBook(book);
            if (!validationResult.equals("Valid")) {
                return validationResult;
            }

            if ("Success".equals(dao.update(book))) {
                logger.info("Book with id " + book.getId() + " updated successfully.");
                return "Success: Book updated successfully.";
            } else {
                logger.error("Failed to update book with id {}", book.getId());
                return "Error: Failed to update book.";
            }
        } catch (Exception e) {
            logger.error("Exception occurred while updating a book: {}", e.getMessage());
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