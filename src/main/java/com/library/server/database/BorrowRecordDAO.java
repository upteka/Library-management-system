package main.java.com.library.server.database;

import main.java.com.library.server.model.BorrowRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BorrowRecordDAO extends BaseDAO<BorrowRecord> {

    @Override
    protected BorrowRecord mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new BorrowRecord(
                rs.getString("recordID"),
                rs.getString("userID"),
                rs.getString("bookID"),
                rs.getDate("borrowDate"),
                rs.getDate("returnDate")
        );
    }

    public boolean addBorrowRecord(BorrowRecord record) {
        String query = "INSERT INTO borrow_records (recordID, userID, bookID, borrowDate, returnDate) VALUES (?, ?, ?, ?, ?)";
        return executeUpdate(query, record.getRecordID(), record.getUserID(), record.getBookID(), new java.sql.Date(record.getBorrowDate().getTime()), record.getReturnDate() != null ? new java.sql.Date(record.getReturnDate().getTime()) : null);
    }

    public boolean deleteBorrowRecord(String recordID) {
        String query = "DELETE FROM borrow_records WHERE recordID = ?";
        return executeUpdate(query, recordID);
    }

    public boolean returnBook(String recordID) {
        String query = "UPDATE borrow_records SET returnDate = ? WHERE recordID = ?";
        return executeUpdate(query, new java.sql.Date(new java.util.Date().getTime()), recordID);
    }

    public BorrowRecord getBorrowRecord(String recordID) {
        String query = "SELECT * FROM borrow_records WHERE recordID = ?";
        return executeQueryForObject(query, recordID);
    }

    public List<BorrowRecord> getAllBorrowRecords() {
        String query = "SELECT * FROM borrow_records";
        return executeQueryForList(query);
    }
}