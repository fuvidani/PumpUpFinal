package sepm.ss15.grp16.gui.controller.workout;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Arc;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import sepm.ss15.grp16.entity.training.TrainingsSession;
import sepm.ss15.grp16.entity.training.helper.ExerciseSet;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.gui.ImageLoader;
import sepm.ss15.grp16.persistence.dao.exercise.ExerciseDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 06.05.15.
 * This is the central controller of the training's stage.
 * It consists of a graphicsController and a musicPlayerController.
 */

public class WorkoutController extends Controller implements Initializable {

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

    public WorkoutController(ExerciseDAO exerciseDAO) {
        LinkedList<ExerciseSet> list = new LinkedList<>();

        try {
            list.add(new ExerciseSet(exerciseDAO.searchByID(0), null, 10, ExerciseSet.SetType.repeat, 1, false));
            list.add(new ExerciseSet(exerciseDAO.searchByID(1), null, 15, ExerciseSet.SetType.time, 2, false));
            list.add(new ExerciseSet(exerciseDAO.searchByID(2), null, 10, ExerciseSet.SetType.repeat, 3, false));
            list.add(new ExerciseSet(exerciseDAO.searchByID(3), null, 20, ExerciseSet.SetType.time, 4, false));

        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        session = new TrainingsSession();
        session.setExerciseSets(list);
    }

    public void initialize(URL location, ResourceBundle resources) {

        exerciseList = new ArrayList<>(session.getExerciseSets());
        activeExercisePosition = -1;

        activeImagePosition = -1;
        imageList = new ArrayList<>();
        imageTimeline = new Timeline();
        imageTimeline.setCycleCount(Timeline.INDEFINITE);
        imageTimeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(IMAGEDURATION), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        activeImagePosition = (activeImagePosition + 1) % imageList.size();
                        exerciseImageView.setImage(imageList.get(activeImagePosition));
                    }
                }, new KeyValue[0]));

        timeSeconds = new SimpleIntegerProperty();
        counterLable.textProperty().bind(timeSeconds.asString());

        counterTimeline = new Timeline();
        counterTimeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pause();
            }
        });

        durationField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.isEmpty()) {

                } else if (newValue.matches("\\d*")) {
                    int value = Integer.parseInt(newValue);
                } else {
                    durationField.setText(oldValue);
                }
            }
        });

        repetionField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.isEmpty()) {

                } else if (newValue.matches("\\d*")) {
                    int value = Integer.parseInt(newValue);
                } else {
                    repetionField.setText(oldValue);
                }
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
        durationField.setDisable(true);
        repetionField.setDisable(true);
    }

    private void pause() {
        circleCounter.setLength(0);

        if (allExercisesDone()) {
            status = Status.FINISHED;
            imageTimeline.stop();
            pauseButton.setText("Trainingsresultate");
            exerciseLabel.setText("Training beendet!");
            discriptionLabel.setText(activeExercise().getExercise().getDescription());
            exerciseImageView.setImage(null);
            durationField.setDisable(true);
            repetionField.setDisable(true);
        } else {
            if (activeExercisePosition >= 0) {
                lastExerciseLable.setText(activeExercise().getExercise().getName());
                if (activeExercise().getType() == ExerciseSet.SetType.repeat) {
                    durationField.setText(counterLable.getText());
                    repetionField.setText(activeExercise().getRepeat() + "");
                } else {
                    durationField.setText(activeExercise().getRepeat() + "");
                    repetionField.setText("");
                }
            }

            durationField.setDisable(false);
            repetionField.setDisable(false);

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
            // TODO LOAD TRAININGSRESULTATE
        }
    }


    private enum Status {
        RUNNUNG, PAUSED, FINISHED;
    }

    public class ExerciseView extends StackPane {

        public ExerciseView(ExerciseSet exerciseSet) {
            super();

            setMinHeight(50);
            setMinWidth(110);
            setPadding(new Insets(0, 10, 0, 0));

            ImageView imageView = new ImageView(images.get(exerciseSet).get(0));
            imageView.setFitWidth(100);
            imageView.setFitHeight(50);
            getChildren().add(imageView);
            BorderPane borderPane = new BorderPane();
            borderPane.setBottom(new Label(exerciseSet.getRepresentationText()));
            getChildren().add(borderPane);
        }


        public void avtivate() {
            setStyle("-fx-padding: 10;" +
                    "-fx-background-color: firebrick;" +
                    "-fx-background-radius: 5;");
        }
    }
}
