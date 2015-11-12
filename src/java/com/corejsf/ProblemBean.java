package com.corejsf;

import java.io.Serializable;
import java.util.ArrayList;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped

public class ProblemBean implements Serializable {
   private String question;
   private ArrayList<String> solution;
   private String answer;
   
   public ProblemBean() {}

   public ProblemBean(String question, ArrayList<String> solution, String answer) {
     
      this.question = question;
      this.solution = solution;
      this.answer = answer;
      
   }

   public String getQuestion() { return question; }
   public void setQuestion(String newValue) { question = newValue; }
   
   public String getAnswer() { return answer; }
   public void setAnswer(String newValue) { answer = newValue; }

   public ArrayList<String> getSolution() { return solution; }
   public void setSolution(ArrayList<String> newValue) { solution = newValue; }
}
