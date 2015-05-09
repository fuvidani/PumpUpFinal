package sepm.ss15.grp16.gui.controller.Exercises;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 08.05.15.
 * This controller is responsible for the stage where the user can create
 * a new exercise or edit an existing one.
 */
public class ManageExerciseController implements Initializable{


    @FXML
    private CheckBox punchBagCheck;

    @FXML
    private ImageView imageView;

    @FXML
    private CheckBox chestCheck;

    @FXML
    private TextField videoLinkField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private CheckBox tricepCheck;

    @FXML
    private TextField caloriesField;

    @FXML
    private CheckBox legsCheck;

    @FXML
    private TextField durationOfQuantityField;

    @FXML
    private CheckBox abWheelCheck;

    @FXML
    private RadioButton quantityTypeRadio;

    @FXML
    private CheckBox balanceCheck;

    @FXML
    private CheckBox shoulderCheck;

    @FXML
    private CheckBox jumpRopeCheck;

    @FXML
    private RadioButton secondsTypeRadio;

    @FXML
    private CheckBox enduranceCheck;

    @FXML
    private CheckBox strengthCheck;

    @FXML
    private CheckBox flexibilityCheck;

    @FXML
    private CheckBox backCheck;

    @FXML
    private ListView<?> imagesListView;

    @FXML
    private CheckBox exerciseBallCheck;

    @FXML
    private CheckBox barCheck;

    @FXML
    private CheckBox dumbbellCheck;

    @FXML
    private CheckBox expanderCheck;

    @FXML
    private CheckBox abdominalCheck;

    @FXML
    private TextField exerciseNameField;

    @FXML
    private CheckBox bicepCheck;

    @FXML
    private TextField durationField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void browseClicked(ActionEvent event) {

    }

    @FXML
    void cancelClicked(ActionEvent event) {

    }

    @FXML
    void saveClicked(ActionEvent event) {

    }



}
