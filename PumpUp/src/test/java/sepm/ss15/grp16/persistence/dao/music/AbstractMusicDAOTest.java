package sepm.ss15.grp16.persistence.dao.music;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import sepm.ss15.grp16.entity.music.Playlist;
import sepm.ss15.grp16.persistence.dao.AbstractDAOTest;
import sepm.ss15.grp16.persistence.dao.DAO;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Author: Lukas
 * Date: 04.06.2015
 */
public abstract class AbstractMusicDAOTest extends AbstractDAOTest<Playlist> {
    private static final Logger LOGGER = LogManager.getLogger(AbstractMusicDAOTest.class);

    @Override
    public abstract DAO<Playlist> getDAO();

    @Test(expected = IllegalStateException.class)
    public void testCreate() throws Exception {
        LOGGER.info("testCreate");
        Playlist playlist = getDAO().create(getDummyPlaylist());

        Media media = new Media(testPath().toString());
        MediaPlayer player = new MediaPlayer(media);
        Assert.assertTrue(playlist.getPlayers().contains(player));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testFindAll() throws Exception {
        LOGGER.info("testFindAll");
        createValid(getDummyPlaylist());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSearchByID() throws Exception {
        LOGGER.info("testSearchByID");
        createValid(getDummyPlaylist());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUpdate() throws Exception {
        LOGGER.info("testUpdate");
        createValid(getDummyPlaylist());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testDelete() throws Exception {
        LOGGER.info("testDelete");
        createValid(getDummyPlaylist());
    }

    @Test(expected = IllegalStateException.class)
    public void motivationTest()throws Exception{
        LOGGER.info("motivationTest");
        Map<String,Playlist> playlistMap= ((MusicDAO)getDAO()).getMotivations();
    }

    public Playlist getDummyPlaylist() throws URISyntaxException {
        return new Playlist(null, testPath().getPath(), null);
    }

    public URI testPath() throws URISyntaxException {
        return this.getClass().getResource("/music/testfile.mp3").toURI();
    }
}