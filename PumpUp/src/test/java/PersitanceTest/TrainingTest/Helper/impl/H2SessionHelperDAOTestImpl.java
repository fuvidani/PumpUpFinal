package PersitanceTest.TrainingTest.Helper.impl;

import PersitanceTest.TrainingTest.Helper.AbstractSessionHelperDAOTest;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import sepm.ss15.grp16.persistence.dao.Training.Helper.TrainingsSessionHelperDAO;
import sepm.ss15.grp16.persistence.dao.Training.TrainingsplanDAO;
import sepm.ss15.grp16.persistence.database.DBHandler;
import sepm.ss15.grp16.persistence.exception.DBException;

import java.sql.SQLException;

/**
 * Author: Lukas
 * Date: 13.05.2015
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
@TestExecutionListeners(inheritListeners = false, listeners =
		{DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class H2SessionHelperDAOTestImpl extends AbstractSessionHelperDAOTest{

	@Autowired
	private DBHandler dbConnector;

	@Autowired
	TrainingsplanDAO trainingsplanDAO;

	@Autowired
	TrainingsSessionHelperDAO trainingsSessionHelperDAO;

	@Override
	public TrainingsplanDAO getTrainingsplanDAO() {
		return trainingsplanDAO;
	}

	@Override
	public TrainingsSessionHelperDAO getDAO() {
		return trainingsSessionHelperDAO;
	}

	@Before
	public void setUp() throws DBException, SQLException {
		dbConnector.activateTestMode();
	}

	@After
	public void tearDown() throws DBException, SQLException {
		dbConnector.deactivateTestMode();
	}
}
