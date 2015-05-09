package sepm.ss15.grp16.gui.controller.Workout;

import javafx.fxml.Initializable;
import sepm.ss15.grp16.gui.controller.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 06.05.15.
 * This is the central controller of the training's stage.
 * It consists of a graphicsController and a musicPlayerController.
 *
 */
public class WorkoutController extends Controller implements Initializable{


    private WorkoutGraphicsController graphicsController;
    private WorkoutMusicPlayerController musicPlayerController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.graphicsController = new WorkoutGraphicsController();
        this.musicPlayerController = new WorkoutMusicPlayerController();
        this.graphicsController.initialize(location,resources);
        this.musicPlayerController.initialize(location,resources);

    }


}
