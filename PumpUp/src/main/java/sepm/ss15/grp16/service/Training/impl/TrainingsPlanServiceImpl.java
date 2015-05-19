package sepm.ss15.grp16.service.training.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.User;
import sepm.ss15.grp16.entity.training.TrainingsSession;
import sepm.ss15.grp16.entity.training.Trainingsplan;
import sepm.ss15.grp16.entity.training.helper.ExerciseSet;
import sepm.ss15.grp16.persistence.dao.training.TrainingsSessionDAO;
import sepm.ss15.grp16.persistence.dao.training.TrainingsplanDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;
import sepm.ss15.grp16.service.ExerciseService;
import sepm.ss15.grp16.service.training.TrainingsplanService;
import sepm.ss15.grp16.service.UserService;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Lukas
 * Date: 12.05.2015
 */
public class TrainingsPlanServiceImpl implements TrainingsplanService {
	private static final Logger LOGGER = LogManager.getLogger(TrainingsPlanServiceImpl.class);

	private final TrainingsplanDAO trainingsplanDAO;
	private final TrainingsSessionDAO trainingsSessionDAO;
	private final ExerciseService exerciseService;
	private final UserService userService;

	TrainingsPlanServiceImpl(TrainingsplanDAO trainingsplanDAO, TrainingsSessionDAO trainingsSessionDAO,
	                         ExerciseService exerciseService, UserService userService) {
		this.trainingsplanDAO = trainingsplanDAO;
		this.trainingsSessionDAO = trainingsSessionDAO;
		this.exerciseService = exerciseService;
		this.userService = userService;
	}

