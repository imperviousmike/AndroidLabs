package com.example.androidlabs;

public class Message {

    private String message;
    private MessageType type;
    private int id;

    public Message(String message, MessageType type) {
        setMessage(message);
        setType(type);
    }

    public Message(int id, String message, MessageType type) {
        setId(id);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
