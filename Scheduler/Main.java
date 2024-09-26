package Scheduler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Scheduler.LoginController.resourceBundle;

/**
 * This class creates an GUI-based scheduling application.
 */
public class Main extends Application {

    /**
     * This method starts the top level JavaFX Container.
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception{

        try {
            URL mainScreenLocation=LoginController.class.getResource("LogIn.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(mainScreenLocation);
            Parent root= fxmlLoader.load();
            Scene scene=new Scene(root);
            stage.setTitle(resourceBundle.getString("login"));
            stage.setScene(scene);
            stage.show();

        } catch (IOException exception) {
            exception.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(resourceBundle.getString("error"));
            alert.setContentText(resourceBundle.getString("loadError"));
            alert.showAndWait();
        }
    }

    /**
     * Main method, called when app starts
     * @param args
     */
    public static void main(String[] args) {
        DBConnection.connectStart();
        launch(args);
        DBConnection.termConnection();
    }
}