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
import sepm.ss15.grp16.persistence.database.DBHandler;
import sepm.ss15.grp16.persistence.exception.DBException;
import sepm.ss15.grp16.service.exercise.CategoryService;
import sepm.ss15.grp16.service.exercise.ExerciseService;
import sepm.ss15.grp16.service.training.AbstractGenWorkoutPlanTest;
import sepm.ss15.grp16.service.training.GeneratedWorkoutplanService;
import sepm.ss15.grp16.service.user.UserService;
import sepm.ss15.grp16.service.user.WeightHistoryService;

import java.sql.SQLException;

/**
 * Created by Daniel Fuevesi on 19.05.15.
 * Runs the corresponding tests.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config-test.xml")
@TestExecutionListeners(inheritListeners = false, listeners =
        {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class GenWorkoutPlanTest extends AbstractGenWorkoutPlanTest {

    @Autowired
    public void setWorkoutplanService(GeneratedWorkoutplanService workoutplanService) {
        this.workoutplanService = workoutplanService;
    }

    @Autowired
    public void setMockedCategoryService(CategoryService mockedCategoryService) {
        this.mockedCategoryService = mockedCategoryService;
    }

    @Autowired
    public void setMockedUserService(UserService mockedUserService) {
        this.mockedUserService = mockedUserService;
    }

    @Autowired
    public void setMockedWeightHistoryService(WeightHistoryService mockedWeightHistoryService) {
        this.mockedWeightHistoryService = mockedWeightHistoryService;
    }

    @Autowired
    public void setMockedExerciseService(ExerciseService mockedExerciseService){
        this.mockedExerciseService = mockedExerciseService;
    }

    /**
     * Sets upt the testing environment.
     *
     * @throws DBException
     * @throws SQLException
     */
    @Before
    public void setUp() throws DBException, SQLException {
        Mockito.reset(mockedCategoryService);
        Mockito.reset(mockedUserService);
        Mockito.reset(mockedWeightHistoryService);
        Mockito.reset(mockedExerciseService);
    }

    /**
     * This method is executed after each test.
     * Runs a rollback on the data source and closes it.
     *
     * @throws DBException
     * @throws SQLException
     */
    @After
    public void tearDown() throws DBException, SQLException {
    }


}
