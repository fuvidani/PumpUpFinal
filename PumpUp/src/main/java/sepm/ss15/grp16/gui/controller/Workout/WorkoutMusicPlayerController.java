package sepm.ss15.grp16.gui.controller.Workout;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.media.MediaView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 08.05.15.
 * This controller controls the lower section of the training's stage.
 */
public class WorkoutMusicPlayerController implements Initializable {


    @FXML
    private Label songTotalLengthLabel;

    @FXML
    private MediaView musicPlayerSlide;

    @FXML
    private Label artistAndSongLabel;

    @FXML
    private Label songSecondsCounterLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void rewindButtonClicked(ActionEvent event) {

    }

    @FXML
    void playButtonClicked(ActionEvent event) {

    }

    @FXML
    void forwardButtonClicked(ActionEvent event) {

    }

    @FXML
    void playlistButtonClicked(ActionEvent event) {

    }

}
