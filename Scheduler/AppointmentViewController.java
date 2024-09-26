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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import static Scheduler.LoginController.resourceBundle;


/**
 * Creates controller
 */
public class AppointmentViewController implements Initializable {
    static ObservableList<Appointment>appointments;
    @FXML
    private RadioButton allradio;
    @FXML
    private RadioButton weekradio;
    @FXML
    private RadioButton monthradio;
    @FXML
    private Button searchbutton;
    @FXML
    private TextField searchtf;
    @FXML
    private TableView<Appointment> apptableview;
    @FXML
    private TableColumn appidcol;
    @FXML
    private TableColumn titlecol;
    @FXML
    private TableColumn desccol;
    @FXML
    private TableColumn locationcol;
    @FXML
    private TableColumn contactcol;
    @FXML
    private TableColumn typecol;
    @FXML
    private TableColumn startcol;
    @FXML
    private  TableColumn endcol;
    @FXML
    private TableColumn custidcol;
    @FXML
    private TableColumn useridcol;
    @FXML
    private Button createbutton;
    @FXML
    private Button updatebutton;
    @FXML
    private Button deletebutton;
    @FXML
    private Button mainbutton;

    @FXML
    private ToggleGroup ToggleView;

    /**
     * Allows toggling between the radio buttons
     * @param event button event
     */


    @FXML
    void ToggleView(ActionEvent event){
        if(allradio.isSelected()){
            try{
                appointments=AppQuery.getAppointments();
                apptableview.setItems(appointments);
                apptableview.refresh();
            }catch(SQLException exception){
                exception.printStackTrace();
            }
        }else if (ToggleView.getSelectedToggle().equals(monthradio)){
            try{
                appointments=AppQuery.getMonthsApps();
                apptableview.setItems((appointments));
                apptableview.refresh();
            }catch(SQLException exception){
                exception.printStackTrace();
            }
        }else if (ToggleView.getSelectedToggle().equals(weekradio)){
            try{
                appointments=AppQuery.getAppointmentsWeek();
                apptableview.setItems(appointments);
                apptableview.refresh();
            }catch(SQLException exception){
                exception.printStackTrace();
            }
        }
    }

    /**
     * When Create is clicked allows view to switch to Create App view
     * @param event button event
     */

    @FXML
    void onActionCreate(ActionEvent event){
        try{
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            URL mainScreenLocation=null;
            if(Locale.getDefault().getLanguage().equals("es")) {
                mainScreenLocation = HomeScreenController.class.getResource("AddAppointment(ES).fxml");
            }
            else {
                mainScreenLocation = AddAppointmentController.class.getResource("Add Appointment.fxml");
            }
            FXMLLoader fxmlLoader = new FXMLLoader(mainScreenLocation);
            Parent root= fxmlLoader.load();
            Scene scene=new Scene(root);
            stage.setTitle(resourceBundle.getString("addApp"));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("loadError"));
            alert.showAndWait();
        }
    }

    /**
     * Goes to Update Screen when button clicked
     * @param event button event
     */

    @FXML
    void onActionUpdate(ActionEvent event) {
        UpdateAppController.chosenApp(apptableview.getSelectionModel().getSelectedItem());

        if (apptableview.getSelectionModel().getSelectedItem() !=null){
            try {
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                URL mainScreenLocation=null;
                if(Locale.getDefault().getLanguage().equals("es")) {
                    mainScreenLocation = HomeScreenController.class.getResource("UpdateAppointment(ES).fxml");
                }
                else {
                    mainScreenLocation = UpdateAppController.class.getResource("UpdateAppointment.fxml");
                }
                FXMLLoader fxmlLoader = new FXMLLoader(mainScreenLocation);
                Parent root= fxmlLoader.load();
                Scene scene=new Scene(root);
                stage.setTitle(resourceBundle.getString("updApp"));
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(resourceBundle.getString("error"));
                alert.setContentText(resourceBundle.getString("loadError"));
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(resourceBundle.getString("selectApp"));
            alert.showAndWait();
        }
    }

    /**
     * Deletes Selected App
     * @param event button event
     */

    @FXML
    void onActionDelete(ActionEvent event){
        Appointment appSelected=apptableview.getSelectionModel().getSelectedItem();
        if (appSelected==null){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(resourceBundle.getString("selectDelete"));
            alert.showAndWait();
        }else if(apptableview.getSelectionModel().getSelectedItem()!= null){
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION,(resourceBundle.getString( "continueDelete")));
            Optional<ButtonType>result= alert.showAndWait();
            if(result.isPresent()&&(result.get()==ButtonType.OK)){
                try{
                    boolean successfulDeletion= AppQuery.deleteApp((apptableview.getSelectionModel().getSelectedItem().getAppointmentId()));
                    if(successfulDeletion){
                        alert=new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle(resourceBundle.getString("delSuccess"));
                        alert.setContentText(resourceBundle.getString("appId") +appSelected.getAppointmentId()+"type" +appSelected.getType());
                        alert.showAndWait();
                        appointments= AppQuery.getAppointments();
                        apptableview.setItems(appointments);
                        apptableview.refresh();
                    }else{
                        alert=new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("error");
                        alert.setContentText(resourceBundle.getString("noDel"));
                        alert.showAndWait();
                    }
                }catch(SQLException exception){
                    exception.printStackTrace();
                }
            }
        }
    }

    /**
     * Allows use of the button to return home
     * @param event button event
     */
    @FXML
    void Home(ActionEvent event) {

        try {
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            URL mainScreenLocation=null;
            if(Locale.getDefault().getLanguage().equals("es")) {
                mainScreenLocation = HomeScreenController.class.getResource("Main(ES).fxml");
            }
            else {
                mainScreenLocation = HomeScreenController.class.getResource("Main.fxml");
            }
            FXMLLoader fxmlLoader = new FXMLLoader(mainScreenLocation);
            Parent root= fxmlLoader.load();
            Scene scene=new Scene(root);
            stage.setTitle(resourceBundle.getString("homeScreen"));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("loadError"));
            alert.showAndWait();
        }
    }

    /**
     * Upd Table when searched
     * @param event button event
     */

    @FXML
    void onActionSearch (ActionEvent event){
        ObservableList<Appointment> tableUpd = searchApp(searchtf.getText());
        apptableview.setItems(tableUpd);
    }
    private static ObservableList<Appointment> searchApp (String searchInput){
        ObservableList<Appointment> appList = FXCollections.observableArrayList();

        for (Appointment appointment : appointments) {
            if (appointment.getTitle().contains(searchInput)) {
                appList.add(appointment);
            } else if (Integer.toString(appointment.getAppointmentId()).contains(searchInput)) {
                appList.add(appointment);
            }
        }
        return appList;
    }

    /**
     * TableView Initializer
     * @param location URL
     * @param resourceBundle resource for intializing
     */

    @Override
    public void initialize (URL location, ResourceBundle resourceBundle){
        allradio.setToggleGroup(ToggleView);
        weekradio.setToggleGroup(ToggleView);
        monthradio.setToggleGroup(ToggleView);
        try {
            appointments = AppQuery.getAppointments();

            apptableview.setItems(appointments);
            appidcol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            titlecol.setCellValueFactory(new PropertyValueFactory<>("title"));
            desccol.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationcol.setCellValueFactory(new PropertyValueFactory<>("location"));
            contactcol.setCellValueFactory(new PropertyValueFactory<>("contact"));
            typecol.setCellValueFactory(new PropertyValueFactory<>("type"));
            startcol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            endcol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            custidcol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            useridcol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}

