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
public class QuizBean implements Serializable {
   private ArrayList<ProblemBean> problems = new ArrayList<ProblemBean>();     
   private ArrayList<String> titles = new ArrayList<String>();
   private int currentIndex;
   private int score;
   private int ref;
   private int lastRight;
   private String input;
   private ArrayList<ArrayList<ProblemBean>> master = new ArrayList<ArrayList<ProblemBean>>();
   private int quizRef;
   private String User;
   
   public QuizBean() {      
   
       lastRight =0;
       score = 0;
       ref = 1;
       quizRef =0;
       
       //math questions:
       
          ArrayList<String> ans = new ArrayList<String>(Arrays.asList("2", "16", "256", "8"));
          problems.add(new ProblemBean("What is 2 ^ 4 ",ans,"16"));
          
          ans = new ArrayList<String>(Arrays.asList("0", "2", "6", "5"));
          problems.add(new ProblemBean("If 5x = 30, then x = ",ans, "6"));
          
          ans = new ArrayList<String>(Arrays.asList("4", "-5", "1", "-1"));
          problems.add(new ProblemBean("i ^2 = ",ans, "-1"));
          
          ans = new ArrayList<String>(Arrays.asList("0", "2", "6", "5"));
          problems.add(new ProblemBean("If 3x +2 = 2, then x = ", ans, "0"));
       
           master.add(problems);
           
           // CS questions:
           problems = new ArrayList<ProblemBean>();
           
            ans = new ArrayList<String>(Arrays.asList("1", "4", "9", "36"));
            problems.add(new ProblemBean("9 % 4 = ",ans, "1"));
            
             ans = new ArrayList<String>(Arrays.asList("9", "10", "11", "101010"));
             problems.add(new ProblemBean("int y = 10;\ny++;\nWhat is the value of y?",ans, "11"));
             
              ans = new ArrayList<String>(Arrays.asList("c++", "java", "html", "python"));
              problems.add(new ProblemBean("The phrase 'write once, run anywhere' was made popular by which programing language", ans,"java"));
               
               ans = new ArrayList<String>(Arrays.asList("public", "private", "protected", "void"));
              problems.add(new ProblemBean("A function that returns nothing should be declared",ans, "void"));
           
                master.add(problems);
                
            problems = new ArrayList<ProblemBean>();
             ans = new ArrayList<String>(Arrays.asList("force", "gravity", "friction", "velocity"));
           problems.add(new ProblemBean("____ = Mass X Accelereation",ans, "force"));
           
            ans = new ArrayList<String>(Arrays.asList("acceleration", "velocity", "speed", "inertia"));
             problems.add(new ProblemBean("What is the term used to denote the tendency of an object to remain in a state of rest until acted upon by an external force?", ans, "inertia"));
              
              ans = new ArrayList<String>(Arrays.asList("gravity", "friction", "momentum", "kelvin"));
             problems.add(new ProblemBean("What is the force that opposes the relative motion of two bodies that are in contact?",ans, "friction"));
             
              ans = new ArrayList<String>(Arrays.asList("liquids", "hallogens", "plasma", "vector"));
             problems.add(new ProblemBean("What is described as an ionized gas with approximately equal numbers of positive and negative charges?", ans,"plasma"));
               
               master.add(problems);
                
            problems = new ArrayList<ProblemBean>();
             ans = new ArrayList<String>(Arrays.asList("3", "30", "33", "300"));
           problems.add(new ProblemBean(".3 = __%",ans, "30"));
           
            ans = new ArrayList<String>(Arrays.asList("100", "25", "20", "5"));
             problems.add(new ProblemBean("What is the mean of the following values: 10, 25, 0, 40, 25",ans, "20"));
             
              ans = new ArrayList<String>(Arrays.asList("0", "100", "25", "50"));
              problems.add(new ProblemBean("The odds of flipping a coin twice, and both times landing heads up is __%",ans, "25"));
              
               ans = new ArrayList<String>(Arrays.asList("2", "1", "3", "14"));
               problems.add(new ProblemBean("Find the mode of the following numbers: 1,2,3,2,3,2,1",ans, "2"));
               
               master.add(problems);
   }   
   
