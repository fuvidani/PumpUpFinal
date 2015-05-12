package sepm.ss15.grp16.persistence.dao.Training.Helper;

import sepm.ss15.grp16.entity.Training.TrainingsSession;
import sepm.ss15.grp16.entity.Training.Trainingsplan;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.List;

/**
 * Author: Lukas
 * Date: 12.05.2015
 */
public interface TrainingsSessionHelperDAO extends HelperDAO<TrainingsSession> {

	List<TrainingsSession> searchByPlanID(int ID_plan) throws PersistenceException;

	Trainingsplan getPlanBySession(TrainingsSession session) throws PersistenceException;
}
