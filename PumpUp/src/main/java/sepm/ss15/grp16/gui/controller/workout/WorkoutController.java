package sepm.ss15.grp16.gui.controller.workout;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Arc;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import org.apache.logging.log4j.LogManager;
import sepm.ss15.grp16.entity.calendar.Appointment;
import sepm.ss15.grp16.entity.exercise.Exercise;
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

    private static final int                             IMAGEDURATION = 1500;
    private static final org.apache.logging.log4j.Logger LOGGER        = LogManager.getLogger();

    @FXML
    private Arc circleCounter;

    @FXML
    private Label lastExerciseLable;

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
    private HBox exerciseFlow;

    @FXML
    private Label counterLable;

    @FXML
    private Pane pictureFitPane;

    @FXML
    private WorkoutMusicPlayerController musicPlayerController;

    private MotivatonModul motivationModul;

    private Timeline                               counterTimeline;
    private IntegerProperty                        timeSeconds;
    private StringProperty                         time;
    private ArrayList<ExerciseSet>                 exerciseList;
    private int                                    activeExercisePosition;
    private ArrayList<Image>                       imageList;
    private int                                    activeImagePosition;
    private Timeline                               imageTimeline;
    private HashMap<ExerciseSet, ArrayList<Image>> images;
    private LinkedList<ExerciseView>               exerciseViews;
    private Status                                 status;
    private TrainingsSession                       session;
    private WorkoutResult                          workoutResult;

    public WorkoutController(ExerciseDAO exerciseDAO, MotivatonModul motivationModul) {
        this.motivationModul = motivationModul;
    }

    @Override
    public void initController() {
        Appointment appointment = ((MainController) getParentController()).getExecutionAppointment();
        workoutResult = new WorkoutResult(appointment);
        session = appointment.getSession();

        mainFrame.addPageManeItem("Übungsdetails anzeien", event1 -> onDetailedExerciseClicked());
        mainFrame.addPageManeItem("Open Fullscreen", event1 -> mainFrame.openFullScreenMode());
        mainFrame.addPageManeItem("Training abbrechen", event1 -> {
            musicPlayerController.stopMusic();
            counterTimeline.stop();
            mainFrame.navigateToParent();
        });

        exerciseImageView.setOnMouseClicked(event1 -> onDetailedExerciseClicked());
        exerciseImageView.setOnTouchReleased(event1 -> onDetailedExerciseClicked());

        exerciseImageView.setPreserveRatio(true);
        exerciseImageView.fitWidthProperty().bind(pictureFitPane.widthProperty());
        exerciseImageView.fitHeightProperty().bind(pictureFitPane.heightProperty());
        exerciseList = new ArrayList<>(session.getExerciseSets());

        loadImages();

        durationField.setDisable(true);
        repetionField.setDisable(true);

        musicPlayerController.setParent(this);
        motivationModul.setMusicPlayerController(musicPlayerController);
        musicPlayerController.play();
        activeExercisePosition = -1;

        activeImagePosition = -1;
        imageList = new ArrayList<>();
        imageTimeline = new Timeline();
        imageTimeline.setCycleCount(Timeline.INDEFINITE);
        imageTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(IMAGEDURATION), event -> {
            activeImagePosition = (activeImagePosition + 1) % imageList.size();
            exerciseImageView.setImage(imageList.get(activeImagePosition));
        }, new KeyValue[0]));

        time = new SimpleStringProperty();
        timeSeconds = new SimpleIntegerProperty();
        timeSeconds.addListener((observable1, oldValue1, newValue1) -> {
            if (status == Status.RUNNUNG) {
                motivationModul.play(newValue1.intValue(), activeExercise().getType());
            }
        });
        time.bindBidirectional(timeSeconds, new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                return fillUp(object.intValue() / 60) + ":" + fillUp(object.intValue() % 60);
            }

            @Override
            public Number fromString(String string) {
                return 0;
            }

            private String fillUp(int num) {
                if (num < 10) {
                    return "0" + num;
                } else {
                    return num + "";
                }
            }
        });
        counterLable.textProperty().bind(time);

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


        Stage stage = (Stage) lastExerciseLable.getScene().getWindow();
        stage.setOnCloseRequest(e -> {
            musicPlayerController.stopMusic();
            counterTimeline.stop();
            mainFrame.navigateToMain();
        });

        exerciseViews = new LinkedList<>();
        for (ExerciseSet set : exerciseList) {
            exerciseViews.add(new ExerciseView(set));
        }
        exerciseFlow.getChildren().addAll(exerciseViews);
        exerciseViews.getFirst().avtivate();

        pause();
    }

    private void loadImages() {
        try {
            Stage dialogStage = new Stage();
            dialogStage.setWidth(300);
            dialogStage.setHeight(200);
            ProgressBar pb = new ProgressBar(exerciseList.size());
            pb.setMaxWidth(250);
            ProgressIndicator pi = new ProgressIndicator(exerciseList.size());
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setTitle("Lade");
            VBox vBox = new VBox(new Label("Lade Live-Modus"), pb, pi);
            vBox.setFillWidth(true);
            vBox.setAlignment(Pos.CENTER);
            vBox.setSpacing(10);
            dialogStage.setScene(new Scene(vBox));
            String pathToResource = getClass().getClassLoader().getResource("icons").toURI().toString();
            dialogStage.getIcons().add(new Image(pathToResource.concat("/logo.png")));
            Task<Void> task = new Task<Void>() {
                @Override
                public Void call() {
                    updateMessage("Lade Livemodus . . .");
                    updateProgress(0, exerciseList.size());

                    images = new HashMap<>();
                    for (int i = 0; i < exerciseList.size(); i++) {
                        ExerciseSet set = exerciseList.get(i);
                        ArrayList<Image> imageList = new ArrayList<>();
                        for (String img : set.getExercise().getGifLinks()) {
                            try {
                                imageList.add(ImageLoader.loadImage(this.getClass(), img));
                            } catch (URISyntaxException e) {
                                LOGGER.warn("Couldn't load Image: " + img, e);
                            }
                            updateProgress(i + 1, exerciseList.size());
                        }
                        if (imageList.isEmpty()) {
                            imageList.add(null);
                        }
                        images.put(set, imageList);
                    }
                    return null;
                }
            };
            pb.progressProperty().bind(task.progressProperty());
            pi.progressProperty().bind(task.progressProperty());
            task.setOnSucceeded(event -> dialogStage.close());

            Thread th = new Thread(task);
            th.setDaemon(true);
            th.start();
            dialogStage.showAndWait();

        } catch (URISyntaxException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private ExerciseSet activeExercise() {
        return exerciseList.get(activeExercisePosition);
    }

    private void switchToNextExercise() {
        if (activeExercisePosition >= 0) {
            //Animate remove
            ExerciseView last = exerciseViews.removeFirst();
            ExerciseView next = exerciseViews.getFirst();

            last.setMinWidth(last.getWidth());
            last.getChildren().clear();
            Timeline flowAnimation = new Timeline(new KeyFrame(Duration.millis(700), event -> {
                exerciseFlow.getChildren().remove(last);
            }, new KeyValue(last.minWidthProperty(), 0)));
            Timeline removeAnimation = new Timeline(new KeyFrame(Duration.millis(300), event -> {
                flowAnimation.playFromStart();
            }, new KeyValue(last.opacityProperty(), 0)));
            removeAnimation.playFromStart();
            next.avtivate();
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
            workoutResult.setExecution(exerciseList.get(activeExercisePosition - 1), repetionField.getText().isEmpty() ? null : Integer.parseInt(repetionField.getText()), durationField.getText().isEmpty() ? null : Integer.parseInt(durationField.getText()));
            repetionField.setDisable(true);
        }
    }

    private void pause() {
        circleCounter.setLength(0);

        if (activeExercisePosition >= 0) {
            lastExerciseLable.setText(activeExercise().getExercise().getName());
            if (activeExercise().getType() == ExerciseSet.SetType.repeat) {
                durationField.setText(timeSeconds.getValue() + "");
                repetionField.setText(activeExercise().getRepeat() + "");
            } else {
                durationField.setText((activeExercise().getRepeat() - timeSeconds.getValue()) + "");
                repetionField.setText("");
                repetionField.setDisable(false);
                repetionField.requestFocus();
            }
        }

        if (allExercisesDone()) {
            status = Status.FINISHED;
            imageTimeline.stop();
            exerciseImageView.setImage(null);
            pauseButton.setText("Trainingsresultate");
            exerciseLabel.setText("Training beendet!");
        } else {
            switchToNextExercise();

            status = Status.PAUSED;
            pauseButton.setText("Start");
            exerciseLabel.setText(activeExercise().getRepresentationText());

            counterTimeline.getKeyFrames().clear();

            try {
                reloadImages();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

            if (activeExercise().getType() == ExerciseSet.SetType.time) {
                timeSeconds.set(activeExercise().getRepeat());
                circleCounter.setLength(360);
                counterTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(activeExercise().getRepeat() + 1), new KeyValue(timeSeconds, 0), new KeyValue(circleCounter.lengthProperty(), 0)));

            } else {
                timeSeconds.set(0);
                counterTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(Integer.MAX_VALUE), new KeyValue(timeSeconds, Integer.MAX_VALUE)));
            }
        }
    }

    public void finish() {
        workoutResult.setExecution(activeExercise(), repetionField.getText().isEmpty() ? null : Integer.parseInt(repetionField.getText()), durationField.getText().isEmpty() ? null : Integer.parseInt(durationField.getText()));
        musicPlayerController.stopMusic();
        mainFrame.openDialog(PageEnum.WorkoutResult);
        mainFrame.navigateToParent();
    }

    @FXML
    private void onActionButtonClicked(ActionEvent event) {
        if (status == Status.PAUSED) {
            runExercise();
        } else if (status == Status.RUNNUNG) {
            counterTimeline.stop();
            pause();
        } else {
            finish();
        }
    }

    @FXML
    private void onDetailedExerciseClicked() {
        if (status == Status.PAUSED) {
            launchDialog(PageEnum.DisplayExercise);
        }
    }

    @FXML
    private void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            onActionButtonClicked(null);
        }
    }

    public void launchDialog(PageEnum page) {
        mainFrame.openDialog(page);
    }

    public WorkoutMusicPlayerController getMusicPlayerController() {
        return musicPlayerController;
    }

    public Exercise getExercise() {
        return activeExercise().getExercise();
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
            setPadding(new Insets(3, 3, 3, 3));

            setPrefWidth(0);
            ImageView imageView = new ImageView(images.get(exerciseSet).get(0));
            imageView.setFitHeight(90);
            imageView.setPreserveRatio(true);
            imageView.fitWidthProperty().bind(widthProperty());//??
            Tooltip tooltip = new Tooltip(exerciseSet.getRepresentationText());
            getChildren().add(imageView);
            BorderPane borderPane = new BorderPane();
            Label label = new Label(exerciseSet.getRepresentationText());
            label.setStyle("-fx-font-family: Segoe UI Bold;" + "-fx-text-fill: white;");
            label.setTooltip(tooltip);
            borderPane.setBottom(label);
            getChildren().add(borderPane);
            getStyleClass().clear();
            getStyleClass().add("inactiveBarElement");
        }


        public void avtivate() {
            getStyleClass().clear();
            getStyleClass().add("activeBarElement");
        }
    }
}
