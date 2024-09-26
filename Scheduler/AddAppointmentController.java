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
import java.util.Comparator;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import static Scheduler.LoginController.resourceBundle;

/**
 * initializes the controller for adding appointment
 */
public class AddAppointmentController implements Initializable {
    private ZonedDateTime startTimeConvert;
    private ZonedDateTime endTimeConvert;
    @FXML
    private Label contactlabe;
    @FXML
    private ComboBox<String> startcombo;
    @FXML
    private ComboBox<String> endtimecombo;
    @FXML
    private ComboBox<String> contactcombo;

    @FXML
    private Label titlelabel;
    @FXML
    private TextField titletext;
    @FXML
    private Label descriptionlabel;
    @FXML
    private TextField desctf;
    @FXML
    private Label locationlabel;
    @FXML
    private TextField locationtext;
    @FXML
    private Label typelabel;
    @FXML
    private ComboBox<String> typecombo;
    @FXML
    private Label datelabel;
    @FXML
    private DatePicker startdatepick;
    @FXML
    private Label enddatelabel;
    @FXML
    private DatePicker enddatepick;
    @FXML
    private Label appidlabel;
    @FXML
    private TextField appidtf;

    @FXML
    private Label endlabel;
    @FXML
    private Label custlabel;
    @FXML
    private TextField custtf;
    @FXML
    private Label custidlabel;
    @FXML
    private ComboBox<Integer> custidcombo;
    @FXML
    private Label useridlabel;
    @FXML
    private ComboBox<Integer> useridcombo;

    @FXML
    private Button confirmappbutton;
    @FXML
    private Button cancelappbutton;

    /**
     *Creates a target time of 10PM Eastern to register against local time to make sure it is within operating hours of the business.
     * @return true or false if before close
     */
    private boolean beforeClose(){
        /** Get the current date and time in the specified zone
         *
         */
        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/New_York"));




        /** Create a ZonedDateTime object for the target time and zone
         *
         */

        endTimeConvert=toESTConversion(LocalDateTime.of(enddatepick.getValue(),LocalTime.parse(endtimecombo.getSelectionModel().getSelectedItem())));
        startTimeConvert=toESTConversion(LocalDateTime.of(startdatepick.getValue(), LocalTime.parse(startcombo.getSelectionModel().getSelectedItem())));
        /** Set the time to 10 PM
         *
         */
        int selectedMonth=startTimeConvert.getMonthValue();
        int selectedDay= startTimeConvert.getDayOfMonth();
        LocalDateTime targetTime = now.withMonth(selectedMonth).withDayOfMonth(selectedDay).withHour(22).withMinute(0).withSecond(0);
        ZonedDateTime targetDateTime = ZonedDateTime.of(targetTime, ZoneId.of("America/New_York"));
        if(startTimeConvert.isAfter(targetDateTime) || endTimeConvert.isAfter(targetDateTime)){
            return false;
        }
        else{
            return true;
        }

    }

    /**
     * detects if time is after 8AM
     * @return true or false whether time is after open of 8AM
     */
    private boolean afterOpen() {
        /** Get the current date and time in the specified zone
         *
         */
        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/New_York"));




        /** Create a ZonedDateTime object for the target time and zone
         *
         */

        endTimeConvert = toESTConversion(LocalDateTime.of(enddatepick.getValue(), LocalTime.parse(endtimecombo.getSelectionModel().getSelectedItem())));
        startTimeConvert = toESTConversion(LocalDateTime.of(startdatepick.getValue(), LocalTime.parse(startcombo.getSelectionModel().getSelectedItem())));
        /** Set the time to 8 AM
         *
         */

        int selectedMonth= startTimeConvert.getMonthValue();
        int selectedDay= startTimeConvert.getDayOfMonth();
        LocalDateTime targetTime = now.withMonth(selectedMonth).withDayOfMonth(selectedDay).withHour(8).withMinute(0).withSecond(0);
        ZonedDateTime targetDateTime = ZonedDateTime.of(targetTime, ZoneId.of("America/New_York"));
        if (startTimeConvert.isBefore(targetDateTime) || endTimeConvert.isBefore(targetDateTime)) {
            return false;
        } else {
            return true;
        }
    }



