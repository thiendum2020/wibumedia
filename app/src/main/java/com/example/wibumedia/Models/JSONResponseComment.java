package com.example.wibumedia.Models;

import java.util.ArrayList;

public class JSONResponseComment {
    private String status;
    private String message;
    private ArrayList<Comment> data;

    public JSONResponseComment(String status, String message, ArrayList<Comment> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public JSONResponseComment() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Comment> getData() {
        return data;
    }

    public void setData(ArrayList<Comment> data) {
        this.data = data;
    }
}
