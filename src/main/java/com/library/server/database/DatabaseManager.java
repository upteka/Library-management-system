package main.java.com.library.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/library";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void createTables() {
        try (Connection conn = getConnection()) {

            String createUsersTable = "CREATE TABLE IF NOT EXISTS users ("
                    + "userID VARCHAR(255) PRIMARY KEY,"
                    + "username VARCHAR(255) UNIQUE NOT NULL,"
                    + "password VARCHAR(255) NOT NULL,"
                    + "role VARCHAR(50) NOT NULL"
                    + ")";
            conn.createStatement().execute(createUsersTable);

            String createCategoriesTable = "CREATE TABLE IF NOT EXISTS categories ("
                    + "categoryID VARCHAR(255) PRIMARY KEY,"
                    + "categoryName VARCHAR(255) UNIQUE NOT NULL"
                    + ")";
            conn.createStatement().execute(createCategoriesTable);

            String createBooksTable = "CREATE TABLE IF NOT EXISTS books ("
                    + "bookID VARCHAR(255) PRIMARY KEY,"
                    + "title VARCHAR(255) NOT NULL,"
                    + "author VARCHAR(255) NOT NULL,"
                    + "ISBN VARCHAR(255) UNIQUE NOT NULL,"
                    + "categoryID VARCHAR(255),"
                    + "status VARCHAR(50) NOT NULL,"
                    + "FOREIGN KEY (categoryID) REFERENCES categories(categoryID)"
                    + ")";
            conn.createStatement().execute(createBooksTable);

            String createBorrowRecordsTable = "CREATE TABLE IF NOT EXISTS borrow_records ("
                    + "recordID VARCHAR(255) PRIMARY KEY,"
                    + "userID VARCHAR(255) NOT NULL,"
                    + "bookID VARCHAR(255) NOT NULL,"
                    + "borrowDate DATE NOT NULL,"
                    + "returnDate DATE,"
                    + "FOREIGN KEY (userID) REFERENCES users(userID),"
                    + "FOREIGN KEY (bookID) REFERENCES books(bookID)"
                    + ")";
            conn.createStatement().execute(createBorrowRecordsTable);

            String createLogsTable = "CREATE TABLE IF NOT EXISTS logs ("
                    + "logID VARCHAR(255) PRIMARY KEY,"
                    + "userID VARCHAR(255),"
                    + "action VARCHAR(255) NOT NULL,"
                    + "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                    + "FOREIGN KEY (userID) REFERENCES users(userID)"
                    + ")";
            conn.createStatement().execute(createLogsTable);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}