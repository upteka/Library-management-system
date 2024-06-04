package main.java.com.library.server.requests.impl;

import main.java.com.library.common.entity.Entity;
import main.java.com.library.common.entity.impl.FavoriteRecord;
import main.java.com.library.common.network.RequestPack;
import main.java.com.library.common.network.ResponsePack;
import main.java.com.library.common.network.handlers.RequestHandler;
import main.java.com.library.common.network.handlers.ResponseHandler;
import main.java.com.library.server.database.impl.BaseDao;
import main.java.com.library.server.requests.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class Favorite implements Request<FavoriteRecord> {
    private static final Logger logger = LoggerFactory.getLogger(Favorite.class);
    private final String action = "favorite";
    private final BaseDao<FavoriteRecord> favoriteDao = new BaseDao<>(FavoriteRecord.class);

    @Override
    public String getAction() {
        return action;
    }

    @Override
    public ResponsePack<FavoriteRecord> handle(RequestPack<? extends Entity> requestPack) {
        try {
            Entity entity = RequestHandler.unPackRequest(requestPack);
            if (!(entity instanceof FavoriteRecord favoriteRecord)) {
                return ResponseHandler.packResponse(action, false, "Invalid request type, expected FavoriteRecord", null);
            }

            // 尝试添加收藏记录
            favoriteDao.add(favoriteRecord);

            return ResponseHandler.packResponse(action, true, "Favorite added successfully", favoriteRecord);
        } catch (SQLException e) {
            // 捕获唯一约束异常
            if (e.getSQLState().equals("23505")) {
                return ResponseHandler.packResponse(action, false, "Favorite record already exists", null);
            } else {
                logger.error("SQL error occurred: {}", e.getMessage(), e);
                return ResponseHandler.packResponse(action, false, "Internal database error", null);
            }
        } catch (Exception e) {
            logger.error("Failed to add favorite for request: {}", requestPack, e);
            return ResponseHandler.packResponse(action, false, "Internal server error", null);
        }
    }
}
