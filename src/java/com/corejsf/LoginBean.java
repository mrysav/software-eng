package com.corejsf;

import java.io.Serializable;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

@Named
@SessionScoped
public class LoginBean implements Serializable {

    private String username;
    private String password;

    public LoginBean() {
        this.password = "";
        this.username = "";
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setUsername(String username) {
        this.username = username.toLowerCase();
    }
    
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LoginBean make() {
        return this;
    }

    public String back() {
        return "index";
    }
}
