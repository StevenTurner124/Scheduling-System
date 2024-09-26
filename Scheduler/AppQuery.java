package Scheduler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * SQL Ops for Appointment Query
 */

public class AppQuery {
    public static ObservableList<Appointment> getAppointments() throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        String queryStatement = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID;";

        DBQuery.setStatement(DBConnection.connectStart(), queryStatement);
        PreparedStatement preparedStatement = DBQuery.getStatement();

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                Appointment newAppointment = new Appointment(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Contact_Name"),
                        resultSet.getString("Type"),
                        resultSet.getDate("Start").toLocalDate(),
                        resultSet.getDate("End").toLocalDate(),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Contact_ID"));


                appointments.add(newAppointment);
            }
            return appointments;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Last week worth of apps
     * @return null if empty
     * @throws SQLException if null
     */
    public static ObservableList<Appointment> getAppointmentsWeek() throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        LocalDateTime todaysDate = LocalDateTime.now();
        LocalDateTime lastWeek = todaysDate.plusDays(7);

        String queryStatement = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE Start >= ? AND Start <= ?;";

        DBQuery.setStatement(DBConnection.connectStart(), queryStatement);
        PreparedStatement preparedStatement = DBQuery.getStatement();

        preparedStatement.setDate(1, Date.valueOf(todaysDate.toLocalDate()));
        preparedStatement.setDate(2, Date.valueOf(lastWeek.toLocalDate()));

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                Appointment newAppointment = new Appointment(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Contact_Name"),
                        resultSet.getString("Type"),
                        resultSet.getDate("Start").toLocalDate(),
                        resultSet.getDate("End").toLocalDate(),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Contact_ID"));

                appointments.add(newAppointment);
            }
            return appointments;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Lists 30 days worth of apps
     * @return null if empty
     * @throws SQLException if null
     */
    public static ObservableList<Appointment> getMonthsApps() throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        LocalDateTime todaysDate = LocalDateTime.now();
        LocalDateTime lastMonth = todaysDate.plusDays(30);

        String queryStatement = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE Start >= ? AND Start <= ?;";

        DBQuery.setStatement(DBConnection.connectStart(), queryStatement);
        PreparedStatement preparedStatement = DBQuery.getStatement();

