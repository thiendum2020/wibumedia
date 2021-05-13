package com.example.wibumedia.Models;

public class Post {
    private String id;
    private String content;
    private String image;
    private User user;

    public Post(String id, String content, String image, User user) {
        this.id = id;
        this.content = content;
        this.image = image;
        this.user = user;
    }

    public Post() {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
