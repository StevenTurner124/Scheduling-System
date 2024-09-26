package Scheduler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Creates prepares statements
 */

public class DBQuery {
    private static PreparedStatement statement;

    /**
     * Setter for PreparedStatement
     * @param connection
     * @param SQLStateement
     * @throws SQLException
     */


    public static void setStatement(Connection connection,String SQLStateement)throws SQLException{
        statement=connection.prepareStatement(SQLStateement);
    }

    /**
     * Getter for PreparedStatement
     * @return statement
     */


    public static PreparedStatement getStatement(){
        return statement;
    }
}
