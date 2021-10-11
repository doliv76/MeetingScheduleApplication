package DAOImplementation;

import static DAOImplementation.CustomerDAOImpl.active;
import Model.Customer;
import Model.CustomersTableRow;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBQuery;

public class CustomersTableRowDAOImpl {
    public static ObservableList<CustomersTableRow> getAllTableRows(Connection connection) throws SQLException, Exception {
        ObservableList<CustomersTableRow> allTableRows = FXCollections.observableArrayList();
        String sqlStatement = "SELECT \n" +
                            " customerName,\n" +
                            " address,\n" +
                            " postalCode,\n" +
                            " phone,\n" +
                            " city,\n" +
                            " country\n" +
                            "FROM\n" +
                            " customer\n" +
                            "INNER JOIN\n" +
                            " address USING (addressId)\n" +
                            "INNER JOIN\n" +
                            " city USING (cityId)\n" +
                            "INNER JOIN\n" +
                            " country USING (countryId);";
        try {
            DBQuery.constructQuery(sqlStatement, connection);
            CustomersTableRow retrievedCustomersTableRow;
            ResultSet resultSet = DBQuery.getResultSet();
            
            if(resultSet.next() == false) {
                System.out.println("ResultSet is empty");
            }
            else {
                resultSet.beforeFirst();
                while(resultSet.next()) {
                    String retCustomerName = resultSet.getString("customerName");
                    String retAddress = resultSet.getString("address");
                    String retPostalCode = resultSet.getString("postalCode");
                    String retPhone = resultSet.getString("phone");
                    String retCity = resultSet.getString("city");
                    String retCountry = resultSet.getString("country");
            
                    retrievedCustomersTableRow = new CustomersTableRow(retCustomerName, retAddress, retPostalCode, retPhone, retCity, retCountry);
                    allTableRows.add(retrievedCustomersTableRow);
                    
                    
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
}
