package Scheduler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SQL Ops made on Country.
 */
public class CountryQuery {

    /**
     * Fetches List of Country Obj
     * @return country list or null if empty
     * @throws SQLException
     */
    public static ObservableList<Country> getCountries() throws SQLException {
        ObservableList<Country> countries = FXCollections.observableArrayList();

        String searchStatement = "SELECT * FROM countries;";

        DBQuery.setStatement(DBConnection.getConnection(), searchStatement);
        PreparedStatement preparedStatement = DBQuery.getStatement();

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
/**
 * Forward scroll resultSet
 */

            while (resultSet.next()) {

                Country newCountry = new Country(
                        resultSet.getInt("Country_ID"),
                        resultSet.getString("Country")
                );

                countries.add(newCountry);
            }
            return countries;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
public static String countryName(int id) throws SQLException{
    String queryStatement= "SELECT * FROM countries WHERE Country_ID=?;";
    DBQuery.setStatement((DBConnection.connectStart()), queryStatement);
    PreparedStatement preparedStatement = DBQuery.getStatement();
    preparedStatement.setInt(1,id);
    try{
        preparedStatement.execute();
        ResultSet resultSet=preparedStatement.getResultSet();

        while(resultSet.next()) {
            String country=resultSet.getString("Country");
            return country;
        }
    }catch (Exception exception){
        System.out.println("Error"+ exception.getMessage());
    }
    return null;
}
    /**
     * Fetches Country by Country Name
     * @param country
     * @return newCountry or null if empty
     * @throws SQLException
     */
    public static Country getCountryId(String country) throws SQLException {

        String queryStatement = "SELECT * FROM countries WHERE Country=?";

        DBQuery.setStatement(DBConnection.getConnection(), queryStatement);
        PreparedStatement preparedStatement = DBQuery.getStatement();

        preparedStatement.setString(1, country);

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                Country newCountry = new Country(
                        resultSet.getInt("Country_ID"),
                        resultSet.getString("Country")
                );
                return newCountry;
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
}
