package sepm.ss15.grp16.persistence.dao;

import sepm.ss15.grp16.entity.BodyfatHistory;

import java.util.List;

/**
 * This class represents the DAO for a bodyfathistory
 *
 * @author Michael Sober
 * @version 1.0
 */
public interface BodyfatHistoryDAO extends DAO<BodyfatHistory>{

    List<BodyfatHistory> searchByUserID(int user_id);

}
