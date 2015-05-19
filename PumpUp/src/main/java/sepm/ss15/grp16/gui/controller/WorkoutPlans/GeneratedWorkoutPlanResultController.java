package sepm.ss15.grp16.gui.controller.WorkoutPlans;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.training.Trainingsplan;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.gui.controller.StageTransitionLoader;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 16.05.15.
 *
 */
public class GeneratedWorkoutPlanResultController extends Controller implements Initializable {


    private static final Logger LOGGER = LogManager.getLogger();
    private Trainingsplan generatedWorkoutPlan;
    private StageTransitionLoader transitionLoader;
    private BooleanProperty DTOArrived = new SimpleBooleanProperty();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.transitionLoader = new StageTransitionLoader(this);
        DTOArrived.addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                displayWorkoutPlan();
            }
        });
        LOGGER.info("GeneratedWorkoutPlanResult successfully initialized!");

    }

    public void setGeneratedWorkoutPlan(Trainingsplan generatedWorkoutPlan){
        this.generatedWorkoutPlan = generatedWorkoutPlan;
        LOGGER.info("Generated workoutplan arrived.");
    }

    @FXML
    public void insertIntoCalendarClicked() {
        LOGGER.info("InsertIntoCalendar clicked, delegating workoutplan and request towards the calendar...");
    }


    @FXML
    public void saveWorkoutPlanClicked(){
        LOGGER.info("Save button clicked, delegating request towards the service layer...");
    }

    @FXML
    public void cancelClicked() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Ansicht verlassen.");
        alert.setHeaderText("Wenn Sie abbrechen, wir der angezeigte Trainingsplan nicht gespeichert und verworfen.");
        alert.setContentText("Möchten Sie die Ansicht verlassen?");
        ButtonType yes = new ButtonType("Ja");
        ButtonType cancel = new ButtonType("Nein", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yes, cancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yes) {
            stage.close();
            LOGGER.info("User clicked 'Cancel', leaving GeneratedWorkoutPlanResult...");
        }else {
            alert.close();
        }
    }

    public void setFlag(boolean val){
        DTOArrived.set(val);
    }

    private void displayWorkoutPlan(){

    }

}
