package sepm.ss15.grp16.persistence.dao;

import sepm.ss15.grp16.entity.impl.TrainingsplanType;

import java.util.List;

/**
 * Author: Lukas
 * Date: 08.05.2015
 */
public interface TrainingsplanTypeDAO extends DAO<TrainingsplanType> {

	List<TrainingsplanType> find(TrainingsplanType trainingsplanType);
}
