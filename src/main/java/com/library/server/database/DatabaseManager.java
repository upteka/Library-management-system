package main.java.com.library.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseManager {
    private static final Logger LOGGER = Logger.getLogger(DatabaseManager.class.getName());
    private static final String URL = "jdbc:mysql://localhost:3306/library";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    private static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS users ("
            + "userID VARCHAR(255) PRIMARY KEY,"
            + "email VARCHAR(255) NOT NULL,"
            + "username VARCHAR(255) UNIQUE NOT NULL,"
            + "password VARCHAR(255) NOT NULL,"
            + "role VARCHAR(50) NOT NULL"
            + ")";

    private static final String CREATE_BOOKS_TABLE = "CREATE TABLE IF NOT EXISTS books ("
            + "bookID VARCHAR(255) PRIMARY KEY,"
            + "title VARCHAR(255) NOT NULL,"
            + "author VARCHAR(255) NOT NULL,"
            + "ISBN VARCHAR(255) UNIQUE NOT NULL,"
            + "status VARCHAR(50) NOT NULL,"
            + "count INTEGER NOT NULL,"
            + "availableCount INTEGER NOT NULL,"
            + "publisher VARCHAR(255) NOT NULL,"
            + "introduction VARCHAR(255) NOT NULL"
            + ")";

    private static final String CREATE_BORROW_RECORDS_TABLE = "CREATE TABLE IF NOT EXISTS borrow_records ("
            + "borrowID VARCHAR(255) PRIMARY KEY,"
            + "userID VARCHAR(255) NOT NULL,"
            + "bookID VARCHAR(255) NOT NULL,"
            + "borrowDate TIMESTAMP NOT NULL,"
            + "returnDate TIMESTAMP,"
            + "FOREIGN KEY (userID) REFERENCES users(userID),"
            + "FOREIGN KEY (bookID) REFERENCES books(bookID)"
            + ")";

    private static final String CREATE_RETURN_RECORDS_TABLE = "CREATE TABLE IF NOT EXISTS return_records ("
            + "returnID VARCHAR(255) PRIMARY KEY,"
            + "borrowID VARCHAR(255) NOT NULL,"
            + "returnDate TIMESTAMP NOT NULL,"
            + "FOREIGN KEY (borrowID) REFERENCES borrow_records(borrowID)"
            + ")";

    private static final String CREATE_FAVORITES_TABLE = "CREATE TABLE IF NOT EXISTS favorites ("
            + "favoriteID VARCHAR(255) PRIMARY KEY,"
            + "userID VARCHAR(255) NOT NULL,"
            + "bookID VARCHAR(255) NOT NULL,"
            + "FOREIGN KEY (userID) REFERENCES users(userID),"
            + "FOREIGN KEY (bookID) REFERENCES books(bookID)"
            + ")";


    private static final String CREATE_BORROW_TRIGGER = "CREATE TRIGGER after_borrow_insert "
            + "AFTER INSERT ON borrow_records "
            + "FOR EACH ROW "
            + "BEGIN "
            + "    UPDATE books SET availableCount = availableCount - 1 "
            + "    WHERE bookID = NEW.bookID;"
            + "    IF (SELECT availableCount FROM books WHERE bookID = NEW.bookID) <= 0 THEN "
            + "        UPDATE books SET status = 'unavailable' "
            + "        WHERE bookID = NEW.bookID; "
            + "    END IF; "
            + "END;";


    private static final String CREATE_RETURN_TRIGGER = "CREATE TRIGGER after_return_insert "
            + "AFTER INSERT ON return_records "
            + "FOR EACH ROW "
            + "BEGIN "
            + "    UPDATE books SET availableCount = availableCount + 1 "
            + "    WHERE bookID = (SELECT bookID FROM borrow_records WHERE borrowID = NEW.borrowID);"
            + "    IF (SELECT availableCount FROM books WHERE bookID = (SELECT bookID FROM borrow_records WHERE borrowID = NEW.borrowID)) > 0 THEN "
            + "        UPDATE books SET status = 'available' "
            + "        WHERE bookID = (SELECT bookID FROM borrow_records WHERE borrowID = NEW.borrowID); "
            + "    END IF; "
            + "END;";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void createTables() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(CREATE_USERS_TABLE);
            stmt.execute(CREATE_BOOKS_TABLE);
            stmt.execute(CREATE_BORROW_RECORDS_TABLE);
            stmt.execute(CREATE_RETURN_RECORDS_TABLE);
            stmt.execute(CREATE_FAVORITES_TABLE);

            // 创建触发器
            stmt.execute(CREATE_BORROW_TRIGGER);
            stmt.execute(CREATE_RETURN_TRIGGER);

            System.out.println("Tables and triggers created successfully");

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database error", e);
            System.err.println("Error creating tables/triggers: " + e.getMessage());
        }
    }
}