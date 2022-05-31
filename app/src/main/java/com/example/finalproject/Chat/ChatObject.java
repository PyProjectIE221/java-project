package com.example.finalproject.Chat;

public class ChatObject {
    private String message;
    private Boolean currentUser;
    public ChatObject(String message, Boolean currentUser){
        this.message = message;
        this.currentUser = currentUser;
    }

    public Boolean getCurrentUser() {
        return this.currentUser;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCurrentUser(Boolean currentUser) {
        this.currentUser = currentUser;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
