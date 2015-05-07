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
 * This is the central controller of the training's stage.
 * It consists of a graphicsController and a musicPlayerController.
 *
 */
public class TrainingController implements Initializable{


    private TrainingGraphicsController graphicsController;
    private TrainingMusicPlayerController musicPlayerController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.graphicsController = new TrainingGraphicsController();
        this.musicPlayerController = new TrainingMusicPlayerController();
        this.graphicsController.initialize(location,resources);
        this.musicPlayerController.initialize(location,resources);

    }


}
