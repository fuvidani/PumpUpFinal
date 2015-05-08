package sepm.ss15.grp16.persistence.dao.impl;

import sepm.ss15.grp16.entity.impl.Trainingsplan;
import sepm.ss15.grp16.persistence.dao.TrainingsplanDAO;
import sepm.ss15.grp16.persistence.database.impl.H2DBConnectorImpl;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.List;

/**
 * Author: Lukas
 * Date: 08.05.2015
 */
public class H2TrainingsplanDAOImpl implements TrainingsplanDAO{
	private H2TrainingsplanDAOImpl(H2DBConnectorImpl handler) {
	}

	@Override
	public List<Trainingsplan> find(Trainingsplan trainingsplan) {
		return null;
	}

	@Override
	public Trainingsplan create(Trainingsplan dto) throws PersistenceException {
		return null;
	}

	@Override
	public List<Trainingsplan> findAll() throws PersistenceException {
		return null;
	}

	@Override
	public Trainingsplan searchByID(int id) throws PersistenceException {
		return null;
	}

	@Override
	public Trainingsplan update(Trainingsplan dto) throws PersistenceException {
		return null;
	}

	@Override
	public void delete(Trainingsplan dto) throws PersistenceException {

	}
}
