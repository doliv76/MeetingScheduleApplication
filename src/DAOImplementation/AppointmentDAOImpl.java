package DAOImplementation;

import Model.Appointment;
import Model.Customer;
import Model.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBQuery;

public class AppointmentDAOImpl {
    public static Appointment getAppointmentByTypeAndStart(String type, String start, Connection connection) throws SQLException, Exception {
        String selectStatement = "SELECT * FROM appointment WHERE type = '"
                                    + type + "' " 
                                    + " AND start = '" + start +"';";
        try {
            DBQuery.constructQuery(selectStatement, connection);
            Appointment retrievedAppointment;
            ResultSet resultSet = DBQuery.getResultSet();
            
            if(resultSet.next() == false) {
                System.out.println("ResultSet is empty");
            }
            else {
                resultSet.beforeFirst();
                while(resultSet.next()) {
                    int retAppointmentId = resultSet.getInt("appointmentId");
                    int retCustomerId = resultSet.getInt("customerId");
                    int retUserId = resultSet.getInt("userId");
                    String retTitle = resultSet.getString("title");
                    String retDescription = resultSet.getString("description");
                    String retLocation = resultSet.getString("location");
                    String retContact = resultSet.getString("contact");
                    String retType = resultSet.getString("type");
                    String retStart = resultSet.getString("start");
                    String retEnd = resultSet.getString("end");
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
            
                    retrievedAppointment = new Appointment(retAppointmentId, retCustomerId, retUserId, retTitle, retDescription, retLocation, retContact,  retType, "url", retStart, retEnd, createDateConv, retCreatedBy, updateDateConv, retLastUpdateBy);
                    return retrievedAppointment;
                    
                    
                }
            }
            
        } catch (SQLException er) {
            System.out.println("Error: " + er.getMessage());

        } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
        }
        return null;
        
    }
   
    public static ObservableList<Appointment> getAllAppointments(Connection connection) throws SQLException, Exception {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String selectStatement = "SELECT * FROM appointment;";
        try {
            DBQuery.constructQuery(selectStatement, connection);
            Appointment retrievedAppointment;
            ResultSet resultSet = DBQuery.getResultSet();
            
            if(resultSet.next() == false) {
                System.out.println("ResultSet is empty");
            }
            else {
                resultSet.beforeFirst();
                while(resultSet.next()) {
                    int retAppointmentId = resultSet.getInt("appointmentId");
                    int retCustomerId = resultSet.getInt("customerId");
                    int retUserId = resultSet.getInt("userId");
                    String retTitle = resultSet.getString("title");
                    String retDescription = resultSet.getString("description");
                    String retLocation = resultSet.getString("location");
                    String retContact = resultSet.getString("contact");
                    String retType = resultSet.getString("type");
                    String retStart = resultSet.getString("start");
                    String retEnd = resultSet.getString("end");
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
            
                    retrievedAppointment = new Appointment(retAppointmentId, retCustomerId, retUserId, retTitle, retDescription, retLocation, retContact,  retType, "url", retStart, retEnd, createDateConv, retCreatedBy, updateDateConv, retLastUpdateBy);
                    allAppointments.add(retrievedAppointment);
                    
                    
                }
                return allAppointments;
            }
            
        } catch (SQLException er) {
            System.out.println("Error: " + er.getMessage());

        } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
        }
        return null;
        
    }
    
    public static void createAppointment (Appointment appointment, User user, Customer customer, Connection connection) throws SQLException, Exception {

        String insertStatement = "INSERT INTO appointment(customerId, userId, title, description, location, contact, `type`, url, `start`, `end`, createDate, createdBy, lastUpdate, lastUpdateBy) \n" +
            "VALUES(" + customer.getCustomerId() + ", "
                 + user.getUserId() + ", "
                 + "'" + appointment.getTitle() + "', "
                 + "'" + appointment.getDescription() + "', "
                 + "'" + appointment.getLocation() + "', "
                 + "'" + appointment.getContact() + "', "
                 + "'" + appointment.getType() + "', "
                 + "'https://blah.com', "
                 + "'" + convertToUTC(appointment.getStart()) + "', "
                 + "'" + convertToUTC(appointment.getEnd()) + "', "
                 + "current_timestamp(), "
                 + "'" + user.getUserName() + "', "
                 + "current_timestamp(), "
                 + "'" + user.getUserName() + "');";
         
        System.out.println(insertStatement);
         DBQuery.constructQuery(insertStatement, connection);
     }
    
    public static void updateAppointment(Appointment appointment, Customer customer, User user, Connection connection) throws SQLException, Exception {
        
        String updateStatement = "UPDATE appointment SET "
                + "customerId = " + customer.getCustomerId() + ", "
                + "userId = " + user.getUserId() + ", "
                + "title = '" + appointment.getTitle() + "', "
                + "description = '" + appointment.getDescription() + "', "
                + "location = '" + appointment.getLocation() + "', "
                + "contact = '" + appointment.getContact() + "', "
                + "`type` = '" + appointment.getType() + "', "
                + "url = '" + appointment.getUrl() + "', "
                + "`start` = '" + convertFromUTC(appointment.getStart()) + "', "
                + "`end` = '" + convertFromUTC(appointment.getEnd()) + "', "
                + "lastUpdate = current_timestamp(), "
                + "lastUpdateBy = '" + user.getUserName() + "'"
                + "WHERE appointmentId = " + appointment.getAppointmentId() + ";";
        
        DBQuery.constructQuery(updateStatement, connection);
    }
    
    public static void deleteAppointment(Appointment appointment, Connection connection) throws SQLException, Exception {
        String deleteStatement = "DELETE FROM appointment WHERE appointmentId = " + appointment.getAppointmentId() + ";";
        DBQuery.constructQuery(deleteStatement, connection);
    }
        
    private static String convertToUTC(String resultSetString) {
        ZoneId zoneId = ZoneId.systemDefault();
        DateTimeFormatter dFor = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");        
        LocalDateTime inputLDT, convLDT;
        
        inputLDT = LocalDateTime.parse(resultSetString, dFor);
        
        ZonedDateTime inputZDT = inputLDT.atZone(zoneId);
        
        OffsetDateTime inputODT = inputZDT.withZoneSameInstant(ZoneId.of("UTC")).toOffsetDateTime();
                
        convLDT = inputODT.toLocalDateTime();
        
        resultSetString = dFor.format(convLDT);
        
        return resultSetString;
    }
    
    private static String convertFromUTC(String resultSetString) {
        ZoneId zoneId = ZoneId.systemDefault();
        DateTimeFormatter dFor = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.s");        
        LocalDateTime inputLDT, convLDT;
        
        inputLDT = LocalDateTime.parse(resultSetString, dFor);
        
        ZonedDateTime inputZDT = inputLDT.atZone(zoneId);
        
        OffsetDateTime inputODT = inputZDT.withZoneSameInstant(ZoneId.of("UTC")).toOffsetDateTime();
                
        convLDT = inputODT.toLocalDateTime();
        
        resultSetString = dFor.format(convLDT);
        
        return resultSetString;
    }
}
