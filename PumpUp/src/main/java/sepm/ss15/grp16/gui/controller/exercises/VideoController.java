package sepm.ss15.grp16.gui.controller.exercises;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.exercise.Exercise;
import sepm.ss15.grp16.gui.PageEnum;
import sepm.ss15.grp16.gui.controller.Controller;

/**
 * Created by lukas on 10.06.2015.
 */
public class VideoController extends Controller {
    private final Logger LOGGER = LogManager.getLogger();


    @FXML
    private VBox videoBox;
    @FXML
    private Button playBtn;
    private Media media;
    private MediaPlayer player = null;
    private boolean isPlaying = false;
    private MediaView smallMediaView = new MediaView();
    private  Exercise exercise;


    @Override
    public void initController() {
        exercise = ((ExercisesController) this.getParentController()).getExercise();
        this.showVideo();
    }

    private void showVideo() {
        try {


            if (exercise.getVideolink() != null) {
                String pathToResource = getClass().getClassLoader().getResource("video").toURI().toString();
                String filePath = pathToResource.concat("/" + exercise.getVideolink());
                LOGGER.debug("filepath: " + filePath);
                LOGGER.debug("videolink: " + exercise.getVideolink());
                media = new Media(filePath);
                player = new MediaPlayer(media);

                player.setAutoPlay(false);

                smallMediaView.setMediaPlayer(player);
                smallMediaView.setVisible(true);
                smallMediaView.setFitHeight(300);

                videoBox.getChildren().add(smallMediaView);
            } else {
                smallMediaView.setMediaPlayer(null);

                smallMediaView.setVisible(false);
            }
        } catch (Exception e) {
        }
    }
    @FXML
    private void playVideo() {

        Duration totalDuration = player.getCycleDuration();
        Duration currentDuration = player.getCurrentTime();
        if (currentDuration.compareTo(totalDuration) == 0) {
            isPlaying = false;
            player = new MediaPlayer(media);
            smallMediaView.setMediaPlayer(null);
            smallMediaView.setMediaPlayer(player);
            playBtn.setText("Video abspielen");
        }
        if (isPlaying) {
            playBtn.setText("Video abspielen");
            player.pause();
            isPlaying = false;
        } else {
            playBtn.setText("Video pausieren");
            player.play();
            isPlaying = true;
        }
    }

}
