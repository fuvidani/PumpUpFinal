package sepm.ss15.grp16.gui.controller.WorkoutPlans;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 08.05.15.
 * This controller is the central point of all workout plans assigned to the user.
 */
public class WorkoutPlansController implements Initializable{

    private Stage stage;

    @FXML
    private CheckBox customWorkoutPlansCheck;

    @FXML
    private TableView<?> workoutPlanTable;

    @FXML
    private Label trainingTypeLabel;

    @FXML
    private CheckBox defaultWorkoutPlansCheck;

    @FXML
    private ListView<?> workoutPlansListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Sets the stage of this controller.
     * @param stage the responsible stage
     */
    public void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    void newWorkoutPlanClicked(ActionEvent event) {

    }

    @FXML
    void copyWorkoutPlanClicked(ActionEvent event) {

    }

    @FXML
    void generateWorkoutPlanClicked(ActionEvent event) {

    }

    @FXML
    void deleteWorkoutPlanClicked(ActionEvent event) {

    }

    @FXML
    void editWorkoutPlanClicked(ActionEvent event) {

    }

    @FXML
    void embedInCalenderClicked(ActionEvent event) {

    }

    @FXML
    void getBackClicked(ActionEvent event) {
        this.stage.close();
    }


}
