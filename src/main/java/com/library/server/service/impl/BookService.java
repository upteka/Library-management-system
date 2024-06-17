package main.java.com.library.server.service.impl;

import main.java.com.library.common.entity.impl.Book;
import main.java.com.library.server.database.impl.BookDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookService extends BaseService<Book> {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    public BookService() {
        super(new BookDao(Book.class));
    }

    @Override
    public String add(Book book) {
        try {
            String validationResult = validateBook(book);
            if (!validationResult.equals("Valid")) {
                return validationResult;
            }

            // Check if the book already exists
            Book existingBook = super.getByField("ISBN", book.getISBN());
            logger.info("Checking if book with id {} already exists.", book.getId());

            if (existingBook != null) {
                logger.info("Book with ISBN {} already exists. Updating count.", book.getISBN());
                // Update existing book count
                int existingCount = (existingBook.getCount() != null) ? existingBook.getCount() : 0;
                int newBookCount = (book.getCount() != null) ? book.getCount() : 0;
                int newCount = existingCount + newBookCount;
                int existingAvailableCount = (existingBook.getAvailableCount() != null) ? existingBook.getAvailableCount() : 0;
                int newAvailableCount = existingAvailableCount + newBookCount;

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
                Book newBook = new Book(book.getTitle(), book.getAuthor(), book.getISBN(), book.getCount(), book.getIntroduction(), book.getPublisher());
                newBook.setStatus(book.getCount() > 0 ? "available" : "unavailable");
                String addResult = super.add(newBook);
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
                logger.info("Book with id {} updated successfully.", book.getId());
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

    public String delete(String id) {

        Book existingBook = dao.get(id);
        if (existingBook == null) {
            return "Error: Book with id " + existingBook.getId() + " does not exist.";
        }
        if (existingBook.getAvailableCount() < existingBook.getCount()) {
            return "Error: The book has been borrowed and cannot be deleted.";
        }
        existingBook.setStatus("deleted");
        if ("Success".equals(dao.update(existingBook))) {
            logger.info("Book with id {} deleted successfully.", existingBook.getId());
            return "Success: Book deleted successfully.";
        } else {
            logger.error("Failed to delete book with id {}", existingBook.getId());
            return "Error: Failed to delete book.";
        }
    }
}