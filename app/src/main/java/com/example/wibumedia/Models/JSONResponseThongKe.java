package com.example.wibumedia.Models;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class JSONResponseThongKe {
    private String status;
    private String message;
    private ArrayList<ThongKe> data;

    public JSONResponseThongKe(String status, String message, ArrayList<ThongKe> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public JSONResponseThongKe() {
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

    public ArrayList<ThongKe> getData() {
        return data;
    }

    public void setData(ArrayList<ThongKe> data) {
        this.data = data;
    }
}
