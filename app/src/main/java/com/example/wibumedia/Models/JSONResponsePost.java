package com.example.wibumedia.Models;

import java.util.ArrayList;

public class JSONResponsePost {
    private String status;
    private String message;
    private ArrayList<Post> data;

    public JSONResponsePost(String status, String message, ArrayList<Post> data) {
        this.status = status;
        this.message = message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Post> getData() {
        return data;
    }

    public void setData(ArrayList<Post> data) {
        this.data = data;
    }
}
