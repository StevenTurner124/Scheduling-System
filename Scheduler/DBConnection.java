package Scheduler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Creates and destroys connection to DB
 */

public class DBConnection {
    private static final String protocol="jdbc";
    private static final String vendor= ":mysql:";
    private static final String ipAdd="//localhost:3306/";
    private static final String databaseName="appointment_scheduler";
    private static final String jdbcURL= "jdbc:mysql://localhost:3306/appointment_scheduler?connectionTimeZone=SERVER";
    private static String password="password1!";    private static final String Driver="com.mysql.cj.jdbc.Driver";
    private static final String userName="root";    private static Connection connection=null;
    public static Connection getConnection(){
        return connection;    }

    /**
     * Connection Starter to DB
     * @return connection
     */

    public static Connection connectStart(){
        try{        Class.forName(Driver);
            connection=DriverManager.getConnection(jdbcURL,userName,password);
            System.out.println("Successful Connecting");
        }catch (SQLException exception){
            System.out.println(exception.getMessage());
        }catch (ClassNotFoundException exception){
            System.out.println("Error: " +exception.getMessage());
        }
        return connection;
    }

    /**
     * Closes Connection to DB
     */
    public static void termConnection(){
        try{
            connection.close();
            System.out.println("Closed connection successfully");
        }catch(SQLException exception){
            System.out.println("Error: "+ exception.getMessage());
        }
    }
}