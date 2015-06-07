package sepm.ss15.grp16.entity.music;

import javafx.scene.media.MediaPlayer;
import sepm.ss15.grp16.entity.DTO;
import sepm.ss15.grp16.entity.user.User;

import java.util.List;

/**
 * Author: Lukas
 * Date: 04.06.2015
 */
public class Playlist implements DTO {

    private User user;
    private String dir;
    private List<MediaPlayer> players;

    MediaPlayer activePlayer;

    public Playlist() {

    }

    public Playlist(User user, String dir, List<MediaPlayer> players) {
        this.user = user;
        this.dir = dir;
        this.players = players;
    }

    public Playlist(Playlist playlist) {
        this.user = playlist.user;
        this.dir = playlist.dir;
        this.players = playlist.players;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public List<MediaPlayer> getPlayers() {
        return players;
    }

    public void setPlayers(List<MediaPlayer> players) {
        this.players = players;
    }

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public void setId(Integer id) {

    }

    @Override
    public Boolean getIsDeleted() {
        return false;
    }

    @Override
    public void setIsDeleted(Boolean deleted) {

    }

    public MediaPlayer getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(MediaPlayer activePlayer) {
        this.activePlayer = activePlayer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Playlist)) return false;

        Playlist playlist = (Playlist) o;

        if (user != null ? !user.equals(playlist.user) : playlist.user != null) return false;
        if (dir != null ? !dir.equals(playlist.dir) : playlist.dir != null) return false;
        return !(players != null ? !players.equals(playlist.players) : playlist.players != null);

    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (dir != null ? dir.hashCode() : 0);
        result = 31 * result + (players != null ? players.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "user=" + user +
                ", dir='" + dir + '\'' +
                ", players=" + players +
                '}';
    }

    public enum SupportedFormat{
        mp3, wav
    }
}
