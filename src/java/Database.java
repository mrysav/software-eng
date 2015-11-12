import com.corejsf.LoginBean;
import com.corejsf.RegisterBean;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.sql.DataSource;
import java.sql.DriverManager;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Named(value = "database")
@ApplicationScoped
public class Database {

    private String currentUser;
   
  @Resource(name = "jdbc/databaseDB")
  private DataSource quizSource;

  
  private List<String> buildList(ResultSet resultSet, String columnName)
      throws SQLException {
    List<String> list = new ArrayList<>();
    while (resultSet.next()) {
      list.add(resultSet.getString(columnName));
    }
    return list;
  }

  public Database(){
  }
  
  public String registerUser(RegisterBean User) throws SQLException{
      
      //check if username and passwords are valid
        if ( ! User.getPassword1().equals(User.getPassword2())){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage("loginForm", new FacesMessage("Passwords must match!"));
        return null;
        }

       
        String url = "jdbc:derby://localhost:1527/databaseDB"; 
            Connection conn = DriverManager.getConnection(url,"app","team2phonedb"); 
            final String queryCheck = "SELECT * FROM users WHERE \"name\" = '" + User.getUsername() + "'";
            final Statement ps = conn.createStatement();
            
            final ResultSet resultSet = ps.executeQuery(queryCheck);
            if(resultSet.next()) {
         FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage("loginForm", new FacesMessage("Username already taken!"));
        return null;
}
            else {
 try { 
     
           addUser(User);
      
       
 }catch (Exception e) { 
            System.err.println("Got an exception! "); 
            System.err.println(e.getMessage()); 
        } 
            }
            
        
        return "input";
      
      
      
  }
  
  public String getName(){
      return currentUser;
  }
  
  
  public void addUser(RegisterBean User) throws SQLException{
      
 try { 
     
              String url = "jdbc:derby://localhost:1527/databaseDB"; 
            Connection conn = DriverManager.getConnection(url,"app","team2phonedb"); 
            Statement st = conn.createStatement(); 
            String sql = "INSERT INTO users " + 
                "VALUES ('" +User.getUsername() + "','" + User.getPassword1() + "')"; 
            
            st.executeUpdate(sql);
            this.currentUser = User.getUsername();
    
            
 }catch (Exception e) { 
            System.err.println("Got an exception! "); 
            System.err.println(e.getMessage()); 
        } 
 
  }
 
  
  public String checkUser(LoginBean User) throws SQLException{
      
      
      String url = "jdbc:derby://localhost:1527/databaseDB"; 
      Connection conn = DriverManager.getConnection(url,"app","team2phonedb"); 
            final String queryCheck = "SELECT * FROM users WHERE \"name\" = '" + User.getUsername() + "'"
                    + " AND \"password\" ='" + User.getPassword() +"'";
            final Statement ps = conn.createStatement();
            
            final ResultSet resultSet = ps.executeQuery(queryCheck);
            if(resultSet.next()) {
                currentUser = User.getUsername();
                return "input";
}
      
       FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage("loginForm", new FacesMessage("Incorrect Username or Password!"));
        return null;
  }
  
}