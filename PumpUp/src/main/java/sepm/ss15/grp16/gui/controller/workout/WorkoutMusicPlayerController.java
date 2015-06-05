package sepm.ss15.grp16.gui.controller.workout;

import javafx.beans.value.ChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
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
import java.util.*;

/**
 * Created by Daniel Fuevesi on 08.05.15.
 * This controller controls the lower section of the training's stage.
 */
public class WorkoutMusicPlayerController extends Controller implements Initializable {
    private static final String MUSIC_DIR = "C:\\Users\\Lukas\\Desktop\\Music";
    public static final String TAG_COLUMN_NAME = "Tag";
    public static final String VALUE_COLUMN_NAME = "Value";
    public static final List<String> SUPPORTED_FILE_EXTENSIONS = Arrays.asList(".mp3", ".m4a");
    public static final int FILE_EXTENSION_LEN = 4;

    private Playlist playlist;
    private MusicService musicService;
    private UserService userService;

    private List<MediaPlayer> players;
    final ProgressBar progress = new ProgressBar();
    //final TableView<Map> metadataTable = new TableView<>();
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
    private Button btnPlay;

    //@FXML
    //private ProgressBar progress = new ProgressBar();

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

        // create some media players.
        players = playlist.getPlayers();

        if (!players.isEmpty()) {
            System.out.println("success!");
            musicPlayerSlide = new MediaView(players.get(0));
            btnSkip = new Button("Skip");
            btnPlay = new Button("Pause");

            // play each audio file in turn.
            for (int i = 0; i < players.size(); i++) {
                final MediaPlayer player = players.get(i);
                final MediaPlayer nextPlayer = players.get((i + 1) % players.size());
                player.setOnEndOfMedia(() -> {
                    player.currentTimeProperty().removeListener(progressChangeListener);
                    player.getMedia().getMetadata().removeListener(metadataChangeListener);
                    player.stop();
                    musicPlayerSlide.setMediaPlayer(nextPlayer);
                    nextPlayer.play();
                });
            }

            // display the name of the currently playing track.
            musicPlayerSlide.mediaPlayerProperty().addListener((observableValue, oldPlayer, newPlayer) -> {
                setCurrentlyPlaying(newPlayer);
            });

            // start playing the first track.
            musicPlayerSlide.setMediaPlayer(players.get(0));
            musicPlayerSlide.getMediaPlayer().play();
            setCurrentlyPlaying(musicPlayerSlide.getMediaPlayer());

            // silly invisible button used as a template to get the actual preferred size of the Pause button.
            Button invisiblePause = new Button("Pause");
            invisiblePause.setVisible(false);
            btnPlay.prefHeightProperty().bind(invisiblePause.heightProperty());
            btnPlay.prefWidthProperty().bind(invisiblePause.widthProperty());

            VBox.setVgrow(vBoxProg, Priority.ALWAYS);
            vBoxProg.setAlignment(Pos.CENTER);
            vBoxProg.getChildren().setAll(progress);
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
            songSecondsCounterLabel.setText(String.format("%1$.2f", newPlayer.getCurrentTime().toMinutes()).replace(",",":"));
        };
        newPlayer.currentTimeProperty().addListener(progressChangeListener);

        String source = newPlayer.getMedia().getSource();
        source = source.substring(0, source.length() - FILE_EXTENSION_LEN);
        source = source.substring(source.lastIndexOf("/") + 1).replaceAll("%20", " ");
        artistAndSongLabel.setText(source);

        setMetaDataDisplay(newPlayer.getMedia().getMetadata());
    }

    private void setMetaDataDisplay(ObservableMap<String, Object> metadata) {
        metadataChangeListener = change -> convertMetadataToTableMap(metadata);
        metadata.addListener(metadataChangeListener);
    }

    private void convertMetadataToTableMap(ObservableMap<String, Object> metadata) {
        String labeltext = artistAndSongLabel.getText();
        String artist ="";
        String album = "";
        String title = "";

        for (String key : metadata.keySet()) {
            if(key.equals("artist")) artist = (String) metadata.get(key);
            if(key.equals("album artist")) artist = (String) metadata.get(key);
            if(key.equals("album")) album = (String) metadata.get(key);
            if(key.equals("title")) title = (String) metadata.get(key);
        }

        System.out.println(String.format("New Metadata: %s %s %s",artist, album, title));

        if(!title.equals("")) labeltext = title;
        if(!artist.equals("")) labeltext = artist + " - " + labeltext;
        if(!album.equals("")) labeltext += " (" + album + " )";
        artistAndSongLabel.setText(labeltext);

    }

    /**
     * @return a MediaPlayer for the given source which will report any errors it encounters
     */
    private MediaPlayer createPlayer(String mediaSource) {
        final Media media = new Media(mediaSource);
        final MediaPlayer player = new MediaPlayer(media);
        player.setOnError(() -> System.out.println("Media error occurred: " + player.getError()));
        return player;
    }


    @FXML
    void rewindButtonClicked(ActionEvent event) {

    }

    @FXML
    void playButtonClicked(ActionEvent event) {
        if ("Pause".equals(btnPlay.getText())) {
            musicPlayerSlide.getMediaPlayer().pause();
            btnPlay.setText("Play");
        } else {
            musicPlayerSlide.getMediaPlayer().play();
            btnPlay.setText("Pause");
        }
    }

    @FXML
    void forwardButtonClicked(ActionEvent event) {
        final MediaPlayer curPlayer = musicPlayerSlide.getMediaPlayer();
        curPlayer.currentTimeProperty().removeListener(progressChangeListener);
        curPlayer.getMedia().getMetadata().removeListener(metadataChangeListener);
        curPlayer.stop();

        MediaPlayer nextPlayer = players.get((players.indexOf(curPlayer) + 1) % players.size());
        musicPlayerSlide.setMediaPlayer(nextPlayer);
        nextPlayer.play();
    }

    @FXML
    void playlistButtonClicked(ActionEvent event) {
        mainFrame.openDialog(PageEnum.Playlist);
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initController();
    }
}
