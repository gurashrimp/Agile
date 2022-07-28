package com.example.herogame.model;

public class Receipt {
    private int id_receipt;
    private int total_price;
    private int id_product;
    private String status;

    public Receipt(int id_receipt, int total_price, int id_product, String status) {
        this.id_receipt = id_receipt;
        this.total_price = total_price;
        this.id_product = id_product;
        this.status = status;
    }

    public int getId_receipt() {
        return id_receipt;
    }

    public void setId_receipt(int id_receipt) {
        this.id_receipt = id_receipt;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
