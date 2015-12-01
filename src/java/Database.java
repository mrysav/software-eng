
import com.corejsf.LoginBean;
import com.corejsf.RegisterBean;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.sql.DataSource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

@Named(value = "database")
@ApplicationScoped
public class Database {

    @Resource(name = "jdbc/databaseDB")
    private DataSource dataSource;

    private List<String> buildList(ResultSet resultSet, String columnName)
            throws SQLException {
        List<String> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(resultSet.getString(columnName));
        }
        return list;
    }

    public Database() {
    }

    public String registerUser(RegisterBean User) throws SQLException {

        //boolean hasSpecialChars = false;
        boolean hasNums = false;
        boolean hasLetters = false;

        //check if username and passwords are valid
        if (!User.getPassword1().equals(User.getPassword2())) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage("loginForm", new FacesMessage("Passwords must match!"));
            return null;
        } // check if password is long enough
        else if (User.getPassword1().length() < 8) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage("loginForm", new FacesMessage("Passwords must be at least 8 characters long!"));
            return null;
        } else if (User.getPassword1().length() > 16) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage("loginForm", new FacesMessage("Passwords must be less than 16 characters long!"));
            return null;
        }

        for (int i = 0; i < User.getPassword1().length(); i++) {
            if (Character.isDigit((User.getPassword1().charAt(i)))) {
                hasNums = true;
            }
            if (Character.isLetter(User.getPassword1().charAt(i))) {
                hasLetters = true;
            }
        }

        if (!hasLetters) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage("loginForm", new FacesMessage("Passwords must contain at least one letter!"));
            return null;
        }

        if (!hasNums) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage("loginForm", new FacesMessage("Passwords must contain at least one number!"));
            return null;
        }

        try (Connection conn = dataSource.getConnection()) {
            final String queryCheck = "SELECT * FROM users WHERE \"name\" = '" + User.getUsername() + "'";
            final Statement ps = conn.createStatement();

            final ResultSet resultSet = ps.executeQuery(queryCheck);
            if (resultSet.next()) {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                facesContext.addMessage("loginForm", new FacesMessage("Username already taken!"));
                return null;
            } else {
                try {

                    addUser(User);

                } catch (Exception e) {
                    System.err.println("Got an exception! ");
                    System.err.println(e.getMessage());
                }
            }
        }

        return "input";

    }

    public void addUser(RegisterBean User) throws SQLException {

        try (Connection conn = dataSource.getConnection()) {
            Statement st = conn.createStatement();
            String sql = "INSERT INTO users "
                    + "VALUES ('" + User.getUsername() + "','" + User.getPassword1() + "')";

            st.executeUpdate(sql);

        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

    }

    public String checkUser(LoginBean User) throws SQLException {
        try (Connection conn = dataSource.getConnection()) {
            String queryCheck = "SELECT * FROM users WHERE \"name\" = '" + User.getUsername() + "'" + " AND \"password\" ='" + User.getPassword() + "'";
            Statement ps = conn.createStatement();

            ResultSet resultSet = ps.executeQuery(queryCheck);
            if (resultSet.next()) {
                return "input";
            }
        }
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        // Send a global message ('null') so we don't duplicate login error messages
        facesContext.addMessage(null, new FacesMessage("Error!", "Username or password was incorrect."));
        return null;
    }
}
