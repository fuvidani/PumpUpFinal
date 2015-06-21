package sepm.ss15.grp16.service.training.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import sepm.ss15.grp16.entity.training.Trainingsplan;
import sepm.ss15.grp16.persistence.dao.DAO;
import sepm.ss15.grp16.persistence.dao.training.TrainingsplanDAO;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.exercise.ExerciseService;
import sepm.ss15.grp16.service.training.AbstractTrainingsServiceTest;
import sepm.ss15.grp16.service.training.TrainingsplanService;
import sepm.ss15.grp16.service.user.UserService;

/**
 * Author: Lukas
 * Date: 13.05.2015
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config-test.xml")
@TestExecutionListeners(inheritListeners = false, listeners =
        {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class TrainingsServiceTestImpl extends AbstractTrainingsServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    ExerciseService exerciseService;
    @Autowired
    TrainingsplanService trainingsplanService;
    @Autowired
    TrainingsplanDAO mockedTrainingsplanDAO;

    @Before
    public void setUp() {
        Mockito.reset(mockedTrainingsplanDAO);
    }

    @Override
    public Service<Trainingsplan> getService() {
        return trainingsplanService;
    }

    @Override
    public DAO<Trainingsplan> getMockedDAO() {
        return mockedTrainingsplanDAO;
    }
}
