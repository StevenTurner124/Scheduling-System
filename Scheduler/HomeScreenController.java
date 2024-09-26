package Scheduler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static Scheduler.LoginController.resourceBundle;

/**
 * Creates Activity Log interface allowing the log of login attempts
 */
interface ActivityLog{
    public String getFileName();
}
public class HomeScreenController {
    //Lambda Expression//
    ActivityLog activityLogged=() ->

    {
        return "loginLog";
    };
    @FXML
    private Button custButton;
    @FXML
    private Button reportsbutton;
    @FXML
    private Button appbutton;
    @FXML
    private Button logoutbutton;

    /**
     * Enables Log out use
     * @param event
     */

    @FXML
    void onActionLogout(ActionEvent event) {
        createFile();
        loggedOut();
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            URL mainScreenLocation = LoginController.class.getResource("LogIn.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(mainScreenLocation);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setTitle(resourceBundle.getString("login"));
            stage.setScene(scene);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("loadError"));
            alert.showAndWait();
        }
    }

    /**
     * Writes Log out activity to file
     */


    private void loggedOut(){
        try{
            FileWriter fileWriter=new FileWriter(activityLogged.getFileName(), true);
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat(("dd-MM-yyyy HH:mm:ss"));
            Date date=new Date((System.currentTimeMillis()));
            fileWriter.write("Logout Successful: "+ simpleDateFormat.format(date)+"\n");
            fileWriter.close();
        }catch(IOException exception){
            exception.printStackTrace();
        }
    }
    private void createFile() {
        try {
            File newFile = new File(activityLogged.getFileName());
            if (newFile.createNewFile()) {
                System.out.println(("File was created:" + newFile.getPath()));
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Allows use of applications button
     * @param event
     */
    @FXML
    void onActionApps(ActionEvent event){
        try{
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            URL mainScreenLocation=null;
            if(Locale.getDefault().getLanguage().equals("es")) {
                mainScreenLocation = HomeScreenController.class.getResource("Appointments(ES).fxml");
            }
            else {
                mainScreenLocation = AppointmentViewController.class.getResource("Appointments.fxml");
            }
            FXMLLoader fxmlLoader = new FXMLLoader(mainScreenLocation);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setTitle(resourceBundle.getString("apps"));
            stage.setScene(scene);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("loadError"));
            alert.showAndWait();
        }
    }

    /**
     * Goes to Customer View
     * @param event
     */
    @FXML
    void onActionCustomers(ActionEvent event){
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            URL mainScreenLocation=null;
            if(Locale.getDefault().getLanguage().equals("es")) {
                mainScreenLocation = HomeScreenController.class.getResource("CustomerView(ES).fxml");
            }
            else {
                mainScreenLocation = CustController.class.getResource("CustomerView.fxml");
            }
            FXMLLoader fxmlLoader = new FXMLLoader(mainScreenLocation);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setTitle(resourceBundle.getString("patients"));
            stage.setScene(scene);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("loadError"));
            alert.showAndWait();
        }
    }

    /**
     *  Goes to Reports
     * @param event
     */

    @FXML
    void onActionReports(ActionEvent event){
        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            URL mainScreenLocation=null;
            if(Locale.getDefault().getLanguage().equals("es")) {
                mainScreenLocation = HomeScreenController.class.getResource("Reports(ES).fxml");
            }
            else {
                mainScreenLocation = ReportsController.class.getResource("Reports.fxml");
            }
            FXMLLoader fxmlLoader = new FXMLLoader(mainScreenLocation);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setTitle(resourceBundle.getString("reports"));
            stage.setScene(scene);
            stage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("loadError"));
            alert.showAndWait();
        }
    }
}
