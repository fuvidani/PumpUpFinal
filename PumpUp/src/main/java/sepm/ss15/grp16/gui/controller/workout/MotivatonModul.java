package sepm.ss15.grp16.gui.controller.workout;

import javafx.scene.media.MediaPlayer;
import sepm.ss15.grp16.entity.music.Playlist;
import sepm.ss15.grp16.entity.training.helper.ExerciseSet;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.music.MusicService;

import java.util.List;
import java.util.Random;

/**
 * Author: Lukas
 * Date: 06.06.2015
 */
public class MotivatonModul {
    private WorkoutMusicPlayerController MusicPlayerController;
    private MusicService musicService;

    private Playlist playlist;
    private Random random = new Random();

    public MotivatonModul(MusicService musicService) {
        this.musicService = musicService;

        try {
            //playlist =
            musicService.getMotivations();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    public void setMusicPlayerController(WorkoutMusicPlayerController musicPlayerController) {
        MusicPlayerController = musicPlayerController;

    }

    public void play() {
        List<MediaPlayer> players = playlist.getPlayers();
        int randomNum = random.nextInt(players.size());
        MediaPlayer player = players.get(randomNum);

        MusicPlayerController.reduceVol();
        player.play();

        while (player.getStatus() == MediaPlayer.Status.PLAYING) {
            //wait
        }
        MusicPlayerController.raiseVol();
    }

    public void play(int i, ExerciseSet.SetType setType)
    {
        if(setType == ExerciseSet.SetType.repeat && i > 0 && i % 10 == 0)
        {
            play();
        }
        else if(setType == ExerciseSet.SetType.repeat && i > 5 && i % 10 == 0)
        {
            play();
        }
    }
}
