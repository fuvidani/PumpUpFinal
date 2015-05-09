package sepm.ss15.grp16.gui.controller.WorkoutPlans;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import sepm.ss15.grp16.gui.controller.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 08.05.15.
 *
 */
public class WorkoutPlanToCalendarController  extends Controller implements Initializable{


    @FXML
    private CheckBox thursdayCheck;

    @FXML
    private TextField dateTextField;

    @FXML
    private CheckBox tuesdayCheck;

    @FXML
    private CheckBox mondayCheck;

    @FXML
    private CheckBox wednesdayCheck;

    @FXML
    private CheckBox sundayCheck;

    @FXML
    private CheckBox fridayCheck;

    @FXML
    private CheckBox saturdayCheck;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void generateButtonClicked(ActionEvent event) {
        stage.close();
    }

    @FXML
    void cancelButtonClicked(ActionEvent event) {
        stage.close();
    }

}
