package sepm.ss15.grp16.gui.controller.exercises;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.exercise.EquipmentCategory;
import sepm.ss15.grp16.entity.exercise.Exercise;
import sepm.ss15.grp16.entity.exercise.MusclegroupCategory;
import sepm.ss15.grp16.entity.exercise.TrainingsCategory;
import sepm.ss15.grp16.gui.StageTransitionLoader;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.service.exercise.CategoryService;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.user.UserService;
import sepm.ss15.grp16.service.exception.ServiceException;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 07.05.15.
 * Controller of the "Übungen" stage.
 */
public class ExercisesController extends Controller implements Initializable {


    private static Exercise exercise;
    private final Logger LOGGER = LogManager.getLogger();
    private Service<Exercise> exerciseService;
    private StageTransitionLoader transitionLoader;
    @FXML
    private Label exerciseNameLabel;
    @FXML
    private Label trainingDeviceLabel1;
    @FXML
    private Label trainingDeviceLabel2;
    @FXML
    private Label trainingDeviceLabel3;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private MediaView smallMediaView;
    @FXML
    private MediaView bigMediaView;
    @FXML
    private Label categoryTypeLabel;
    @FXML
    private TableColumn<Exercise, String> uebungColumn;
    @FXML
    private TableView<Exercise> uebungsTableView;
    @FXML
    private ImageView imageView;
    @FXML
    private Button nexPic;
    @FXML
    private Button prevPic;
    @FXML
    private TextField tf_search;
    @FXML
    private CheckBox customExercisesCheckbox;
    @FXML
    private CheckBox defaultExercisesCheckbox;
    @FXML
    private VBox vboxCategory;
    @FXML
    private WebView webViewVideo;

    @FXML
    private ImageView editImg = new ImageView();
    @FXML
    private ImageView deleteImg = new ImageView();
    @FXML
    private ImageView newImg = new ImageView();