	@Override
	public Trainingsplan create(Trainingsplan plan) throws ServiceException {
		validate_withoutID(plan);
		try {
			LOGGER.info("Service try to create Trainingsplan " + plan);
			Trainingsplan trainingsplan = trainingsplanDAO.create(plan);
			LOGGER.info("Service creation successful");
			return trainingsplan;
		} catch (PersistenceException e) {
			LOGGER.error("" + e);
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Trainingsplan> findAll() throws ServiceException {
		try {
			LOGGER.info("Service try to find all");
			List<Trainingsplan> list = trainingsplanDAO.findAll();
			List<Trainingsplan> returnlist = new ArrayList<>();

			for (Trainingsplan plan : list) {
// TODO				if (plan.getUser() == null || plan.getUser().getId().equals(userService.getLoggedInUser().getId())) {
				if (plan.getUser() == null) {
					returnlist.add(plan);
				}
			}
			LOGGER.info("Service find all successful");
			return returnlist;
		} catch (PersistenceException e) {
			LOGGER.error("" + e);
			throw new ServiceException(e);
		}
	}

	@Override
	public Trainingsplan update(Trainingsplan plan) throws ServiceException {
		validate(plan);
		try {
			LOGGER.info("Service try to update Trainingsplan " + plan);
			Trainingsplan trainingsplan = trainingsplanDAO.update(plan);
			LOGGER.info("Service update successful");
			return trainingsplan;
		} catch (PersistenceException e) {
			LOGGER.error("" + e);
			throw new ServiceException(e);
		}
	}

	@Override
	public void delete(Trainingsplan plan) throws ServiceException {
		validate(plan);
		try {
			LOGGER.info("Service try to delete Trainingsplan " + plan);
			trainingsplanDAO.delete(plan);
			LOGGER.info("Service delete successful");
		} catch (PersistenceException e) {
			LOGGER.error("" + e);
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Trainingsplan> find(Trainingsplan filter) throws ServiceException {
		try {
			LOGGER.info("Service try to search with filter " + filter);
			List<Trainingsplan> list = trainingsplanDAO.find(filter);
			List<Trainingsplan> returnlist = new ArrayList<>();

			for (Trainingsplan plan : list) {
// TODO				if (plan.getUser() == null || plan.getUser().getId().equals(userService.getLoggedInUser().getId())) {
				if (plan.getUser() == null) {
					returnlist.add(plan);
				}
			}
			LOGGER.info("Service search successful");
			return returnlist;
		} catch (PersistenceException e) {
			LOGGER.error("" + e);
			throw new ServiceException(e);
		}
	}

	@Override
	public Trainingsplan getPlanBySession(TrainingsSession session) throws ServiceException {
		validate(session);
		try {
			LOGGER.info("Service try to find corresponding Trainingsplan to Session " + session);
			Trainingsplan plan = trainingsplanDAO.getPlanBySession(session);
			LOGGER.info("Service search successful");
			return plan;
		} catch (PersistenceException e) {
			LOGGER.error("" + e);
			throw new ServiceException(e);
		}
	}

	@Override
	public Trainingsplan getPlanBySet(ExerciseSet set) throws ServiceException {
		validate(set);
		try {
			LOGGER.info("Service try to find corresponding Trainingsplan to Ses " + set);
			Trainingsplan plan = trainingsplanDAO.getPlanBySet(set);
			LOGGER.info("Service search successful");
			return plan;
		} catch (PersistenceException e) {
			LOGGER.error("" + e);
			throw new ServiceException(e);
		}
	}

	@Override
	public List<TrainingsSession> searchByUser(User user) throws ServiceException {
		try {
			LOGGER.info("Service try to find all sessions corresponding to user " + user);
			List<TrainingsSession> list = trainingsSessionDAO.searchByUser(user);
			LOGGER.info("Service search successful");
			return list;
		} catch (PersistenceException e) {
			LOGGER.error("" + e);
			throw new ServiceException(e);
		}
	}

	@Override
	public List<TrainingsSession> searchByPlanID(int ID_plan) throws ServiceException {
		try {
			LOGGER.info("Service try to find all sessions corresponding to ID_Plan " + ID_plan);
			List<TrainingsSession> list = trainingsSessionDAO.searchByPlanID(ID_plan);
			LOGGER.info("Service search successful");
			return list;
		} catch (PersistenceException e) {
			LOGGER.error("" + e);
			throw new ServiceException(e);
		}
	}

	@Override
	public void validate(Trainingsplan plan) throws ValidationException {
		if (plan == null) {
			LOGGER.error("error validating " + plan);
			throw new ValidationException("Trainingsplan darf nicht null sein!");
		}

		validate_withoutID(plan);

		if (plan.getId() == null) {
			LOGGER.error("error validating " + plan);
			throw new ValidationException("Trainingsplan-ID darf nicht null sein!");
		}
		if (plan.getTrainingsSessions() != null) {

			List<TrainingsSession> trainingsSessions = plan.getTrainingsSessions();
			for (TrainingsSession session : trainingsSessions) {
				validate(session);
			}
		}
		if (plan.getUser() != null) {
			userService.validate(plan.getUser());
		}
	}

	private void validate_withoutID(Trainingsplan plan) throws ValidationException {
		if (plan == null) {
			LOGGER.error("error validating " + plan);
			throw new ValidationException("Trainingsplan darf nicht null sein!");
		}

		if (plan.getName() == null) {
			LOGGER.error("error validating " + plan);
			throw new ValidationException("Trainingsplan Name darf nicht leer sein!");
		}
		if (plan.getIsDeleted() == null) {
			LOGGER.error("error validating " + plan);
			throw new ValidationException("Trainingsplan isDeleted darf nicht leer sein!");

		}
		if (plan.getTrainingsSessions() != null) {

			List<TrainingsSession> trainingsSessions = plan.getTrainingsSessions();
			for (TrainingsSession session : trainingsSessions) {
				validate_withoutID(session);
			}
		}
	}

	private void validate(TrainingsSession session) throws ValidationException {
		if (session == null) {
			LOGGER.error("error validating " + session);
			throw new ValidationException("TrainingsSession darf nicht null sein!");
		}

		validate_withoutID(session);

		if (session.getId() == null) {
			LOGGER.error("error validating " + session);
			throw new ValidationException("TrainingsSession-ID darf nicht leer sein!");

		}
		if (session.getExerciseSets() != null) {

			List<ExerciseSet> sets = session.getExerciseSets();
			for (ExerciseSet set : sets) {
				validate(set);
			}
		}

		if (session.getUser() != null) {
			userService.validate(session.getUser());
		}
	}

	private void validate_withoutID(TrainingsSession session) throws ValidationException {
		if (session == null) {
			LOGGER.error("error validating " + session);
			throw new ValidationException("TrainingsSession darf nicht leer sein!");
		}
		if (session.getName() == null) {
			LOGGER.error("error validating " + session);
			throw new ValidationException("TrainingsSession Name darf nicht leer sein!");
		}
		if (session.getIsDeleted() == null) {
			LOGGER.error("error validating " + session);
			throw new ValidationException("TrainingsSession isDeleted darf nicht leer sein!");

		}
		if (session.getExerciseSets() != null) {

			List<ExerciseSet> sets = session.getExerciseSets();
			for (int i = 0; i < sets.size(); i++) {
				validate_withoutID(sets.get(i));
				if (sets.get(i).getOrder_nr() != (i + 1)) {
					LOGGER.error("error validating " + session);
					throw new ValidationException("Ordnungsnummern falsch!");
				}
			}
		}
	}

	private void validate(ExerciseSet set) throws ValidationException {
		if (set == null) {
			LOGGER.error("error validating " + set);
			throw new ValidationException("ExerciseSet darf nicht leer sein!");
		}

		validate_withoutID(set);

		if (set.getId() == null) {
			LOGGER.error("error validating " + set);
			throw new ValidationException("ExerciseSet-ID darf nicht leer sein!");

		}
		if (set.getExercise() != null) {
			exerciseService.validate(set.getExercise());
		}

		if (set.getUser() != null) {
			userService.validate(set.getUser());
		}
	}

	private void validate_withoutID(ExerciseSet set) throws ValidationException {
		if (set == null) {
			LOGGER.error("error validating " + set);
			throw new ValidationException("ExerciseSet darf nicht leer sein!");
		}

		if (set.getRepeat() == null) {
			LOGGER.error("error validating " + set);
			throw new ValidationException("ExerciseSet Wiederholung darf nicht leer sein!");
		}
		if (set.getOrder_nr() == null || set.getIsDeleted() == null) {
			LOGGER.error("error validating " + set);
			throw new ValidationException("ExerciseSet Reihenfolge darf nicht leer sein!");
		}

	}
}
