package main.java.com.library.common.entity.impl;

import com.github.f4b6a3.ulid.UlidCreator;
import main.java.com.library.common.entity.Entity;

public class FavoriteRecord implements Entity {
    private final String favoriteID;
    private final String userID;
    private final String bookID;

    // 无参数构造器
    public FavoriteRecord() {
        this.favoriteID = UlidCreator.getUlid().toString();
        this.userID = null;
        this.bookID = null;
    }

    public FavoriteRecord(String userID, String bookID) {
        this.favoriteID = UlidCreator.getUlid().toString();
        this.userID = userID;
        this.bookID = bookID;
    }

    public String getUserID() {
        return userID;
    }

    public String getBookID() {
        return bookID;
    }

    @Override
    public String getId() {
        return favoriteID;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "favoriteID='" + favoriteID + '\'' +
                ", userID='" + userID + '\'' +
                ", bookID='" + bookID + '\'' +
                '}';
    }
}