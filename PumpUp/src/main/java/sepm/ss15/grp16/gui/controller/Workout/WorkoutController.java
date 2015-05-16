package sepm.ss15.grp16.gui.controller.Workout;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import sepm.ss15.grp16.entity.Training.Helper.ExerciseSet;
import sepm.ss15.grp16.gui.controller.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 06.05.15.
 * This is the central controller of the training's stage.
 * It consists of a graphicsController and a musicPlayerController.
 */

public class WorkoutController extends Controller implements Initializable {

    @FXML
    private Arc circleCounter;

    @FXML
    private Label instructionLabel;

    @FXML
    private Button pauseButton;

    @FXML
    private Label exerciseLabel;

    @FXML
    private ImageView exerciseImageView;

    @FXML
    private Label counterLable;

    private final Integer STARTTIME = 15;

    private Timeline timeline;
    private IntegerProperty timeSeconds =
            new SimpleIntegerProperty(STARTTIME);

    ExerciseSet activeExercise;

    private boolean mode = true; // true counter mode
    private boolean pause = true;




    public void initialize(URL location, ResourceBundle resources) {
        counterLable.textProperty().bind(timeSeconds.asString());
        circleCounter.setLength(0);

        timeline = new Timeline();
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pause();
            }
        });
        pause();
    }

    public void start(boolean mode) {
        pause = false;
        pauseButton.setText("Stop");
        circleCounter.setLength(0);
        timeline.getKeyFrames().clear();
        if (mode) {
            timeSeconds.set(STARTTIME);
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(STARTTIME + 1),
                            new KeyValue(timeSeconds, 0), new KeyValue(circleCounter.lengthProperty(), 360)));

        } else {
            timeSeconds.set(0);
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(Integer.MAX_VALUE),
                            new KeyValue(timeSeconds, Integer.MAX_VALUE)));
        }
        timeline.playFromStart();
    }

    public void pause() {
        pause = true;
        pauseButton.setText("Start");
    }

    @FXML
    void onPause(ActionEvent event) {
        if (pause) {
            start(mode);
            mode = !mode;
        } else {
            timeline.stop();
            pause();
        }

    }
}
