package com.example.chatbox.model.Chat;

public class Chats {
    private String dateTime;
    private String textMessage;
    private String type;
    private String sender;
    private String receiver;

    public Chats() {
    }

    public Chats(String dateTime, String textMessage, String type, String sender, String receiver) {
        this.dateTime = dateTime;
        this.textMessage = textMessage;
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public String getType() {
        return type;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }
}
