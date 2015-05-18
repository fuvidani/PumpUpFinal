package sepm.ss15.grp16.persistence.dao.training;

import sepm.ss15.grp16.entity.training.helper.ExerciseSet;
import sepm.ss15.grp16.entity.training.TrainingsSession;
import sepm.ss15.grp16.entity.training.Trainingsplan;
import sepm.ss15.grp16.persistence.dao.DAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.List;

/**
 * Author: Lukas
 * Date: 08.05.2015
 */
public interface TrainingsplanDAO extends DAO<Trainingsplan> {

	/**
	 * creating a new Trainingsplan and all his containings elements (TrainingsSessions 6 ExerciseSets)
	 * @param trainingsplan which shall be inserted into the underlying persistance layer.
	 *                 must not be null, id must not be null
	 * @return the given trainingsplan for further usage, now with ID for all containings elements
	 * @throws PersistenceException if there are complications with the persitance layer
	 */
	Trainingsplan create(Trainingsplan trainingsplan) throws PersistenceException;

	/**
	 * deleting a given trainingsplan and all it's containings elements
	 *
	 * @param trainingsplan which shall be deleted from the underlying persitance implementation layer
	 * @throws PersistenceException if there are complications with the persitance layer
	 */
	void delete(Trainingsplan trainingsplan) throws PersistenceException;

	/**
	 * find all with filter matching trainingsplans
	 *
	 * @param filter for the search, must not be valid, you can search after name and user
	 * @return found trainingsplan, null if nothing was found
	 * @throws PersistenceException if there are complications with the persitance layer
	 */
	List<Trainingsplan> find(Trainingsplan filter) throws PersistenceException;

	/**
	 * generates the corresponding trainingsplan for a trainingssession
	 *
	 * @param session must has a valid ID
	 * @return the corresponding plan_interClassCommunication, null if nothing was found
	 * @throws PersistenceException if there are complications with the persitance layer
	 */
	Trainingsplan getPlanBySession(TrainingsSession session) throws PersistenceException;

	/**
	 * generates the corresponding trainingsplan for an ExerciseSet
	 *
	 * @param set must has a valid ID
	 * @return the corresponding plan_interClassCommunication, null if nothing was found
	 * @throws PersistenceException if there are complications with the persitance layer
	 */
	Trainingsplan getPlanBySet(ExerciseSet set) throws PersistenceException;
}
