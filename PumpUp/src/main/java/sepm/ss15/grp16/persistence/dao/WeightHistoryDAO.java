package sepm.ss15.grp16.persistence.dao;

import sepm.ss15.grp16.entity.WeightHistory;

import java.util.List;

/**
 * This class represents the DAO for a weighthistory
 *
 * @author Michael Sober
 * @version 1.0
 */
public interface WeightHistoryDAO extends DAO<WeightHistory>{

    List<WeightHistory> searchByUserID(int user_id);

}
