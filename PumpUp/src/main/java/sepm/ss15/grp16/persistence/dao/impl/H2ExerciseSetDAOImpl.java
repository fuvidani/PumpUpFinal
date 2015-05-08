package sepm.ss15.grp16.persistence.dao.impl;

import sepm.ss15.grp16.entity.impl.ExerciseSet;
import sepm.ss15.grp16.persistence.dao.ExerciseSetDAO;
import sepm.ss15.grp16.persistence.database.impl.H2DBConnectorImpl;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.List;

/**
 * Author: Lukas
 * Date: 08.05.2015
 */
public class H2ExerciseSetDAOImpl implements ExerciseSetDAO{
	public H2ExerciseSetDAOImpl(H2DBConnectorImpl handler) {
	}

	@Override
	public List<ExerciseSet> find(ExerciseSet exerciseSet) {
		return null;
	}

	@Override
	public ExerciseSet create(ExerciseSet dto) throws PersistenceException {
		return null;
	}

	@Override
	public List<ExerciseSet> findAll() throws PersistenceException {
		return null;
	}

	@Override
	public ExerciseSet searchByID(int id) throws PersistenceException {
		return null;
	}

	@Override
	public ExerciseSet update(ExerciseSet dto) throws PersistenceException {
		return null;
	}

	@Override
	public void delete(ExerciseSet dto) throws PersistenceException {

	}
}
