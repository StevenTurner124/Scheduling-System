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
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import static Scheduler.LoginController.resourceBundle;

/**
 * Controller to Upd Customer
 */

public class UpdateCustomerController implements Initializable {
    private static Customer custSelected;
    @FXML
    private TextField nametf;
    @FXML
    private TextField addtf;

    @FXML
    private TextField posttf;
    @FXML
    private TextField phonetf;
    @FXML
    private Label namelabel;
    @FXML
    private Label addlabel;
    @FXML
    private Label postlabel;
    @FXML
    private Label phonelabel;
    @FXML
    private Button submitbutton;
    @FXML
    private Button cancelbutton;
    @FXML
    private Label countrylabel;
    @FXML
    private TextField custidtf;
    @FXML
    private Label custidlabel;
    @FXML
    private ComboBox<String> countrycombo;
    @FXML
    private Label divlabel;
    @FXML
    private ComboBox<String> divcombo;
    @FXML
    private Label dividlabel;
    @FXML
    private TextField dividtf;
    @FXML
    private Button homebutton;
    @FXML
    private TextField agetf;
    @FXML
    private ComboBox vipcombo;

    /**
     * Fills in Division ID Combo box
     * @param event
     */

    @FXML
    void countrySelect(ActionEvent event){
        // ObservableList<String>divList= FXCollections.observableArrayList();
        try{
            popCountryDivs();
            // ObservableList<Division>divisions=DivisionQuery.getDivByCountry(countrycombo.getSelectionModel().getSelectedItem());
            //if(divisions!=null){
            //  for(Division division:divisions){
            //   divList.add(division.getDiv());
            //  }
            //}
            //divcombo.setItems(divList);
            divcombo.getSelectionModel().select(0);
            String divString=divcombo.getSelectionModel().getSelectedItem();
            Division selectedDivision=DivisionQuery.getDivID(divString);
            dividtf.setText(Integer.toString(selectedDivision.getDivID()));
        }catch(SQLException exception){
            exception.printStackTrace();
        }
    }

    /**
     * populates divisions based on country selection
     */
    void popCountryDivs() {
        ObservableList<String> divList = FXCollections.observableArrayList();
        try {
            ObservableList<Division> divisions = DivisionQuery.getDivByCountry(countrycombo.getSelectionModel().getSelectedItem());
            if (divisions != null) {
                for (Division division : divisions) {
                    divList.add(division.getDiv());
                }
            }
            divcombo.setItems(divList);
        }catch(SQLException exception){
            exception.printStackTrace();
        }
    }

