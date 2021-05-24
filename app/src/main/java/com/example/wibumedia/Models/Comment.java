package com.example.wibumedia.Models;

public class Comment {
    private String id;
    private String content;
    private User user_id;

    public Comment(String id, String content, User user_id) {
        this.id = id;
        this.content = content;
        this.user_id = user_id;
    }

    public Comment() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }
}
