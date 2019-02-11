package com.example.androidlabs;

public class ChatMessages {
    public String message;
    public boolean isSend;

    public ChatMessages(String message, boolean isSend) {
        this.message = message;
        this.isSend = isSend;
    }

    public ChatMessages() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }
}
