package sepm.ss15.grp16.persistence.dao.music.impl;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sepm.ss15.grp16.entity.music.Playlist;
import sepm.ss15.grp16.entity.music.Song;
import sepm.ss15.grp16.persistence.dao.music.MusicDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Author: Lukas
 * Date: 04.06.2015
 */
public class MusicDAOImpl implements MusicDAO {
    @Override
    public Playlist create(Playlist dto) throws PersistenceException {

        if (dto == null || dto.getDir() == null) {
            throw new PersistenceException(new NullPointerException());
        }

        List<MediaPlayer> songList = new ArrayList<>();
        File dir = new File(dto.getDir());

        String[] pathList = dir.list();
        String dirpath = dir.getAbsolutePath();

        if (pathList == null) {
            throw new PersistenceException(new NullPointerException());
        }

        for (String path : pathList) {
            path = dirpath + "\\" + path;
            path = path.replace("\\", "/");
            File file = new File(path);
            if (file.isFile() && checkSupportedFormat(getExtension(file.getPath()))) {
                Media media = new Media(file.toURI().toString());
                MediaPlayer player = new MediaPlayer(media);
                songList.add(player);
            }
        }
        dto.setPlayers(songList);
        return dto;
    }

    private String getExtension(String path) {
        String extension = "";

        int i = path.lastIndexOf('.');
        if (i > 0) {
            extension = path.substring(i + 1);
        }
        return extension;
    }

    private boolean checkSupportedFormat(String extension) {
        for (Playlist.SupportedFormat format : Playlist.SupportedFormat.values()) {
            if (format.name().equals(extension)) {
                return true;
            }
        }
        return false;
    }

    private Song getSong(File file) {
        Song song = new Song();
        song.setSongfile(file);
        Media media = new Media(file.toURI().toString());

        Set<String> metadatas = media.getMetadata().keySet();

        for (String metadata : metadatas) {
            handleMetadata(metadata, media.getMetadata().get(metadata), song);
        }
        System.out.println(song);
        return song;
    }

    private Song handleMetadata(String key, Object value, Song song) {
        switch (key) {
            case "album":
                song.setAlbum(value.toString());
                break;
            case "artist":
                song.setArtist(value.toString());
                break;
            case "title":
                song.setTitle(value.toString());
                break;
        }
        return song;
    }

    @Override
    public List<Playlist> findAll() throws PersistenceException {
        throw new UnsupportedOperationException("findAll not supported!");
    }

    @Override
    public Playlist searchByID(int id) throws PersistenceException {
        throw new UnsupportedOperationException("searchByID not supported!");
    }

    @Override
    public Playlist update(Playlist dto) throws PersistenceException {
        throw new UnsupportedOperationException("update not supported!");
    }

    @Override
    public void delete(Playlist dto) throws PersistenceException {
        throw new UnsupportedOperationException("delete not supported!");
    }
}
