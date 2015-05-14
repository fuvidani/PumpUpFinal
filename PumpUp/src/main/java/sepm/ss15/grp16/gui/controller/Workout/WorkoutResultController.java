package sepm.ss15.grp16.gui.controller.Workout;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import sepm.ss15.grp16.gui.controller.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 08.05.15.
 *
 */
public class WorkoutResultController extends Controller implements Initializable{


    @FXML
    private TableView<?> exercisesTable;

    @FXML
    private LineChart<?, ?> workoutChart;

    @FXML
    private Label burnedCaloriesLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void shareFacebookClicked(ActionEvent event) {

    }

    @FXML
    void endWorkoutClicked(ActionEvent event) {
        stage.close();
    }


}
