package sepm.ss15.grp16.gui.controller.workout;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.media.MediaPlayer;
import sepm.ss15.grp16.entity.music.Playlist;
import sepm.ss15.grp16.gui.controller.Controller;

/**
 * Created by Daniel Fuevesi on 06.05.15.
 */
public class PlaylistController extends Controller {

    @FXML
    private TableColumn<MediaPlayer, String> artistColumn;

    @FXML
    private TableView<MediaPlayer> tblPlaylist;

    @FXML
    private TableColumn<MediaPlayer, String> titleColumn;

    @FXML
    private TableColumn<MediaPlayer, String> lengthColumn;

    @Override
    public void initController() {
        WorkoutMusicPlayerController musicPlayerController = ((WorkoutController) this.getParentController()).getMusicPlayerController();
        Playlist playlist = musicPlayerController.getPlaylist();

        titleColumn.setCellValueFactory(p -> {
            String title = (String) p.getValue().getMedia().getMetadata().get("title");
            String source = p.getValue().getMedia().getSource();
            source = source.substring(0, source.length() - 4);
            source = source.substring(source.lastIndexOf("/") + 1).replaceAll("%20", " ");
            return new SimpleStringProperty((title == null || title.equals("")) ? source : title);
        });
        artistColumn.setCellValueFactory(p -> {
            String artist = (String) p.getValue().getMedia().getMetadata().get("artist");
            String album_artist = (String) p.getValue().getMedia().getMetadata().get("album artist");
            String artist_return = (artist == null || artist.equals("")) ? (album_artist == null || album_artist.equals("") ? "" : album_artist) : artist;
            return new SimpleStringProperty(artist_return);
        });
        lengthColumn.setCellValueFactory(p -> new SimpleStringProperty(String.format("%1$.2f", p.getValue().getTotalDuration().toMinutes()).replace(",", ":")));
        ObservableList<MediaPlayer> masterdata = FXCollections.observableArrayList(playlist.getPlayers());
        tblPlaylist.setItems(masterdata);
        tblPlaylist.getSelectionModel().clearAndSelect(masterdata.indexOf(playlist.getActivePlayer()));
    }

    @FXML
    void onClickFinish(ActionEvent event) {
        mainFrame.navigateToParent();
    }


}
