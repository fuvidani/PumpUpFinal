package sepm.ss15.grp16.persistence.dao.Training;

import sepm.ss15.grp16.entity.Training.TrainingsSession;
import sepm.ss15.grp16.persistence.dao.DAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.List;

/**
 * Author: Lukas
 * Date: 08.05.2015
 */
public interface TrainingsSessionDAO extends DAO<TrainingsSession> {

	List<TrainingsSession> find(TrainingsSession trainingsSession) throws PersistenceException;
}
