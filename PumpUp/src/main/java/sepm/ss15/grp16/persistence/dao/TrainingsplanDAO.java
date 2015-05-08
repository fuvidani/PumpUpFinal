package sepm.ss15.grp16.persistence.dao;

import sepm.ss15.grp16.entity.impl.Trainingsplan;

import java.util.List;

/**
 * Author: Lukas
 * Date: 08.05.2015
 */
public interface TrainingsplanDAO extends DAO<Trainingsplan> {

	List<Trainingsplan> find(Trainingsplan trainingsplan);
}
