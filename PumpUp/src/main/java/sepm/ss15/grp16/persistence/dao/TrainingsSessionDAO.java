package sepm.ss15.grp16.persistence.dao;

import sepm.ss15.grp16.entity.impl.TrainingsSession;

import java.util.List;

/**
 * Author: Lukas
 * Date: 08.05.2015
 */
public interface TrainingsSessionDAO extends DAO<TrainingsSession> {

	List<TrainingsSession> find(TrainingsSession trainingsSession);
}
