package sepm.ss15.grp16.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 06.05.15.
 *
 */
public class TrainingController implements Initializable{


    @FXML
    private Label songTotalLengthLabel;

    @FXML
    private Circle clock;

    @FXML
    private MediaView mediaView;

    @FXML
    private Label artistAndSongLabel;

    @FXML
    private Label songSecondsCounterLabel;

    @FXML
    private Label currentExerciseLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void playButtonClicked(ActionEvent event) {

    }

    @FXML
    void forwardButtonClicked(ActionEvent event) {

    }

    @FXML
    void rewindButtonClicked(ActionEvent event) {

    }

    @FXML
    void playlistButtonClicked(ActionEvent event) {

    }


}
