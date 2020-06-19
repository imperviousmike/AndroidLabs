package com.example.androidlabs;

public class Message {

    private String message;
    private MessageType type;

    public Message(String message, MessageType type){
        setMessage(message);
        setType(type);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}
