package sepm.ss15.grp16.persistence.dao;

import sepm.ss15.grp16.entity.PictureHistory;

import java.util.List;

/**
 * This class represents the DAO for a picturehistory
 *
 * @author Michael Sober
 * @version 1.0
 */
public interface PictureHistoryDAO extends DAO<PictureHistory> {

    List<PictureHistory> searchByUserID(int user_id);

}
