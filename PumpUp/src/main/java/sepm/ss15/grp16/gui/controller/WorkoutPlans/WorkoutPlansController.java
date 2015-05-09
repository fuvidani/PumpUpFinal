package sepm.ss15.grp16.gui.controller.WorkoutPlans;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.gui.controller.StageTransitionLoader;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 08.05.15.
 * This controller is the central point of all workout plans assigned to the user.
 */
public class WorkoutPlansController extends Controller implements Initializable{


    private StageTransitionLoader transitionLoader;

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
        this.transitionLoader = new StageTransitionLoader(this);
    }

    @FXML
    void newWorkoutPlanClicked(ActionEvent event) {
        transitionLoader.openStage("fxml/Create_Edit_WorkoutPlans.fxml",(Stage)workoutPlanTable.getScene().getWindow(),"Trainingsplan erstellen / bearbeiten",1000,620, true);
    }

    @FXML
    void copyWorkoutPlanClicked(ActionEvent event) {
        transitionLoader.openStage("fxml/Create_Edit_WorkoutPlans.fxml",(Stage)workoutPlanTable.getScene().getWindow(),"Trainingsplan erstellen / bearbeiten",1000,620, true);
    }

    @FXML
    void generateWorkoutPlanClicked(ActionEvent event) {
        transitionLoader.openStage("fxml/GenerateWorkoutPlan.fxml",(Stage)workoutPlanTable.getScene().getWindow(),"Trainingsplan generieren",600,400, false);
    }

    @FXML
    void deleteWorkoutPlanClicked(ActionEvent event) {

    }

    @FXML
    void editWorkoutPlanClicked(ActionEvent event) {
        transitionLoader.openStage("fxml/Create_Edit_WorkoutPlans.fxml",(Stage)workoutPlanTable.getScene().getWindow(),"Trainingsplan erstellen / bearbeiten",1000,620, true);
    }

    @FXML
    void embedInCalenderClicked(ActionEvent event) {
        transitionLoader.openStage("fxml/WorkoutPlanIntoCalendar.fxml",(Stage)workoutPlanTable.getScene().getWindow(),"Trainingsplan in Kalender einfügen",600,400, false);
    }

    @FXML
    void getBackClicked(ActionEvent event) {
        this.stage.close();
    }


}
