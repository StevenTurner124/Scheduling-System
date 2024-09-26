package Scheduler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Allows use of logger for login attempts
 */
interface ActivityLogged{
    public  String getFile();


}

/**
 * Constructor and initializer
 */
public class LoginController  implements Initializable {
    //Lambda sends Log activity file to newFile, successfulLog, logFailed.//
    ActivityLogged activityLogged=() -> {
        return "loginLog";
    };
    public static ResourceBundle resourceBundle;
    @FXML
    public TextField usernamebox;
    @FXML
    public Label usernameLabel;
    @FXML
    public Label countrylabel;
    @FXML
    public PasswordField pwbox;
    @FXML
    public Label pwLabel;
    @FXML
    public Button loginbutton;
    @FXML
    public Label zoneidLabel;
    @FXML
    public Pane mainscreen;
    @FXML
    public Label timezonelabel;

    /**Allows use of log in button and validates password and username. Logs attempt.
     * @param event
     */
    @FXML
    void onActionLogIn(ActionEvent event) {
        usernameFilledIn(usernamebox.getText());
        passwordFilledIn(pwbox.getText());
        newfile();
        try {
            boolean valid = UserQuery.usernamePwValid(usernamebox.getText(), pwbox.getText());
            if (valid) {
                successfullLog();

                try {
                    Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    URL mainScreenLocation=null;
                    if(Locale.getDefault().getLanguage().equals("es")){
                        mainScreenLocation=HomeScreenController.class.getResource("Main(ES).fxml");
                    }
                    else {
                         mainScreenLocation = HomeScreenController.class.getResource("Main.fxml");
                    }
                        FXMLLoader fxmlLoader = new FXMLLoader(mainScreenLocation);
                        Parent root = fxmlLoader.load();
                        Scene scene = new Scene(root);
                        stage.setTitle(resourceBundle.getString("homeScreen"));
                        stage.setScene(scene);
                        stage.show();
                } catch (Exception exception) {
                    exception.printStackTrace();

                    if (Locale.getDefault().getLanguage().equals("es") || Locale.getDefault().getLanguage().equals("en")) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle(resourceBundle.getString("error"));
                        alert.setContentText(resourceBundle.getString("errorLoading"));
                        alert.showAndWait();
                    }
                }

            } else {
                logFailed();
                if (Locale.getDefault().getLanguage().equals("es") || Locale.getDefault().getLanguage().equals("en")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(resourceBundle.getString("error"));
                    alert.setContentText(resourceBundle.getString("infoIncorrect"));
                    alert.showAndWait();
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Creates login activity list if one does not already exist
     * @throws IOException
     */
    private void newfile(){
        try{
            File createFile=new File(activityLogged.getFile());
            if(createFile.createNewFile()){
                System.out.println("File created:"+createFile.getPath());
            }
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }

    /**
     * Validates Username is filled in
     * @param username
     */

    private void usernameFilledIn(String username) {
        if (username.isEmpty()) {
            if (Locale.getDefault().getLanguage().equals("es") || Locale.getDefault().getLanguage().equals("en")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(resourceBundle.getString("error"));
                alert.setContentText(resourceBundle.getString("needUserName"));
                alert.showAndWait();
            }
        }
    }

    /**
     * Validates Password is filled in
     * @param password
     */

    private void passwordFilledIn(String password) {
        if (password.isEmpty()) {
            if (Locale.getDefault().getLanguage().equals("es") || Locale.getDefault().getLanguage().equals("en")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(resourceBundle.getString("error"));
                alert.setContentText(resourceBundle.getString("needPW"));
                alert.showAndWait();
            }
        }
    }

    /**
     * Reminder for appointment on log in checker
     */
    ObservableList<Appointment> remindOfApp = FXCollections.observableArrayList();
    private DateTimeFormatter datetimeDTF = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private ZoneId localZoneId = ZoneId.systemDefault();
    private void  updTimeZone(){
        TimeZone localTimeZone= TimeZone.getDefault();
        timezonelabel.setText((resourceBundle.getString("timeZone")) +" " + localTimeZone.getDisplayName());
    }
    private void appAlert(){
        System.out.println("Appointment Alert");
        LocalDateTime now= LocalDateTime.now();
        LocalDateTime nowPlus15= now.plusMinutes(15);
        System.out.println("Now: " + now);
        System.out.println("NowPlus15: " + nowPlus15);
        try {
            remindOfApp = AppQuery.getAppointments();
        }catch(SQLException exception){
            exception.printStackTrace();
        }
        //List<Appointment> appointments = new ArrayList<>();
        // Add appointments to the list
/**
 * Filter appointments within 15 minutes
 */

        List<Appointment> filteredList = remindOfApp.stream()
                .filter(appointment -> {
                    LocalDateTime current = LocalDateTime.now();
                    LocalDateTime fifteenMinutesLater = current.plusMinutes(15);
                    LocalDateTime appTime=appointment.getStartTime();
                    return appointment.getStartTime().isAfter(current) && appointment.getStartTime().isBefore(fifteenMinutesLater);
                })
                .collect(Collectors.toList());


        if(filteredList.isEmpty()) {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(resourceBundle.getString("appAlert"));
            alert.setContentText(resourceBundle.getString("noAppAlert"));
            alert.showAndWait();
        }else {
            int appID= filteredList.get(0).getAppointmentId();
            LocalDateTime start= filteredList.get(0).getStartTime();
            String message=resourceBundle.getString("fifteenOrLess")+"\n"+
            (resourceBundle.getString("appId ") )+": " + appID+"\n"+
            (resourceBundle.getString("dateAndTime")) + start.toString();
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(resourceBundle.getString("appAlert"));
            alert.setContentText(message);
            alert.showAndWait();
        }
    }


    /**
     * successful log in attempt writer
     * @throws IOException
     */
    private void successfullLog(){
        appAlert();
        try{
            FileWriter fileWriter=new FileWriter(activityLogged.getFile(),true);
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date=new Date(System.currentTimeMillis());
            fileWriter.write(String.format("Login was a success=" + usernamebox.getText() + "Password=" + pwbox.getText() + "TimeStamp:" + simpleDateFormat.format(date)+("\n")));
            fileWriter.close();
        }catch(IOException exception){
            exception.printStackTrace();
        }
    }

    /**
     * Write Log in failure
     * @throws IOException
     */

    private void logFailed(){
        try{
            FileWriter fileWriter=new FileWriter(activityLogged.getFile(),true);
            SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date= new Date(System.currentTimeMillis());
            fileWriter.write("LogIn Failed: Username="+usernamebox.getText()+"Password=" +pwbox.getText()+"Timestamp: "+simpleDateFormat.format(date)+"\n");
            fileWriter.close();
        }catch(IOException exception){
            exception.printStackTrace();
        }
    }

    /**
     * initializes the view and translates based on localization
     * @param url
     * @param resources
     */
    @Override
    public void initialize(URL url, ResourceBundle resources){
        resourceBundle=ResourceBundle.getBundle("Language/language",Locale.getDefault());
        if(Locale.getDefault().getLanguage().equals("es")|| Locale.getDefault().getLanguage().equals("en")){
            usernameLabel.setText(resourceBundle.getString("username"));
            pwLabel.setText(resourceBundle.getString("password"));
            loginbutton.setText(resourceBundle.getString("login"));
            zoneidLabel.setText(resourceBundle.getString("location"));
            countrylabel.setText(resourceBundle.getLocale().getCountry());
            updTimeZone();

        }
    }

}