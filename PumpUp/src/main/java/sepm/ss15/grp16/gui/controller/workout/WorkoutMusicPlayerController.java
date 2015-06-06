package sepm.ss15.grp16.gui.controller.workout;

import javafx.beans.value.ChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
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

    private Playlist playlist;
    private MusicService musicService;
    private UserService userService;
    private WorkoutController parent;

    private List<MediaPlayer> players;
    private List<MediaPlayer> original;
    private boolean playing = false;
    private boolean muted = false;
    private boolean shuffled = false;

    @FXML
    private ProgressBar progress;
    private ChangeListener<Duration> progressChangeListener;
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
            e.printStackTrace();
        }
        if (playlist == null) {
            mainPane.setVisible(false);
        } else {
            players = playlist.getPlayers();
            original = new ArrayList<>(players);

            if (!players.isEmpty()) {
                System.out.println("success!");
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
                musicPlayerSlide.getMediaPlayer().play();
                setCurrentlyPlaying(musicPlayerSlide.getMediaPlayer());
                playing = true;

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
            songTotalLengthLabel.setText(String.format("/%1$.2f", newPlayer.getTotalDuration().toMinutes()).replace(",", ":"));
            progress.setProgress(1.0 * newPlayer.getCurrentTime().toMillis() / newPlayer.getTotalDuration().toMillis());
            songSecondsCounterLabel.setText(String.format("%1$.2f", newPlayer.getCurrentTime().toMinutes()).replace(",", ":"));
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
            System.out.println("size: " + original.size());
            for (int i = 0; i < original.size(); i++) {
                int randomNum = rand.nextInt(original.size() - i);
                shuffledList.add(original.get(randomNum));
            }
            players = shuffledList;
            shuffled = true;
        }

    }

    public void reduceVol(Integer percent) {
        Double vol = musicPlayerSlide.getMediaPlayer().getVolume();
        Double new_vol = vol + (vol * (percent / 100));
        new_vol = new_vol > 1 ? 1 : new_vol;
        this.
                musicPlayerSlide.getMediaPlayer().setVolume(new_vol);
    }

    public void reduceVol() {
        reduceVol(50);
    }

    public void raiseVol(Integer percent) {
        Double vol = musicPlayerSlide.getMediaPlayer().getVolume();
        Double new_vol = vol - (vol * (percent / 100));
        new_vol = new_vol < 0 ? 0 : new_vol;
        musicPlayerSlide.getMediaPlayer().setVolume(new_vol);
    }

    public void raiseVol() {
        raiseVol(50);
    }

    public void stopMusic() {
        musicPlayerSlide.getMediaPlayer().stop();
        musicPlayerSlide = null;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
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
}
