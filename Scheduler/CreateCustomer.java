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
 * Constructor for Creating customer class
 */
public class CreateCustomer implements Initializable {
    @FXML
    private Label namelabel;
    @FXML
    private TextField nametf;
    @FXML
    private Label addLabel;
    @FXML
    private TextField addtf;
    @FXML
    private Label postLabel;
    @FXML
    private TextField posttf;
    @FXML
    private Label phonelabel;
    @FXML
    private TextField phonetf;
    @FXML
    private Label countrylabel;
    @FXML
    private ComboBox<String> countrycombo;
    @FXML
    private Label custidlabel;
    @FXML
    private TextField custidtf;
    @FXML
    private Label divlabel;
    @FXML
    private ComboBox<String> divcombo;
    @FXML
    private Button submitbutton;
    @FXML
    private Button cancelbutton;
    @FXML
    private Label dividlabel;
    @FXML
    private TextField dividtf;
    @FXML
    private TextField agetf;
    @FXML
    private ComboBox vipcombo;
    @FXML
    private Label viplabel;

    /**
     * Allows selection of country for customer info and sets the division choices based on country
     * @param event
     */
    @FXML
    void CountrySelect(ActionEvent event){
        ObservableList<String>divList=FXCollections.observableArrayList();
        try{
            ObservableList<Division>divisions=DivisionQuery.getDivByCountry(countrycombo.getSelectionModel().getSelectedItem());
            if(divisions!= null){
                for(Division division:divisions){
                    divList.add(division.getDiv());
                }
            }
            divcombo.setItems(divList);
            divcombo.getSelectionModel().select(0);
            String divString=divcombo.getSelectionModel().getSelectedItem();
            Division selectedDivision=DivisionQuery.getDivID(divString);
            dividtf.setText(Integer.toString(selectedDivision.getDivID()));
        }catch(SQLException exception){
            exception.printStackTrace();
        }
    }

    /**
     * allows selection of division list based upon country previously selected
     * @param event
     */
    @FXML
    void divSelect(ActionEvent event) {
        try {
            String divString = divcombo.getSelectionModel().getSelectedItem();
            Division selectedDivision = DivisionQuery.getDivID(divString);
            dividtf.setText(Integer.toString(selectedDivision.getDivID()));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Creates Customer
     * @param event
     * @throws SQLException
     */
    @FXML
    void Save(ActionEvent event)throws SQLException {
        boolean successful = notEmpty(
                nametf.getText(),
                addtf.getText(),
                posttf.getText(),
                phonetf.getText());



        if (successful) {
            try {
                boolean accepted = CustomersQuery.createCust(
                        nametf.getText(),
                        addtf.getText(),
                        posttf.getText(),
                        phonetf.getText(),
                        divcombo.getValue(),
                        agetf.getText(),
                        vipcombo.getSelectionModel().getSelectedIndex());

                if (accepted) {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, (resourceBundle.getString("created")));
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && (result.get() == ButtonType.OK)) {
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
                            Parent root= fxmlLoader.load();
                            Scene scene=new Scene(root);
                            stage.setTitle(resourceBundle.getString("patients"));
                            stage.setScene(scene);
                            stage.show();
                        } catch (Exception exception) {
                            exception.printStackTrace();
                            alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("error");
                            alert.setContentText("error");
                            alert.showAndWait();
                        }
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, (resourceBundle.getString("noSave")));
                    Optional<ButtonType> result = alert.showAndWait();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * Validates form is not empty
     * @param name
     * @param address
     * @param postal
     * @param phone
     * @return false if any selection is empty
     */

    private boolean notEmpty(String name, String address, String postal, String phone) {
        if (name.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(resourceBundle.getString("nameReq"));
            alert.showAndWait();
            return false;
        }
        if (address.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(resourceBundle.getString("addReq"));
            alert.showAndWait();
            return false;
        }
        if (postal.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(resourceBundle.getString("postReq"));
            alert.showAndWait();
            return false;
        }
        if (phone.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(resourceBundle.getString("phoneReq"));
            alert.showAndWait();
            return false;
        }
        if (divcombo.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(resourceBundle.getString("divReq"));
            alert.showAndWait();
            return false;
        }
        if (countrycombo.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(resourceBundle.getString("countryReq"));
            alert.showAndWait();
            return false;
        }
        if(agetf.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(resourceBundle.getString("ageReq"));
            alert.showAndWait();
            return false;
        }
        if(vipcombo.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error");
            alert.setContentText(resourceBundle.getString("vipReq"));
            alert.showAndWait();
            return false;
        }
        return true;
    }

    /**
     * Allows cancellation request and doesn't save the customer
     * @param event
     */
    @FXML
    void Cancel(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, (resourceBundle.getString("exit")));
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && (result.get() ==  ButtonType.OK)) {
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
                Parent root= fxmlLoader.load();
                Scene scene=new Scene(root);
                stage.setTitle(resourceBundle.getString("patients"));
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(resourceBundle.getString("error"));
                alert.setContentText(resourceBundle.getString("loadError"));
                alert.showAndWait();
            }
        }
    }

    /**
     * Allows use of the return home button
     * @param event
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
                mainScreenLocation = CustController.class.getResource("Main.fxml");
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
     * Division combo box choice generator
     */
    void setDivcombo(){
        ObservableList<String>divList= FXCollections.observableArrayList();
        try{
            ObservableList<Division> divisons= DivisionQuery.getDivs();
            if(divisons!= null){
                for( Division division: divisons){
                    divList.add(division.getDiv());
                }
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }
        divcombo.setItems(divList);
    }
    void setVipcombo(){
        ObservableList<String>vipList= FXCollections.observableArrayList();
        vipList.add("false");
        vipList.add("true");
        vipcombo.setItems(vipList);
    }
    /**
     * creates data for country list
     */

    private void setCountrycombo(){
        ObservableList<String>countryList=FXCollections.observableArrayList();
        try{
            ObservableList<Country>countries=CountryQuery.getCountries();
            if(countries != null){
                for(Country country:countries){
                    countryList.add(country.getCountry());
                }
            }
        }catch(SQLException exception){
            exception.printStackTrace();
        }
        countrycombo.setItems(countryList);
    }

    /**
     * initializes the screen and the combos
     * @param location
     * @param resourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resourceBundle){
        setDivcombo();
        setCountrycombo();
        setVipcombo();
    }
}
