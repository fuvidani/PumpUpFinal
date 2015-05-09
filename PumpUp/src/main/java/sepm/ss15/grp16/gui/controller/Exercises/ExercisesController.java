package sepm.ss15.grp16.gui.controller.Exercises;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sepm.ss15.grp16.entity.DTO;
import sepm.ss15.grp16.entity.Exercise;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.gui.controller.StageTransitionLoader;
import sepm.ss15.grp16.service.ExerciseService;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.impl.ExerciseServiceImpl;

import java.io.FileInputStream;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 07.05.15.
 * Controller of the "Übungen" stage.
 */
public class ExercisesController extends Controller implements Initializable{


    private Service<Exercise> exerciseService;
    private StageTransitionLoader transitionLoader;

    @FXML
    private CheckBox defaultExercisesCheckbox;

    @FXML
    private Label exerciseNameLabel;

    @FXML
    private Label trainingDeviceLabel1;

    @FXML
    private Label trainingDeviceLabel2;

    @FXML
    private Label trainingDeviceLabel3;

    @FXML
    private CheckBox customExercisesCheckbox;

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

    private Exercise exercise;
    private Integer picIndex = 0;
    private ObservableList<Exercise>  masterdata = FXCollections.observableArrayList();
    private static final Logger LOGGER = LogManager.getLogger();

    public void setExerciseService(Service<Exercise> exerciseService){
        this.exerciseService=exerciseService;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.transitionLoader = new StageTransitionLoader(this);
        uebungColumn.setCellValueFactory(new PropertyValueFactory<Exercise, String>("name"));
        uebungsTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Exercise>() {
            @Override
            public void changed(ObservableValue<? extends Exercise> observable, Exercise oldValue, Exercise newValue) {
                showExercise(oldValue, newValue);
            }
        });
        this.setContent();
    }


    private void setContent(){
        try{
            masterdata.addAll(exerciseService.findAll());
            uebungsTableView.setItems(masterdata);
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error(e);
        }
    }

    private void showExercise(Exercise old, Exercise newExercise){
        if (newExercise == null && old != null) {
            newExercise = old;
            LOGGER.debug("horse null, olt not!");
        }

        if (newExercise != null && old == null) {
            LOGGER.debug("first click");
        }

        if (newExercise == null) {
            LOGGER.debug("horse null");
            newExercise = old;
        }

        exerciseNameLabel.setText(newExercise.getName());
        descriptionTextArea.setText(newExercise.getDescription());
        exercise = newExercise;
        showPicture(0);
    }

    private void showPicture(Integer index){
        try {
            String path = "" + (getClass().getClassLoader().getResource("img/").toString().substring(6)).concat(exercise.getGifLinks().get(index)
            );
            LOGGER.debug("show details method path: " + path);
            FileInputStream reading = new FileInputStream(path);
            Image img = new Image(reading);
            imageView.setImage(img);
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.error(e);
        }
    }

    @FXML
    private void nexPicButtonClicked(){
            showPicture((++picIndex)%exercise.getGifLinks().size());
    }

    @FXML
    private void prevPicButtonClicked(){
        showPicture((--picIndex)%exercise.getGifLinks().size());
    }

    @FXML
    void newExerciseButtonClicked(ActionEvent event) {
        transitionLoader.openStage("fxml/ManageExercise.fxml",(Stage)uebungsTableView.getScene().getWindow(),"Übung erstellen/ bearbeiten",1000,620, true);

    }

    @FXML
    void editExerciseButtonClicked(ActionEvent event) {
        transitionLoader.openStage("fxml/ManageExercise.fxml",(Stage)uebungsTableView.getScene().getWindow(),"Übung erstellen/ bearbeiten",1000,620, true);
    }

    @FXML
    void deleteExerciseButtonClicked(ActionEvent event) {
        // TODO
    }

    @FXML
    void getBackButtonClicked(ActionEvent event) {
        //TODO: ask user if he/she wants to abort when changes are made (when data is not saved, changes will be lost!)
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Uebungen verlassen");
        alert.setHeaderText("Das Uebungsfenster schliessen.");
        alert.setContentText("Moechten Sie die Uebungsuebersicht wirklich beenden?");
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


}
