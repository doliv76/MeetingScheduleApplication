package DAOImplementation;

import Model.Country;
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

public class CountryDAOImpl {
    public static Country getCountryByCountryId(int countryId, Connection connection) throws SQLException, Exception {
        String selectStatement = "SELECT * FROM country WHERE countryId = '" +
                countryId + "';";
        try {
            DBQuery.constructQuery(selectStatement, connection);
            Country retrievedCountry;
            ResultSet resultSet = DBQuery.getResultSet();
            if(resultSet.next() == false) {
                System.out.println("ResultSet is empty");
            }
            else {
                resultSet.beforeFirst();
                while(resultSet.next()) {
                    int retCountryId = resultSet.getInt("countryId");
                    String retCountryName = resultSet.getString("country");
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

                    retrievedCountry = new Country(retCountryId, retCountryName, createDateConv, retCreatedBy, updateDateConv, retLastUpdateBy);
                    return retrievedCountry;
                    
                    
                }
            }
            
        } catch (SQLException er) {
            System.out.println("Error: " + er.getMessage());

        } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
    
    public static Country getCountryByName(String country, Connection connection) throws SQLException, Exception {
        String selectStatement = "SELECT * FROM country WHERE country = '" +
                country + "';";
        try {
            DBQuery.constructQuery(selectStatement, connection);
            Country retrievedCountry;
            ResultSet resultSet = DBQuery.getResultSet();
            if(resultSet.next() == false) {
                System.out.println("ResultSet is empty");
            }
            else {
                resultSet.beforeFirst();
                while(resultSet.next()) {
                    int retCountryId = resultSet.getInt("countryId");
                    String retCountryName = resultSet.getString("country");
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

                    retrievedCountry = new Country(retCountryId, retCountryName, createDateConv, retCreatedBy, updateDateConv, retLastUpdateBy);
                    return retrievedCountry;
                    
                    
                }
            }
            
        } catch (SQLException er) {
            System.out.println("Error: " + er.getMessage());

        } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
    
    public static ObservableList<Country> getAllCountries(Connection connection) throws SQLException, Exception {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        String selectStatement = "SELECT * FROM country;";
        try {
            DBQuery.constructQuery(selectStatement, connection);
            Country retrievedCountry;
            ResultSet resultSet = DBQuery.getResultSet();
            if(resultSet.next() == false) {
                System.out.println("ResultSet is empty");
            }
            else {
                resultSet.beforeFirst();
                while(resultSet.next()) {
                    int retCountryId = resultSet.getInt("countryId");
                    String retCountryName = resultSet.getString("country");
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

                    retrievedCountry = new Country(retCountryId, retCountryName, createDateConv, retCreatedBy, updateDateConv, retLastUpdateBy);
                    allCountries.add(retrievedCountry);
                }
                return allCountries;
            }
            
        } catch (SQLException er) {
            System.out.println("Error: " + er.getMessage());

        } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
    
    public static void createCountry(Country country, User user, Connection connection) throws SQLException, Exception {
        String insertStatement = "INSERT INTO country(country, createDate, createdBy, lastupdateBy) "
                + "VALUES('" + country.getCountry() + "',"
                + "CURRENT_TIMESTAMP,"
                + "'" +  user.getUserName() + "',"
//                + "CURRENT_TIMESTAMP,"
                + "'" +  user.getUserName() + "');";
        
        DBQuery.constructQuery(insertStatement, connection);
    }
    
    public static void updateCountry(Country country, User user, Connection connection) throws SQLException, Exception {
        String count, lastUpdateBy;
        int countryId;
        count = country.getCountry();
        lastUpdateBy = user.getUserName();
        countryId = country.getCountryId();
        String updateStatement = "UPDATE country SET "
                + "country = '" + count + "', "
                + "lastUpdate = CURRENT_TIMESTAMP, "
                + "lastUpdateBy = '" + lastUpdateBy + "' "
                +"WHERE countryId = " + countryId + ";";
        
        DBQuery.constructQuery(updateStatement, connection);
    }
}