   public String getAns1(){
       
       ArrayList<String> a = getCurrent().getSolution();
       
       return a.get(0);
       
   }
   
   public String getAns2(){
         ArrayList<String> a = getCurrent().getSolution();
       
       return a.get(1);
   }
   
   public String getAns3(){
        ArrayList<String> a = getCurrent().getSolution();
       
       return a.get(2);
   }
   
   public String getAns4(){
        ArrayList<String> a = getCurrent().getSolution();
       
       return a.get(3);
   }

   public void setProblems(ArrayList<ProblemBean> newValue) { 
      problems = newValue;
      currentIndex = 0;
      score = 0;
      ref =1;
      quizRef=0;
   } 
     
    public void valueChanged(ValueChangeEvent event) {
   
       currentIndex = Integer.parseInt(event.getNewValue().toString());
       quizRef = currentIndex;
       
}
    
    public void ansChanged(ValueChangeEvent event) {
 
       int index = Integer.parseInt(event.getNewValue().toString());
       input = getCurrent().getSolution().get(index);
}
 
   public boolean displayScore(){
       
       if (ref !=1){
       
      return true;
       }
       return false;
   }
   public String go(){ return "first";}
   
   public String getTitle(){
       
       if (quizRef == 0){
           return "Mathematics";
       }
       
       else if (quizRef == 1){
           return "Computer Science";
       }
       
       else if (quizRef == 2){
           return "Physics";
       }
       
       else {
           return "Statistics";
       }
   }
   
   public String getLast(){
       
       if (lastRight == score){
           return "Sorry, the correct answer was " + getCorrect() +".";
       }
       
       lastRight = score;
       return "That is right!";
       
   }
   
   public String getQuestion(){
       
       return getCurrent().getQuestion();
   }
   
   public String getCorrect(){
       
        if (lastRight == score){
            
       return getPrevious().getAnswer();
        }
        
        else {
            return "";
        }
   }
   
   public int getQuizRef(){return quizRef;}
   
   public String getPrompt(){
       
       if (ref == 1){
           return "first";
       }
       
       else if (ref == 2 || ref == 3){
           return "next";
       }
       
       else {
           return "last";
       }
   }
   
   public int getRef(){
       return ref-1;
   }
   
    public ProblemBean getPrevious() { 
    
        if (currentIndex == 0){
            return master.get(quizRef).get(problems.size()-1);
        }
        
        return master.get(quizRef).get(currentIndex-1); 
    
    }
    
   public String getColor(){
       
         if (quizRef == 0){
           return "blue";
       }
       
       else if (quizRef == 1){
           return "green";
       }
       
       else if (quizRef == 2){
           return "purple";
       }
       
       else {
           return "red";
       } 
   }
   
   public int getPage(){
       return currentIndex;
   }
   
   public boolean getNotFirst(){
       if (ref ==1){
           return true;
       }
       return false;
   }
   public boolean getDone(){
       
       if (ref == 5){
           return true;
       }
       return false;
   }
   public int getScore() { return score; }

   public ProblemBean getCurrent() { return master.get(quizRef).get(currentIndex); }

   public String restartQuiz(){
       
       currentIndex =0;
       score =0;
       ref = 1;
       lastRight =0;
       quizRef = 0;
       return "home"; 
   }
   
   public QuizBean make(){
       return this;
   }
   
   public void setInput(String a){
       
       int index = Integer.parseInt(a);
       
       input = getCurrent().getSolution().get(index);
   }
   
   public String getInput(){
       return input;
   }
   
   public String getAnswer() { return ""; }
      
    public String check() { 
        
        input = input.toLowerCase();
        
         if ( input.trim().equals(getCurrent().getAnswer()) ){
             score++;
         }            
         currentIndex = (currentIndex + 1) % (problems.size());
         ref++;
       
         return "first";
 
   }   
  
}
