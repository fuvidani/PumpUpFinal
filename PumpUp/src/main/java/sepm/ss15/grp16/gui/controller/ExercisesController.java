package sepm.ss15.grp16.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.media.MediaView;
import sepm.ss15.grp16.service.ExerciseService;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 07.05.15.
 * Controller of the "Übungen" stage.
 */
public class ExercisesController implements Initializable{


    private ExerciseService exerciseService;

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
    private ListView<?> exercisesList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void newExerciseButtonClicked(ActionEvent event) {

    }

    @FXML
    void editExerciseButtonClicked(ActionEvent event) {

    }

    @FXML
    void deleteExerciseButtonClicked(ActionEvent event) {

    }

    @FXML
    void getBackButtonClicked(ActionEvent event) {

    }


}
