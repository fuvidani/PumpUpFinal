package sepm.ss15.grp16.gui.controller.WorkoutPlans;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import sepm.ss15.grp16.gui.controller.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 08.05.15.
 *
 */
public class Create_Edit_WorkoutPlanController extends Controller implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    void saveWorkoutClicked(ActionEvent event) {
        stage.close();
    }

    @FXML
    void cancelClicked(ActionEvent event) {
        stage.close();
    }


}
