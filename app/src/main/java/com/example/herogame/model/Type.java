package com.example.herogame.model;

public class Type {
    private int id_type;
    private String name_type;

    public Type(int id_type, String name_type) {
        this.id_type = id_type;
        this.name_type = name_type;
    }

    public int getId_type() {
        return id_type;
    }

    public void setId_type(int id_type) {
        this.id_type = id_type;
    }

    public String getName_type() {
        return name_type;
    }

    public void setName_type(String name_type) {
        this.name_type = name_type;
    }
}
