package Scheduler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SQL Ops made on Customer.
 */
public class CustomersQuery {

    /**
     * Fetches Custs and First-Level-Div joined by the Division ID
     * @return customers otherwise null if empty
     * @throws SQLException
     */
    public static ObservableList<Customer> getCustomers() throws SQLException {
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        String searchStatement = "SELECT * FROM customers AS c INNER JOIN first_level_divisions AS d ON c.Division_ID = d.Division_ID INNER JOIN countries AS co ON co.Country_ID=d.COUNTRY_ID;";

        DBQuery.setStatement(DBConnection.connectStart(), searchStatement);
        PreparedStatement preparedStatement = DBQuery.getStatement();

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();


            while (resultSet.next()) {

                Customer newCustomer = new Customer(
                        resultSet.getInt("Customer_ID"),
                        resultSet.getString("Customer_Name"),
                        resultSet.getString("Address"),
                        resultSet.getString("Postal_Code"),
                        resultSet.getString("Phone"),
                        resultSet.getString("Division"),
                        resultSet.getInt("Division_ID"),
                        resultSet.getString("Country"),
                        resultSet.getString("Age"),
                        resultSet.getInt("VIP"));



                customers.add(newCustomer);
            }
            return customers;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Creates a new Customer
     * @param name
     * @param address
     * @param postalCode
     * @param phone
     * @param division
     * @return true if created otherwise false if not
     * @throws SQLException
     */
    public static boolean createCust(String name, String address, String postalCode, String phone, String division,String Age,int vip) throws SQLException {

        int custID=getNewCustID();
        Division newDivision = DivisionQuery.getDivID(division);

        String insertStatement = "INSERT INTO customers(Customer_ID,Customer_Name, Address, Postal_Code, Phone, Division_ID,Age,VIP) VALUES (?,?, ?, ?, ?, ?,?,?)";

        DBQuery.setStatement(DBConnection.connectStart(), insertStatement);
        PreparedStatement preparedStatement = DBQuery.getStatement();
        preparedStatement.setInt(1,custID);
        preparedStatement.setString(2, name);
        preparedStatement.setString(3, address);
        preparedStatement.setString(4, postalCode);
        preparedStatement.setString(5, phone);
        preparedStatement.setInt(6, newDivision.getDivID());
        preparedStatement.setString(7,Age);
        preparedStatement.setInt(8,vip);

        try {
            preparedStatement.execute();
            if (preparedStatement.getUpdateCount() > 0) {
                System.out.println("Rows affected: " + preparedStatement.getUpdateCount());
            } else {
                System.out.println("No change");
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Upd cust based on custID
     * @param customerId
     * @param name
     * @param address
     * @param postalCode
     * @param phone
     * @param division
     * @return true if updated or false if not
     * @throws SQLException
     */
    public static boolean updateCust(int customerId, String name, String address, String postalCode, String phone, String division, String Age,int vip) throws SQLException {
        Division newDivision = DivisionQuery.getDivID(division);

        String insertStatement = "UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Division_ID=?, Age=?,VIP=? WHERE Customer_ID=?";

        DBQuery.setStatement(DBConnection.connectStart(), insertStatement);
        PreparedStatement preparedStatement = DBQuery.getStatement();

        preparedStatement.setString(1, name);
        preparedStatement.setString(2, address);
        preparedStatement.setString(3, postalCode);
        preparedStatement.setString(4, phone);
        preparedStatement.setInt(5, newDivision.getDivID());
        preparedStatement.setString(6,Age);
        preparedStatement.setInt(7,vip);
        preparedStatement.setInt(8, customerId);

        try {
            preparedStatement.execute();
            if (preparedStatement.getUpdateCount() > 0) {
                System.out.println("Rows affected: " + preparedStatement.getUpdateCount());
            } else {
                System.out.println("No change");
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    /**
     *  Delete Existing Customer
     * @param customerId
     * @return true if deleted otherwise false if not
     * @throws SQLException
     */
    public static boolean deleteCust(int customerId) throws SQLException {
        String insertStatement = "DELETE from customers WHERE Customer_Id=?";

        DBQuery.setStatement(DBConnection.connectStart(), insertStatement);
        PreparedStatement preparedStatement = DBQuery.getStatement();

        preparedStatement.setInt(1, customerId);

        try {
            preparedStatement.execute();
            if (preparedStatement.getUpdateCount() > 0) {
                System.out.println("Rows affected: " + preparedStatement.getUpdateCount());
            } else {
                System.out.println("No change");
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
    public static int getNewCustID() throws SQLException{
        String searchStatement="SELECT MAX(Customer_ID) FROM customers";
        DBQuery.setStatement(DBConnection.connectStart(), searchStatement);
        PreparedStatement preparedStatement = DBQuery.getStatement();
        int custID=-1;
        try{
            preparedStatement.execute();
            ResultSet resultSet=preparedStatement.getResultSet();

            while (resultSet.next()){
                custID=resultSet.getInt("MAX(Customer_ID)");
                return custID+1;
            }
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return -1;
        }
        return custID;
    }
    public static Customer getCustomerbyID(int customerId) throws SQLException{
        String searchStatement="SELECT * FROM customers WHERE Customer_ID=?";

        DBQuery.setStatement(DBConnection.connectStart(), searchStatement);
        PreparedStatement preparedStatement = DBQuery.getStatement();
        preparedStatement.setInt(1,customerId);
        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            Customer fetchedCustomer=null;
            while (resultSet.next()) {
            int divID=resultSet.getInt("Division_ID");
                String divName = DivisionQuery.getDivName(divID);
                int countryID=DivisionQuery.getCountryID(divID);
                String country=CountryQuery.countryName(countryID);
                fetchedCustomer = new Customer(
                        resultSet.getInt("Customer_ID"),
                        resultSet.getString("Customer_Name"),
                        resultSet.getString("Address"),
                        resultSet.getString("Postal_Code"),
                        resultSet.getString("Phone"),
                         divName,
                        divID,
                        country,
                        resultSet.getString("Age"),
                        resultSet.getInt("VIP"));


            }
            return fetchedCustomer;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }





    }

}