    /**
     * allows selection of division in combo box
     * @param event
     */
    @FXML
    void divSelect(ActionEvent event) {
        try {
            String divString = divcombo.getSelectionModel().getSelectedItem();
            Division selectedDivision = DivisionQuery.getDivID(divString);
            dividtf.setText(Integer.toString(selectedDivision.getDivID()));
        }catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    /**
     * Updates Customer on submission and validation
     * @param event
     */

    @FXML
    void onActionSubmit(ActionEvent event){
        boolean allowed=notEmpty(
                nametf.getText(),
                addtf.getText(),
                posttf.getText(),
                phonetf.getText());
        if(allowed){
            try{
                boolean saved=CustomersQuery.updateCust(
                        Integer.parseInt(custidtf.getText()),
                        nametf.getText(),
                        addtf.getText(),
                        posttf.getText(),
                        phonetf.getText(),
                        divcombo.getValue(),
                        agetf.getText(),
                        vipcombo.getSelectionModel().getSelectedIndex());

                if(saved){
                    Alert alert=new Alert(Alert.AlertType.CONFIRMATION,(resourceBundle.getString("patUpd")));
                    Optional<ButtonType> result=alert.showAndWait();
                    if(result.isPresent()&& (result.get()==ButtonType.OK)){
                        try{
                            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                            URL mainScreenLocation=null;
                            if(Locale.getDefault().getLanguage().equals("es")) {
                                mainScreenLocation = HomeScreenController.class.getResource("CustomerView(ES).fxml");
                            }
                            else {
                                mainScreenLocation = CustController.class.getResource("CustomerView.fxml");
                            }
                            FXMLLoader fxmlLoader = new FXMLLoader(mainScreenLocation);
                            Parent root= fxmlLoader.load();
                            Scene scene=new Scene(root);
                            stage.setTitle(resourceBundle.getString("patients"));
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
                    Alert alert=new Alert(Alert.AlertType.ERROR, (resourceBundle.getString("patNoSave")));
                    Optional<ButtonType>result=alert.showAndWait();
                }
            }catch(Exception exception){
                exception.printStackTrace();
            }
        }
    }

    /**
     * Validates fields are filled in
     * @param name
     * @param address
     * @param postal
     * @param phone
     * @return false if any section is empty
     */

    private boolean notEmpty(String name, String address,String postal,String phone){
        if(name.isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("nameReq"));
            alert.showAndWait();
            return false;
        }
        if(address.isEmpty()){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("addReq"));
            alert.showAndWait();
            return false;
        }
        if(postal.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("postReq"));
            alert.showAndWait();
            return false;
        }
        if(phone.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("phoneReq"));
            alert.showAndWait();
            return false;
        }
        if(divcombo.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("divReq"));
            alert.showAndWait();
            return false;
        }
        if(countrycombo.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("countryReq"));
            alert.showAndWait();
            return false;
        }
        if(agetf.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("ageReq"));
            alert.showAndWait();
            return false;
        }
        if(vipcombo.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("vipReq"));
            alert.showAndWait();
            return false;
        }
        return true;
    }

    /**
     * Retrieve customer selected
     * @param customer
     */

    public static void customerSelected(Customer customer){
        custSelected=customer;
    }

    /**
     * Cancels creation and returns to Customer Table View
     * @param event
     */

    @FXML
    void onActionCancel(ActionEvent event){
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION,(resourceBundle.getString("canUpd")));
        Optional<ButtonType>result=alert.showAndWait();
        if(result.isPresent()&& result.get()==ButtonType.OK){
            try{
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                URL mainScreenLocation=null;
                if(Locale.getDefault().getLanguage().equals("es")) {
                    mainScreenLocation = HomeScreenController.class.getResource("CustomerView(ES).fxml");
                }
                else {
                    mainScreenLocation = CustController.class.getResource("CustomerView.fxml");
                }
                FXMLLoader fxmlLoader = new FXMLLoader(mainScreenLocation);
                Parent root= fxmlLoader.load();
                Scene scene=new Scene(root);
                stage.setTitle(resourceBundle.getString("patients"));
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
    }

    /**
     * Returns to Main Screen
     * @param event
     */
    @FXML
    void Main(ActionEvent event){
        try{
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();URL mainScreenLocation=null;
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
        }catch (Exception exception){
            exception.printStackTrace();
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("error"));
            alert.showAndWait();
        }
    }

    /**
     * Fills in Division Combo
     * @throws SQLException
     */

    private void setDivcombo() {
        ObservableList<String> divList = FXCollections.observableArrayList();
        try {
            ObservableList<Division> divisions = DivisionQuery.getDivs();
            if (divisions != null) {
                for (Division division : divisions) {
                    divList.add(division.getDiv());
                }
            }


        } catch (SQLException exception) {
            exception.printStackTrace();

            divcombo.setItems(divList);
        }
    }
    void setVipcombo() {
        ObservableList<String> vipList = FXCollections.observableArrayList();
        vipList.add("false");
        vipList.add("true");
        vipcombo.setItems(vipList);
    }

    /**
     * Fills in Country Combo
     * @throws SQLException
     */

    private void setCountrycombo () {
        ObservableList<String> countryList = FXCollections.observableArrayList();
        try {
            ObservableList<Country> countries = CountryQuery.getCountries();
            if (countries != null) {
                for (Country country : countries) {
                    countryList.add(country.getCountry());
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        countrycombo.setItems(countryList);
    }

    /**
     * Fills in fields for form to update
     * @param location
     * @param resourceBundle
     */

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        /**
         * Fill in combos
         */

        setCountrycombo();
        setDivcombo();
        setVipcombo();
        /**
         * Fill in other fields
         * @throws SQLException
         */

        custidtf.setText(Integer.toString(custSelected.getCustID()));
        nametf.setText(custSelected.getCustName());
        posttf.setText(custSelected.getPostCode());
        addtf.setText(custSelected.getAddress());
        phonetf.setText(custSelected.getPhoneNum());
        countrycombo.getSelectionModel().select(custSelected.getCountry());
        popCountryDivs();
        divcombo.getSelectionModel().select(custSelected.getDiv());
        agetf.setText((custSelected.getAge()));
        vipcombo.getSelectionModel().select(custSelected.getVip());
        try {
            String divString = divcombo.getSelectionModel().getSelectedItem();
            Division selectedDivision = DivisionQuery.getDivID(divString);
            dividtf.setText(Integer.toString(selectedDivision.getDivID()));
        }catch(SQLException exception){
            exception.printStackTrace();
        }
    }
}


