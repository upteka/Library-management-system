package main.java.com.library.server.service.impl;

import main.java.com.library.common.entity.impl.Book;
import main.java.com.library.server.database.impl.BaseDao;

import java.util.Optional;
import java.util.logging.Logger;

public class BookService extends BaseService<Book> {

    private static final Logger logger = Logger.getLogger(BookService.class.getName());

    public BookService() {
        super(new BaseDao<>(Book.class));
    }

    @Override
    public String add(Book book) {
        try {
            // Validate required fields
            if (book.getTitle() == null || book.getAuthor() == null || book.getISBN() == null || book.getCount() == null || book.getPublisher() == null) {
                return "Error: Missing required field(s). Title, Author, Count, Publisher, and ISBN must be provided.";
            }
            // Check if the book already exists by ISBN
            Book existingBook = super.get(book.getId());
            if (existingBook != null) {
                // If it exists, increase the count
                existingBook.setCount(Optional.ofNullable(existingBook.getCount()).orElse(0) + Optional.ofNullable(book.getCount()).orElse(0));
                if (update(existingBook).contains("success")) {
                    return "Existing book updated successfully with increased count.";
                } else {
                    logger.severe("Failed to update existing book: " + existingBook);
                    return "Error: Failed to update existing book.";
                }
            } else {
                // If it doesn't exist, add the new book
                if (add(book).equals("success")) {
                    return "Book added successfully.";
                } else {
                    logger.severe("Failed to add new book: " + book);
                    return "Error: Failed to add book.";
                }
            }
        } catch (Exception e) {
            logger.severe("Exception occurred while adding a book: " + e.getMessage());
            return "Error: An unexpected error occurred." + e.getMessage();
        }
    }




}