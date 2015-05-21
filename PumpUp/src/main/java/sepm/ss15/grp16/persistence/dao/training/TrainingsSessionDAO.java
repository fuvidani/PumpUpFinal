package sepm.ss15.grp16.persistence.dao.training;

import sepm.ss15.grp16.entity.training.TrainingsSession;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.persistence.dao.DAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.List;

/**
 * Author: Lukas
 * Date: 08.05.2015
 */
public interface TrainingsSessionDAO extends DAO<TrainingsSession> {

	/**
	 * not allowed & implemented, go with TrainingsplanDAO
	 *
	 * @throws UnsupportedOperationException
	 */
	TrainingsSession create(TrainingsSession dto);

	/**
	 * not allowed & implemented, go with TrainingsplanDAO
	 *
	 * @throws UnsupportedOperationException
	 */
	List<TrainingsSession> findAll();

	/**
	 * not allowed & implemented, go with TrainingsplanDAO
	 *
	 * @throws UnsupportedOperationException
	 */
	TrainingsSession update(TrainingsSession dto);

	/**
	 * not allowed & implemented, go with TrainingsplanDAO
	 *
	 * @throws UnsupportedOperationException
	 */
	void delete(TrainingsSession dto);

	/**
	 * find all trainingssession of a corresponding plan_interClassCommunication
	 *
	 * @param ID_plan to search, must not be null
	 * @return found sessions, null if nothing was found
	 * @throws PersistenceException if there are complications with the persitance layer
	 */
	List<TrainingsSession> searchByPlanID(int ID_plan) throws PersistenceException;

	/**
	 * find all trainingssession of an user
	 *
	 * @param user to search, can be null, UID must not be null
	 * @return found sessions, null if nothing was found
	 * @throws PersistenceException if there are complications with the persitance layer
	 */
	List<TrainingsSession> searchByUser(User user) throws PersistenceException;
}
