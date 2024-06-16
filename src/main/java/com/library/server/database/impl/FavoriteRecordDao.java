package main.java.com.library.server.database.impl;

import main.java.com.library.common.entity.impl.FavoriteRecord;

public class FavoriteRecordDao extends BaseDao<FavoriteRecord> {
    public FavoriteRecordDao(Class<FavoriteRecord> favoriteRecordClass) {
        super(FavoriteRecord.class);
    }

}