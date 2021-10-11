package DAOImplementation;

import static DAOImplementation.CustomerDAOImpl.active;
import Model.Address;
import Model.Address;
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

public class AddressDAOImpl {
    public static Address getAddressByAddressId(int addressId, Connection connection) throws SQLException, Exception {
        String selectStatement = "SELECT * FROM address WHERE addressId = '" +
                addressId +"';";
        try {
            DBQuery.constructQuery(selectStatement, connection);
            Address retrievedAddress;
            ResultSet resultSet = DBQuery.getResultSet();
            if(resultSet.next() == false) {
                System.out.println("ResultSet is empty");
            }
            else {
                resultSet.beforeFirst();
                while(resultSet.next()) {
                    int retAddressId = resultSet.getInt("addressId");
                    String retAddress = resultSet.getString("address");
                    String retAddress2 = resultSet.getString("address2");
                    int retCityId = resultSet.getInt("cityId");
                    String retPostalCode = resultSet.getString("postalCode");
                    String retPhone = resultSet.getString("phone");
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
            
                    retrievedAddress = new Address(retAddressId, retAddress, retAddress2, retCityId, retPostalCode, retPhone, createDateConv, retCreatedBy, updateDateConv, retLastUpdateBy);
                    return retrievedAddress;
                    
                    
                }
            }
            
        } catch (SQLException er) {
            System.out.println("Error: " + er.getMessage());

        } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
    
    public static Address getAddressByAddressPostalPhone(String address, String postalCode, String phone, Connection connection) throws SQLException, Exception {
        String selectStatement = "SELECT * FROM address WHERE address = '" +
                address +"'"
                + "AND postalCode ='"
                + postalCode
                + "'AND phone ='"
                + phone
                + "';";
        try {
            DBQuery.constructQuery(selectStatement, connection);
            Address retrievedAddress;
            ResultSet resultSet = DBQuery.getResultSet();
            if(resultSet.next() == false) {
                System.out.println("ResultSet is empty");
            }
            else {
                resultSet.beforeFirst();
                while(resultSet.next()) {
                    int retAddressId = resultSet.getInt("addressId");
                    String retAddress = resultSet.getString("address");
                    String retAddress2 = resultSet.getString("address2");
                    int retCityId = resultSet.getInt("cityId");
                    String retPostalCode = resultSet.getString("postalCode");
                    String retPhone = resultSet.getString("phone");
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
            
                    retrievedAddress = new Address(retAddressId, retAddress, retAddress2, retCityId, retPostalCode, retPhone, createDateConv, retCreatedBy, updateDateConv, retLastUpdateBy);
                    return retrievedAddress;
                    
                    
                }
            }
            
        } catch (SQLException er) {
            System.out.println("Error: " + er.getMessage());

        } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
    
    public static ObservableList<Address> getAllAddresses(Connection connection) throws SQLException, Exception {
        ObservableList<Address> allAddresses = FXCollections.observableArrayList();
        String selectStatement = "SELECT * FROM address;";
        try {
            DBQuery.constructQuery(selectStatement, connection);
            Address retrievedAddress;
            ResultSet resultSet = DBQuery.getResultSet();
            
            if(resultSet.next() == false) {
                System.out.println("ResultSet is empty");
            }
            else {
                resultSet.beforeFirst();
                while(resultSet.next()) {
                    int retAddressId = resultSet.getInt("addressId");
                    String retAddress = resultSet.getString("address");
                    String retAddress2 = resultSet.getString("address2");
                    int retCityId = resultSet.getInt("cityId");
                    String retPostalCode = resultSet.getString("postalCode");
                    String retPhone = resultSet.getString("phone");
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
            
                    retrievedAddress = new Address(retAddressId, retAddress, retAddress2, retCityId, retPostalCode, retPhone, createDateConv, retCreatedBy, updateDateConv, retLastUpdateBy);
                    allAddresses.add(retrievedAddress);
                    
                    
                }
                return allAddresses;
            }
            
        } catch (SQLException er) {
            System.out.println("Error: " + er.getMessage());

        } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
        }
        return null;
        
    }
    
    public static void createAddress(Address address, User user, Connection connection) throws SQLException, Exception {
        String insertStatement = "INSERT INTO address(address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdateBy) \n" +
"	VALUES('" + address.getAddress() + "', "
                + "'" + address.getAddress2() + "', "
                + "'" + address.getCityId() + "', "
                + "'" + address.getPostalCode() + "', "
                + "'" + address.getPhone() + "', "
                + "CURRENT_TIMESTAMP, "
                + "'" + user.getUserName() + "', "
                + "CURRENT_TIMESTAMP, "
                + "'" + user.getUserName() + "');";
        
        DBQuery.constructQuery(insertStatement, connection);
    }
    
    public static void updateAddress(Address address, User user, Connection connection) throws SQLException, Exception {
        String addr, addr2, postCode, phoneNum, lastUpdateBy;
        int cityId, addressId;
        addr = address.getAddress();
        addr2 = address.getAddress2();
        postCode = address.getPostalCode();
        phoneNum = address.getPhone();
        lastUpdateBy = user.getUserName();
        cityId = address.getCityId();
        addressId = address.getAddressId();
        String updateStatement = "UPDATE address SET "
                + "address = '" + addr + "', "
                + "address2 = '" + addr2 + "', "
                + "cityId = " + cityId + ", "
                + "postalCode = '" + postCode + "', "
                + "phone = '" + phoneNum + "', "
                + "lastUpdate = CURRENT_TIMESTAMP, "
                + "lastUpdateBy = '" + lastUpdateBy + "' "
                +"WHERE addressId = " + addressId + ";";
        
        DBQuery.constructQuery(updateStatement, connection);
    }
}
