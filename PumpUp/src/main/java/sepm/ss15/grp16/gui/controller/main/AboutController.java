package sepm.ss15.grp16.gui.controller.main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.gui.controller.Controller;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

/**
 * Created by lukas on 31.05.2015.
 */
public class AboutController extends Controller implements Initializable {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    /**
     * This method is called when the user wants to like our Facebook page.
     * Opens the application page's URL in the default browser.
     * If there is no internet connection, an error dialog is displayed.
     */
    @FXML
    public void likeFacebookClicked(){
        try {
            URL url = new URL("http://www.google.com");
            URLConnection connection = url.openConnection();
            connection.connect();
            if(Desktop.isDesktopSupported())
            {
                Desktop.getDesktop().browse(new URI("https://www.facebook.com/PumpUpTUVienna"));
            }
        }
        catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fehler");
            alert.setHeaderText("Fehler beim Öffnen des Browsers");
            alert.setContentText("Es konnte keine Internetverbindung hergestellt werden, somit können Sie leider derzeit Facebook nicht erreichen.");
            alert.showAndWait();
        }
        catch (URISyntaxException ex){
            LOGGER.info("Error in URI: " +  ex.getMessage());
        }
    }
}
