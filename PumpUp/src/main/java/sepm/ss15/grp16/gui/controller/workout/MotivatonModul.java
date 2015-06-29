package sepm.ss15.grp16.gui.controller.workout;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.music.Playlist;
import sepm.ss15.grp16.entity.training.helper.ExerciseSet;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.music.MusicService;

import java.security.Key;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Author: Lukas
 * Date: 06.06.2015
 */
public class MotivatonModul {
    private static final int VOLFADETIME = 1;

    private static final Logger LOGGER = LogManager.getLogger(MotivatonModul.class);

    private WorkoutMusicPlayerController MusicPlayerController;
    private MusicService                 musicService;

    private Map<String, Playlist> motivations;
    private Random random = new Random();

    public MotivatonModul(MusicService musicService) {
        this.musicService = musicService;

        try {
            motivations = musicService.getMotivations();
        } catch (ServiceException e) {
            LOGGER.error("" + e);
        }
    }

    public void setMusicPlayerController(WorkoutMusicPlayerController musicPlayerController) {
        MusicPlayerController = musicPlayerController;
    }

    public void play(Playlist myPlaylist) {
        List<MediaPlayer> players = myPlaylist.getPlayers();
        int randomNum = random.nextInt(players.size());
        MediaPlayer player = players.get(randomNum);
        player.setVolume(1.0);
        LOGGER.info("playing: " + myPlaylist.getDir());


        // Fade in/out play Motivation
        KeyValue reduceValue = MusicPlayerController.reduceVolKeyValue();
        KeyValue raiseValue = MusicPlayerController.raiseVolKeyValue();

        KeyFrame reduceFrame = new KeyFrame(Duration.seconds(VOLFADETIME), event -> {
            player.play();
        }, reduceValue);
        KeyFrame raiseFrame = new KeyFrame(Duration.seconds(VOLFADETIME), raiseValue);

        Timeline reduceLine = new Timeline(reduceFrame);
        reduceLine.setCycleCount(1);
        reduceLine.setAutoReverse(false);
        Timeline raiseLine = new Timeline(raiseFrame);
        raiseLine.setCycleCount(1);
        raiseLine.setAutoReverse(false);

        player.setOnEndOfMedia(() -> {
            player.stop();
            raiseLine.play();
        });

        reduceLine.play();
    }

    public void play(int i, ExerciseSet.SetType setType) {
        if (setType == ExerciseSet.SetType.repeat && i != 0 && (i + VOLFADETIME) % 20 == 10) {
            play(motivations.get("random"));
        } else if (setType == ExerciseSet.SetType.time) {
            Playlist p = motivations.get((i - VOLFADETIME) + "");
            if (p != null) {
                play(p);
            }
        }
    }

    public void welcome() {
        List<MediaPlayer> players = motivations.get("welcome").getPlayers();
        int randomNum = random.nextInt(players.size());
        MediaPlayer player = players.get(randomNum);
        player.setVolume(1);
        player.play();
        player.setOnEndOfMedia(player::stop);
    }
}
