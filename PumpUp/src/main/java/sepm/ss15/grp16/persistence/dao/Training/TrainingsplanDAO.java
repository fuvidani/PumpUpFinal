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
public interface TrainingsplanDAO extends DAO<Trainingsplan> {

	List<Trainingsplan> find(Trainingsplan trainingsplan) throws PersistenceException;

	List<TrainingsplanType> findAssociatedTypes(int id) throws PersistenceException;

	void createAssociationToType(int id_plan, int id_type) throws PersistenceException;

	void deleteAssociationToType(int id_plan, int id_type) throws PersistenceException;


}