    private CategoryService categoryService;
    private UserService userService;
    private Integer picIndex = 0;
    private ObservableList<Exercise> masterdata = FXCollections.observableArrayList();
    private ObservableList<Exercise> filteredData = FXCollections.observableArrayList();

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void setExerciseService(Service<Exercise> exerciseService) {
        this.exerciseService = exerciseService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public Exercise getExercise() {
        return exercise;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        this.transitionLoader = new StageTransitionLoader(this);
        webViewVideo.setVisible(false);
        uebungColumn.setCellValueFactory(new PropertyValueFactory<Exercise, String>("name"));
        uebungsTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Exercise>() {
            @Override
            public void changed(ObservableValue<? extends Exercise> observable, Exercise oldValue, Exercise newValue) {
                showExercise(oldValue, newValue);
            }
        });

        tf_search.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                updateFilteredData();
            }
        });

        editImg.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                editExerciseButtonClicked(null);
                event.consume();
            }
        });

        newImg.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                newExerciseButtonClicked(null);
                event.consume();
            }
        });

        deleteImg.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                deleteExerciseButtonClicked(null);
                event.consume();
            }
        });

        this.setContent();
    }


    @FXML
    private void playVideo() {
        if (exercise.getVideolink() == null || exercise.getVideolink().isEmpty()) {
            webViewVideo.setVisible(false);
        } else {
            webViewVideo.setVisible(true);
            webViewVideo.getEngine().load(exercise.getVideolink());
        }
    }

    private void updateFilteredData() {
        ObservableList<Exercise> temp = FXCollections.observableArrayList();
        for (Exercise e : filteredData) {
            if (matchesFilter(e))
                temp.add(e);
        }

        uebungsTableView.setItems(temp);
    }

    private boolean matchesFilter(Exercise e) {
        String filter = tf_search.getText();
        if (filter == null || filter.isEmpty())
            return true;

        if (e.getName().toLowerCase().indexOf(filter.toLowerCase()) != -1) {
            return true;
        }

        return false;
    }


    public void setContent() {
        try {
            masterdata.removeAll(exerciseService.findAll());
            masterdata.addAll(exerciseService.findAll());
            uebungsTableView.setItems(null);
            uebungsTableView.setItems(masterdata);
            uebungsTableView.getColumns().get(0).setVisible(false);
            uebungsTableView.getColumns().get(0).setVisible(true);

        } catch (ServiceException e) {
            e.printStackTrace();
            LOGGER.error(e);
        }
    }

    private void showExercise(Exercise old, Exercise newExercise) {
        if (newExercise != null && old == null) {
            LOGGER.debug("first click");
        }

        if (newExercise == null) {
            LOGGER.debug("exercise null");
            return;
        }
        if (newExercise != null) {
            exerciseNameLabel.setText(newExercise.getName());
            descriptionTextArea.setText(newExercise.getDescription());
            exercise = newExercise;
            playVideo();
            if (exercise.getGifLinks().size() > 0) {
                showPicture(0);
            } else {
                imageView.setImage(null);
            }
        }
        try {

            if(vboxCategory.getChildren()!=null) {
                for (int i = 0; i < vboxCategory.getChildren().size(); i++) {
                    vboxCategory.getChildren().remove(i);
                }
            }
            for (TrainingsCategory t : categoryService.getAllTrainingstype()) {
                if (exercise.getCategories().contains(t))
                    vboxCategory.getChildren().add(new Label(t.getName()));
            }
            for (EquipmentCategory t : categoryService.getAllEquipment()) {
                if (exercise.getCategories().contains(t))
                    vboxCategory.getChildren().add(new Label(t.getName()));
            }
            for (MusclegroupCategory t : categoryService.getAllMusclegroup()) {
                if (exercise.getCategories().contains(t))
                    vboxCategory.getChildren().add(new Label(t.getName()));
            }
        }catch (ServiceException e){
            LOGGER.error(e);
            e.printStackTrace();
        }
    }

    private void showPicture(Integer index) {
        try {
            if (exercise.getGifLinks().isEmpty())
                return;

            String pathToResource = getClass().getClassLoader().getResource("img").toURI().getPath();
            LOGGER.debug("show details method path: " + pathToResource);
            FileInputStream reading = new FileInputStream(pathToResource + "/" + exercise.getGifLinks().get(index));
            Image img = new Image(reading);
            imageView.setImage(img);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error(e);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            LOGGER.error(e);

        }
    }

    @FXML
    private void nexPicButtonClicked() {
        if (exercise.getGifLinks().size() > 0) {
            showPicture(Math.abs(++picIndex) % exercise.getGifLinks().size());
        }
    }

    @FXML
    private void prevPicButtonClicked() {
        if (exercise.getGifLinks().size() > 0) {
            showPicture(Math.abs(--picIndex) % exercise.getGifLinks().size());
        }
    }

    @FXML
    void newExerciseButtonClicked(ActionEvent event) {
        Exercise backup = null;
        if (exercise != null) {
            //TODO
            backup = new Exercise(exercise.getName(), exercise.getDescription(), exercise.getCalories(), exercise.getVideolink(), exercise.getGifLinks(), exercise.getIsDeleted(), userService.getLoggedInUser(), null);
            exercise = null;
        }

        transitionLoader.openWaitStage("fxml/exercise/ManageExercise.fxml", (Stage) uebungsTableView.getScene().getWindow(), "Übung erstellen/ bearbeiten", 1000, 620, true);
        this.setContent();
        if (backup != null) {
            //TODO categories
            exercise = new Exercise(backup.getName(), backup.getDescription(), backup.getCalories(), backup.getVideolink(), backup.getGifLinks(), backup.getIsDeleted(), userService.getLoggedInUser(), null);
        }

    }


    @FXML
    void editExerciseButtonClicked(ActionEvent event) {
        if (exercise==null || exercise.getUser() != null && exercise.getUser().equals(userService.getLoggedInUser())) {
            transitionLoader.openWaitStage("fxml/exercise/ManageExercise.fxml", (Stage) uebungsTableView.getScene().getWindow(), "Übung erstellen/ bearbeiten", 1000, 620, true);
            this.setContent();

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Fehler");
            alert.setHeaderText("Editier fehler.");
            alert.setContentText("System \u00dcbungen k\u00f6nnen nicht editiert werden.");
            alert.showAndWait();
        }

    }

    @FXML
    void deleteExerciseButtonClicked(ActionEvent event) {
        try {
            if (exercise.getUser() != null && exercise.getUser().equals(userService.getLoggedInUser())) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("\u00dcbungen l\u00f6schen");
                alert.setHeaderText("Die \u00dcbung " + exercise.getName() + " wirklich l\u00f6schen");
                alert.setContentText("M\u00f6chten Sie die \u00dcbung wirklich l\u00f6schen?");
                ButtonType yes = new ButtonType("Ja");
                ButtonType cancel = new ButtonType("Nein", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(yes, cancel);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == yes) {
                    exerciseService.delete(exercise);
                    masterdata.remove(exercise);
                    this.setContent();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Fehler");
                alert.setHeaderText("L\u00f6sch fehler.");
                alert.setContentText("System \u00dcbungen k\u00f6nnen nicht gel\u00f6scht werden.");
                alert.showAndWait();
            }
            return;

        } catch (ServiceException e) {
            LOGGER.error(e);
            e.printStackTrace();
        }
    }

    @FXML
    void getBackButtonClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("\u00dcbungen verlassen");
        alert.setHeaderText("Das \u00dcbungsfenster schlie\u00dfen.");
        alert.setContentText("M\u00f6chten Sie die \u00dcbungsuebersicht wirklich beenden?");
        ButtonType yes = new ButtonType("Ja");
        ButtonType cancel = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yes, cancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yes) {
            stage.close();
        } else {
            stage.show();
        }
    }


    @FXML
    private void filterCheckboxes() {
        if (defaultExercisesCheckbox.isSelected() && customExercisesCheckbox.isSelected()) {
            filteredData.clear();
            filteredData.addAll(masterdata);
            uebungsTableView.setItems(filteredData);
            return;
        } else if (defaultExercisesCheckbox.isSelected()) {

            filteredData.clear();
            for (Exercise e : masterdata) {
                if (e.getUser() == null) {
                    filteredData.add(e);
                }
            }

            uebungsTableView.setItems(filteredData);
            return;
        } else if (customExercisesCheckbox.isSelected()) {
            filteredData.clear();
            for (Exercise e : masterdata) {
                if (e.getUser() != null && e.getUser().equals(userService.getLoggedInUser())) {
                    filteredData.add(e);
                }
            }
            uebungsTableView.setItems(filteredData);
            return;
        } else {
            uebungsTableView.setItems(masterdata);
        }

    }


}
