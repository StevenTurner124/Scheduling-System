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
 * Controller for use with customer class
 */

public class CustController implements Initializable {
    //customer list generation//
    static ObservableList<Customer>customers;
    @FXML
    private TableView<Customer> custtable;
    @FXML
    private TableColumn<?,?> idcol;
    @FXML
    private TableColumn<?,?> namecol;
    @FXML
    private TableColumn<?,?> addcol;
    @FXML
    private TableColumn<?,?> postcol;
    @FXML
    private TableColumn<?,?> phonecol;
    @FXML
    private TableColumn<?,?> countrycol;
    @FXML
    private TableColumn<?,?> divcol;
    @FXML
    private TableColumn<?,?> agecol;
    @FXML
    private TextField searchtf;
    @FXML
    private Button searchbutton;
    @FXML
    private Button homebutton;
    @FXML
    private Label custtitle;
    @FXML
    private Button addbutton;
    @FXML
    private Button updbutton;
    @FXML
    private Button deletebutton;

    /**
     * Opens Create Customer when Button pressed
     * @param event
     */

    @FXML
    void Create(ActionEvent event){
        try{
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            URL mainScreenLocation=null;
            if(Locale.getDefault().getLanguage().equals("es")){
                mainScreenLocation=HomeScreenController.class.getResource("CustomerInfo(ES).fxml");
            }
                else{
             mainScreenLocation=UpdateCustomerController.class.getResource("CustomerInfo.fxml");
            }
            FXMLLoader fxmlLoader = new FXMLLoader(mainScreenLocation);
            Parent root= fxmlLoader.load();
            Scene scene=new Scene(root);
            stage.setTitle(resourceBundle.getString("createPatient"));
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
     * Opens Update Customer when Button pressed
     * @param event
     */

    @FXML
    void Update(ActionEvent event){
        UpdateCustomerController.customerSelected(custtable.getSelectionModel().getSelectedItem());
        if(custtable.getSelectionModel().getSelectedItem()!= null){
            try{
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                URL mainScreenLocation=null;
                if(Locale.getDefault().getLanguage().equals("es")){
                    mainScreenLocation=HomeScreenController.class.getResource("UpdateCustomer(ES).fxml");
                }
                else{
                 mainScreenLocation=UpdateCustomerController.class.getResource("UpdateCustomer.fxml");
                }
                FXMLLoader fxmlLoader = new FXMLLoader(mainScreenLocation);
                Parent root= fxmlLoader.load();
                Scene scene=new Scene(root);
                stage.setTitle(resourceBundle.getString("updPat"));
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
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("selectPat"));
            alert.showAndWait();
        }
    }

    /**
     * Helper to make sure appointments are not linked and allows deletion.
     * @param custSelected
     * @return true if app found by customer ID otherwise return false
     * @throws SQLException
     */

    private boolean appCheck(Customer custSelected){
        try{
            ObservableList appointment= AppQuery.getAppsByCustID(custSelected.getCustID());
            if(appointment!= null&&appointment.size()<1){
                return true;
            }else {
                return false;
            }
        }catch(SQLException exception){
            exception.printStackTrace();
            return false;
        }
    }

    /**
     * Allows Deletion button function
     * @param event
     */

    @FXML
    void Delete(ActionEvent event){
        Customer custSelected=custtable.getSelectionModel().getSelectedItem();
        if(custSelected==null){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("deletePat"));
            alert.showAndWait();
        } else if (custtable.getSelectionModel().getSelectedItem()!= null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, (resourceBundle.getString("confDel")));
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                try {
                    boolean allowed = appCheck(custSelected);
                    if (allowed) {
                        boolean deletionDone = CustomersQuery.deleteCust(custtable.getSelectionModel().getSelectedItem().getCustID());
                        if (deletionDone) {
                            customers = CustomersQuery.getCustomers();
                            custtable.setItems(customers);
                            custtable.refresh();
                        } else {
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle(resourceBundle.getString("error"));
                            alert.setContentText(resourceBundle.getString("notDel"));
                            alert.showAndWait();
                        }
                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle(resourceBundle.getString("error"));
                        alert.setContentText(resourceBundle.getString("cantDel"));
                        alert.showAndWait();
                    }
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    /**
     * Goes to Main Screen
     * @param event
     */

    @FXML
    void Home(ActionEvent event){
        try{
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            URL mainScreenLocation=null;
            if(Locale.getDefault().getLanguage().equals("es")){
                mainScreenLocation=HomeScreenController.class.getResource("Main(ES).fxml");
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
     * Updates table when searching
     * @param event
     */

    @FXML
    void Search(ActionEvent event){
        ObservableList<Customer>updtable=custsearch(searchtf.getText());
        custtable.setItems(updtable);
    }

    /**
     * Helper for Search
     * @param input
     * @return custList
     */

    private static ObservableList<Customer> custsearch(String input){
        ObservableList<Customer>custList= FXCollections.observableArrayList();
        for(Customer customer:customers){
            if(customer.getCustName().contains(input)){
                custList.add(customer);
            }
        }
        return custList;
    }

    /**
     * Initializer for table view
     * @param location
     * @param resourceBundle
     */

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle){
        try{
            customers=CustomersQuery.getCustomers();
            custtable.setItems(customers);
            idcol.setCellValueFactory(new PropertyValueFactory<>("custID"));
            namecol.setCellValueFactory(new PropertyValueFactory<>("custName"));
            addcol.setCellValueFactory(new PropertyValueFactory<>("address"));
            postcol.setCellValueFactory(new PropertyValueFactory<>("postCode"));
            phonecol.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
            divcol.setCellValueFactory(new PropertyValueFactory<>("div"));
            countrycol.setCellValueFactory(new PropertyValueFactory<>("country"));
            agecol.setCellValueFactory(new PropertyValueFactory<>("age"));
        }catch (SQLException exception){
            exception.printStackTrace();
        }
    }
}