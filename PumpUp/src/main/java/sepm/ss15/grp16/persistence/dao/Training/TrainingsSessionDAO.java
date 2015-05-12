package sepm.ss15.grp16.persistence.dao.Training;

import sepm.ss15.grp16.entity.Training.TrainingsSession;
import sepm.ss15.grp16.entity.User;
import sepm.ss15.grp16.persistence.dao.DAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.List;

/**
 * Author: Lukas
 * Date: 08.05.2015
 */
public interface TrainingsSessionDAO extends DAO<TrainingsSession> {

	List<TrainingsSession> searchByPlanID(int ID_plan) throws PersistenceException;

	List<TrainingsSession> searchByUser(User user) throws PersistenceException;
}
