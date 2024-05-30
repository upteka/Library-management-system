package main.java.com.library.server.database;

import main.java.com.library.server.model.ReturnRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ReturnRecordDAO extends BaseDAO<ReturnRecord> {

    @Override
    protected ReturnRecord mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new ReturnRecord(
                rs.getString("returnID"),
                rs.getString("recordID"),
                rs.getDate("returnDate")
        );
    }

    public boolean addReturnRecord(ReturnRecord returnRecord) {
        String query = "INSERT INTO return_records (returnID, recordID, returnDate) VALUES (?, ?, ?)";
        return executeUpdate(query, returnRecord.getReturnID(), returnRecord.getRecordID(), new java.sql.Date(returnRecord.getReturnDate().getTime()));
    }

    public boolean deleteReturnRecord(String returnID) {
        String query = "DELETE FROM return_records WHERE returnID = ?";
        return executeUpdate(query, returnID);
    }

    public ReturnRecord getReturnRecord(String returnID) {
        String query = "SELECT * FROM return_records WHERE returnID = ?";
        return executeQueryForObject(query, returnID);
    }

    public List<ReturnRecord> getAllReturnRecords() {
        String query = "SELECT * FROM return_records";
        return executeQueryForList(query);
    }
}