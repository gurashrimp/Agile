package com.example.herogame.model;

public class Profile {
    private int id_user;
    private String name_user;
    private String password_user;
    private String email_user;
    private int phone_user;
    private String role_user;

    public Profile(int id_user, String name_user, String password_user, String email_user, int phone_user, String role_user) {
        this.id_user = id_user;
        this.name_user = name_user;
        this.password_user = password_user;
        this.email_user = email_user;
        this.phone_user = phone_user;
        this.role_user = role_user;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getName_user() {
        return name_user;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    public String getPassword_user() {
        return password_user;
    }

    public void setPassword_user(String password_user) {
        this.password_user = password_user;
    }

    public String getEmail_user() {
        return email_user;
    }

    public void setEmail_user(String email_user) {
        this.email_user = email_user;
    }

    public int getPhone_user() {
        return phone_user;
    }

    public void setPhone_user(int phone_user) {
        this.phone_user = phone_user;
    }

    public String getRole_user() {
        return role_user;
    }

    public void setRole_user(String role_user) {
        this.role_user = role_user;
    }
}
