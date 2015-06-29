package sepm.ss15.grp16.gui.controller.workout;

import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.music.Playlist;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.gui.PageEnum;
import sepm.ss15.grp16.gui.controller.Controller;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.music.MusicService;
import sepm.ss15.grp16.service.user.UserService;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Created by Daniel Fuevesi on 08.05.15.
 * This controller controls the lower section of the training's stage.
 */
public class WorkoutMusicPlayerController extends Controller implements Initializable {
    private static final Logger LOGGER = LogManager.getLogger(WorkoutMusicPlayerController.class);

    private static final double VOLUME_ACTIVE   = 0.3;
    private static final double VOLUME_INACTIVE = 0.1;

    private Playlist          playlist;
    private MusicService      musicService;
    private UserService       userService;
    private WorkoutController parent;

    private List<MediaPlayer> players;
    private List<MediaPlayer> original;
    private boolean playing  = false;
    private boolean muted    = false;
    private boolean shuffled = false;

    @FXML
    private ProgressBar                       progress;
    private ChangeListener<Duration>          progressChangeListener;
    private MapChangeListener<String, Object> metadataChangeListener;

    @FXML
    private Label songTotalLengthLabel;

    @FXML
    private MediaView musicPlayerSlide;

    @FXML
    private Label artistAndSongLabel;

    @FXML
    private Label songSecondsCounterLabel;

    @FXML
    private VBox vBoxProg;

    @FXML
    private Button btnSkip;

    @FXML
    private Button btnLast;

    @FXML
    private Button btnPlay;

    @FXML
    private VBox mainPane;

    public WorkoutMusicPlayerController(MusicService musicService, UserService userService) {
        this.musicService = musicService;
        this.userService = userService;
    }

    @Override
    public void initController() {
        User user = userService.getLoggedInUser();
        try {
            playlist = musicService.create(new Playlist(user, user.getPlaylist(), null));
        } catch (ServiceException e) {
            LOGGER.error("+e");
        }
        if (playlist == null) {
            mainPane.setVisible(false);
            musicPlayerSlide = null;
        } else {
            players = playlist.getPlayers();
            original = new ArrayList<>(players);

            if (!players.isEmpty()) {
                musicPlayerSlide = new MediaView(players.get(0));

                // play each audio file in turn.
                for (int i = 0; i < players.size(); i++) {
                    final MediaPlayer player = players.get(i);
                    final MediaPlayer nextPlayer = players.get((i + 1) % players.size());
                    player.setOnEndOfMedia(() -> {
                        player.currentTimeProperty().removeListener(progressChangeListener);
                        player.getMedia().getMetadata().removeListener(metadataChangeListener);
                        player.stop();
                        musicPlayerSlide.setMediaPlayer(nextPlayer);
                        playlist.setActivePlayer(nextPlayer);
                        nextPlayer.setVolume(player.getVolume());
                        nextPlayer.play();
                    });
                }

                // display the name of the currently playing track.
                musicPlayerSlide.mediaPlayerProperty().addListener((observableValue, oldPlayer, newPlayer) -> {
                    setCurrentlyPlaying(newPlayer);
                });

                // start playing the first track.
                musicPlayerSlide.setMediaPlayer(players.get(0));
                playlist.setActivePlayer(players.get(0));
                setCurrentlyPlaying(musicPlayerSlide.getMediaPlayer());

                //VBox.setVgrow(vBoxProg, Priority.ALWAYS);
                //vBoxProg.setAlignment(Pos.CENTER);
                //vBoxProg.getChildren().setAll(progress);
            }
        }
    }

    /**
     * sets the currently playing label to the label of the new media player and updates the progress monitor.
     */
    private void setCurrentlyPlaying(final MediaPlayer newPlayer) {
        newPlayer.seek(Duration.ZERO);

        progress.setProgress(0);
        songSecondsCounterLabel.setText("0");
        progressChangeListener = (observableValue, oldValue, newValue) -> {
            songTotalLengthLabel.setText("/" + ((int) newPlayer.getTotalDuration().toMinutes()) + ":" + String.format("%02d", (((int) newPlayer.getTotalDuration().toSeconds()) % 60)));
            progress.setProgress(1.0 * newPlayer.getCurrentTime().toMillis() / newPlayer.getTotalDuration().toMillis());
            songSecondsCounterLabel.setText(((int) newPlayer.getCurrentTime().toMinutes()) + ":" + String.format("%02d", (((int) newPlayer.getCurrentTime().toSeconds()) % 60)));
        };

        newPlayer.currentTimeProperty().addListener(progressChangeListener);

        String source = newPlayer.getMedia().getSource();
        source = source.substring(0, source.length() - 4);
        source = source.substring(source.lastIndexOf("/") + 1).replaceAll("%20", " ");
        artistAndSongLabel.setText(source);

        setMetaDataDisplay(newPlayer.getMedia().getMetadata());
    }

    private void setMetaDataDisplay(ObservableMap<String, Object> metadata) {
        metadataChangeListener = change -> readMetaData(metadata);
        metadata.addListener(metadataChangeListener);
        readMetaData(metadata);
    }

