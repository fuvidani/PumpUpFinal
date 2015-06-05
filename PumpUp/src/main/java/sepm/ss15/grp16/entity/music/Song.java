package sepm.ss15.grp16.entity.music;

import sepm.ss15.grp16.entity.DTO;

import java.io.File;
import java.time.Duration;

/**
 * Author: Lukas
 * Date: 04.06.2015
 */
public class Song implements DTO {

    private String artist;
    private String title;
    private Duration duration;
    private Duration time_position;
    private File songfile;
    private String album;

    public Song() {
    }

    public Song(Duration time_position, String artist, String title, Duration duration, File songfile) {
        this.time_position = time_position;
        this.artist = artist;
        this.title = title;
        this.duration = duration;
        this.songfile = songfile;
    }

    public Song(Song song) {
        this.songfile = song.songfile;
        this.time_position = song.time_position;
        this.artist = song.artist;
        this.title = song.title;
        this.duration = song.duration;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Duration getTime_position() {
        return time_position;
    }

    public void setTime_position(Duration time_position) {
        this.time_position = time_position;
    }


    public void setAlbum(String album) {
        this.album = album;
    }
    public File getSongfile() {
        return songfile;
    }

    public void setSongfile(File songfile) {
        this.songfile = songfile;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;

        Song song = (Song) o;

        return !(artist != null ? !artist.equals(song.artist) : song.artist != null)
                && !(title != null ? !title.equals(song.title) : song.title != null)
                && !(duration != null ? !duration.equals(song.duration) : song.duration != null)
                && !(time_position != null ? !time_position.equals(song.time_position) : song.time_position != null)
                && !(songfile != null ? !songfile.equals(song.songfile) : song.songfile != null);

    }

    @Override
    public int hashCode() {
        int result = artist != null ? artist.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (time_position != null ? time_position.hashCode() : 0);
        result = 31 * result + (songfile != null ? songfile.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Song{" +
                "artist='" + artist + '\'' +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                ", time_position=" + time_position +
                ", songfile=" + songfile +
                '}';
    }
}
