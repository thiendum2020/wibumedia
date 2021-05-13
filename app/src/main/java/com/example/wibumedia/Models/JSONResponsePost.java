package com.example.wibumedia.Models;

public class JSONResponsePost {
    private String status;
    private Post []data;

    public JSONResponsePost(String status, Post []data) {
        this.status = status;
        this.data = data;
    }

    public JSONResponsePost() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Post[] getData() {
        return data;
    }

    public void setData(Post[] data) {
        this.data = data;
    }
}
