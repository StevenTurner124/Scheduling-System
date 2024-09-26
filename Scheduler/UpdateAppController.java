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
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;

import static Scheduler.LoginController.resourceBundle;

/**
 * Controller for updating App
 */

public class UpdateAppController implements Initializable {
    private ZonedDateTime startConversion;
    private ZonedDateTime endConversion;
    /**
     * Fetches App from App Controller
     */
    private static Appointment appSelected;
    @FXML
    private DatePicker startdatepick;
    @FXML
    private DatePicker enddatepick;
    @FXML
    private TextField locationtf;
    @FXML
    private Button cancelappbutton;
    @FXML
    private Button mainscreenbutton;

    @FXML
    private Button confirmappbutton;
    @FXML
    private ComboBox<String> contactcombo;
    @FXML
    private TextField desctf;
    @FXML
    private Label startlabel;
    @FXML
    private TextField appidtf;
    @FXML
    private TextField custidtf;
    @FXML
    private Label endlabel;
    @FXML
    private Label desclabel;
    @FXML
    private Label appidlabel;
    @FXML
    private Label titlelabel;
    @FXML
    private ComboBox<String>startcombo;
    @FXML
    private TextField titletext;
    @FXML
    private Label typelabel;
    @FXML
    private Label locationlabel;
    @FXML
    private ComboBox<String> endcombo;
    @FXML
    private Label enddatelabel;
    @FXML
    private Label contactlabel;
    @FXML
    private Label custidlabel;
    @FXML
    private Label useridlabel;
    @FXML
    private ComboBox<Integer> useridcombo;
    @FXML
    private ComboBox<Integer> custidcombo;
    @FXML
    private ComboBox<String> typecombo;
    @FXML
    private Label startltimelabel;
    @FXML
    private TextField custtf;

    /**
     * detects if an appointment is before close
     * @return true if before closing otherwise returns false
     */
    private boolean beforeClose(){
        /**
         * Get the current date and time in the specified zone
         */

        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/New_York"));
        endConversion=estConvert(LocalDateTime.of(enddatepick.getValue(),LocalTime.parse(endcombo.getSelectionModel().getSelectedItem())));
        startConversion=estConvert(LocalDateTime.of(startdatepick.getValue(), LocalTime.parse(startcombo.getSelectionModel().getSelectedItem())));
/**
 *  Set the time to 10 PM
 */

        LocalDateTime targetStartTime = now.withYear(startConversion.getYear()).withMonth(startConversion.getMonthValue()).withDayOfMonth(startConversion.getDayOfMonth()).withHour(22).withMinute(0).withSecond(0);
        LocalDateTime targetEndTime = now.withYear(endConversion.getYear()).withMonth(endConversion.getMonthValue()).withDayOfMonth(endConversion.getDayOfMonth()).withHour(22).withMinute(0).withSecond(0);
/**
 * Create a ZonedDateTime object for the target time and zone
 */

        ZonedDateTime targetStartDateTime = ZonedDateTime.of(targetStartTime, ZoneId.of("America/New_York"));
        ZonedDateTime targetEndDateTime = ZonedDateTime.of(targetEndTime, ZoneId.of("America/New_York"));
        if(startConversion.isAfter(targetStartDateTime) || endConversion.isAfter(targetEndDateTime)){
            return false;
        }
        else{
            return true;
        }

    }
    private boolean afterOpen(){
        /**
         * Get the current date and time in the specified zone
         */


        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/New_York"));
/**
 * Set the time to 8 AM
 */

        LocalDateTime targetTime = now.withHour(8).withMinute(0).withSecond(0);
/**
 * Create a ZonedDateTime object for the target time and zone
 */

        ZonedDateTime targetDateTime = ZonedDateTime.of(targetTime,ZoneId.of("America/New_York"));
        endConversion=estConvert(LocalDateTime.of(enddatepick.getValue(),LocalTime.parse(endcombo.getSelectionModel().getSelectedItem())));
        startConversion=estConvert(LocalDateTime.of(startdatepick.getValue(), LocalTime.parse(startcombo.getSelectionModel().getSelectedItem())));
        if(startConversion.isBefore(targetDateTime) || endConversion.isBefore(targetDateTime)){
            return false;
        }
        else{
            return true;
        }

    }

