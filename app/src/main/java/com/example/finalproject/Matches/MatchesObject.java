package com.example.finalproject.Matches;

public class MatchesObject {
    private String userId, userName, imageUrl;
    public MatchesObject(String userId, String userName, String imageUrl){

        this.userId = userId;
        this.userName = userName;
        this.imageUrl = imageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