    /**
     *Time Conversion
     * @param time
     * @return time converted to eastern
     */
    private ZonedDateTime toESTConversion(LocalDateTime time){
        /** Specify the local time zone
         *
         */
        ZoneId localZone = ZoneId.systemDefault();

        /** Convert the local time to Eastern Time
         *
         */
        ZonedDateTime easternDateTime = time.atZone(localZone).withZoneSameInstant(ZoneId.of("America/New_York"));
        return easternDateTime;
        //return ZonedDateTime.of(time, ZoneId.of("America/New_York"));
    }

     /**
     *  If appointment is valid, creates the appointment
     * @param event button event
     */
    @FXML
    void onActionConfirm(ActionEvent event) {
        boolean valid = appIsValid(
                titletext.getText(),
                desctf.getText(),
                locationtext.getText(),
                appidtf.getText());
        if (valid) {
            try {
                boolean accept = AppQuery.createnewApp(
                        Integer.parseInt(appidtf.getText()),
                        contactcombo.getSelectionModel().getSelectedItem(),
                        titletext.getText(),
                        desctf.getText(),
                        locationtext.getText(),
                        typecombo.getSelectionModel().getSelectedItem(),
                        LocalDateTime.of(startdatepick.getValue(), LocalTime.parse(startcombo.getSelectionModel().getSelectedItem())),
                        LocalDateTime.of(enddatepick.getValue(), LocalTime.parse(endtimecombo.getSelectionModel().getSelectedItem())),
                        custidcombo.getSelectionModel().getSelectedItem(),
                        useridcombo.getSelectionModel().getSelectedItem());

                if (accept) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, resourceBundle.getString("appCreated"));
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && (result.get() == ButtonType.OK)) {
                        try {
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
                        } catch (Exception exception) {
                            exception.printStackTrace();
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle(resourceBundle.getString("error"));
                            alert.setContentText(resourceBundle.getString("loadError"));
                            alert.showAndWait();
                        }
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, resourceBundle.getString("appNotCreated"));
                    Optional<ButtonType> result = alert.showAndWait();
                }

            }catch(Exception exception){
                exception.printStackTrace();
            }
        }
    }

    /**
     *  validates whether the app is valid or needs information
     * @param title
     * @param description
     * @param location
     * @param appID
     * @return false if app content is empty in any section
     */
    private boolean appIsValid(String title, String description, String location, String appID){
        if(contactcombo.getSelectionModel().isEmpty()){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("contactRequired"));
            alert.showAndWait();
            return false;
        }
        /**
         * validates if title is filled in
         * @return false if title is empty
         */
        if(title.isEmpty()){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("titleRequired"));
            alert.showAndWait();
            return false;
        }
        /**
         * validates if description is filled in
         * @return false if description is empty
         */
        if(description.isEmpty()){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("descRequired"));
            alert.showAndWait();
            return false;
        }
        /**
         * validates if location is filled in
         * @return false if Location is empty
         */
        if(location.isEmpty()){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));;
            alert.setContentText(resourceBundle.getString("locRequired"));
            alert.showAndWait();
            return false;
        }
        /**
         * validates if appID is filled in
         * @return false if AppID is empty
         */
        if(appID.isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("appIDReq"));
            alert.showAndWait();
            return false;
        }
        /**
         * validates if type is chosen
         * @return false if type is empty
         */
        if(typecombo.getSelectionModel().isEmpty()){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("typeReq"));
            alert.showAndWait();
            return false;
        }
        /**
         * validates if start date was chosen
         * @return false if start date is empty
         */
        if (startdatepick.getValue()==null){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("startReq"));
            alert.showAndWait();
            return false;
        }
        /**
         * validates choice on end date
         * @return false if end date is empty
         */
        if(enddatepick.getValue()== null){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("endReq"));
            alert.showAndWait();
            return false;
        }
        /**
         * validates start time was chosen
         * @return false if start time is empty
         */
        if(startcombo.getSelectionModel().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("timeStart"));
            alert.showAndWait();
            return false;
        }
        /**
         * validates end time was chosen
         * @return false if end time is empty
         */
        if (endtimecombo.getSelectionModel().isEmpty()){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("timeEnd"));
            alert.showAndWait();
            return false;
        }
        /**
         * validates whether customer id was chosen
         * @return false if Customer ID is empty
         */
        if(custidcombo.getSelectionModel().isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("idReq"));
            alert.showAndWait();
            return false;
        }

        LocalTime start=LocalTime.parse(startcombo.getSelectionModel().getSelectedItem());
        LocalTime end=LocalTime.parse(endtimecombo.getSelectionModel().getSelectedItem());
