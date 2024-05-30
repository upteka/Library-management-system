package main.java.com.library.server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/library";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    private static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS users ("
            + "userID VARCHAR(255) PRIMARY KEY,"
            + "username VARCHAR(255) UNIQUE NOT NULL,"
            + "password VARCHAR(255) NOT NULL,"
            + "role VARCHAR(50) NOT NULL"
            + ")";

    private static final String CREATE_CATEGORIES_TABLE = "CREATE TABLE IF NOT EXISTS categories ("
            + "categoryID VARCHAR(255) PRIMARY KEY,"
            + "categoryName VARCHAR(255) UNIQUE NOT NULL"
            + ")";

    private static final String CREATE_BOOKS_TABLE = "CREATE TABLE IF NOT EXISTS books ("
            + "bookID VARCHAR(255) PRIMARY KEY,"
            + "title VARCHAR(255) NOT NULL,"
            + "author VARCHAR(255) NOT NULL,"
            + "ISBN VARCHAR(255) UNIQUE NOT NULL,"
            + "categoryID VARCHAR(255),"
            + "status VARCHAR(50) NOT NULL,"
            + "FOREIGN KEY (categoryID) REFERENCES categories(categoryID)"
            + ")";

    private static final String CREATE_BORROW_RECORDS_TABLE = "CREATE TABLE IF NOT EXISTS borrow_records ("
            + "recordID VARCHAR(255) PRIMARY KEY,"
            + "userID VARCHAR(255) NOT NULL,"
            + "bookID VARCHAR(255) NOT NULL,"
            + "borrowDate DATE NOT NULL,"
            + "returnDate DATE,"
            + "FOREIGN KEY (userID) REFERENCES users(userID),"
            + "FOREIGN KEY (bookID) REFERENCES books(bookID)"
            + ")";

    private static final String CREATE_ADMINS_TABLE = "CREATE TABLE IF NOT EXISTS admins ("
            + "adminID VARCHAR(255) PRIMARY KEY,"
            + "username VARCHAR(255) UNIQUE NOT NULL,"
            + "password VARCHAR(255) NOT NULL,"
            + ")";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void createTables() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(CREATE_USERS_TABLE);
            stmt.execute(CREATE_CATEGORIES_TABLE);
            stmt.execute(CREATE_BOOKS_TABLE);
            stmt.execute(CREATE_BORROW_RECORDS_TABLE);
            stmt.execute(CREATE_ADMINS_TABLE);

            System.out.println("Tables created successfully");

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error creating tables: " + e.getMessage());
        }
    }
}