        preparedStatement.setDate(1, Date.valueOf(todaysDate.toLocalDate()));
        preparedStatement.setDate(2, Date.valueOf(lastMonth.toLocalDate()));

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                Appointment newAppointment = new Appointment(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Contact_Name"),
                        resultSet.getString("Type"),
                        resultSet.getDate("Start").toLocalDate(),
                        resultSet.getDate("End").toLocalDate(),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Contact_ID"));


                appointments.add(newAppointment);
            }
            return appointments;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }


    /**
     * Creates New App
     * @param id int
     * @param contactName String
     * @param title String
     * @param description String
     * @param location String
     * @param type String
     * @param start LocalDateTime
     * @param end LocalDateTime
     * @param customerId Integer
     * @param userID Integer
     * @return true or false depending on result set
     * @throws SQLException if app not created
     */
    public static boolean createnewApp(int id,String contactName, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, Integer customerId, Integer userID) throws SQLException {

        Contact contact = ContactsQuery.getContactId(contactName);

        String insertStatement = "INSERT INTO appointments(Appointment_ID,Title, Description, Location, Type, Start, End, Customer_ID, Contact_ID, User_ID) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?)";

        DBQuery.setStatement(DBConnection.connectStart(), insertStatement);
        PreparedStatement preparedStatement = DBQuery.getStatement();
        preparedStatement.setInt(1,id);
        preparedStatement.setString(2, title);
        preparedStatement.setString(3, description);
        preparedStatement.setString(4, location);
        preparedStatement.setString(5, type);
        preparedStatement.setTimestamp(6, Timestamp.valueOf(start));
        preparedStatement.setTimestamp(7, Timestamp.valueOf(end));
        preparedStatement.setInt(8, customerId);
        preparedStatement.setInt(9, contact.getContactID());
        preparedStatement.setInt(10, userID);

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
     * Updates APPID
     * @param contactName String
     * @param title String
     * @param description String
     * @param location String
     * @param type String
     * @param start LocalDateTime
     * @param end LocalDateTime
     * @param customerId Integer
     * @param userID Integer
     * @param appointmentID Integer
     * @return false if app isn't updated
     * @throws SQLException
     */
    public static boolean updApp(String contactName, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, Integer customerId, Integer userID, Integer appointmentID) throws SQLException {
        Contact contact = ContactsQuery.getContactId(contactName);

        String updateStatement = "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Customer_ID=?, Contact_ID=?, User_ID=? WHERE Appointment_ID = ?;";

        DBQuery.setStatement(DBConnection.connectStart(), updateStatement);
        PreparedStatement preparedStatement = DBQuery.getStatement();

        preparedStatement.setString(1, title);
        preparedStatement.setString(2, description);
        preparedStatement.setString(3, location);
        preparedStatement.setString(4, type);
        preparedStatement.setTimestamp(5, Timestamp.valueOf(start));
        preparedStatement.setTimestamp(6, Timestamp.valueOf(end));
        preparedStatement.setInt(7, customerId);
        preparedStatement.setInt(8, contact.getContactID());
        preparedStatement.setInt(9, userID);
        preparedStatement.setInt(10, appointmentID);

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
     * Delete Apps by APPID
     * @param appointmentId
     * @return true if deletion is valid, false if not
     * @throws SQLException
     */
    public static boolean deleteApp(int appointmentId) throws SQLException {
        String insertStatement = "DELETE from appointments WHERE Appointment_Id=?";

        DBQuery.setStatement(DBConnection.connectStart(), insertStatement);
        PreparedStatement preparedStatement = DBQuery.getStatement();

        preparedStatement.setInt(1, appointmentId);

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
     * Fetches an App by Contact ID
     * @param contactID
     * @return appointments by contact ID if found
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAppsByContactID(int contactID) throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        String queryStatement = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE a.Contact_ID=?;";

        DBQuery.setStatement(DBConnection.connectStart(), queryStatement);
        PreparedStatement preparedStatement = DBQuery.getStatement();

        preparedStatement.setInt(1, contactID);

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();


            while (resultSet.next()) {
                Appointment newAppointment = new Appointment(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Contact_Name"),
                        resultSet.getString("Type"),
                        resultSet.getDate("Start").toLocalDate(),
                        resultSet.getDate("End").toLocalDate(),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Contact_ID"));

                appointments.add(newAppointment);
            }
            return appointments;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }


    /**
     * Fetches App by Cust ID
     * @param CustomerID
     * @return  apps by cust ID if found
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAppsByCustID(int CustomerID) throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        String queryStatement = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE Customer_ID=?;";

        DBQuery.setStatement(DBConnection.connectStart(), queryStatement);
        PreparedStatement preparedStatement = DBQuery.getStatement();

        preparedStatement.setInt(1, CustomerID);

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();


            while (resultSet.next()) {
                Appointment newAppointment = new Appointment(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Contact_Name"),
                        resultSet.getString("Type"),
                        resultSet.getDate("Start").toLocalDate(),
                        resultSet.getDate("End").toLocalDate(),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Contact_ID"));

                appointments.add(newAppointment);
            }
            return appointments;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }


    /**
     * Fetches an App by App ID
     * @param AppointmentID
     * @return app  by APP ID
     * @throws SQLException
     */
    public static Appointment getAppByAppID(int AppointmentID) throws SQLException {

        String queryStatement = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE Appointment_ID=?;";

        DBQuery.setStatement(DBConnection.connectStart(), queryStatement);
        PreparedStatement preparedStatement = DBQuery.getStatement();

        preparedStatement.setInt(1, AppointmentID);

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();


            while (resultSet.next()) {
                Appointment newAppointment = new Appointment(
                        resultSet.getInt("Appointment_ID"),
                        resultSet.getInt("Customer_ID"),
                        resultSet.getInt("User_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Description"),
                        resultSet.getString("Location"),
                        resultSet.getString("Contact_Name"),
                        resultSet.getString("Type"),
                        resultSet.getDate("Start").toLocalDate(),
                        resultSet.getDate("End").toLocalDate(),
                        resultSet.getTimestamp("Start").toLocalDateTime(),
                        resultSet.getTimestamp("End").toLocalDateTime(),
                        resultSet.getInt("Contact_ID"));


                return newAppointment;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
}
