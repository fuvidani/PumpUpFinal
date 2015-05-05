package sepm.ss15.grp16.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 06.05.15.
 * This is the main controller of the Management section.
 */
public class ManagementController  implements Initializable{


    private Management_ExerciseController exerciseController;
    private Management_WorkoutplanController workoutplanController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exerciseController.initialize(location,resources);
        workoutplanController.initialize(location,resources);

    }

    /**
     * This method is called whenever the user hits "Zurueck" in the window.
     * Closes this stage and directs back to the previous one.
     * @param event
     */
    @FXML
    void goBackButtonClicked(ActionEvent event) {

    }



}
