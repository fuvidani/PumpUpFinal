package sepm.ss15.grp16.gui.controller.exercises;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.exercise.*;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exercise.CategoryService;
import sepm.ss15.grp16.service.user.UserService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Daniel Fuevesi on 08.05.15.
 * This controller is responsible for the stage where the user can create
 * m new exercise or edit an existing one.
 */
public class ManageExerciseController extends Controller {

    private static final Logger LOGGER = LogManager.getLogger();

    @FXML
    private ImageView imageView;

    @FXML
    private TextArea descriptionArea;
    @FXML
    private TextField caloriesField;

    @FXML
    private ListView<String> imagesListView;
    @FXML
    private TextField exerciseNameField;
    @FXML
    private VBox vboxType;
    @FXML
    private VBox vboxMuscle;
    @FXML
    private VBox vboxEquipment;
    @FXML
    private Button deleteBtn = new Button();
    @FXML
    private Button addBtn = new Button();

    private Service<Exercise> exerciseService;
    private CategoryService categoryService;
    private UserService userService;
    private List<String> exerciseGifList = new ArrayList<>();
    private ObservableList<String> observablePicListData = FXCollections.observableArrayList();
    private Exercise exercise = null;
    private String picture;
    private ObservableList<CheckBox> checkboxes = FXCollections.observableArrayList();
    private List<CheckBox> allCheckboxes = new ArrayList<>();

    public void setExerciseService(Service<Exercise> exerciseService) {
        this.exerciseService = exerciseService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }


