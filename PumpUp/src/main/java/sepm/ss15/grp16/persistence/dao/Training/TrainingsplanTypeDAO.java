package sepm.ss15.grp16.persistence.dao.Training;

import sepm.ss15.grp16.entity.Training.Trainingsplan;
import sepm.ss15.grp16.entity.Training.TrainingsplanType;
import sepm.ss15.grp16.persistence.dao.DAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.List;

/**
 * Author: Lukas
 * Date: 08.05.2015
 */
public interface TrainingsplanTypeDAO extends DAO<TrainingsplanType> {

	List<TrainingsplanType> find(TrainingsplanType trainingsplanType) throws PersistenceException;

	List<Trainingsplan> findAssociatedPlans(int id) throws PersistenceException;

	void createAssociationToPlan(int id_plan, int id_type) throws PersistenceException;

	void deleteAssociationToPlan(int id_plan, int id_type) throws PersistenceException;
}
