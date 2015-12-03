package com.corejsf;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named
@SessionScoped
public class LoginBean implements Serializable {

    private String username;
    private String password;
    private Boolean loggedIn = false;

    @Inject
    private Database database;

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
    
    FacesContext getContext() {
        return FacesContext.getCurrentInstance();
    }

    public boolean getIsLoggedIn() {
        return loggedIn;
    }

    public String authenticate() {
        try {
            if (database.authenticate(username, password)) {
                loggedIn = true;
                // Don't store the password in memory
                password = "";
                return "input";
            } else {
                loggedIn = false;
                username = "";
                password = "";
                getContext().addMessage(null, new FacesMessage("Login failed."));
            }
        } catch (SQLException ex) {
            getContext().addMessage(null, new FacesMessage("Login failed - database exception occured."));
        } catch (NoSuchAlgorithmException ex) {
            getContext().addMessage(null, new FacesMessage("Login failed - decryption exception occured."));
        }
        
        return "index";
    }

    public String logout() {
        username = "";
        password = "";
        loggedIn = false;
        return "index";
    }
}
