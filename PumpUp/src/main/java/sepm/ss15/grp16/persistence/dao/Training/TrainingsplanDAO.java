package sepm.ss15.grp16.persistence.dao.Training;

import sepm.ss15.grp16.entity.Training.Helper.ExerciseSet;
import sepm.ss15.grp16.entity.Training.TrainingsSession;
import sepm.ss15.grp16.entity.Training.Trainingsplan;
import sepm.ss15.grp16.persistence.dao.DAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.List;

/**
 * Author: Lukas
 * Date: 08.05.2015
 */
public interface TrainingsplanDAO extends DAO<Trainingsplan> {

	List<Trainingsplan> find(Trainingsplan trainingsplan) throws PersistenceException;

	Trainingsplan getPlanBySession(TrainingsSession session) throws PersistenceException;

	Trainingsplan getPlanBySet(ExerciseSet set) throws PersistenceException;
}