/**
 * makes sure an appointment doesn't end before it starts
 * @return false if start time is before the end time.
 */
        if(end.isBefore(start)){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("startEnd"));
            alert.showAndWait();
            return false;
        }
        LocalDate dateStart= startdatepick.getValue();
        LocalDate dateEnd=enddatepick.getValue();
/**
 * Makes sure appointment starts and ends on the same day
 * @return false if appointment doesn't end on the same day it starts
 */
        if(!dateStart.equals(dateEnd)){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("sameDay"));
            alert.showAndWait();
            return false;
        }

        /**
         * Checks to ensure apps don't overlap
         */

        LocalDateTime startSelected=dateStart.atTime(start);
        LocalDateTime endSelected= dateEnd.atTime(end);

        LocalDateTime selectedStartTime;
        LocalDateTime selectedEndTime;
        try{
            ObservableList<Appointment>appointments= AppQuery.getAppsByCustID(custidcombo.getSelectionModel().getSelectedItem());
            for(Appointment appointment:appointments){
                selectedStartTime= appointment.getStartDate().atTime(appointment.getStartTime().toLocalTime());
                selectedEndTime=appointment.getEndDate().atTime(appointment.getEndTime().toLocalTime());

                if(selectedStartTime.isAfter(startSelected)&& selectedStartTime.isBefore(endSelected)){
                    Alert alert= new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(resourceBundle.getString("error"));
                    alert.setContentText(resourceBundle.getString("noOverlap"));
                    alert.showAndWait();
                    return false;
                }else if(selectedEndTime.isAfter(startSelected)&& selectedEndTime.isBefore(endSelected)){
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(resourceBundle.getString("error"));
                    alert.setContentText(resourceBundle.getString("noOverlap"));
                    alert.showAndWait();
                    return false;
                }
                else if(selectedStartTime.isEqual(startSelected)|| selectedEndTime.isEqual(endSelected)){
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
 * Check if between hours of 8AM and 10PM
 * @return true if within the hours of operation
 */
        //startTimeConvert = toESTConversion(LocalDateTime.of(startdatepick.getValue(),LocalTime.parse(startcombo.getSelectionModel().getSelectedItem())));
        // endTimeConvert = toESTConversion(LocalDateTime.of(enddatepick.getValue(), LocalTime.parse(endtimecombo.getSelectionModel().getSelectedItem())));
        if(!beforeClose()){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(resourceBundle.getString("timing"));
            alert.showAndWait();
            return false;
        }
        if(!afterOpen()){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(resourceBundle.getString("timing"));
            alert.showAndWait();
            return false;
        }



        //if(startTimeConvert.toLocalTime().isAfter(LocalTime.of(22,0))){
        // Alert alert=new Alert(Alert.AlertType.ERROR);
        // alert.setTitle("Error");
        // alert.setContentText("Appointment must be between hours of 8AM and 10PM EST");
        // alert.showAndWait();
        //  return false;
        // }
        //if(endTimeConvert.toLocalTime().isBefore(LocalTime.of(8,0))){
        //Alert alert= new Alert(Alert.AlertType.ERROR);
        //alert.setTitle("Error");
        // alert.setContentText("Appointment must be within hours of 8AM and 10PM EST");
        // alert.showAndWait();
        //return false;
        //}
       else{
           return true;
        }
    }

    @FXML
    void ChooseStartDate(ActionEvent event){

    }
    @FXML
    void ChooseEndDate(ActionEvent event){

    }
    @FXML
    void ChooseStartTime(ActionEvent event){

    }
    @FXML
    void ChooseEndTime(ActionEvent event){
    }
    @FXML
    void ChooseType(ActionEvent event){

    }

    /**
     *
     * allows use of the cancel button and cancels appointment creation
     * @param event button event
     */
    @FXML
    void onActionCancel(ActionEvent event){
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION,(resourceBundle.getString("appCancel")));
        Optional<ButtonType>result= alert.showAndWait();
        if(result.isPresent()&& (result.get()==ButtonType.OK)){
            try {
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

            }catch (Exception exception) {
                exception.printStackTrace();
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(resourceBundle.getString("error"));
                alert.setContentText(resourceBundle.getString("error"));
                alert.showAndWait();
            }
        }
    }
    public static VIPPatient convertVIP(Customer customer){
        return new VIPPatient(customer.getCustID(),customer.getCustName(),customer.getAddress(),customer.getPostCode(),customer.getPhoneNum(),customer.getDiv(),customer.getDivID(),customer.getCountry(),customer.getAge(),customer.getVip());
    }
    @FXML
    void setcustomer(ActionEvent actionEvent) {

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
     * fills in the time combo boxes with choices
     */
    private void popTimeCombo(){
        ObservableList<String>time=FXCollections.observableArrayList();
        LocalTime start= LocalTime.of(8,0);
        LocalTime end= LocalTime.of(22,0);

        time.add(start.toString());
        while(start.isBefore(end)){
            start=start.plusMinutes(15);
            time.add(start.toString());
        }
        startcombo.setItems(time);
        endtimecombo.setItems(time);
    }

    /**
     * fills in the contact combo box with choices
     */
    private void popContactCombo(){
        ObservableList<String>contactCombo=FXCollections.observableArrayList();
        try{
            ObservableList<Contact>contacts= ContactsQuery.getContacts();
            if(contacts!= null){
                for(Contact contact:contacts){
                    if(!contactCombo.contains(contact.getContactName())){
                        contactCombo.add(contact.getContactName());
                    }
                }
            }
        }catch(SQLException exception){
            exception.printStackTrace();
        }
        contactcombo.setItems(contactCombo);
    }

    /**
     * fills in the customer ID options
     */
    private void popCustID() {
        ObservableList<Integer> custIDList = FXCollections.observableArrayList();

        try {
            ObservableList<Customer> customers = CustomersQuery.getCustomers();
            if
            (customers != null) {
                for (Customer customer : customers) {
                    custIDList.add(customer.getCustID());
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        custidcombo.setItems(custIDList);
    }

    /**
     * fills in the user id options
     */
    private void popUserID() {
        ObservableList<Integer> userID = FXCollections.observableArrayList();
        try {
            ObservableList<User> users = UserQuery.getUsers();
            if (users != null) {
                for (User user : users) {
                    userID.add(user.getUserID());
                }
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
        useridcombo.setItems(userID);
    }

    /**
     * fills in the type choices
     */
    private void poptype(){
        ObservableList<String>type=FXCollections.observableArrayList();
        type.addAll("Initial","Follow Up");
        typecombo.setItems(type);

    }

    /**
     * creates an App ID sequentially
     */
    private void popAppID(){
        try{
            ObservableList<Appointment> appointments = AppQuery.getAppointments();
            Appointment maxAppointment = appointments.stream().max(Comparator.comparingInt(Appointment::getAppointmentId)).orElse(null);

            if (maxAppointment != null) {
                appidtf.setText(Integer.toString(maxAppointment.getAppointmentId()+1));
            } else {
                appidtf.setText("1");
            }
        }catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * initializes and calls the above populations
     * @param url URL
     * @param resourceBundle ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        popTimeCombo();
        popContactCombo();
        popCustID();
        popUserID();
        poptype();
        popAppID();

    }
}