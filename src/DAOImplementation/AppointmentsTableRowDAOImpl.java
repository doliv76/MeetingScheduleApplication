package DAOImplementation;

import Model.AppointmentsTableRow;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBQuery;

/**
 *
 * @author upont
 */
public class AppointmentsTableRowDAOImpl {
     public static ObservableList<AppointmentsTableRow> getAllTableRows(Connection connection) throws SQLException, Exception {
        ObservableList<AppointmentsTableRow> allTableRows = FXCollections.observableArrayList();
        String sqlStatement = "SELECT \n" +
                            " customerName,\n" +
                            " userName, \n" +
                            " description, \n" +
                            " location, \n" +
                            " `type`,\n" +
                            " `start`, \n" +
                            " `end`\n" +
                            "FROM\n" +
                            " appointment\n" +
                            "INNER JOIN\n" +
                            " customer USING (customerId)\n" +
                            "INNER JOIN\n" +
                            " `user` USING (userId);";
        try {
            DBQuery.constructQuery(sqlStatement, connection);
            AppointmentsTableRow retrievedAppointmentsTableRow;
            ResultSet resultSet = DBQuery.getResultSet();
            
            if(resultSet.next() == false) {
                System.out.println("ResultSet is empty");
            }
            else {
                resultSet.beforeFirst();
                while(resultSet.next()) {
                    String retCustomerName = resultSet.getString("customerName");
                    String retConstultantName = resultSet.getString("userName");
                    String retDescription = resultSet.getString("description");
                    String retLocation = resultSet.getString("location");
                    String retType = resultSet.getString("type");
                    String retStart = convertToLocal(resultSet.getString("start"));
                    String retEnd = convertToLocal(resultSet.getString("end"));
                    retrievedAppointmentsTableRow = new AppointmentsTableRow(retCustomerName, retConstultantName, retDescription, retLocation, retType, retStart, retEnd);
                    allTableRows.add(retrievedAppointmentsTableRow);
                    
                    
                }
                return allTableRows;
            }
            
        } catch (SQLException er) {
            System.out.println("Error: " + er.getMessage());

        } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
     
     public static ObservableList<AppointmentsTableRow> getUserTableRows(Connection connection, int userId) throws SQLException, Exception {
        ObservableList<AppointmentsTableRow> allTableRows = FXCollections.observableArrayList();
        String sqlStatement = "SELECT \n" +
                            " customerName,\n" +
                            " userName, \n" +
                            " description, \n" +
                            " location, \n" +
                            " `type`,\n" +
                            " `start`, \n" +
                            " `end`\n" +
                            "FROM\n" +
                            " appointment\n" +
                            "INNER JOIN\n" +
                            " customer USING (customerId)\n" +
                            "INNER JOIN\n" +
                            " `user` USING (userId)\n" +
                            " WHERE userId = " + userId + ";";
        try {
            DBQuery.constructQuery(sqlStatement, connection);
            AppointmentsTableRow retrievedAppointmentsTableRow;
            ResultSet resultSet = DBQuery.getResultSet();
            
            if(resultSet.next() == false) {
                System.out.println("ResultSet is empty");
            }
            else {
                resultSet.beforeFirst();
                while(resultSet.next()) {
                    String retCustomerName = resultSet.getString("customerName");
                    String retConstultantName = resultSet.getString("userName");
                    String retDescription = resultSet.getString("description");
                    String retLocation = resultSet.getString("location");
                    String retType = resultSet.getString("type");
                    String retStart = convertToLocal(resultSet.getString("start"));
                    String retEnd = convertToLocal(resultSet.getString("end"));
                    retrievedAppointmentsTableRow = new AppointmentsTableRow(retCustomerName, retConstultantName, retDescription, retLocation, retType, retStart, retEnd);
                    allTableRows.add(retrievedAppointmentsTableRow);
                    
                    
                }
                return allTableRows;
            }
            
        } catch (SQLException er) {
            System.out.println("Error: " + er.getMessage());

        } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
     
     
    private static String convertToLocal(String resultSetString) {
        ZoneId zoneId = ZoneId.systemDefault();
        DateTimeFormatter dFor = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.s");        
        LocalDateTime inputLDT, convLDT;
        
        //Parse ResultSet String into LocalDateTime object accordint to dFor DateTimeFormatter Pattern
        inputLDT = LocalDateTime.parse(resultSetString, dFor);
        
        //Create ZonedDateTime object to convert inputLDT to a ZDT to store UTC DATETIME info from DB
        ZonedDateTime inputZDT = inputLDT.atZone(ZoneOffset.UTC);
        
        //Create OffsetDateTime to offset the DateTime to system local for displaying in the UI
        OffsetDateTime inputODT = inputZDT.withZoneSameInstant(ZoneId.of(zoneId.getId())).toOffsetDateTime();
        
        //Convert inputODT to a LocalDateTime to format back into a string displayable by the UI
        convLDT = inputODT.toLocalDateTime();
        
        //Format TimeZone-adjust convLDT to the resultSetString for passing back to the caller
        resultSetString = dFor.format(convLDT);

        return resultSetString;
    }
}
