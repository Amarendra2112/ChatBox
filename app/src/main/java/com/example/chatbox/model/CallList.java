package com.example.chatbox.model;

public class CallList {

    private String username;
    private String time;
    private String date;
    private String urlProfile;
    private String userId;
    private String callType;

    public CallList()
    {

    }

    public CallList(String userId,String userName,String time,String date, String urlProfile, String callType)
    {
        this.userId = userId;
        this.username = userName;
        this.time = time;
        this.date =date;
        this.urlProfile =urlProfile;
        this.callType = callType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }
}
