package com.example.chatbox.model;

public class User {
    private String userId;
    private String userName;
    private String userPhone;
    private String imageProfile;
    private String email;
    private String dateOfBirth;
    private String gender;
    private String status;
    private String bio;

    public User()
    {

    }

    public User(String userId, String userName, String userPhone, String imageProfile, String email, String dateOfBirth, String gender, String status, String bio) {
        this.userId = userId;
        this.userName = userName;
        this.userPhone = userPhone;
        this.imageProfile = imageProfile;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.status = status;
        this.bio = bio;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getImageProfile() {
        return imageProfile;
    }

    public String getEmail() {
        return email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getStatus() {
        return status;
    }

    public String getBio() {
        return bio;
    }
}
