package sepm.ss15.grp16.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 05.05.15.
 *
 */
public class Management_ExerciseController implements Initializable{

    private static final Logger LOGGER = LogManager.getLogger();

    @FXML
    private Label exerciseNameLabel;

    @FXML
    private ImageView exerciseImage;

    @FXML
    private TableView<?> exerciseTable;

    @FXML
    private TableColumn<?, ?> exerciseColumn;

    @FXML
    private TextArea exerciseTextArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void newExerciseButtonClicked(ActionEvent event) {


    }

    @FXML
    void showExerciseButtonClicked(ActionEvent event) {

    }

    @FXML
    void editExerciseButtonClicked(ActionEvent event) {

    }

    @FXML
    void deleteExerciseButtonClicked(ActionEvent event) {

    }


}
