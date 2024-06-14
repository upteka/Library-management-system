package main.java.com.library.server.requests.impl;

import main.java.com.library.common.entity.Entity;
import main.java.com.library.common.entity.impl.FavoriteRecord;
import main.java.com.library.common.network.JwtUtil;
import main.java.com.library.common.network.RequestPack;
import main.java.com.library.common.network.ResponsePack;
import main.java.com.library.common.network.handlers.RequestHelper;
import main.java.com.library.common.network.handlers.ResponseHelper;
import main.java.com.library.server.requests.Request;
import main.java.com.library.server.service.impl.FavoriteRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class Favorite implements Request<FavoriteRecord> {
    private static final Logger logger = LoggerFactory.getLogger(Favorite.class);
    private static final String ACTION = "favorite";

    private final FavoriteRecordService favoriteRecordService;

    public Favorite(FavoriteRecordService favoriteRecordService) {
        this.favoriteRecordService = favoriteRecordService;
    }

    @Override
    public String getAction() {
        return ACTION;
    }

    @Override
    public ResponsePack<FavoriteRecord> handle(RequestPack<? extends Entity> requestPack) {
        try {
            Entity entity = RequestHelper.unPackRequest(requestPack);

            if (!(entity instanceof FavoriteRecord favoriteRecord)) {
                return ResponseHelper.packResponse(ACTION, false, "Invalid request type, expected FavoriteRecord", null);
            }
            favoriteRecord.setUserID(JwtUtil.extractUserId(requestPack.getJwtToken()));

            if (requestPack.getParams().size() != 1) {
                return ResponseHelper.packResponse(ACTION, false, "Invalid request parameters, expected 1 parameter", null);
            }

            String param = requestPack.getParams().getFirst();
            return switch (param) {
                case "favorite" -> favoriteRecordService.Favorite(favoriteRecord);
                case "unfavorite" -> favoriteRecordService.unFavorite(favoriteRecord);
                default ->
                        ResponseHelper.packResponse(ACTION, false, "Invalid request parameter, expected 'favorite' or 'unfavorite'", null);
            };
        } catch (SQLException e) {
            return handleSQLException(e);
        } catch (Exception e) {
            logger.error("Failed to process request: {}", requestPack, e);
            return ResponseHelper.packResponse(ACTION, false, "Internal server error", null);
        }
    }

    private ResponsePack<FavoriteRecord> handleSQLException(SQLException e) {
        return favoriteRecordService.handleSQLException(e);
    }
}