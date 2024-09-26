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
import java.time.LocalDate;
import java.time.Month;
import java.util.Locale;
import java.util.ResourceBundle;

import static Scheduler.LoginController.resourceBundle;

/**
 * Constructor for reports controller
 */
public class ReportsController implements Initializable {
    @FXML
    private Label tcalabel;
    @FXML
    private RadioButton typeradio;
    @FXML
    private RadioButton monthradio;
    @FXML
    private Button processbuttontca;
    @FXML
    private Label schedbycontidlabel;
    @FXML
    private ComboBox<Integer> comboboxscid;
    @FXML
    private Button processbuttoncid;
    @FXML
    private Label totnumappcustidlabel;
    @FXML
    private ComboBox<Integer> comboboxtotnumcid;
    @FXML
    private Button procbuttontotnumcid;
    @FXML
    private Button mainmenubutton;
    @FXML
    private ToggleGroup ToggleView;

    /**
     * Creates Report of Type or Month
     * @param event
     */


    @FXML
    void onActionprocess(ActionEvent event){

        //type//
        ObservableList<String>initial=FXCollections.observableArrayList();
        ObservableList<String>followup=FXCollections.observableArrayList();

        //Month//
        ObservableList<Integer>jan=FXCollections.observableArrayList();
        ObservableList<Integer>feb=FXCollections.observableArrayList();
        ObservableList<Integer>mar=FXCollections.observableArrayList();
        ObservableList<Integer>apr=FXCollections.observableArrayList();
        ObservableList<Integer>may=FXCollections.observableArrayList();
        ObservableList<Integer>june=FXCollections.observableArrayList();
        ObservableList<Integer>july=FXCollections.observableArrayList();
        ObservableList<Integer>aug=FXCollections.observableArrayList();
        ObservableList<Integer>sep=FXCollections.observableArrayList();
        ObservableList<Integer>oct=FXCollections.observableArrayList();
        ObservableList<Integer>nov=FXCollections.observableArrayList();
        ObservableList<Integer>dec=FXCollections.observableArrayList();
        try{
            ObservableList<Appointment>appointments=AppQuery.getAppointments();
            if(appointments != null){
                for(Appointment appointment:appointments){
                    String type=appointment.getType();
                    LocalDate date=appointment.getStartDate();

                    if(type.equals("Initial")){
                        initial.add(type);
                    }
                    if(type.equals("Follow Up")){
                        followup.add(type);
                    }

                    if(date.getMonth().equals(Month.of(1))){
                        jan.add(date.getMonthValue());
                    }
                    if(date.getMonth().equals(Month.of(2))){
                        feb.add(date.getMonthValue());
                    }
                    if(date.getMonth().equals(Month.of(3))){
                        mar.add(date.getMonthValue());
                    }
                    if(date.getMonth().equals(Month.of(4))){
                        apr.add(date.getMonthValue());
                    }
                    if(date.getMonth().equals(Month.of(5))){
                        may.add(date.getMonthValue());
                    }
                    if(date.getMonth().equals(Month.of(6))){
                        june.add(date.getMonthValue());
                    }
                    if(date.getMonth().equals(Month.of(7))){
                        july.add(date.getMonthValue());
                    }
                    if(date.getMonth().equals(Month.of(8))){
                        aug.add(date.getMonthValue());
                    }
                    if(date.getMonth().equals(Month.of(9))){
                        sep.add(date.getMonthValue());
                    }
                    if(date.getMonth().equals(Month.of(10))){
                        oct.add(date.getMonthValue());
                    }
                    if(date.getMonth().equals(Month.of(11))){
                        nov.add(date.getMonthValue());
                    }
                    if(date.getMonth().equals(Month.of(12))){
                        dec.add(date.getMonthValue());
                    }
                }
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }
        /**
         * determines type is selected and then generates report
         */
        if(typeradio.isSelected()){
            Alert alert= new Alert((Alert.AlertType.INFORMATION));
            alert.setTitle(resourceBundle.getString("countBy"));
            alert.setContentText(resourceBundle.getString("totalBy") +"\n"+
                    (resourceBundle.getString("initial")) +initial.size()+ "\n"+
                            (resourceBundle.getString("followUp"))+followup.size());
            alert.setResizable(true);
            alert.showAndWait();
        }
        /**
         * determines month is selected and generates report
         */
        if(monthradio.isSelected()){
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(resourceBundle.getString("monthCount"));
            alert.setContentText(resourceBundle.getString("countMonth")+"\n"+
                    (resourceBundle.getString( "jan"))+ jan.size()+"\n"+
                            (resourceBundle.getString("feb"))+ feb.size()+"\n"+
                                    (resourceBundle.getString("mar"))+ mar.size()+"\n"+
                                            (resourceBundle.getString("apr"))+ apr.size()+"\n"+
                                                    (resourceBundle.getString("mayMonth"))+ may.size()+"\n"+
                                                            (resourceBundle.getString("june"))+ june.size()+"\n"+
                                                                    (resourceBundle.getString("july"))+ july.size()+"\n"+
                                                                            (resourceBundle.getString("aug"))+ aug.size()+"\n"+
                                                                                    (resourceBundle.getString("sept"))+ sep.size()+"\n"+
                                                                                            (resourceBundle.getString("oct"))+oct.size()+"\n"+
                                                                                                    (resourceBundle.getString("nov"))+ nov.size()+"\n"+
                                                                                                            (resourceBundle.getString("dec"))+dec.size());
            alert.showAndWait();
        }
    }

    /**
     * limits only one radio option selection
     * @param event
     */
    @FXML
    void ToggleView(ActionEvent event){
        if (typeradio.isSelected()) {
            monthradio.setSelected(false);
        }
        else if(monthradio.isSelected()) {
            typeradio.setSelected(false);
        }
    }

    /**
     * Creates a report of appointments by Contact ID
     * @param event
     */
    @FXML

    void onActiongenerate(ActionEvent event){
        Integer contactID=comboboxscid.getSelectionModel().getSelectedItem();
        try {
            ObservableList<Appointment>appointments=AppQuery.getAppsByContactID(contactID);
            if(appointments != null){
                for(Appointment appointment: appointments){
                    Alert alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle(resourceBundle.getString("cIdRep"));
                    alert.setContentText(resourceBundle.getString("aCId") + contactID+":"+"\n"+
                            (resourceBundle.getString("appId")+" "+appointment.getAppointmentId())+"\n"+
                                    (resourceBundle.getString("title")+" "+ appointment.getTitle())+"\n"+
                                            (resourceBundle.getString("type")+" "+ appointment.getType())+"\n"+
                                                    (resourceBundle.getString("desc")+" "+appointment.getDescription())+"\n"+
                                                            (resourceBundle.getString("sDate")+" "+appointment.getStartDate())+"\n"+
                                                                    (resourceBundle.getString("sTime")+" "+appointment.getStartTime())+"\n"+
                            (resourceBundle.getString("eDate")+" "+appointment.getEndDate())+"\n"+
                                    (resourceBundle.getString("eTime")+" "+appointment.getEndTime())+"\n"+
                                            (resourceBundle.getString("pID")+" "+appointment.getCustomerID())+"\n"+
                                                    (resourceBundle.getString("uID")+" "+appointment.getUserID()));
                    alert.setResizable(true);
                    alert.showAndWait();
                }
            }
        }catch(SQLException exception){
            exception.printStackTrace();
        }
    }

    /**
     * Creates Report for total appointments by Customer ID
     * @param event
     */


    @FXML
    void onActionFetch(ActionEvent event){
        Integer customerID=comboboxtotnumcid.getSelectionModel().getSelectedItem();
        try{
            ObservableList<Appointment> appointments=AppQuery.getAppsByCustID(customerID);
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(resourceBundle.getString("countByPId"));
            alert.setContentText(resourceBundle.getString("totalByPId")+customerID+":"+appointments.size());
            alert.setResizable(true);
            alert.showAndWait();
        }catch(SQLException exception){
            exception.printStackTrace();
        }
    }

    /**
     * allows use of return home button
     * @param event
     */
    @FXML
    void returnmain(ActionEvent event){
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
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("loadError"));
            alert.showAndWait();
        }
    }

    /**
     * Fills in Contact ID Combo box from Contact ID List
     * @throws SQLException
     */

    private void popContactIDCombo(){
        ObservableList<Integer>contactIDList=FXCollections.observableArrayList();
        try{
            ObservableList<Contact>contacts=ContactsQuery.getContacts();
            if(contacts!=null){
                for(Contact contact:contacts){
                    if(!contactIDList.contains(contact.getContactID())){
                        contactIDList.add(contact.getContactID());
                    }
                }
            }
        }catch(SQLException exception){
            exception.printStackTrace();
        }
        comboboxscid.setItems(contactIDList);
    }

    /**
     * Fills in Customer ID combo with Customer ID List
     * @throws SQLException
     */

    private void popCustomerID() {
        ObservableList<Integer> customerIDList = FXCollections.observableArrayList();
        try {
            ObservableList<Customer> customers = CustomersQuery.getCustomers();
            if (customers != null) {
                for (Customer customer : customers) {
                    customerIDList.add(customer.getCustID());
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        comboboxtotnumcid.setItems(customerIDList);

    }

    /**
     *  Initialize combo boxes in reports
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources){
        popCustomerID();
        popContactIDCombo();
        typeradio.setToggleGroup(ToggleView);
        monthradio.setToggleGroup(ToggleView);
    }
}

