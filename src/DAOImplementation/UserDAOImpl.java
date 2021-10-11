package DAOImplementation;


import Model.User;
import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBQuery;

public class UserDAOImpl {
    static boolean active;
    

    public static User getUser(String userName, String password, Connection connection) throws SQLException, Exception {
        String selectStatement = "SELECT * FROM user WHERE userName = '" +
                userName + "' AND password = '" + password +"';";
        
        try {
            DBQuery.constructQuery(selectStatement, connection);
            User retrievedUser;
            ResultSet resultSet = DBQuery.getResultSet();
            if(resultSet.next() == false) {
                System.out.println("ResultSet is empty");
            }
            else {
                resultSet.beforeFirst();
                while(resultSet.next()) {
                    int retUserId = resultSet.getInt("userId");
                    String retUserName = resultSet.getString("userName");
                    String retPassword = resultSet.getString("password");
                    int retActive = resultSet.getInt("active");
                    String createDate = resultSet.getString("createDate");
                    String createdBy = resultSet.getString("createdBy");
                    String lastUpdate = resultSet.getString("lastUpdate");
                    String lastUpdateBy = resultSet.getString("lastUpdateby");
            
                //move this to a utility class later (converting string to calendar)
                    Calendar createDateConv = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Date date = dateFormat.parse(createDate);
                    createDateConv.setTime(date);

                    Calendar updateDateConv = Calendar.getInstance();
                    date = dateFormat.parse(lastUpdate);
                    updateDateConv.setTime(date);
            
                    if(retActive == 1)
                        active = true;
            
                    retrievedUser = new User(retUserId, retUserName, retPassword, active, createDateConv, createdBy, updateDateConv, lastUpdateBy);
                    return retrievedUser;            
                } 
            }
        } catch (SQLException er) {
            System.out.println("Error: " + er.getMessage());

        } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
    
    public static User getUserByName(String userName, Connection connection) throws SQLException, Exception {
        String selectStatement = "SELECT * FROM user WHERE userName = '" +
                userName + "';";
        
        try {
            DBQuery.constructQuery(selectStatement, connection);
            User retrievedUser;
            ResultSet resultSet = DBQuery.getResultSet();
            if(resultSet.next() == false) {
                System.out.println("ResultSet is empty");
            }
            else {
                resultSet.beforeFirst();
                while(resultSet.next()) {
                    int retUserId = resultSet.getInt("userId");
                    String retUserName = resultSet.getString("userName");
                    String retPassword = resultSet.getString("password");
                    int retActive = resultSet.getInt("active");
                    String createDate = resultSet.getString("createDate");
                    String createdBy = resultSet.getString("createdBy");
                    String lastUpdate = resultSet.getString("lastUpdate");
                    String lastUpdateBy = resultSet.getString("lastUpdateby");
            
                //move this to a utility class later (converting string to calendar)
                    Calendar createDateConv = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Date date = dateFormat.parse(createDate);
                    createDateConv.setTime(date);

                    Calendar updateDateConv = Calendar.getInstance();
                    date = dateFormat.parse(lastUpdate);
                    updateDateConv.setTime(date);
            
                    if(retActive == 1)
                        active = true;
            
                    retrievedUser = new User(retUserId, retUserName, retPassword, active, createDateConv, createdBy, updateDateConv, lastUpdateBy);
                    return retrievedUser;            
                } 
            }
        } catch (SQLException er) {
            System.out.println("Error: " + er.getMessage());

        } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
    
    public static ObservableList<User> getAllUsers(Connection connection) throws SQLException, Exception {
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        String selectStatement = "SELECT * FROM user;";
        
        try {
            DBQuery.constructQuery(selectStatement, connection);
            User retrievedUser;
            ResultSet resultSet = DBQuery.getResultSet();
            if(resultSet.next() == false) {
                System.out.println("ResultSet is empty");
            }
            else {
                resultSet.beforeFirst();
                while(resultSet.next()) {
                    int retUserId = resultSet.getInt("userId");
                    String retUserName = resultSet.getString("userName");
                    String retPassword = resultSet.getString("password");
                    int retActive = resultSet.getInt("active");
                    String createDate = resultSet.getString("createDate");
                    String createdBy = resultSet.getString("createdBy");
                    String lastUpdate = resultSet.getString("lastUpdate");
                    String lastUpdateBy = resultSet.getString("lastUpdateby");
            
                //move this to a utility class later (converting string to calendar)
                    Calendar createDateConv = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Date date = dateFormat.parse(createDate);
                    createDateConv.setTime(date);

                    Calendar updateDateConv = Calendar.getInstance();
                    date = dateFormat.parse(lastUpdate);
                    updateDateConv.setTime(date);
            
                    if(retActive == 1)
                        active = true;
            
                    retrievedUser = new User(retUserId, retUserName, retPassword, active, createDateConv, createdBy, updateDateConv, lastUpdateBy);
                    allUsers.add(retrievedUser);            
                }
                return allUsers;   
            }
        } catch (SQLException er) {
            System.out.println("Error: " + er.getMessage());

        } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
}
