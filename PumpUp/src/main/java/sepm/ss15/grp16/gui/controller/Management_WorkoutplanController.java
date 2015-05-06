package sepm.ss15.grp16.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 05.05.15.
 *
 */
public class Management_WorkoutplanController implements Initializable {


    @FXML
    private Label trainingNameLabel;

    @FXML
    private TableView<?> trainingTable;

    @FXML
    private TextArea trainingTextArea;

    @FXML
    private TableColumn<?, ?> trainingColumn;

    @FXML
    private ImageView trainingImage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void newTrainingButtonClicked(ActionEvent event) {

    }

    @FXML
    void showTrainingButtonClicked(ActionEvent event) {

    }

    @FXML
    void editTrainingButtonClicked(ActionEvent event) {

    }

    @FXML
    void deleteTrainingButtonClicked(ActionEvent event) {

    }



}