    /**
     * Time Conversion
     * @param time
     * @return converted time to eastern
     */
    private ZonedDateTime estConvert(LocalDateTime time){
        /**
         * Specify the local time zone
         */

        ZoneId localZone = ZoneId.systemDefault();
/**
 * Convert the local time to Eastern Time
 */

        ZonedDateTime easternDateTime = time.atZone(localZone).withZoneSameInstant(ZoneId.of("America/New_York"));
        return easternDateTime;
        //return ZonedDateTime.of(time, ZoneId.of("America/New_York"));
    }
    private ZonedDateTime timeZoneConvert(LocalDateTime time, String zoneId){
        return ZonedDateTime.of(time,ZoneId.of(zoneId));
    }
    @FXML
    void StartDateChosen(ActionEvent event){

    }
    @FXML
    void EndDateChosen(ActionEvent event){

    }

    /**
     * App updater if app is allowed based on validation
     * @param event
     */

    @FXML
    void onActionConfirm(ActionEvent event){
        boolean allowed=appAllowed(
                titletext.getText(),
                desctf.getText(),
                locationtf.getText(),
                appidtf.getText());

        if(allowed){
            try{
                boolean successful=AppQuery.updApp(
                        contactcombo.getSelectionModel().getSelectedItem(),
                        titletext.getText(),
                        desctf.getText(),
                        locationtf.getText(),
                        typecombo.getSelectionModel().getSelectedItem(),
                        LocalDateTime.of(startdatepick.getValue(), LocalTime.parse(startcombo.getSelectionModel().getSelectedItem())),
                        LocalDateTime.of(enddatepick.getValue(),LocalTime.parse(endcombo.getSelectionModel().getSelectedItem())),
                        custidcombo.getSelectionModel().getSelectedItem(),
                        useridcombo.getSelectionModel().getSelectedItem(),
                        Integer.parseInt(appidtf.getText()));

                if(successful){
                    Alert alert= new Alert(Alert.AlertType.CONFIRMATION,(resourceBundle.getString("appUpd")));
                    Optional<ButtonType> result= alert.showAndWait();
                    if(result.isPresent()&& result.get()==ButtonType.OK){
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
                            Parent root= fxmlLoader.load();
                            Scene scene=new Scene(root);
                            stage.setTitle(resourceBundle.getString("apps"));
                            stage.setScene(scene);
                            stage.show();
                        }catch(Exception exception){
                            exception.printStackTrace();
                            alert=new Alert(Alert.AlertType.ERROR);
                            alert.setTitle(resourceBundle.getString("error"));
                            alert.setContentText(resourceBundle.getString("error"));
                            alert.showAndWait();
                        }
                    }
                }else{
                    Alert alert=new Alert(Alert.AlertType.ERROR, (resourceBundle.getString("noUpd")));
                    Optional<ButtonType>result=alert.showAndWait();
                }
            }catch(Exception exception){
                exception.printStackTrace();
            }
        }
    }

    /**
     * Helper Function to make sure fields are not empty
     * @param title
     * @param desc
     * @param location
     * @param appID
     * @return false if any area is empty
     */

    private boolean appAllowed(String title,String desc,String location,String appID){
        if(contactcombo.getSelectionModel().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("contactRequired"));
            alert.showAndWait();
            return false;
        }
        if(title.isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("titleRequired"));
            alert.showAndWait();
            return false;
        }
        if(location.isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("locRequired"));
            alert.showAndWait();
            return false;
        }
        if(typecombo.getSelectionModel().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("typeReq"));
            alert.showAndWait();
            return false;
        }
        if(appID.isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("appIDReq"));
            alert.showAndWait();
            return false;
        }
        if(startdatepick.getValue()==null){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("startReq"));
            alert.showAndWait();
            return false;
        }
        if(enddatepick.getValue()==null){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("endReq"));
            alert.showAndWait();
            return false;
        }
        if(startcombo.getSelectionModel().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("timeStart"));
            alert.showAndWait();
            return false;
        }
        if(endcombo.getSelectionModel().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("timeEnd"));
            alert.showAndWait();
            return false;
        }
        if(enddatepick.getValue().isBefore(startdatepick.getValue())){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("endChoice"));
            alert.showAndWait();
            return false;
        }
        if(custidcombo.getSelectionModel().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("idReq"));
            alert.showAndWait();
            return false;
        }
        if(useridcombo.getSelectionModel().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("userReq"));
            alert.showAndWait();
            return false;
        }
        /**
         * Date Validators
         * @return false if end time is not after start time
         */

        LocalTime start=LocalTime.parse(startcombo.getSelectionModel().getSelectedItem());
        LocalTime end=LocalTime.parse(endcombo.getSelectionModel().getSelectedItem());
        if(end.isBefore(start)){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("timeChoice"));
            alert.showAndWait();
            return false;
        }
        LocalDate dateStart=startdatepick.getValue();
        LocalDate dateEnd=enddatepick.getValue();
