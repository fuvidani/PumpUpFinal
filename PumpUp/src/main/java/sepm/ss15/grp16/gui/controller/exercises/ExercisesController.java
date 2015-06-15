package sepm.ss15.grp16.gui.controller.exercises;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.exercise.EquipmentCategory;
import sepm.ss15.grp16.entity.exercise.Exercise;
import sepm.ss15.grp16.entity.exercise.MusclegroupCategory;
import sepm.ss15.grp16.entity.exercise.TrainingsCategory;
import sepm.ss15.grp16.gui.PageEnum;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exercise.CategoryService;
import sepm.ss15.grp16.service.exercise.ExerciseService;
import sepm.ss15.grp16.service.user.UserService;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * Created by Daniel Fuevesi on 07.05.15.
 * Controller of the "Übungen" stage.
 */
public class ExercisesController extends Controller implements VideoPlayable{


    private static Exercise exercise;
    private final Logger LOGGER = LogManager.getLogger();
    private ExerciseService exerciseService;
    @FXML
    private Label exerciseNameLabel;
    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TableColumn<Exercise, String> uebungColumn;
    @FXML
    private TableView<Exercise> uebungsTableView;
    @FXML
    private ImageView imageView;
    @FXML
    private TextField tf_search;
    @FXML
    private CheckBox customExercisesCheckbox;
    @FXML
    private CheckBox defaultExercisesCheckbox;

    @FXML
    private ImageView leftArrow = new ImageView();
    @FXML
    private ImageView rightArrow = new ImageView();

    @FXML
    private Button addBtn = new Button();
    @FXML
    private Button deleteBtn = new Button();
    @FXML
    private Button editBtn = new Button();
    @FXML
    private VBox vboxType = new VBox();
    @FXML
    private VBox vboxEquipment = new VBox();
    @FXML
    private VBox vboxMuscle = new VBox();
    @FXML
    private Button playVideoBtn = new Button();

