package main.java.com.library.server.database;

import main.java.com.library.server.model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BookDAO extends BaseDAO<Book> {

    @Override
    protected Book mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new Book(
                rs.getString("bookID"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("ISBN"),
                rs.getString("status")
        );
    }

    public boolean addBook(Book book) {
        String query = "INSERT INTO books (bookID, title, author, ISBN, status) VALUES (?, ?, ?, ?, ?)";
        return executeUpdate(query, book.getBookID(), book.getTitle(), book.getAuthor(), book.getISBN(), book.getStatus());
    }

    public boolean deleteBook(String bookID) {
        String query = "DELETE FROM books WHERE bookID = ?";
        return executeUpdate(query, bookID);
    }

    public Book getBook(String identifier) {
        String query = "SELECT * FROM books WHERE bookID = ? OR title = ?";
        return executeQueryForObject(query, identifier, identifier);
    }

    public List<Book> getAllBooks() {
        String query = "SELECT * FROM books";
        return executeQueryForList(query);
    }

    public boolean updateBook(Book book) {
        String query = "UPDATE books SET title = ?, author = ?, ISBN = ?, status = ? WHERE bookID = ?";
        return executeUpdate(query, book.getTitle(), book.getAuthor(), book.getISBN(), book.getStatus(), book.getBookID());
    }
}