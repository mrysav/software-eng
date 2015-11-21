package com.corejsf;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    
    private int mins;
    private int texts;
    private int gigs;
    private String plan1;
    private String plan2;
    private String plan3;
    private static final String databaseURL = "jdbc:derby://ukko.d.umn.edu:16020/databaseDB";
    
    public PlanBean(){ 
    } 
    
    public String submit() throws SQLException {
        
         Connection conn = DriverManager.getConnection(databaseURL, "app", "team2phonedb");
        final String queryCheck = "SELECT * FROM plans WHERE "
                + " (\"unlimited_data\" OR \"data_gb_per_month\" >=" + gigs + ")" +
                "AND (\"unlimited_calls\" OR \"minutes_per_month\" >= " + mins + ")"+
                "AND (\"unlimited_texts\" OR \"texts_per_month\" >= " + texts + ") ORDER BY \"monthy_price\" ASC";
        
        
        final Statement ps = conn.createStatement();
        ResultSet rs = ps.executeQuery(queryCheck);
        int i =0;
       
        while (i < 3 && rs.next()) {
           
            if (i ==0){
            plan1 = rs.getString("carrier_name");
            plan1 += "  " + rs.getString("plan_name");
            plan1 += "   Cost: " + rs.getInt("monthy_price");
            }
            
            else if (i == 1){
                
            plan2 = rs.getString("carrier_name");
            plan2 += "  " + rs.getString("plan_name");
            plan2 += "   Cost: " + rs.getInt("monthy_price");
                
            }
            
            else {
                
            plan3 = rs.getString("carrier_name");
            plan3 += "  " + rs.getString("plan_name");
            plan3 += "   Cost: " + rs.getInt("monthy_price");
                
            }
            rs = ps.getResultSet();
            i++;
            
            
        }
     
        return "output.xhtml";
    }
    
    
    public String getPlan1(){
        return plan1;
    }
    
    public String getPlan2(){
        return plan2;
    }
    
    public String getPlan3(){
        return plan3;
    }
    
    public String restart(){
        return "input.xhtml";
    }
    
    public void setMins(int mins){
     this.mins = mins;   
    }
    
    public int getMins(){
     return this.mins; 
    }
    
     public void setTexts(int texts){
     this.texts = texts;
    }
    
    public int getTexts(){
     return this.texts; 
    }
    
     public void setGigs(int gigs){
     this.gigs = gigs;   
    }
    
    public int getGigs(){
     return this.gigs; 
    }

    
    
    
}