/**
 * No overlapping appointments
 * @return false if appointments overlap
 * @throws SQLException
 */

        LocalDateTime startChoice=dateStart.atTime(start);
        LocalDateTime endChoice=dateEnd.atTime(end);

        LocalDateTime electedStart;
        LocalDateTime electedEnd;
        try{
            ObservableList<Appointment>appointments=AppQuery.getAppsByContactID(custidcombo.getSelectionModel().getSelectedItem());
            for(Appointment appointment:appointments){
                electedStart=appointment.getStartDate().atTime(appointment.getStartTime().toLocalTime());
                electedEnd=appointment.getEndDate().atTime(appointment.getEndTime().toLocalTime());
                if(electedStart.isAfter(startChoice)&& electedStart.isBefore(endChoice)){
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(resourceBundle.getString("error"));
                    alert.setContentText(resourceBundle.getString("noOverlap"));
                    alert.showAndWait();
                    return false;
                }else if(electedEnd.isAfter(startChoice)&& electedEnd.isBefore(endChoice)){
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(resourceBundle.getString("error"));
                    alert.setContentText(resourceBundle.getString("noOverlap"));
                    alert.showAndWait();
                    return false;
                }
            }
        }catch(SQLException exception){
            exception.printStackTrace();
        }
        /**
         * Between business Hours validator
         * @return false if outside business hours
         */


        if(!beforeClose()){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("timing"));
            alert.showAndWait();
            return false;
        }

        if
        (!afterOpen()){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("timing"));
            alert.showAndWait();
            return false;
        }

        return true;
    }

    /**
     * Fetches choice of selected App
     * @param appointment
     */

    public static void chosenApp(Appointment appointment){
        appSelected=appointment;
    }
    @FXML
    void onActionCancel(ActionEvent event){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION,(resourceBundle.getString("cancelUpd")));
        Optional<ButtonType>result=alert.showAndWait();
        if(result.isPresent()&&(result.get()== ButtonType.OK)){
            try {
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                URL mainScreenLocation = null;
                if (Locale.getDefault().getLanguage().equals("es")) {
                    mainScreenLocation = HomeScreenController.class.getResource("Appointments(ES).fxml");
                }
                else{
                    mainScreenLocation = AppointmentViewController.class.getResource("Appointments.fxml");
            }
                FXMLLoader fxmlLoader = new FXMLLoader(mainScreenLocation);
                Parent root= fxmlLoader.load();
                Scene scene=new Scene(root);
                stage.setTitle(resourceBundle.getString("apps"));
                stage.setScene(scene);
                stage.show();
            }catch(Exception exception) {
                exception.printStackTrace();
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(resourceBundle.getString("error"));
                alert.setContentText(resourceBundle.getString("error"));
                alert.showAndWait();
            }
        }
    }

    /**
     * allows use of the return home button
     * @param event
     */
    @FXML
    void onActionMain(ActionEvent event){
        try{
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
        }catch(Exception exception){
            exception.printStackTrace();
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("error"));
            alert.showAndWait();
        }
    }

    /**
     * Fills combo box for start and end in 15 min increments
     */
    private void popTimeCombos(){
        ObservableList<String>time= FXCollections.observableArrayList();
        LocalTime start=LocalTime.of(0,0);
        LocalTime end=LocalTime.of(23,45);
        time.add(start.toString());
        while(start.isBefore(end)){
            start=start.plusMinutes(15);
            time.add(start.toString());
        }
        startcombo.setItems(time);
        endcombo.setItems(time);
    }

    /**
     * Fills in contact combo
     * @throws SQLException
     */

    private void popContact() {
        ObservableList<String> contactCombo = FXCollections.observableArrayList();
        try{
            ObservableList<Contact>contacts=ContactsQuery.getContacts();
            if (contacts != null) {
                for (Contact contact :contacts){
                    if (!contactCombo.contains(contact.getContactName())) {
                        contactCombo.add(contact.getContactName());
                    }
                }
            }
        }catch(SQLException exception) {
            exception.printStackTrace();
        }
        contactcombo.setItems(contactCombo);
    }

    /**
     * Fills in Cust ID combo
     * @throws SQLException
     */

    private void popCustIDCombo(){
        ObservableList<Integer>custIdList=FXCollections.observableArrayList();
        try{
            ObservableList<Customer>customers=CustomersQuery.getCustomers();
            if(customers!=null){
                for(Customer customer:customers){
                    custIdList.add(customer.getCustID());
                }
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }
        custidcombo.setItems(custIdList);
    }

    /**
     * Fills in User ID combo
     * @throws SQLException
     */

    private void popUserId(){
        ObservableList<Integer>userIDList=FXCollections.observableArrayList();
        try{
            ObservableList<User>users=UserQuery.getUsers();
            if(users!= null){
                for(User user:users){
                    userIDList.add(user.getUserID());
                }
            }
        }catch(SQLException exception){
            exception.printStackTrace();
        }
        useridcombo.setItems(userIDList);
    }
    public static VIPPatient convertVIP(Customer customer){
        return new VIPPatient(customer.getCustID(),customer.getCustName(),customer.getAddress(),customer.getPostCode(),customer.getPhoneNum(),customer.getDiv(),customer.getDivID(),customer.getCountry(),customer.getAge(),customer.getVip());
    }
    @FXML
    void setcust(ActionEvent actionEvent) {

        try {
            if(custidcombo.getSelectionModel().isEmpty()) {
                return;
            }
            Customer customer= CustomersQuery.getCustomerbyID(custidcombo.getSelectionModel().getSelectedItem());
            if(customer.getVip() != 0){
                VIPPatient patient=convertVIP(customer);
                custtf.setText(patient.getCustName());
            }
            else {
                custtf.setText(customer.getCustName());
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Fills in type combo
     */

    private void popTypeCombo(){
        ObservableList<String>typeList=FXCollections.observableArrayList();
        typeList.addAll("Initial","Follow Up");
        typecombo.setItems(typeList);
    }

    /**
     * Populates fields with already existing info for updating and combos
     */

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle){
        popTimeCombos();
        popContact();
        popCustIDCombo();
        popUserId();
        popTypeCombo();

        try{
            Appointment appointment=AppQuery.getAppByAppID(appSelected.getAppointmentId());
            ZonedDateTime startTime=timeZoneConvert(appointment.getStartDate().atTime(appointment.getStartTime().toLocalTime()),String.valueOf(ZoneId.of(TimeZone.getDefault().getID())));
            ZonedDateTime endTime=timeZoneConvert(appointment.getEndDate().atTime(appointment.getEndTime().toLocalTime()),String.valueOf(ZoneId.of(TimeZone.getDefault().getID())));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
/**
 * Convert ZonedDateTime to a formatted string (hours and minutes only)
 * @throws SQLException
 */

            String startTimeString = formatter.format(startTime);
            String endTimeString = formatter.format(endTime);
            if(appointment!= null){
                contactcombo.getSelectionModel().select(appointment.getContact());
                titletext.setText(appointment.getTitle());
                desctf.setText(appointment.getDescription());
                locationtf.setText(appointment.getLocation());
                typecombo.getSelectionModel().select(appointment.getType());
                useridcombo.getSelectionModel().select(Integer.valueOf(appointment.getUserID()));
                appidtf.setText(String.valueOf(appointment.getAppointmentId()));
                startdatepick.setValue(appointment.getStartDate());
                startcombo.getSelectionModel().select(String.valueOf(startTimeString));
                enddatepick.setValue(appointment.getEndDate());
                endcombo.getSelectionModel().select(String.valueOf(endTimeString));
                custidcombo.getSelectionModel().select(Integer.valueOf(appointment.getCustomerID()));
                setcust(null);

            }
        }catch(SQLException exception){
            exception.printStackTrace();
        }
    }
}




