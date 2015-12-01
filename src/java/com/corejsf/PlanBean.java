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
    private float gigs;
    private String plan1Carrier;
    private String plan1Name;
    private String plan1Cost;
    private String plan1URL;
   
    private String plan2Carrier;
    private String plan2Name;
    private String plan2Cost;
    private String plan2URL;
    
     private String plan3Carrier;
    private String plan3Name;
    private String plan3Cost;
    private String plan3URL;
    
        private static final String databaseURL = "jdbc:derby://ukko.d.umn.edu:16020/databaseDB";
    
    public PlanBean(){ 
    } 
    
    public String submit() throws SQLException {
        
        plan1Carrier = null;
        plan1Name = null;
        plan1Cost = null;
        plan1URL = null;
        
        plan2Carrier = null;
        plan2Name = null;
        plan2Cost = null;
        plan2URL = null;
        
        plan3Carrier = null;
        plan3Name = null;
        plan3Cost = null;
        plan3URL = null;
        
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
            plan1Carrier =  rs.getString("carrier_name");
            plan1Name =  rs.getString("plan_name");
            plan1Cost = "" + rs.getInt("monthy_price");
            plan1URL = rs.getString("URL");
            }
            
            else if (i == 1){
                
            plan2Carrier = rs.getString("carrier_name");
            plan2Name = rs.getString("plan_name");
            plan2Cost = "" +rs.getInt("monthy_price");
            plan2URL = rs.getString("URL");
            }
            
            else {
                
            plan3Carrier = rs.getString("carrier_name");
            plan3Name =  rs.getString("plan_name");
            plan3Cost =  "" + rs.getInt("monthy_price");
            plan3URL = rs.getString("URL");
            }
            rs = ps.getResultSet();
            i++;
            
            
        }
     
        return "output.xhtml";
    }
    
  
    public String getPlan1Carrier(){
        return plan1Carrier;
    }
    
    public String getPlan1Name(){
        return plan1Name;
    }
    
    public String getPlan1Cost(){
        return plan1Cost;
    }
    
    public String getPlan1URL(){
        return plan1URL;
    }
    
     public String getPlan2Carrier(){
        return plan2Carrier;
    }
    
    public String getPlan2Name(){
        return plan2Name;
    }
    
    public String getPlan2Cost(){
        return plan2Cost;
    }
    
     public String getPlan2URL(){
        return plan2URL;
    }
     
     public String getPlan3Carrier(){
        return plan3Carrier;
    }
    
    public String getPlan3Name(){
        return plan3Name;
    }
    
    public String getPlan3Cost(){
        return plan3Cost;
    }
    
     public String getPlan3URL(){
        return plan3URL;
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
    
     public void setGigs(float gigs){
     this.gigs = gigs;   
    }
    
    public float getGigs(){
     return this.gigs; 
    }

    
    
    
}
