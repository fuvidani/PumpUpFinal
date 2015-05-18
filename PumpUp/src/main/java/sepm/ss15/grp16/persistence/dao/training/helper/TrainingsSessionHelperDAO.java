package sepm.ss15.grp16.persistence.dao.training.helper;

import sepm.ss15.grp16.entity.training.TrainingsSession;
import sepm.ss15.grp16.entity.training.Trainingsplan;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.List;

/**
 * Author: Lukas
 * Date: 12.05.2015
 */
public interface TrainingsSessionHelperDAO extends HelperDAO<TrainingsSession> {

	/**
	 * find all trainingssession of a corresponding plan_interClassCommunication
	 *
	 * @param ID_plan to search, must not be null
	 * @return found sessions, null if nothing was found
	 * @throws PersistenceException if there are complications with the persitance layer
	 */
	List<TrainingsSession> searchByPlanID(int ID_plan) throws PersistenceException;

	/**
	 * generates the corresponding trainingsplan for a trainingssession
	 *
	 * @param session must has a valid ID
	 * @return the corresponding plan_interClassCommunication, null if nothing was found
	 * @throws PersistenceException if there are complications with the persitance layer
	 */
	Trainingsplan getPlanBySession(TrainingsSession session) throws PersistenceException;
}
