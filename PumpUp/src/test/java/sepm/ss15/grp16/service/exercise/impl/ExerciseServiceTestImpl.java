package sepm.ss15.grp16.service.exercise.impl;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import sepm.ss15.grp16.entity.exercise.Exercise;
import sepm.ss15.grp16.persistence.dao.DAO;
import sepm.ss15.grp16.persistence.dao.exercise.ExerciseDAO;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.exercise.AbstractExerciseServiceTest;
import sepm.ss15.grp16.service.exercise.ExerciseService;

/**
 * Created by lukas on 17.05.2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config-test.xml")
@TestExecutionListeners(inheritListeners = false, listeners =
        {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class ExerciseServiceTestImpl extends AbstractExerciseServiceTest {

    @Autowired
    ExerciseDAO mockedExerciseDAO;
    @Autowired
    ExerciseService exerciseService;

    @Before
    public void setUp() throws Exception {
        Mockito.reset(mockedExerciseDAO);
    }

    @Override
    public Service<Exercise> getService() {
        return exerciseService;
    }

    @Override
    public DAO<Exercise> getMockedDAO() {
        return mockedExerciseDAO;
    }
}
