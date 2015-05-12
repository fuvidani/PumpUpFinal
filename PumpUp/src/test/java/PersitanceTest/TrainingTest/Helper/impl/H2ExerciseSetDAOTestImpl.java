package PersitanceTest.TrainingTest.Helper.impl;

import PersitanceTest.TrainingTest.Helper.AbstractExerciseSetDAOTest;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import sepm.ss15.grp16.entity.Training.Helper.ExerciseSet;
import sepm.ss15.grp16.persistence.dao.DAO;
import sepm.ss15.grp16.persistence.dao.ExerciseDAO;
import sepm.ss15.grp16.persistence.dao.Training.Helper.ExerciseSetHelperDAO;
import sepm.ss15.grp16.persistence.dao.Training.TrainingsSessionDAO;
import sepm.ss15.grp16.persistence.dao.Training.TrainingsplanDAO;
import sepm.ss15.grp16.persistence.database.DBHandler;
import sepm.ss15.grp16.persistence.exception.DBException;

import java.sql.SQLException;

/**
 * Created by lukas on 11.05.15.
 */

public class H2ExerciseSetDAOTestImpl {

	private DBHandler dbConnector;

	private ExerciseDAO exerciseDAO;

	private ExerciseSetHelperDAO exerciseSetDAO;

	private TrainingsplanDAO trainingsplanDAO;

	private TrainingsSessionDAO trainingsSessionDAO;

	public ExerciseDAO getExerciseDAO() {
		return exerciseDAO;
	}

	public TrainingsplanDAO getTrainingsplanDAO() {
		return trainingsplanDAO;
	}

	public TrainingsSessionDAO getTrainingsSessionDAO() {
		return trainingsSessionDAO;
	}

	public void setUp() throws DBException, SQLException {
		dbConnector.activateTestMode();
	}

	public void tearDown() throws DBException, SQLException {
		dbConnector.deactivateTestMode();
	}
}
