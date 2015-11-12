/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.corejsf;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Caleb
 */

@Named
@SessionScoped

public class RegisterBean implements Serializable{
    
private String password1;
private String password2;
private String username;

public RegisterBean(){
    password1="";
    password2="";
    username="";
}
    
 public void setPassword1(String password){this.password1 = password; };
    
    public void setUsername(String username){this.username = username.toLowerCase(); };
    
    public String getUsername(){return username;}
    
    public String getPassword1(){return password1;};
    
     public void setPassword2(String password){this.password2 = password; };
    
    public String getPassword2(){return password2;};
    
    public RegisterBean getUser(){ return this;}
    
}