    private CategoryService categoryService;
    private UserService userService;
    private Integer picIndex = 0;
    private ObservableList<Exercise> masterdata = FXCollections.observableArrayList();
    private ObservableList<Exercise> temp = FXCollections.observableArrayList();


    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public void setExerciseService(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public Exercise getExercise() {
        return exercise;
    }

    /**
     * initializing the controller with all services and layout properties needed
     */
    @Override
    public void initController() {

        addBtn.setTooltip(new Tooltip("Neue \u00dcbung anlegen"));
        deleteBtn.setTooltip(new Tooltip("\u00dcbung l\u00f6schen"));
        editBtn.setTooltip(new Tooltip("\u00dcbung bearbeiten"));


        leftArrow.setVisible(false);
        rightArrow.setVisible(false);
        playVideoBtn.setDisable(true);
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

        leftArrow.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                prevPicButtonClicked();
                event.consume();
            }
        });

        rightArrow.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                nexPicButtonClicked();
                event.consume();
            }
        });


        this.setContent();

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
     * method to filter after the the inputtext in the
     * search textfield
     */
    private void updateFilteredData() {
        ObservableList<Exercise> filteredData = FXCollections.observableArrayList();

        if(temp.size()==0){
            for(Exercise e : masterdata){
                temp.add(e);
            }
        }

        for(Exercise e : temp){
            if(matchesFilter(e))
                filteredData.add(e);
        }

        uebungsTableView.setItems(filteredData);

    }


    @FXML
    private void filter(){
        //keine checkbox
        //beide checkboxen
        //--> textfeld ist kriterium
        temp.clear();
        if ((!customExercisesCheckbox.isSelected() && !defaultExercisesCheckbox.isSelected())||
                (customExercisesCheckbox.isSelected() && defaultExercisesCheckbox.isSelected())) {
            for(Exercise e : masterdata){
                temp.add(e);
            }
        } else if (defaultExercisesCheckbox.isSelected() ) {
            temp.clear();
            for (Exercise e : masterdata) {
                if (e.getUser() == null) {
                    temp.add(e);
                }
            }
        } else if (customExercisesCheckbox.isSelected()) {

            for (Exercise e : masterdata) {
                temp.clear();

                if (e.getUser() != null && e.getUser().equals(userService.getLoggedInUser())) {
                    temp.add(e);
                }
            }

        } else {

        }

        ObservableList<Exercise> filteredData = FXCollections.observableArrayList();

        for(Exercise e : temp){
            filteredData.add(e);
        }

        uebungsTableView.setItems(filteredData);
    }

    /**
     * mathing method which checks all names of the  exercises displayed
     * against the text in the textbox
     * @param e exercise to check against
     * @return true if textfield is empty, or matches text
     *          false if exercise name does not macht
     */
    private boolean matchesFilter(Exercise e) {
        String filter = tf_search.getText();
        if (filter == null || filter.isEmpty())
            return true;

        if (e.getName().toLowerCase().indexOf(filter.toLowerCase()) != -1) {
            return true;
        }

        return false;
    }

    /**
     * setting the contnet for the page
     */
    public void setContent() {
        try {
            masterdata.removeAll(exerciseService.findAll());
            masterdata.addAll(exerciseService.findAll());
            uebungsTableView.setItems(null);
            uebungsTableView.setItems(masterdata);
            uebungsTableView.getColumns().get(0).setVisible(false);
            uebungsTableView.getColumns().get(0).setVisible(true);
            editBtn.setDisable(true);
            deleteBtn.setDisable(true);
        } catch (ServiceException e) {
            e.printStackTrace();
            LOGGER.error(e);
        }
    }

    /**
     * displaying one exercise with all the details the exercise contains
     * @param old the exercise displayed before this one
     * @param newExercise the current clicked exercise
     */
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
            if (exercise.getUser() == null) {
                deleteBtn.setDisable(true);
                editBtn.setDisable(true);
            } else {
                deleteBtn.setDisable(false);
                editBtn.setDisable(false);
            }
        }
        try {

            if (vboxType.getChildren() != null) {
                for (int i = 0; i < vboxType.getChildren().size(); i++) {
                    vboxType.getChildren().remove(i);
                }
            }
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
     * @param index which picture is to display
     *
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
     * handling the event of a new exercise:
     * creating a backup of the actual chosen exercise
     * then changing the stage where the user can create the new exercise
     * @param event
     */
    @FXML
    void newExerciseButtonClicked(ActionEvent event) {
        Exercise backup = null;
        if (exercise != null) {
            //TODO
            backup = new Exercise(exercise.getName(), exercise.getDescription(), exercise.getCalories(), exercise.getVideolink(), exercise.getGifLinks(), exercise.getIsDeleted(), userService.getLoggedInUser(), exercise.getCategories());
        }
        exercise = null;

        mainFrame.navigateToChild(PageEnum.Manage_exercises);
        this.setContent();
        if (backup != null) {
            //TODO categories
            exercise = new Exercise(backup.getName(), backup.getDescription(), backup.getCalories(), backup.getVideolink(), backup.getGifLinks(), backup.getIsDeleted(), userService.getLoggedInUser(), backup.getCategories());
        }

    }


    /**
     * editing an exercise if the user has privilege to do so
     * only own exercises can be modifyed
     * @param event
     */
    @FXML
    void editExerciseButtonClicked(ActionEvent event) {

        if (exercise.getUser() != null && exercise.getUser().equals(userService.getLoggedInUser())) {


            mainFrame.navigateToChild(PageEnum.Manage_exercises);

            this.setContent();

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Fehler");
            alert.setHeaderText("Editier fehler.");
            alert.setContentText("System \u00dcbungen k\u00f6nnen nicht editiert werden.");
            alert.showAndWait();
        }

    }


    /**
     * deleting an exercise if the user has privilege to do so
     * only own exercises can be modifyed
     * @param event
     */
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

    /**
     * getting back to the main stage
     * @param event
     */
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
            mainFrame.navigateToParent();
        }
    }


    /**
     * filtering exercises after the clicked checkboxes like:
     * system/default exercises and
     * own exercises
     * also combinations are possible with the textsearch field
     */
    @FXML
    private void filterCheckboxes() {
//        if (defaultExercisesCheckbox.isSelected() && customExercisesCheckbox.isSelected()) {
//            filteredData.clear();
//            filteredData.addAll(masterdata);
//            uebungsTableView.setItems(filteredData);
//            return;
//        } else if (defaultExercisesCheckbox.isSelected()) {
//
//            filteredData.clear();
//            for (Exercise e : masterdata) {
//                if (e.getUser() == null) {
//                    filteredData.add(e);
//                }
//            }
//            uebungsTableView.setItems(null);
//            uebungsTableView.setItems(filteredData);
//            return;
//        } else if (customExercisesCheckbox.isSelected()) {
//            filteredData.clear();
//            for (Exercise e : masterdata) {
//                if (e.getUser() != null && e.getUser().equals(userService.getLoggedInUser())) {
//                    filteredData.add(e);
//                }
//            }
//            uebungsTableView.setItems(null);
//
//            uebungsTableView.setItems(filteredData);
//            return;
//        } else {
//            uebungsTableView.setItems(masterdata);
//        }

    }


}
