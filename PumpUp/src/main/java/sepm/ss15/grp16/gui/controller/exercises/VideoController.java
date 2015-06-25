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
import sepm.ss15.grp16.gui.controller.Controller;

/**
 * Created by lukas on 10.06.2015.
 * a controller with the purpose of playing a video
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
    private Exercise exercise;


    @Override
    public void initController() {

        exercise = ((VideoPlayable) this.getParentController()).getExercise();
        videoBox.getChildren().clear();

        this.showVideo();

    }

    /**
     * method for the show video button
     * redirects to an extra dialogue where the video gets displayed and
     * can be watched
     */
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
                player.setMute(true);

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

    /**
     * method to play the video and paus the video
     * if the video has finished it starts after a klick again
     */
    @FXML
    private void playVideo() {
        Duration totalDuration = player.getCycleDuration();
        Duration currentDuration = player.getCurrentTime();
        if (currentDuration.compareTo(totalDuration) == 0) {
            isPlaying = false;
            player = new MediaPlayer(media);
            player.setMute(true);
            smallMediaView.setMediaPlayer(null);
            smallMediaView.setMediaPlayer(player);

        }
        player.setOnEndOfMedia(() -> {
            playBtn.getStyleClass().add("btnPlay");
            playBtn.getStyleClass().remove("btnMusic_Pause");
        });
        if (isPlaying) {
            player.pause();
            isPlaying = false;
            playBtn.setBackground(null);
            playBtn.getStyleClass().remove("btnMusic_Pause");
            playBtn.getStyleClass().add("btnPlay");
        } else {
            player.play();
            isPlaying = true;
            playBtn.setBackground(null);
            playBtn.getStyleClass().remove("btnPlay");
            playBtn.getStyleClass().add("btnMusic_Pause");

        }

    }

    /**
     * closing the stage and getting back to the called stage
     */
    @FXML
    private void close() {
        videoBox.getChildren().clear();
        player=null;
        media=null;
        smallMediaView=null;
        mainFrame.close();
    }

}
