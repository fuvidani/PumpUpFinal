package sepm.ss15.grp16.persistence.dao.Training.Helper;

import sepm.ss15.grp16.entity.Training.Helper.ExerciseSet;
import sepm.ss15.grp16.entity.Training.TrainingsSession;
import sepm.ss15.grp16.entity.Training.Trainingsplan;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.List;

/**
 * Author: Lukas
 * Date: 12.05.2015
 */
public interface ExerciseSetHelperDAO extends HelperDAO<ExerciseSet> {

	List<ExerciseSet> searchBySessionID(int ID_Session) throws PersistenceException;

	TrainingsSession getSessionBySet(ExerciseSet set) throws PersistenceException;
}
