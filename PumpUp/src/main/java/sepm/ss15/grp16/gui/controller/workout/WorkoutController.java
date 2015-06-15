package sepm.ss15.grp16.gui.controller.workout;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Arc;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import sepm.ss15.grp16.entity.calendar.Appointment;
import sepm.ss15.grp16.entity.training.TrainingsSession;
import sepm.ss15.grp16.entity.training.WorkoutResult;
import sepm.ss15.grp16.entity.training.helper.ExerciseSet;
import sepm.ss15.grp16.gui.ImageLoader;
import sepm.ss15.grp16.gui.PageEnum;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.gui.controller.main.MainController;
import sepm.ss15.grp16.persistence.dao.exercise.ExerciseDAO;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Daniel Fuevesi on 06.05.15.
 * This is the central controller of the training's stage.
 * It consists of a graphicsController and a musicPlayerController.
 */

public class WorkoutController extends Controller {

    private static final int IMAGEDURATION = 1500;
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger();

    @FXML
    private Arc circleCounter;

    @FXML
    private Label lastExerciseLable;

    @FXML
    private Label instructionLabel;

    @FXML
    private Button pauseButton;

    @FXML
    private Label exerciseLabel;

    @FXML
    private TextField repetionField;

    @FXML
    private ImageView exerciseImageView;

    @FXML
    private TextField durationField;

    @FXML
    private FlowPane exerciseFlow;

    @FXML
    private Label counterLable;

    @FXML
    private Label discriptionLabel;

    @FXML
    private Pane pictureFitPane;

    @FXML
    private Node WorkoutMusicPlayer;

    @FXML
    private WorkoutMusicPlayerController musicPlayerController;
    private MotivatonModul motivationModul;

    private Timeline counterTimeline;
    private IntegerProperty timeSeconds;
    private ArrayList<ExerciseSet> exerciseList;
    private int activeExercisePosition;
    private ArrayList<Image> imageList;
    private int activeImagePosition;
    private Timeline imageTimeline;
    private HashMap<ExerciseSet, ArrayList<Image>> images;
    private LinkedList<ExerciseView> exerciseViews;
    private Status status;
    private TrainingsSession session;
    private WorkoutResult workoutResult;

    public WorkoutController(ExerciseDAO exerciseDAO, MotivatonModul motivationModul) {
        this.motivationModul = motivationModul;
    }

