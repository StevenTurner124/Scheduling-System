package Scheduler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SQL Ops for Users
 */

public class UserQuery {
    /**
     * Confirms whether the username and pw are valid
     * @param username
     * @param password
     * @return resultSet or false if no result
     * @throws SQLException
     */

    public static boolean usernamePwValid(String username, String password)throws SQLException{
        String sqlStatement="SELECT * FROM users WHERE User_Name=? AND Password=?;";
        DBQuery.setStatement(DBConnection.getConnection(), sqlStatement);
        PreparedStatement preparedStatement= DBQuery.getStatement();

        preparedStatement.setString(1,username);
        preparedStatement.setString(2,password);
        try{
            preparedStatement.execute();
            ResultSet resultSet= preparedStatement.getResultSet();
            return(resultSet.next());
        }catch(Exception exception){
            System.out.println("Error: "+ exception.getMessage());
            return false;
        }
    }

    /**
     * Returns all Users
     * @return users or null if empty
     * @throws SQLException
     */


    public static ObservableList<User> getUsers()throws SQLException{
        ObservableList<User>users=FXCollections.observableArrayList();
        String sqlStatement="SELECT * FROM users;";
        DBQuery.setStatement(DBConnection.getConnection(), sqlStatement);
        PreparedStatement preparedStatement= DBQuery.getStatement();

        try{
            preparedStatement.execute();
            ResultSet resultSet= preparedStatement.getResultSet();
            while (resultSet.next()){
                User newUser= new User(
                        resultSet.getInt("User_ID"),
                        resultSet.getString("User_Name"),
                        resultSet.getString("Password")
                );
                users.add(newUser);
            }
            return users;
        }catch(Exception exception){
            System.out.println("Error: "+ exception.getMessage());
            return null;
        }
    }
}
