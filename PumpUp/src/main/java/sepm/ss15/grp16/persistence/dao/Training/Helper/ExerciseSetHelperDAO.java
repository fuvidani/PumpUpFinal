package sepm.ss15.grp16.persistence.dao.Training.Helper;

import sepm.ss15.grp16.entity.Training.Helper.ExerciseSet;
import sepm.ss15.grp16.entity.Training.TrainingsSession;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.List;

/**
 * Author: Lukas
 * Date: 12.05.2015
 */
public interface ExerciseSetHelperDAO extends HelperDAO<ExerciseSet> {

	/**
	 * find all ExerciseSet of a corresponding Session
	 *
	 * @param ID_Session to search, must not be null
	 * @return found sets, null if nothing was found
	 * @throws PersistenceException if there are complications with the persitance layer
	 */
	List<ExerciseSet> searchBySessionID(int ID_Session) throws PersistenceException;

	/**
	 * generates the corresponding Session for an ExerciseSet
	 *
	 * @param set must has a valid ID
	 * @return the corresponding plan, null if nothing was found
	 * @throws PersistenceException if there are complications with the persitance layer
	 */
	TrainingsSession getSessionBySet(ExerciseSet set) throws PersistenceException;
}
