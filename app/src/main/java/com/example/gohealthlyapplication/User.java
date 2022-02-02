package com.example.gohealthlyapplication;

public class User {
    private String email ;
    private String password ;
    private String username;
    private String phone;
    private String admin;

    public User()
    {
    }
    public User(String name, String Email ,String Password , String Phone){
        this.username =name;
        this.email=Email;
        this.password=Password;
        this.phone=Phone;
        this.admin="false" ;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
