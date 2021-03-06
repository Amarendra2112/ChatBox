package com.example.chatbox.model;

public class ChatList {
    private String userId, userName,description,date,urlProfile,notification;

    public ChatList()
    {

    }

    public ChatList(String userId,String userName,String description,String date, String urlProfile,String notification)
    {
        this.userId = userId;
        this.userName = userName;
        this.description = description;
        this.date = date;
        this.urlProfile = urlProfile;
        this.notification = notification;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUrlProfile() {
        return urlProfile;
    }

    public void setUrlProfile(String urlProfile) {
        this.urlProfile = urlProfile;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }
}
