package sepm.ss15.grp16.gui.controller.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import sepm.ss15.grp16.gui.controller.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 07.05.15.
 * Controller of the little window with all photos of the user.
 * For further information see the Mockup.pdf file.
 */
public class BodyPhotosController extends Controller implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    @FXML
    void shootPhotoClicked(ActionEvent event) {

    }

    @FXML
    void loadPhotoClicked(ActionEvent event) {

    }

    @FXML
    void getBackButtonClicked(ActionEvent event) {
        stage.close();
    }


}
