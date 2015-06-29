package sepm.ss15.grp16.persistence.dao.training.impl;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import sepm.ss15.grp16.entity.training.TrainingsSession;
import sepm.ss15.grp16.persistence.dao.DAO;
import sepm.ss15.grp16.persistence.dao.training.AbstractTrainingssessionDAOTest;
import sepm.ss15.grp16.persistence.dao.training.TrainingsSessionDAO;
import sepm.ss15.grp16.persistence.dao.training.TrainingsplanDAO;
import sepm.ss15.grp16.persistence.dao.user.UserDAO;

/**
 * Author: Lukas
 * Date: 09.05.2015
 */

@RunWith(SpringJUnit4ClassRunner.class) @ContextConfiguration("classpath:spring-config-test.xml")
@TestExecutionListeners(inheritListeners = false, listeners = {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class H2TrainingssessionDAOTestImpl extends AbstractTrainingssessionDAOTest {

    @Autowired
    private TrainingsSessionDAO trainingsSessionDAO;

    @Autowired
    private TrainingsplanDAO trainingsplanDAO;

    @Autowired
    private UserDAO userDAO;

    @Override
    public TrainingsplanDAO getTrainingsplanDAO() {
        return trainingsplanDAO;
    }

    @Override
    public TrainingsSessionDAO getTrainingsSessionDAO() {
        return trainingsSessionDAO;
    }

    @Override
    public UserDAO getUserDAO() {
        return userDAO;
    }

    @Override
    public DAO<TrainingsSession> getDAO() {
        return trainingsSessionDAO;
    }
}

