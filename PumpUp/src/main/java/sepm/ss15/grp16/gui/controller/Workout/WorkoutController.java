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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Arc;
import javafx.util.Duration;
import sepm.ss15.grp16.entity.Exercise;
import sepm.ss15.grp16.entity.training.TrainingsSession;
import sepm.ss15.grp16.entity.training.helper.ExerciseSet;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.service.ExerciseService;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.exception.ServiceException;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
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

    private Timeline timeline;
    private IntegerProperty timeSeconds;

    ArrayList<ExerciseSet> exerciseList;
    private int activeExercisePosition;

    private boolean pause = true;

    TrainingsSession session;

    public WorkoutController(ExerciseService exerciseService)
    {
        LinkedList<ExerciseSet> list = new LinkedList<>();
        /*try {
            int i = 10;
            boolean mode = true;
            for(Exercise exercise : exerciseService.getAllStrengthExercises())
            {
                ExerciseSet set = new ExerciseSet();
                set.setExercise(exercise);
                set.setType(new ExerciseSet.SetType(mode ? ExerciseSet.SetType.REPEAT : ExerciseSet.SetType.TIME));
                list.add(new ExerciseSet());
                i = i + 10;
                mode = !mode;
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }*/
        list.add(new ExerciseSet(null, null, 10, new ExerciseSet.SetType(ExerciseSet.SetType.REPEAT)))
        session = new TrainingsSession();
        session.setExerciseSets(list);
    }

    public void initialize(URL location, ResourceBundle resources) {

        exerciseList = new ArrayList<ExerciseSet>(session.getExerciseSets());
        activeExercisePosition = 0;

        timeSeconds = new SimpleIntegerProperty();
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

    private ExerciseSet activeExercise()
    {
        return exerciseList.get(activeExercisePosition);
    }

    private void switchToNextExercise()
    {
        activeExercisePosition++;
    }

    private void runExercise() {
        pause = false;
        pauseButton.setText("Stop");
        timeline.playFromStart();
    }



    private void pause() {
        pause = true;
        pauseButton.setText("Start");
        timeline.getKeyFrames().clear();

        switchToNextExercise();

        if (activeExercise().getType().equals("time")) {
            timeSeconds.set(activeExercise().getRepeat());
            circleCounter.setLength(360);
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(activeExercise().getRepeat() + 1),
                            new KeyValue(timeSeconds, 0), new KeyValue(circleCounter.lengthProperty(), 0)));
        } else {
            timeSeconds.set(0);
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(Integer.MAX_VALUE),
                            new KeyValue(timeSeconds, Integer.MAX_VALUE)));
        }
    }

    @FXML
    private void onPause(ActionEvent event) {
        if (pause) {
            runExercise();
        } else {
            timeline.stop();
            pause();
        }

    }
}
