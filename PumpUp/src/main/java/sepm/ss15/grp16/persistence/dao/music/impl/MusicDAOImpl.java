package sepm.ss15.grp16.persistence.dao.music.impl;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sepm.ss15.grp16.entity.music.Playlist;
import sepm.ss15.grp16.persistence.dao.music.MusicDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Lukas
 * Date: 04.06.2015
 */
public class MusicDAOImpl implements MusicDAO {
    @Override
    public Playlist create(Playlist dto) throws PersistenceException {

        if (dto == null || dto.getDir() == null) {
            return null;
        }

        List<MediaPlayer> songList = new ArrayList<>();
        File dir = new File(dto.getDir());

        String[] pathList = dir.list();
        String dirpath = dir.getAbsolutePath();

        if (pathList == null) {
            return null;
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

    @Override
    public Map<String, Playlist> getMotivations() throws PersistenceException {
        Map<String, Playlist> dto = null;
        try {
            dto = new HashMap<>();
            URL url = getClass().getClassLoader().getResource("sound/motivation");

            if (url != null) {
                File rootDir = new File(URLDecoder.decode(url.getFile(), "UTF-8"));

                if (rootDir.isDirectory() && rootDir.listFiles() != null) {
                    File[] dirlist = rootDir.listFiles();

                    if (dirlist != null) {
                        for (File dir : dirlist) {
                            Playlist playlist = new Playlist();

                            List<MediaPlayer> songList = new ArrayList<>();
                            String[] pathList = dir.list();
                            String dirpath = dir.getAbsolutePath();

                            if (pathList != null) {

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
                                playlist.setPlayers(songList);
                            }
                            dto.put(dir.getName(),playlist);
                        }
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return dto;
    }
}
