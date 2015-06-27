package sepm.ss15.grp16.gui.controller.workout;

import javafx.scene.media.MediaPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.music.Playlist;
import sepm.ss15.grp16.entity.training.helper.ExerciseSet;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.music.MusicService;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Author: Lukas
 * Date: 06.06.2015
 */
public class MotivatonModul {
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
        LOGGER.info("playing: " + myPlaylist.getDir());

        MusicPlayerController.reduceVol();
        player.setOnEndOfMedia(() -> {
            player.stop();
            MusicPlayerController.raiseVol();
        });
        player.setVolume(1.0);
        player.play();
    }

    public void play(int i, ExerciseSet.SetType setType) {
        if (setType == ExerciseSet.SetType.repeat && i != 0 && i % 20 == 0) {
            play(motivations.get("random"));
        } else if (setType == ExerciseSet.SetType.time) {
            Playlist p = motivations.get(i + "");
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
