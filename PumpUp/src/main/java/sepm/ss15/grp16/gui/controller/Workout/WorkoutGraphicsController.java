package sepm.ss15.grp16.gui.controller.Workout;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Circle;
import sepm.ss15.grp16.gui.controller.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 08.05.15.
 * This controller controls the upper section of the training's stage.
 * It controls which GIF and/or video is shown and animates the ticking clock.
 */
public class WorkoutGraphicsController extends Controller implements Initializable{


    @FXML
    private Circle clock;

    @FXML
    private MediaView mediaView;

    @FXML
    private Label currentExerciseLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
