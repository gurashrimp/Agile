package com.example.herogame.model;

import java.util.HashMap;
import java.util.Map;

public class Products {
    private int id_product;
    private String name_product;
    private int price;
    private  String type;


    public Products() {
    }

    public Products(int id_product, String name_product, int price, String type) {
        this.id_product = id_product;
        this.name_product = name_product;
        this.price = price;
        this.type = type;

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
        result.put("type",type);

        return  result;
    }
}
