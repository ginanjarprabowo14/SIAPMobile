package com.example.ginanjarpr.siapnonmvp.models;

/**
 * Created by ginanjarpr on 20/03/18.
 */

public class User {

    private String name;
    private String email;
    private String username;
    private String roles;
    private String password;
    private String old_password;
    private String new_password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRoles() {
        return roles;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

}