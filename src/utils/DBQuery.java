package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author upont
 */
public class DBQuery {
    private static String rawQuery;
    private static PreparedStatement prepStatement;
    private static ResultSet resultSet;
    
    public static void constructQuery(String query, Connection connection) {
        rawQuery = query;
        try {
            prepStatement = connection.prepareStatement(query);
        
            //Determine whether query is a SELECT Statement
            if(rawQuery.toUpperCase().startsWith("SELECT")) {
                prepStatement.execute(query);
                resultSet = prepStatement.getResultSet();
            }
            
            //Check against other available SQL statements
            if(rawQuery.toUpperCase().startsWith("DELETE")|| rawQuery.toUpperCase().startsWith("INSERT") || rawQuery.toUpperCase().startsWith("UPDATE"))
                prepStatement.execute(query);
                resultSet = prepStatement.getResultSet();
            
        } catch(SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
        
    public static ResultSet getResultSet() {
        return resultSet;
    }
    
    }
