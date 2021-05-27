package com.example.wibumedia.Models;

public class Post {
    private String id;
    private String content;
    private String image;
    private String comment_count;
    private String like_count;
    private User user;

    public Post(String id, String content, String image, String comment_count, String like_count, User user) {
        this.id = id;
        this.content = content;
        this.image = image;
        this.comment_count = comment_count;
        this.like_count = like_count;
        this.user = user;
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

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public String getLike_count() {
        return like_count;
    }

    public void setLike_count(String like_count) {
        this.like_count = like_count;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
