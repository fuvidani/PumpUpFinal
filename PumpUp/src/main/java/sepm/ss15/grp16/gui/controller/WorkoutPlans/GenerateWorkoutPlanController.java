package sepm.ss15.grp16.gui.controller.WorkoutPlans;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.gui.controller.StageTransitionLoader;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 08.05.15.
 * This controller is responsible for the auto-generated workout plans depending on the user's criteria.
 * This is just a skeleton, further planning and implementation required.
 */
public class GenerateWorkoutPlanController extends Controller implements Initializable{


    @FXML
    private CheckBox barbellCheck;

    @FXML
    private CheckBox punchbagCheck;

    @FXML
    private CheckBox yogaBallCheck;

    @FXML
    private CheckBox dumbbellCheck;

    @FXML
    private CheckBox chinupBarCheck;

    @FXML
    private RadioButton strengthRadio;

    @FXML
    private CheckBox expanderCheck;

    @FXML
    private CheckBox medicineBallCheck;

    @FXML
    private RadioButton balanceRadio;

    @FXML
    private CheckBox absRollerCheck;

    @FXML
    private RadioButton enduranceRadio;

    @FXML
    private CheckBox jumpingRopeCheck;

    @FXML
    private RadioButton flexibilityRadio;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void generateButtonClicked(ActionEvent event) {
        stage.close();
    }


}
