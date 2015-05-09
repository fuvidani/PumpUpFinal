package sepm.ss15.grp16.gui.controller.Calendar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.MediaView;
import sepm.ss15.grp16.gui.controller.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 08.05.15.
 *
 */
public class CalendarController extends Controller implements Initializable{


    @FXML
    private MediaView media;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void exportToGoogleClicked(ActionEvent event) {

    }

    @FXML
    void getBackClicked(ActionEvent event) {
        stage.close();
    }


}
