package utils;
//This class is used to set up a connection to the JDBC URL and gain access 
//to interfaces for making a connection

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author upont
 */
public class DBConnection {
    
    //JDBC URL parts:
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String ipadd = "//wgudb.ucertify.com/U077yn";
    
    //JDBC URL
    private static final String jdbcURL = protocol + vendor + ipadd;
    
    //Driver and Connection Interface reference
    private static final String MYSQLJDBCDriver = "com.mysql.jdbc.Driver";
    private static Connection connection = null;
    
    //Username    
    private static final String username = "U077yn";
    
    //Password
    private static final String password = "53688958693";
    
    //
    public static Connection startConnection() {
        try {
           Class.forName(MYSQLJDBCDriver); 
        //Establish DB Connection
            connection = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection successful");
        } catch(ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return connection;
    }
    
    public static Connection getConnectionReference() {
        return connection;
    }
    
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed");
        } catch(SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
            
        }
}