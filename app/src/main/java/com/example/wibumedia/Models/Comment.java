package com.example.wibumedia.Models;

public class Comment {
    private String id;
    private String content;
    private User user;
    private Post post;

    public Comment(String id, String content, User user_id, Post post_id) {
        this.id = id;
        this.content = content;
        this.user = user_id;
        this.post = post_id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
