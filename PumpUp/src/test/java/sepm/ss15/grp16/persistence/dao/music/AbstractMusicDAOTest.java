package sepm.ss15.grp16.persistence.dao.music;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import sepm.ss15.grp16.entity.music.Playlist;
import sepm.ss15.grp16.persistence.dao.AbstractDAOTest;
import sepm.ss15.grp16.persistence.dao.DAO;

import java.net.URISyntaxException;

/**
 * Author: Lukas
 * Date: 04.06.2015
 */
public abstract class AbstractMusicDAOTest extends AbstractDAOTest<Playlist> {
    private static final Logger LOGGER = LogManager.getLogger(AbstractMusicDAOTest.class);

    @Override
    public abstract DAO<Playlist> getDAO();

    @Test
    public void testCreate() throws Exception {
        LOGGER.info("testCreate");
        Playlist playlist = getDAO().create(getDummyPlaylist());

        //playlist.getPlayers().contains(new File("C:\\Users\\Lukas\\Desktop\\Music\\Within Temptation\\Mother Earth.wma"));

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

    public Playlist getDummyPlaylist() throws URISyntaxException {
        String dir = "C:\\Users\\Lukas\\Desktop\\Music\\Within Temptation";
        return new Playlist(null, dir, null);
    }
}