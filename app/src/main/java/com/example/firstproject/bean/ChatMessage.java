package com.example.firstproject.bean;

public class ChatMessage {

    public enum Type { USER, AI }

    private String content;
    private Type type;

    public ChatMessage(String content, Type type) {
        this.content = content;
        this.type = type;
    }



    public String getContent() { return content; }
    public Type getType() { return type; }

    public void setType(Type type) {
        this.type = type;
    }

    public void setContent(String content) {
        this.content = content;
    }
}