package sepm.ss15.grp16.service.Training.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.Training.Helper.ExerciseSet;
import sepm.ss15.grp16.entity.Training.TrainingsSession;
import sepm.ss15.grp16.entity.Training.Trainingsplan;
import sepm.ss15.grp16.entity.User;
import sepm.ss15.grp16.persistence.dao.Training.TrainingsSessionDAO;
import sepm.ss15.grp16.persistence.dao.Training.TrainingsplanDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.Training.TrainingsplanService;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;

import java.util.List;

/**
 * Author: Lukas
 * Date: 12.05.2015
 */
public class TrainingsPlanServiceImpl implements TrainingsplanService {
	private static final Logger LOGGER = LogManager.getLogger(TrainingsPlanServiceImpl.class);

	private final TrainingsplanDAO trainingsplanDAO;
	private final TrainingsSessionDAO trainingsSessionDAO;

	TrainingsPlanServiceImpl(TrainingsplanDAO trainingsplanDAO, TrainingsSessionDAO trainingsSessionDAO) {
		this.trainingsplanDAO = trainingsplanDAO;
		this.trainingsSessionDAO = trainingsSessionDAO;
	}

	@Override
	public Trainingsplan create(Trainingsplan dto) throws ServiceException {
		validate(dto);
		try {
			LOGGER.info("Service create Trainingsplan");
			return trainingsplanDAO.create(dto);
		} catch (PersistenceException e) {
			LOGGER.error("" + e);
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Trainingsplan> findAll() throws ServiceException {
		try {
			LOGGER.info("Service create Trainingsplan");
			return trainingsplanDAO.findAll();
		} catch (PersistenceException e) {
			LOGGER.error("" + e);
			throw new ServiceException(e);
		}
	}

	@Override
	public Trainingsplan update(Trainingsplan dto) throws ServiceException {
		validate(dto);
		try {
			LOGGER.info("Service create Trainingsplan");
			return trainingsplanDAO.update(dto);
		} catch (PersistenceException e) {
			LOGGER.error("" + e);
			throw new ServiceException(e);
		}
	}

	@Override
	public void delete(Trainingsplan dto) throws ServiceException {
		validate(dto);
		try {
			LOGGER.info("Service create Trainingsplan");
			trainingsplanDAO.delete(dto);
		} catch (PersistenceException e) {
			LOGGER.error("" + e);
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Trainingsplan> find(Trainingsplan dto) throws ServiceException {
		try {
			LOGGER.info("Service create Trainingsplan");
			return trainingsplanDAO.find(dto);
		} catch (PersistenceException e) {
			LOGGER.error("" + e);
			throw new ServiceException(e);
		}
	}

	@Override
	public Trainingsplan getPlanBySession(TrainingsSession session) throws ServiceException {
		validate(session);
		try {
			LOGGER.info("Service create Trainingsplan");
			return trainingsplanDAO.getPlanBySession(session);
		} catch (PersistenceException e) {
			LOGGER.error("" + e);
			throw new ServiceException(e);
		}
	}

	@Override
	public Trainingsplan getPlanBySet(ExerciseSet set) throws ServiceException {
		validate(set);
		try {
			LOGGER.info("Service create Trainingsplan");
			return trainingsplanDAO.getPlanBySet(set);
		} catch (PersistenceException e) {
			LOGGER.error("" + e);
			throw new ServiceException(e);
		}
	}

	@Override
	public List<TrainingsSession> searchByUser(User user) throws ServiceException {
		try {
			LOGGER.info("Service create Trainingsplan");
			return trainingsSessionDAO.searchByUser(user);
		} catch (PersistenceException e) {
			LOGGER.error("" + e);
			throw new ServiceException(e);
		}
	}

	@Override
	public List<TrainingsSession> searchByPlanID(int ID_plan) throws ServiceException {
		try {
			LOGGER.info("Service create Trainingsplan");
			return trainingsSessionDAO.searchByPlanID(ID_plan);
		} catch (PersistenceException e) {
			LOGGER.error("" + e);
			throw new ServiceException(e);
		}
	}

	@Override
	public void validate(Trainingsplan dto) throws ValidationException {

	}

	private void validate(TrainingsSession session) throws ValidationException {

	}

	private void validate(ExerciseSet set) {

	}
}
