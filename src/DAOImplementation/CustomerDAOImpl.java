package DAOImplementation;

import Model.Customer;
import Model.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBQuery;
public class CustomerDAOImpl {
    static boolean active; 
    
    public static Customer getCustomerByName(String customerName, Connection connection) throws SQLException, Exception {
        String selectStatement = "SELECT * FROM customer WHERE customerName = '" +
                customerName + "';";
        try {
            DBQuery.constructQuery(selectStatement, connection);
            Customer retrievedCustomer;
            ResultSet resultSet = DBQuery.getResultSet();
            if(resultSet.next() == false) {
                System.out.println("ResultSet is empty");
            }
            else {
                resultSet.beforeFirst();
                while(resultSet.next()) {
                    int retCustomerId = resultSet.getInt("customerId");
                    String retCustomerName = resultSet.getString("customerName");
                    int retAddressId = resultSet.getInt("addressId");
                    int retActive = resultSet.getInt("active");
                    String retCreateDate = resultSet.getString("createDate");
                    String retCreatedBy = resultSet.getString("createdBy");
                    String retLastUpdate = resultSet.getString("lastUpdate");
                    String retLastUpdateBy = resultSet.getString("lastUpdateBy");
                    
                    Calendar createDateConv = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Date date = dateFormat.parse(retCreateDate);
                    createDateConv.setTime(date);

                    Calendar updateDateConv = Calendar.getInstance();
                    date = dateFormat.parse(retLastUpdate);
                    updateDateConv.setTime(date);
            
                    if(retActive == 1)
                        active = true;
            
                    retrievedCustomer = new Customer(retCustomerId, retCustomerName, retAddressId, active, createDateConv, retCreatedBy, updateDateConv, retLastUpdateBy);
                    return retrievedCustomer;
                    
                    
                }
            }
            
        } catch (SQLException er) {
            System.out.println("Error: " + er.getMessage());

        } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
    
    public static ObservableList<Customer> getAllCustomers(Connection connection) throws SQLException, Exception {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String selectStatement = "SELECT * FROM customer;";
        try {
            DBQuery.constructQuery(selectStatement, connection);
            Customer retrievedCustomer;
            ResultSet resultSet = DBQuery.getResultSet();
            
            if(resultSet.next() == false) {
                System.out.println("ResultSet is empty");
            }
            else {
                resultSet.beforeFirst();
                while(resultSet.next()) {
                    int retCustomerId = resultSet.getInt("customerId");
                    String retCustomerName = resultSet.getString("customerName");
                    int retAddressId = resultSet.getInt("addressId");
                    int retActive = resultSet.getInt("active");
                    String retCreateDate = resultSet.getString("createDate");
                    String retCreatedBy = resultSet.getString("createdBy");
                    String retLastUpdate = resultSet.getString("lastUpdate");
                    String retLastUpdateBy = resultSet.getString("lastUpdateBy");
                    
                    Calendar createDateConv = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Date date = dateFormat.parse(retCreateDate);
                    createDateConv.setTime(date);

                    Calendar updateDateConv = Calendar.getInstance();
                    date = dateFormat.parse(retLastUpdate);
                    updateDateConv.setTime(date);
            
                    if(retActive == 1)
                        active = true;
            
                    retrievedCustomer = new Customer(retCustomerId, retCustomerName, retAddressId, active, createDateConv, retCreatedBy, updateDateConv, retLastUpdateBy);
                    allCustomers.add(retrievedCustomer);
                    
                    
                }
                return allCustomers;
            }
            
        } catch (SQLException er) {
            System.out.println("Error: " + er.getMessage());

        } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
        }
        return null;
        
    }
    
    public static void createCustomer(Customer customer, User user, Connection connection) throws SQLException, Exception {
        String insertStatement = "INSERT INTO customer(customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy) \n" +
"	VALUES('" + customer.getCustomerName()
        + "', " + customer.getAddressId() + ", "
        + "1, "
        +" current_timestamp(), '"
        + user.getUserName() + "', "
        + "current_timestamp(), '"
        + user.getUserName() + "');";
        
        DBQuery.constructQuery(insertStatement, connection);
    }
    
    public static void updateCustomer(Customer customer, User user, Connection connection) throws SQLException, Exception {
        boolean activ = customer.getActive();
        int active = activ ? 1 : 0;
        String updateStatement = "UPDATE customer SET " +
            "customerName = '" + customer.getCustomerName()
            + "', addressId = " + customer.getAddressId() + ", "
            + "active = " + active + ", "
            + "lastUpdate = current_timestamp(), "
            + "lastUpdateBy = '" + user.getUserName() + "'"
            + " WHERE customerId = " + customer.getCustomerId() + ";";
        
        DBQuery.constructQuery(updateStatement, connection);
    }
    
    public static void deleteCustomer(Customer customer, Connection connection) throws SQLException, Exception {
        int cusId = customer.getCustomerId();
        String deleteStatement = "DELETE FROM customer WHERE customerId = " + cusId + ";";
        DBQuery.constructQuery(deleteStatement, connection);
    }
}
