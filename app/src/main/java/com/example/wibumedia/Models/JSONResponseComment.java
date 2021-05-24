package com.example.wibumedia.Models;

public class JSONResponseComment {
    private String status;
    private Comment[] data;

    public JSONResponseComment() {
    }

    public JSONResponseComment(String status, Comment[] data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Comment[] getData() {
        return data;
    }

    public void setData(Comment[] data) {
        this.data = data;
    }
}