    /**
     * initializing the controller with all services and layout properties needed
     */
    @Override
    public void initController() {

        //loading the checkboxes dynamically
        try {
            exercise = ((ExercisesController) this.getParentController()).getExercise();
            for (TrainingsCategory t : categoryService.getAllTrainingstype()) {
                CheckBox box = new CheckBox(t.getName());
                box.setId("" + t.getId());
                LOGGER.debug("trainingsboxID: " + box.getId());
                checkboxes.add(box);
                allCheckboxes.add(box);

            }
            vboxType.getChildren().addAll(checkboxes);
            checkboxes.clear();

            for (MusclegroupCategory m : categoryService.getAllMusclegroup()) {
                CheckBox box = new CheckBox(m.getName());
                box.setId("" + m.getId());
                LOGGER.debug("muscleboxID: " + box.getId());
                checkboxes.add(box);
                allCheckboxes.add(box);
            }
            vboxMuscle.getChildren().addAll(checkboxes);
            checkboxes.clear();


            for (EquipmentCategory e : categoryService.getAllEquipment()) {
                CheckBox box = new CheckBox(e.getName());
                box.setId("" + e.getId());
                LOGGER.debug("equipmentsboxID: " + box.getId());
                checkboxes.add(box);
                allCheckboxes.add(box);

            }
            vboxEquipment.getChildren().addAll(checkboxes);
            checkboxes.clear();

        } catch (ServiceException e) {
            LOGGER.error(e);
        }
        imagesListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                showPic(oldValue, newValue);
            }
        });


        setContent();
    }

    /**
     * setting the contnet for the page
     */
    private void setContent() {
        if (this.exercise != null) { //update called
            observablePicListData.removeAll();
            caloriesField.setText("" + exercise.getCalories());
            exerciseNameField.setText(exercise.getName());
            descriptionArea.setText(exercise.getDescription());
            exerciseGifList = exercise.getGifLinks();
            observablePicListData.addAll(exerciseGifList);
            imagesListView.setItems(observablePicListData);
            if (exercise != null) {
                if (exercise.getGifLinks().size() == 0) {
                    deleteBtn.setDisable(true);
                } else {
                    deleteBtn.setDisable(false);
                }
            }


            if (!observablePicListData.isEmpty()) {
                showPic(observablePicListData.get(0), observablePicListData.get(0));
            }

            for (AbsractCategory c : exercise.getCategories()) {
                allCheckboxes.get(c.getId()).setSelected(true);
            }
        } else {
            deleteBtn.setDisable(true);
        }


    }

    /**
     * showing one picture out of the picture list the
     * current exercise has, defined by the given index
     * to load from the list of pictures
     *
     */
    private void showPic(String oldValue, String newValue) {
        try {
            File file;
            InputStream inputStream;

            if (newValue == null && oldValue != null) {
                newValue = oldValue;
            }

            if (newValue == null && oldValue == null)
                return;

            if (observablePicListData.isEmpty())
                return;

            String pathToResource = getClass().getClassLoader().getResource("img").toURI().getPath();
            if (newValue.contains("img_ex")) {
                file = new File(pathToResource + "/" + newValue);
                picture = file.getName();
            } else {
                file = new File(newValue);
                picture = file.getAbsolutePath();
            }

            LOGGER.debug(picture);

            inputStream = new FileInputStream(file);
            Image img = new Image(inputStream);
            imageView.setImage(img);
            inputStream.close();
        } catch (Exception e) {
            LOGGER.error(e);
        }

    }


    /**
     * handling the cancel button
     * with an alert box
     *
     * @param event
     */
    @FXML
    void cancelClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("\u00dcbung verlassen");
        alert.setHeaderText("Das \u00dcbungsfenster schlie\u00dfen und alle Aenderungen verwerfen?");
        alert.setContentText("M\\u00f6chten Sie die \u00dcbungsuebersicht wirklich beenden?");
        ButtonType yes = new ButtonType("Ja");
        ButtonType cancel = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yes, cancel);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yes) {
            mainFrame.navigateToParent();
        } else {
            stage.show();
        }
    }


    /**
     * handling the save button event
     * extracting an exercise DTO object out of all given values
     * and then passing the object to the service layer
     *
     * @param event
     */
    @FXML
    void saveClicked(ActionEvent event) {
        try {
            if (exercise == null) {
                exerciseService.create(this.extractExercise());
            } else {
                Exercise update = this.extractExercise();
                update.setId(exercise.getId());
                exerciseService.update(update);
            }
            mainFrame.navigateToParent();
        } catch (Exception e) {
            LOGGER.error(e);
        }

    }

    /**
     * method to browse the filesystem after jpg and png files
     *
     * @param event
     */
    @FXML
    void browseClicked(ActionEvent event) {
        LOGGER.debug("browse clicked");
        try {

            addBtn.setDisable(true);

            FileChooser fileChooser = new FileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg");
            FileChooser.ExtensionFilter extFilterPng = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
            fileChooser.getExtensionFilters().add(extFilter);
            fileChooser.getExtensionFilters().add(extFilterPng);

            //Show open file dialog and save picture into file reference
            List<File> files;
            files = fileChooser.showOpenMultipleDialog(null);

            addBtn.setDisable(false);
            //no preview available so need to store picture
            if (imageView.getImage() == null) {

                String filename = files.get(0).toString();
                InputStream inputStream = new FileInputStream(files.get(0));
                Image img = new Image(inputStream);
                imageView.setImage(img);
                inputStream.close();

                for (File f : files) {
                    exerciseGifList.add(f.getAbsolutePath());
                }
                observablePicListData.addAll(exerciseGifList);

            } else {//picture in preview
                LOGGER.debug("no picture in preview updating picturelist");
                String filename = files.get(0).toString();
                InputStream inputStream = new FileInputStream(files.get(0));
                Image img = new Image(inputStream);
                imageView.setImage(img);
                inputStream.close();
                for (File f : files) {
                    LOGGER.debug(f.getName());
                    exerciseGifList.add(f.getAbsolutePath());
                    observablePicListData.add(f.getAbsolutePath());
                }

            }

            imagesListView.setItems(observablePicListData);
            imagesListView.setVisible(false);
            imagesListView.setVisible(true);
            addBtn.setDisable(false);
            deleteBtn.setDisable(false);
        } catch (IOException e) {
            LOGGER.error(e);
        }

    }

    /**
     * removing pictures which are actually stored with the exercise
     */
    @FXML
    private void removeClicked() {

        if (picture.contains("img_ex")) {
            LOGGER.debug("removing pictuer " + picture);
            observablePicListData.remove("/" + picture);
            exerciseGifList.remove("/" + picture);

        } else {
            observablePicListData.remove(picture);
            exerciseGifList.remove(picture);
        }
        if (observablePicListData.size() == 0) {
            deleteBtn.setDisable(true);
        } else {
            deleteBtn.setDisable(false);
        }

        LOGGER.debug("list contins picture to remove:  " + observablePicListData.contains("/" + picture));
        imagesListView.setItems(observablePicListData);
        imagesListView.setVisible(false);
        imagesListView.setVisible(true);
        if (observablePicListData.isEmpty()) {
            imageView.setImage(null);
        }

    }

    /**
     * extracting an exercise object out of all the given information the
     * user has provided
     *
     * @return a new exercise DTO object
     */
    private Exercise extractExercise() {
        Double calories = 0.0;
        try {
            calories = Double.parseDouble(caloriesField.getText());
        } catch (NumberFormatException e) {
            calories = 1.0;
        }


        List<AbsractCategory> temp = new ArrayList<>();
        for (CheckBox c : allCheckboxes) {

            if (c.isSelected())
                temp.add(new TrainingsCategory(Integer.parseInt(c.getId()), c.getText()));
        }

        return new Exercise(null, exerciseNameField.getText(), descriptionArea.getText(), calories, null, exerciseGifList, false, userService.getLoggedInUser(), temp);
    }
}
