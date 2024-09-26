package Scheduler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Constructs Division Query Class
 */
public class DivisionQuery {

    //Fetches List of Divs//
    public static ObservableList<Division> getDivs() throws SQLException{
        ObservableList<Division>divisions=FXCollections.observableArrayList();

        String queryStatement="SELECT * FROM first_level_divisions;";

        DBQuery.setStatement(DBConnection.connectStart(), queryStatement);
        PreparedStatement preparedStatement= DBQuery.getStatement();

        try{
            preparedStatement.execute();
            ResultSet resultSet= preparedStatement.getResultSet();
            while(resultSet.next()){

                Division newDiv=new Division(
                        resultSet.getInt("Division_ID"),
                        resultSet.getString("Division"),
                        resultSet.getInt("Country_ID"));


                divisions.add(newDiv);
            }
            return divisions;
        }catch (Exception exception){
            System.out.println("Error: " + exception.getMessage());
            return null;
        }
    }

    /***
     * Fetches Div by Div Name
     * @param division
     * @return newDiv otherwise null if empty
     * @throws SQLException
     */

    public static Division getDivID(String division) throws SQLException{
        String queryStatement= "SELECT * FROM first_level_divisions WHERE Division=?;";
        DBQuery.setStatement((DBConnection.connectStart()), queryStatement);
        PreparedStatement preparedStatement = DBQuery.getStatement();
        preparedStatement.setString(1,division);
        try{
            preparedStatement.execute();
            ResultSet resultSet=preparedStatement.getResultSet();

            while(resultSet.next()) {
                Division newDiv = new Division(
                        resultSet.getInt("Division_ID"),
                        resultSet.getString("Division"),
                        resultSet.getInt("Country_ID"));




                return newDiv;
            }
        }catch (Exception exception){
            System.out.println("Error"+ exception.getMessage());
        }
        return null;
    }
    public static int getCountryID(int id)throws SQLException{
        String queryStatement= "SELECT * FROM first_level_divisions WHERE Division_ID=?;";
        DBQuery.setStatement((DBConnection.connectStart()), queryStatement);
        PreparedStatement preparedStatement = DBQuery.getStatement();
        preparedStatement.setInt(1,id);
        try{
            preparedStatement.execute();
            ResultSet resultSet=preparedStatement.getResultSet();

            while(resultSet.next()) {
                int countryID=resultSet.getInt("Country_ID");
                return countryID;
            }
        }catch (Exception exception){
            System.out.println("Error"+ exception.getMessage());
        }
        return -1;
    }
    public static String getDivName(int id)throws SQLException{
        String queryStatement= "SELECT * FROM first_level_divisions WHERE Division_ID=?;";
        DBQuery.setStatement((DBConnection.connectStart()), queryStatement);
        PreparedStatement preparedStatement = DBQuery.getStatement();
        preparedStatement.setInt(1,id);
        try{
            preparedStatement.execute();
            ResultSet resultSet=preparedStatement.getResultSet();

            while(resultSet.next()) {
                String div=resultSet.getString("Division");
                return div;
            }
        }catch (Exception exception){
            System.out.println("Error"+ exception.getMessage());
        }
        return null;
    }

    /**
     * Retrieves list of Divs by Country
     * @param country
     * @return divisions otherwise null if empty
     * @throws SQLException
     */


    public static ObservableList<Division>getDivByCountry(String country)throws SQLException{
        Country newCountry= CountryQuery.getCountryId(country);
        ObservableList<Division>divisons=FXCollections.observableArrayList();
        String queryStatement="SELECT * FROM first_level_divisions WHERE COUNTRY_ID=?;";
        DBQuery.setStatement(DBConnection.connectStart(),queryStatement);
        PreparedStatement preparedStatement=DBQuery.getStatement();
        preparedStatement.setInt(1,newCountry.getCountryID());
        try{
            preparedStatement.execute();
            ResultSet resultSet= preparedStatement.getResultSet();

            while(resultSet.next()){
                Division newDiv= new Division(
                        resultSet.getInt("Division_ID"),
                        resultSet.getString("Division"),
                        resultSet.getInt("Country_ID"));


                divisons.add(newDiv);
            }
            return divisons;
        }catch (Exception exception){
            System.out.println("Error: "+ exception.getMessage());
            return null;
        }
    }
}
