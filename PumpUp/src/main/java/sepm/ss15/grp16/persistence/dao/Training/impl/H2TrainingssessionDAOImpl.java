package sepm.ss15.grp16.persistence.dao.Training.impl;

import sepm.ss15.grp16.entity.Training.TrainingsSession;
import sepm.ss15.grp16.entity.User;
import sepm.ss15.grp16.persistence.dao.Training.Helper.TrainingsSessionHelperDAO;
import sepm.ss15.grp16.persistence.dao.Training.Helper.impl.H2TrainingssessionHelperDAOImpl;
import sepm.ss15.grp16.persistence.dao.Training.TrainingsSessionDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.List;

/**
 * Author: Lukas
 * Date: 08.05.2015
 */
public class H2TrainingssessionDAOImpl implements TrainingsSessionDAO {

	private TrainingsSessionHelperDAO trainingsSessionHelperDAO;

	private H2TrainingssessionDAOImpl() {
	}

	@Override
	public TrainingsSession create(TrainingsSession dto) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<TrainingsSession> findAll() {
		throw new UnsupportedOperationException();
	}

	@Override
	public TrainingsSession searchByID(int id) throws PersistenceException {
		return trainingsSessionHelperDAO.searchByID(id);
	}

	@Override
	public List<TrainingsSession> searchByPlanID(int ID_plan) throws PersistenceException {
		return trainingsSessionHelperDAO.searchByPlanID(ID_plan);
	}

	@Override
	public List<TrainingsSession> searchByUser(User user) throws PersistenceException {
		return trainingsSessionHelperDAO.searchByUser(user);
	}

	@Override
	public TrainingsSession update(TrainingsSession dto) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(TrainingsSession dto) {
		throw new UnsupportedOperationException();
	}

	public void setTrainingsSessionHelperDAO(H2TrainingssessionHelperDAOImpl trainingsSessionHelperDAO) {
		this.trainingsSessionHelperDAO = trainingsSessionHelperDAO;
	}
}
