package com.example.wibumedia.Models;

public class ThongKe {
    private User user;
    private String post_count;

    public ThongKe(com.example.wibumedia.Models.User user, String post_count) {
        this.user = user;
        this.post_count = post_count;
    }

    public ThongKe() {
    }

    public com.example.wibumedia.Models.User getUser() {
        return user;
    }

    public void setUser(com.example.wibumedia.Models.User user) {
        this.user = user;
    }

    public String getPost_count() {
        return post_count;
    }

    public void setPost_count(String post_count) {
        this.post_count = post_count;
    }
}
