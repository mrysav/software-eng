package com.corejsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Named; 
import javax.enterprise.context.SessionScoped; 
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;

@Named
@SessionScoped
public class PlanBean implements Serializable {
    
    
    public PlanBean(){ 
    } 
    
    
    public String submit(){
        return "output.xhtml";
    }
}