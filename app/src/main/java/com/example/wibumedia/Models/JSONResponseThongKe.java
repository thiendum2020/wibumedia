package com.example.wibumedia.Models;

public class JSONResponseThongKe {
    private String status;
    private ThongKe[] data;

    public JSONResponseThongKe(String status, ThongKe[] data) {
        this.status = status;
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

    public ThongKe[] getData() {
        return data;
    }

    public void setData(ThongKe[] data) {
        this.data = data;
    }
}
