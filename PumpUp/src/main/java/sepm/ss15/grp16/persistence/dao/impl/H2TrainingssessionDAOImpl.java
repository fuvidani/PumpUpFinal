package sepm.ss15.grp16.persistence.dao.impl;

import sepm.ss15.grp16.entity.impl.TrainingsSession;
import sepm.ss15.grp16.persistence.dao.TrainingsSessionDAO;
import sepm.ss15.grp16.persistence.database.impl.H2DBConnectorImpl;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.List;

/**
 * Author: Lukas
 * Date: 08.05.2015
 */
public class H2TrainingssessionDAOImpl implements TrainingsSessionDAO{
	public H2TrainingssessionDAOImpl(H2DBConnectorImpl handler) {
	}

	@Override
	public List<TrainingsSession> find(TrainingsSession trainingsSession) {
		return null;
	}

	@Override
	public TrainingsSession create(TrainingsSession dto) throws PersistenceException {
		return null;
	}

	@Override
	public List<TrainingsSession> findAll() throws PersistenceException {
		return null;
	}

	@Override
	public TrainingsSession searchByID(int id) throws PersistenceException {
		return null;
	}

	@Override
	public TrainingsSession update(TrainingsSession dto) throws PersistenceException {
		return null;
	}

	@Override
	public void delete(TrainingsSession dto) throws PersistenceException {

	}
}