    private void readMetaData(ObservableMap<String, Object> metadata) {
        String labeltext = artistAndSongLabel.getText();
        String artist = "";
        String album = "";
        String title = "";

        for (String key : metadata.keySet()) {
            if (key.equals("artist")) artist = (String) metadata.get(key);
            if (key.equals("album artist")) artist = (String) metadata.get(key);
            if (key.equals("album")) album = (String) metadata.get(key);
            if (key.equals("title")) title = (String) metadata.get(key);
        }

        if (!title.equals("")) labeltext = title;
        if (!artist.equals("")) labeltext = artist + " - " + labeltext;
        if (!album.equals("")) labeltext += " (" + album + " )";
        artistAndSongLabel.setText(labeltext);

    }

    @FXML
    void rewindButtonClicked(ActionEvent event) {
        final MediaPlayer curPlayer = musicPlayerSlide.getMediaPlayer();
        curPlayer.currentTimeProperty().removeListener(progressChangeListener);
        curPlayer.getMedia().getMetadata().removeListener(metadataChangeListener);
        curPlayer.stop();

        Integer last = players.indexOf(curPlayer) - 1;
        MediaPlayer lastPlayer = players.get(last >= 0 ? last : players.size() + last);
        playlist.setActivePlayer(lastPlayer);
        musicPlayerSlide.setMediaPlayer(lastPlayer);
        if (playing) lastPlayer.play();
    }

    @FXML
    void playButtonClicked(ActionEvent event) {
        if (playing) {
            musicPlayerSlide.getMediaPlayer().pause();
            btnPlay.getStyleClass().add("btnMusic_Play");
            btnPlay.getStyleClass().remove("btnMusic_Pause");
            playing = false;
        } else {
            musicPlayerSlide.getMediaPlayer().play();
            btnPlay.getStyleClass().add("btnMusic_Pause");
            btnPlay.getStyleClass().remove("btnMusic_Play");
            playing = true;
        }
    }

    @FXML
    void forwardButtonClicked(ActionEvent event) {
        final MediaPlayer curPlayer = musicPlayerSlide.getMediaPlayer();
        curPlayer.currentTimeProperty().removeListener(progressChangeListener);
        curPlayer.getMedia().getMetadata().removeListener(metadataChangeListener);
        curPlayer.stop();

        MediaPlayer nextPlayer = players.get((players.indexOf(curPlayer) + 1) % players.size());
        playlist.setActivePlayer(nextPlayer);
        musicPlayerSlide.setMediaPlayer(nextPlayer);
        if (playing) nextPlayer.play();
    }

    @FXML
    void playlistButtonClicked(ActionEvent event) {
        parent.launchDialog(PageEnum.Playlist);
    }

    @FXML
    void onClickMute(ActionEvent event) {
        if (muted) {
            musicPlayerSlide.getMediaPlayer().setMute(false);
            muted = false;
        } else {
            musicPlayerSlide.getMediaPlayer().setMute(true);
            muted = true;
        }
    }

    @FXML
    void onClickShuffle(ActionEvent event) {
        if (shuffled) {
            players = original;
            shuffled = false;
        } else {
            List<MediaPlayer> shuffledList = new ArrayList<>();
            Random rand = new Random();
            for (int i = 0; i < original.size(); i++) {
                int randomNum = rand.nextInt(original.size() - i);
                shuffledList.add(original.get(randomNum));
            }
            players = shuffledList;
            shuffled = true;
        }
    }

    public void play() {
        if (musicPlayerSlide != null) {
            musicPlayerSlide.getMediaPlayer().setVolume(VOLUME_ACTIVE);

/*            musicPlayerSlide.getMediaPlayer().setOnReady(() -> {
                musicPlayerSlide.getMediaPlayer().play();
                new Transition() {{setCycleDuration(Duration.millis(10));}
                    @Override
                    protected void interpolate(double frac) {
                        System.out.println("interpolate");
                        musicPlayerSlide.getMediaPlayer().setVolume(frac);
                    }
                }.play();
            });*/

            musicPlayerSlide.getMediaPlayer().play();
            playing = true;
        }
    }

    public KeyValue reduceVolKeyValue() {
        return volKeyValue(VOLUME_INACTIVE);
    }

    public KeyValue raiseVolKeyValue() {
        return volKeyValue(VOLUME_ACTIVE);
    }

    public KeyValue volKeyValue(Double raise) {
        if (musicPlayerSlide != null) {
            //System.out.println("current: " + musicPlayerSlide.getMediaPlayer().getVolume());
            //System.out.println("new: " + raise);
            raise = raise > 1 ? 1 : raise;
            raise = raise < 0 ? 0 : raise;

            return new KeyValue(musicPlayerSlide.getMediaPlayer().volumeProperty(), raise);
        }
        return null;
    }


    public void stopMusic() {
        if (musicPlayerSlide != null) {
            musicPlayerSlide.getMediaPlayer().stop();
            musicPlayerSlide = null;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initController();
    }

    public void setParent(WorkoutController parent) {
        this.parent = parent;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }
}
