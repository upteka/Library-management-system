package main.java.com.library.server.service.impl;

import main.java.com.library.common.entity.impl.FavoriteRecord;
import main.java.com.library.common.network.ResponsePack;
import main.java.com.library.common.network.handlers.ResponseHelper;
import main.java.com.library.server.database.impl.BaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class FavoriteRecordService extends BaseService<FavoriteRecord> {
    private static final Logger logger = LoggerFactory.getLogger(FavoriteRecordService.class);
    private static final String ACTION = "favorite";

    public FavoriteRecordService() {
        super(new BaseDao<>(FavoriteRecord.class));
    }

    public ResponsePack<FavoriteRecord> Favorite(FavoriteRecord favoriteRecord) throws SQLException {
        if (get(favoriteRecord.getId()) != null) {
            return ResponseHelper.packResponse(ACTION, false, "Favorite record already exists", null);
        }
        if (add(favoriteRecord).equals("Success")) {
            return ResponseHelper.packResponse(ACTION, true, "Favorite record added", favoriteRecord);
        }
        return ResponseHelper.packResponse(ACTION, false, "Failed to add favorite record", favoriteRecord);
    }

    public ResponsePack<FavoriteRecord> unFavorite(FavoriteRecord favoriteRecord) throws SQLException {
        if (get(favoriteRecord.getId()) == null) {
            return ResponseHelper.packResponse(ACTION, false, "Favorite record does not exist", null);
        }
        delete(favoriteRecord.getId());
        return ResponseHelper.packResponse(ACTION, true, "Favorite removed successfully", favoriteRecord);
    }

    public ResponsePack<FavoriteRecord> handleSQLException(SQLException e) {
        if ("23505".equals(e.getSQLState())) { // Unique constraint violation
            return ResponseHelper.packResponse(ACTION, false, "Favorite record already exists", null);
        } else {
            logger.error("SQL error occurred: {}", e.getMessage(), e);
            return ResponseHelper.packResponse(ACTION, false, "Internal database error", null);
        }
    }
}
