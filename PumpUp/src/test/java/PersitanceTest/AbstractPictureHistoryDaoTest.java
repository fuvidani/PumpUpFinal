package PersitanceTest;

import org.junit.Test;
import sepm.ss15.grp16.entity.BodyfatHistory;
import sepm.ss15.grp16.entity.PictureHistory;
import sepm.ss15.grp16.persistence.dao.PictureHistoryDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class provides methods for testing PictureHistoryDAOs
 *
 * @author Michael Sober
 * @version 1.0
 */
public abstract class AbstractPictureHistoryDaoTest {

    protected PictureHistoryDAO pictureHistoryDAO;

    public void setPictureHistoryDAO(PictureHistoryDAO pictureHistoryDAO) {
        this.pictureHistoryDAO = pictureHistoryDAO;
    }

    @Test(expected = PersistenceException.class)
    public void createWithNullShouldThrowException() throws Exception{
        pictureHistoryDAO.create(null);
    }

    @Test
    public void createWithValidPictureHistoryShouldPersist() throws Exception {
        PictureHistory testPictureHistory = new PictureHistory(null, 1, "/Users/michaelsober/Desktop/bild.jpg", new Date());
        List<PictureHistory> allPictureHistories = pictureHistoryDAO.findAll();
        assertFalse(allPictureHistories.contains(testPictureHistory));
        pictureHistoryDAO.create(testPictureHistory);
        allPictureHistories = pictureHistoryDAO.findAll();
        assertTrue(allPictureHistories.contains(testPictureHistory));
    }

}
