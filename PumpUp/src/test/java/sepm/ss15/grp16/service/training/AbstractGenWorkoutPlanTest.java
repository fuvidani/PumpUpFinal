package sepm.ss15.grp16.service.training;

import org.junit.Test;
import sepm.ss15.grp16.entity.exercise.TrainingsCategory;
import sepm.ss15.grp16.entity.training.Gen_WorkoutplanPreferences;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.entity.user.WeightHistory;
import sepm.ss15.grp16.service.exercise.CategoryService;
import sepm.ss15.grp16.service.user.UserService;
import sepm.ss15.grp16.service.user.WeightHistoryService;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.training.GeneratedWorkoutplanService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Daniel Fuevesi on 19.05.15.
 * Abstract class that defines the tests for the functionality supported by GeneratedWorkoutPlanService.
 */
public abstract class AbstractGenWorkoutPlanTest {


    protected GeneratedWorkoutplanService workoutplanService;
    protected CategoryService categoryService;
    protected UserService userService;
    protected WeightHistoryService weightHistoryService;

    /**
     * Service injected by the Framework.
     * @param service a GeneratedWorkoutplanService
     */
    protected void setService(GeneratedWorkoutplanService service) {
        this.workoutplanService = service;
    }

    /**
     * Service injected by the Framework.
     * @param service a CategoryService
     */
    protected void setService(CategoryService service) {
        this.categoryService = service;
    }

    /**
     * Service injected by the Framework.
     * @param service a UserService
     */
    protected void setService(UserService service) {
        this.userService = service;
    }

    /**
     * Service injected by the Framework.
     * @param service a WeightHistoryService
     */
    protected void setService(WeightHistoryService service) {
        this.weightHistoryService = service;
    }


    /**
     * Test of service.generate(.) with null used as parameter.
     * Should throw a service exception
     * @throws ServiceException
     */
    @Test(expected = ServiceException.class)
    public void callWithNullShouldThrowException() throws ServiceException {
        workoutplanService.generate(null);
    }


    /**
     * Test for service.generate(.) with valid parameters.
     * In all cases, the method must return a valid generated workout routine.
     * @throws ServiceException
     */
    @Test
    public void callWithValidPreferences() throws ServiceException {
        dummyUser();
        workoutplanService.generate(dummyPreferencesEndurance());
        workoutplanService.generate(dummyPreferencesStrength());
        workoutplanService.generate(dummyPreferencesBalance());
        workoutplanService.generate(dummyPreferencesFlexibility());
    }

    /**
     * Creates a dummy workout preference for the main test.
     * @return a valid workout preference for Endurance
     * @throws ServiceException
     */
    private Gen_WorkoutplanPreferences dummyPreferencesEndurance() throws ServiceException {
        return new Gen_WorkoutplanPreferences(1, new TrainingsCategory(0, "Ausdauer"), new ArrayList<>());
    }

    /**
     * Creates a dummy workout preference for the main test.
     * @return a valid workout preference for Strength
     * @throws ServiceException
     */
    private Gen_WorkoutplanPreferences dummyPreferencesStrength() throws ServiceException {
        return new Gen_WorkoutplanPreferences(1, new TrainingsCategory(1, "Kraft"), new ArrayList<>());
    }

    /**
     * Creates a dummy workout preference for the main test.
     * @return a valid workout preference for Balance
     * @throws ServiceException
     */
    private Gen_WorkoutplanPreferences dummyPreferencesBalance() throws ServiceException {
        return new Gen_WorkoutplanPreferences(1, new TrainingsCategory(2, "Balance"), new ArrayList<>());
    }

    /**
     * Creates a dummy workout preference for the main test.
     * @return a valid workout preference for Flexibility
     * @throws ServiceException
     */
    private Gen_WorkoutplanPreferences dummyPreferencesFlexibility() throws ServiceException {
        return new Gen_WorkoutplanPreferences(1, new TrainingsCategory(3, "Flexibilität"), new ArrayList<>());
    }

    /**
     * Creates a dummy user that is used by the main test.
     * @throws ServiceException
     */
    private void dummyUser() throws ServiceException {
        User user = new User(50, "Daniel", true, 20, 182, false);
        userService.create(user);
        userService.setLoggedInUser(user);
        weightHistoryService.create(new WeightHistory(null, user.getUser_id(), 75, new Date(Calendar.DATE)));
    }


}
