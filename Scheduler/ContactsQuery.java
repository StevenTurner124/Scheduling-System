package Scheduler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SQL Ops for Contacts
 */
public class ContactsQuery {

    /**
     * Fetches a list of Contact and App Objects joined by the Contact ID
     * @return null if list is empty
     * @throws SQLException
     */
    public static ObservableList<Contact> getContacts() throws SQLException {
        ObservableList<Contact> contacts = FXCollections.observableArrayList();

        String queryStatement = "SELECT * FROM contacts";

        DBQuery.setStatement(DBConnection.connectStart(), queryStatement);
        PreparedStatement preparedStatement = DBQuery.getStatement();

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();;

            while (resultSet.next()) {
                Contact newContact = new Contact(
                        resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name"),
                        resultSet.getString("Email")
                );

                contacts.add(newContact);
            }
            return contacts;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Fetches Contact by Contact Name
     * @param contactName
     * @return null if list is empty
     * @throws SQLException
     */
    public static Contact getContactId(String contactName) throws SQLException {
        String queryStatement = "SELECT * FROM contacts WHERE Contact_Name=?";

        DBQuery.setStatement(DBConnection.connectStart(), queryStatement);
        PreparedStatement preparedStatement = DBQuery.getStatement();

        preparedStatement.setString(1, contactName);


        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();;


            while (resultSet.next()) {
                Contact newContact = new Contact(
                        resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name"),
                        resultSet.getString("Email")
                );

                return newContact;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
}