package com.example.herogame.model;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private int id_product;
    private String name_product;
    private int price,total_price,quantity;
    private  String type;

    public Cart() {
    }

    public Cart(int id_product, String name_product, int price, int total_price,int quantity, String type) {
        this.id_product = id_product;
        this.name_product = name_product;
        this.price = price;
        this.total_price = total_price;
        this.quantity=quantity;
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public String getName_product() {
        return name_product;
    }

    public void setName_product(String name_product) {
        this.name_product = name_product;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public Map<String, Object> toMap(){
        HashMap<String,Object> result=new HashMap<>();
        result.put("id_product",id_product);
        result.put("name_product",name_product);
        result.put("price",price);
        result.put("total_price",total_price);
        result.put("quantity",quantity);
        result.put("type",type);

        return  result;
    }
}
