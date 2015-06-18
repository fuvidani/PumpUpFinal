package sepm.ss15.grp16.gui.controller.exercises;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.exercise.EquipmentCategory;
import sepm.ss15.grp16.entity.exercise.Exercise;
import sepm.ss15.grp16.entity.exercise.MusclegroupCategory;
import sepm.ss15.grp16.entity.exercise.TrainingsCategory;
import sepm.ss15.grp16.gui.PageEnum;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.gui.controller.workout.WorkoutController;
import sepm.ss15.grp16.gui.controller.workoutPlans.SessionEditController_v2;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exercise.CategoryService;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * Created by lukas on 09.06.2015.
 * one controller for the solely purpose of displaying an exercise
 * without controlls of editing or creating
 */
public class DisplayExerciseCotroller extends Controller implements VideoPlayable {
    private static final Logger LOGGER = LogManager.getLogger();
    @FXML
    private TextArea description;
    @FXML
    private ImageView imageView;
    @FXML
    private Label header;
    @FXML
    private Label calories;
    @FXML
    private ImageView leftArrow;
    @FXML
    private ImageView rightArrow;
    @FXML
    private Button playVideoBtn;
    @FXML
    private VBox vboxType;
    @FXML
    private VBox vboxEquipment;
    @FXML
    private VBox vboxMuscle;
    private Exercise exercise = null;
    private Integer picIndex = 0;
    private boolean isPlaying = false;
    private Media media;
    private MediaPlayer player = null;
    private MediaView smallMediaView = new MediaView();
    @FXML
    private VBox videoBox;
    private CategoryService categoryService = null;

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public Exercise getExercise() {
        return exercise;
    }

    /**
     * initializing the controller with all needed services
     */
    @Override
    public void initController() {
        LOGGER.debug("stranded in DisplayExerciseController");
        if(this.getParentController() instanceof  SessionEditController_v2) {
            exercise = ((SessionEditController_v2) this.getParentController()).getExercise();
        }
        else if(this.getParentController() instanceof  WorkoutController)
        {
            exercise = ((WorkoutController) this.getParentController()).getExercise();
        }
        else
        {
            throw new IllegalArgumentException("DisplayExercise called from wrong Controller!");
        }


        leftArrow.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            prevPicButtonClicked();
            event.consume();
        });

        rightArrow.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            nexPicButtonClicked();
            event.consume();
        });

        this.setContent();
    }

    public void setContent() {
        header.setText(exercise.getName());
        description.setText(exercise.getDescription());
        if (exercise.getVideolink() == null) {
            playVideoBtn.setDisable(true);
        } else {
            playVideoBtn.setDisable(false);
        }


        if (exercise.getGifLinks().size() > 0) {
            imageView.setVisible(true);
            showPicture(0);
        } else {
            imageView.setImage(null);
            imageView.setVisible(false);
            leftArrow.setVisible(false);
            rightArrow.setVisible(false);
        }
        if (vboxType.getChildren() != null) {
            for (int i = 0; i < vboxType.getChildren().size(); i++) {
                vboxType.getChildren().remove(i);
            }
        }

        try {
            vboxType.getChildren().clear();
            if (vboxEquipment.getChildren() != null) {
                for (int i = 0; i < vboxEquipment.getChildren().size(); i++) {
                    vboxEquipment.getChildren().remove(i);
                }
            }
            vboxEquipment.getChildren().clear();

            if (vboxMuscle.getChildren() != null) {
                for (int i = 0; i < vboxMuscle.getChildren().size(); i++) {
                    vboxMuscle.getChildren().remove(i);
                }
            }
            vboxMuscle.getChildren().clear();

            for (TrainingsCategory t : categoryService.getAllTrainingstype()) {
                if (exercise.getCategories().contains(t))
                    vboxType.getChildren().add(new Label(t.getName()));
            }

            for (EquipmentCategory t : categoryService.getAllEquipment()) {
                if (exercise.getCategories().contains(t))
                    vboxEquipment.getChildren().add(new Label(t.getName()));
            }
            for (MusclegroupCategory t : categoryService.getAllMusclegroup()) {
                if (exercise.getCategories().contains(t))
                    vboxMuscle.getChildren().add(new Label(t.getName()));
            }
        } catch (ServiceException e) {
            LOGGER.error(e);
            e.printStackTrace();
        }
    }

    /**
     * showing one picture out of the picture list the
     * current exercise has, defined by the given index
     * to load from the list of pictures
     *
     * @param index which picture is to display
     */
    private void showPicture(Integer index) {
        try {
            if (exercise.getGifLinks().isEmpty())
                return;

            String pathToResource = getClass().getClassLoader().getResource("img").toURI().getPath();
            LOGGER.debug("show details method path: " + pathToResource);
            FileInputStream reading = new FileInputStream(pathToResource + "/" + exercise.getGifLinks().get(index));
            Image img = new Image(reading);
            imageView.setImage(img);
            if (exercise.getGifLinks().size() > 1) {
                leftArrow.setVisible(true);
                rightArrow.setVisible(true);
            } else {
                leftArrow.setVisible(false);
                rightArrow.setVisible(false);
            }

        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(e);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            LOGGER.error(e);
        }
    }

    /**
     * changing to the next picture if there is one
     */
    @FXML
    private void nexPicButtonClicked() {
        if (exercise.getGifLinks().size() > 0) {
            showPicture(Math.abs(++picIndex) % exercise.getGifLinks().size());
        }
    }

    /**
     * changing to the previous picture if there is one
     */
    @FXML
    private void prevPicButtonClicked() {
        if (exercise.getGifLinks().size() > 0) {
            showPicture(Math.abs(--picIndex) % exercise.getGifLinks().size());
        }
    }

    /**
     * method for the show video button
     * redirects to an extra dialogue where the video gets displayed and
     * can be watched
     */
    @FXML
    private void showVideo() {
        mainFrame.openDialog(PageEnum.VideoPlayer);
    }

    /**
     * getting back to the main stage
     */
    @FXML
    private void getBack() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("\u00dcbungen verlassen");
        alert.setHeaderText("Das \u00dcbungsfenster schlie\u00dfen.");
        alert.setContentText("M\u00f6chten Sie die \u00dcbungsuebersicht wirklich beenden?");
        ButtonType yes = new ButtonType("Ja");
        ButtonType cancel = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yes, cancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yes) {
            mainFrame.navigateToParent();
        }
    }
}
