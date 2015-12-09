package com.corejsf;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Caleb
 */
@Named
@SessionScoped
public class RegisterBean implements Serializable {

    private String password1;
    private String password2;
    private String username;
    @Inject
    private LoginBean loginBean;
    @Inject
    private Database database;

    public RegisterBean() {
        password1 = "";
        password2 = "";
        username = "";
    }

    public void setPassword1(String password) {
        this.password1 = password;
    }

    public void setUsername(String username) {
        this.username = username.toLowerCase();
    }
    
    public String getUsername() {
        return username;
    }

    public String getPassword1() {
        return password1;
    }
    
     public void setPassword2(String password) {
        this.password2 = password;
    }
    
    public String getPassword2() {
        return password2;
    }
    
    FacesContext getContext() {
        return FacesContext.getCurrentInstance();
    }
    
    public String register() {
        
        // Password does not meet requirements.
        if(!checkPassword())
            return "register";
        
        try {
            if (database.createUser(username, password1)) {
                loginBean.setUsername(username);
                loginBean.setPassword(password1);
                username = "";
                password1 = "";
                password2 = "";
                // make sure we can log in right away, but don't go setting any flags here;
                // that would just be confusing.
                return loginBean.authenticate();
            } else {
                username = "";
                password1 = "";
                password2 = "";
                getContext().addMessage(null, new FacesMessage("Registration failed:", "An error occurred."));
            }
        } catch (SQLException ex) {
            if(ex.getErrorCode() == 30000)
                getContext().addMessage(null, new FacesMessage("Registration failed:","Username already taken."));
            else
                getContext().addMessage(null, new FacesMessage("Registration failed:","Database exception occurred."));

        } catch (NoSuchAlgorithmException ex) {
            getContext().addMessage(null, new FacesMessage("Registration failed:", "Decryption exception occured."));
        } catch (UnsupportedEncodingException ex) {
            getContext().addMessage(null, new FacesMessage("Registration failed:", "Encoding exception occured."));
        }
        
        return "register";
    }

    private boolean checkPassword() {
        boolean hasNums = false;
        boolean hasLetters = false;

        //check if username and passwords are valid
        if (!password1.equals(password2)) {
            getContext().addMessage(null, new FacesMessage("Error:", "Passwords must match!"));
            return false;
        } // check if password is long enough
        else if (password1.length() < 8) {
            getContext().addMessage(null, new FacesMessage("Error:", "Passwords must be at least 8 characters long!"));
            return false;
        } // check password length
        else if (password1.length() > 32) {
            getContext().addMessage(null, new FacesMessage("Error:", "Passwords must be less than 32 characters long!"));
            return false;
        }

        for (int i = 0; i < password1.length(); i++) {
            if (Character.isDigit((password1.charAt(i)))) {
                hasNums = true;
            }
            if (Character.isLetter(password1.charAt(i))) {
                hasLetters = true;
            }
        }

        if (!hasLetters) {
            getContext().addMessage(null, new FacesMessage("Error:", "Passwords must contain at least one letter!"));
            return false;
        }

        if (!hasNums) {
            getContext().addMessage(null, new FacesMessage("Error:", "Passwords must contain at least one number!"));
            return false;
        }
        
        return true;
    }
}
