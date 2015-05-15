package sepm.ss15.grp16.gui.controller.Workout;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.gui.controller.StageTransitionLoader;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 06.05.15.
 * This controller controls the little pop-up window before the actual training starts.
 *
 */
public class WorkoutstartController extends Controller implements Initializable{


    private StageTransitionLoader transitionLoader;
    @FXML
    private ListView<?> toDoListView;

    @FXML
    private Label musicPathLabel;

    @FXML
    private Label trainingTypeLabel;

    @FXML
    private Button startButton;

    public WorkoutstartController(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      this.transitionLoader = new StageTransitionLoader(this);
    }

    @FXML
    void browseMusicClicked(ActionEvent event) {

    }

    @FXML
    void startButtonClicked(ActionEvent event) {
        transitionLoader.openStage("fxml/Workout.fxml", (Stage) toDoListView.getScene().getWindow(), "Training", 1100, 750, true);
    }



}
