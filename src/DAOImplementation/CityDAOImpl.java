package DAOImplementation;

import Model.City;
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

/**
 *
 * @author upont
 */
public class CityDAOImpl {
    public static City getCityByCityId(int cityId, Connection connection) throws SQLException, Exception {
        String selectStatement = "SELECT * FROM city WHERE cityId = '" +
                cityId + "';";
        try {
            DBQuery.constructQuery(selectStatement, connection);
            City retrievedCity;
            ResultSet resultSet = DBQuery.getResultSet();
            if(resultSet.next() == false) {
                System.out.println("ResultSet is empty");
            }
            else {
                resultSet.beforeFirst();
                while(resultSet.next()) {
                    int retCityId = resultSet.getInt("cityId");
                    String retCityName = resultSet.getString("city");
                    int retCountryId = resultSet.getInt("countryId");
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

                    retrievedCity = new City(retCityId, retCityName, retCountryId, createDateConv, retCreatedBy, updateDateConv, retLastUpdateBy);
                    return retrievedCity;
                    
                    
                }
            }
            
        } catch (SQLException er) {
            System.out.println("Error: " + er.getMessage());

        } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
    
    public static City getCityByName(String city, Connection connection) throws SQLException, Exception {
        String selectStatement = "SELECT * FROM city WHERE city = '" +
                city + "';";
        try {
            DBQuery.constructQuery(selectStatement, connection);
            City retrievedCity;
            ResultSet resultSet = DBQuery.getResultSet();
            if(resultSet.next() == false) {
                System.out.println("ResultSet is empty");
            }
            else {
                resultSet.beforeFirst();
                while(resultSet.next()) {
                    int retCityId = resultSet.getInt("cityId");
                    String retCityName = resultSet.getString("city");
                    int retCountryId = resultSet.getInt("countryId");
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

                    retrievedCity = new City(retCityId, retCityName, retCountryId, createDateConv, retCreatedBy, updateDateConv, retLastUpdateBy);
                    return retrievedCity;
                    
                    
                }
            }
            
        } catch (SQLException er) {
            System.out.println("Error: " + er.getMessage());

        } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
      
    public static ObservableList<City> getAllCities(Connection connection) throws SQLException, Exception {
        ObservableList<City> allCities = FXCollections.observableArrayList();
        String selectStatement = "SELECT * FROM city;";
        try {
            DBQuery.constructQuery(selectStatement, connection);
            City retrievedCity;
            ResultSet resultSet = DBQuery.getResultSet();
            if(resultSet.next() == false) {
                System.out.println("ResultSet is empty");
            }
            else {
                resultSet.beforeFirst();
                while(resultSet.next()) {
                    int retCityId = resultSet.getInt("cityId");
                    String retCityName = resultSet.getString("city");
                    int retCountryId = resultSet.getInt("countryId");
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

                    retrievedCity = new City(retCityId, retCityName, retCountryId, createDateConv, retCreatedBy, updateDateConv, retLastUpdateBy);
                    allCities.add(retrievedCity);
                }
                return allCities;
            }
            
        } catch (SQLException er) {
            System.out.println("Error: " + er.getMessage());

        } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
    
    public static void createCity(City city, User user, Connection connection) throws SQLException, Exception {
        String insertStatement = "INSERT INTO city(city, countryId, createDate, createdBy, lastUpdate, lastUpdateBy) \n" +
"	VALUE('" + city.getCity() + "', '"
                + city.getCountryId() + "', "
                + "CURRENT_TIMESTAMP, "
                + "'" + user.getUserName() + "', "
                + "CURRENT_TIMESTAMP, "
                + "'" + user.getUserName() + "');";
        
        DBQuery.constructQuery(insertStatement, connection);
    }
    
    public static void updateCity(City city, User user, Connection connection) throws SQLException, Exception {
        String cit, lastUpdateBy;
        int countryId, cityId;
        cit = city.getCity();
        lastUpdateBy = user.getUserName();
        cityId = city.getCityId();
        countryId = city.getCountryId();
        String updateStatement = "UPDATE city SET "
                + "city = '" + cit + "', "
                + "countryId = " + countryId + ", "
                + "lastUpdate = CURRENT_TIMESTAMP, "
                + "lastUpdateBy = '" + lastUpdateBy + "' "
                +"WHERE cityId = " + cityId + ";";
        
        DBQuery.constructQuery(updateStatement, connection);
    }
}