    @Override
    public void initController() {
        Appointment appointment = ((MainController) getParentController()).getExecutionAppointment();
        workoutResult = new WorkoutResult(appointment);
        session = appointment.getSession();

        mainFrame.addPageManeItem("Open Fullscreen", event1 -> {
            mainFrame.openFullScreenMode();
        });
        mainFrame.addPageManeItem("Training abbrechen", event1 -> {
            mainFrame.navigateToParent();
        });

        exerciseImageView.setPreserveRatio(true);
        exerciseImageView.fitWidthProperty().bind(pictureFitPane.widthProperty());
        exerciseImageView.fitHeightProperty().bind(pictureFitPane.heightProperty());

        durationField.setDisable(true);
        repetionField.setDisable(true);

        musicPlayerController.setParent(this);
        motivationModul.setMusicPlayerController(musicPlayerController);
        musicPlayerController.play();
        exerciseList = new ArrayList<>(session.getExerciseSets());
        activeExercisePosition = -1;

        activeImagePosition = -1;
        imageList = new ArrayList<>();
        imageTimeline = new Timeline();
        imageTimeline.setCycleCount(Timeline.INDEFINITE);
        imageTimeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(IMAGEDURATION), event -> {
                    activeImagePosition = (activeImagePosition + 1) % imageList.size();
                    exerciseImageView.setImage(imageList.get(activeImagePosition));
                }, new KeyValue[0]));

        timeSeconds = new SimpleIntegerProperty();
        counterLable.textProperty().bind(timeSeconds.asString());

        counterTimeline = new Timeline();
        counterTimeline.setOnFinished(event -> pause());

        durationField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {

            } else if (newValue.matches("\\d*")) {
                int value = Integer.parseInt(newValue);
            } else {
                durationField.setText(oldValue);
            }
        });

        repetionField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {

            } else if (newValue.matches("\\d*")) {
                int value = Integer.parseInt(newValue);
            } else {
                repetionField.setText(oldValue);
            }
        });

        loadImages();

        exerciseViews = new LinkedList<>();
        for (ExerciseSet set : exerciseList) {
            exerciseViews.add(new ExerciseView(set));
        }
        exerciseFlow.getChildren().addAll(exerciseViews);
        exerciseViews.getFirst().avtivate();

        pause();
    }

    private void loadImages() {
        images = new HashMap<>();
        for (ExerciseSet set : exerciseList) {
            ArrayList<Image> imageList = new ArrayList<>();
            for (String img : set.getExercise().getGifLinks()) {
                try {
                    imageList.add(ImageLoader.loadImage(this.getClass(), img));
                } catch (URISyntaxException e) {
                    LOGGER.warn("Couldn't load Image: " + img, e);
                }
            }
            if (imageList.isEmpty()) {
                imageList.add(null);
            }
            images.put(set, imageList);
        }
    }

    private ExerciseSet activeExercise() {
        return exerciseList.get(activeExercisePosition);
    }

    private void switchToNextExercise() {
        if (activeExercisePosition >= 0) {
            exerciseFlow.getChildren().remove(exerciseViews.removeFirst());
            exerciseViews.getFirst().avtivate();
        }
        activeExercisePosition++;
    }

    private boolean allExercisesDone() {
        return exerciseList.size() - 1 <= activeExercisePosition;
    }

    private void reloadImages() throws URISyntaxException {
        imageTimeline.stop();
        imageList = images.get(activeExercise());
        exerciseImageView.setImage(imageList.get(0));
        activeImagePosition = 0;
        imageTimeline.play();
    }

    private void runExercise() {
        status = Status.RUNNUNG;
        pauseButton.setText("Stop");
        counterTimeline.playFromStart();
        if (activeExercisePosition > 0) {
            workoutResult.setExecution(exerciseList.get(activeExercisePosition - 1),
                    repetionField.getText().isEmpty() ? null : Integer.parseInt(repetionField.getText()),
                    durationField.getText().isEmpty() ? null : Integer.parseInt(durationField.getText()));
            repetionField.setDisable(true);
        }
    }

    private void pause() {
        circleCounter.setLength(0);

        if (activeExercisePosition >= 0) {
            lastExerciseLable.setText(activeExercise().getExercise().getName());
            if (activeExercise().getType() == ExerciseSet.SetType.repeat) {
                durationField.setText(counterLable.getText());
                repetionField.setText(activeExercise().getRepeat() + "");
            } else {
                durationField.setText((activeExercise().getRepeat() - timeSeconds.getValue()) + "");
                repetionField.setText("");
                repetionField.setDisable(false);
            }
        }

        if (allExercisesDone()) {
            status = Status.FINISHED;
            imageTimeline.stop();
            pauseButton.setText("Trainingsresultate");
            exerciseLabel.setText("training beendet!");
            discriptionLabel.setText(activeExercise().getExercise().getDescription());
            exerciseImageView.setImage(null);
        } else {
            switchToNextExercise();

            status = Status.PAUSED;
            pauseButton.setText("Start");
            counterTimeline.getKeyFrames().clear();
            exerciseLabel.setText(activeExercise().getExercise().getName());
            discriptionLabel.setText(activeExercise().getExercise().getDescription());


            try {
                reloadImages();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            if (activeExercise().getType() == ExerciseSet.SetType.time) {
                timeSeconds.set(activeExercise().getRepeat());
                circleCounter.setLength(360);
                counterTimeline.getKeyFrames().add(
                        new KeyFrame(Duration.seconds(activeExercise().getRepeat() + 1),
                                new KeyValue(timeSeconds, 0), new KeyValue(circleCounter.lengthProperty(), 0)));
            } else {
                timeSeconds.set(0);
                counterTimeline.getKeyFrames().add(
                        new KeyFrame(Duration.seconds(Integer.MAX_VALUE),
                                new KeyValue(timeSeconds, Integer.MAX_VALUE)));
            }
        }
    }

    @FXML
    private void onPause(ActionEvent event) {
        if (status == Status.PAUSED) {
            runExercise();
        } else if (status == Status.RUNNUNG) {
            counterTimeline.stop();
            pause();
        } else {
            workoutResult.setExecution(activeExercise(),
                    repetionField.getText().isEmpty() ? null : Integer.parseInt(repetionField.getText()),
                    durationField.getText().isEmpty() ? null : Integer.parseInt(durationField.getText()));
            mainFrame.openDialog(PageEnum.WorkoutResult);
            mainFrame.navigateToParent();
        }
    }

    public void launchDialog(PageEnum page) {
        mainFrame.openDialog(page);
    }

    public WorkoutMusicPlayerController getMusicPlayerController() {
        return musicPlayerController;
    }

    public WorkoutResult getWorkoutResult() {
        return workoutResult;
    }

    private enum Status {
        RUNNUNG, PAUSED, FINISHED;
    }

    public class ExerciseView extends StackPane {

        public ExerciseView(ExerciseSet exerciseSet) {
            super();

            setMinHeight(90);

            ImageView imageView = new ImageView(images.get(exerciseSet).get(0));
            imageView.setFitHeight(90);
            imageView.setPreserveRatio(true);
            getChildren().add(imageView);
            BorderPane borderPane = new BorderPane();
            Label label = new Label(exerciseSet.getRepresentationText());
            label.setStyle("-fx-font-weight: bold");
            borderPane.setBottom(label);
            getChildren().add(borderPane);
            setStyle("-fx-padding: 10;" +
                    "-fx-background-color: rgba(222, 222, 222, 1);" +
                    "-fx-background-radius: 5");
        }


        public void avtivate() {
            setStyle("-fx-padding: 10;" +
                    "-fx-background-color: firebrick;" +
                    "-fx-background-radius: 5");
        }
    }
}
