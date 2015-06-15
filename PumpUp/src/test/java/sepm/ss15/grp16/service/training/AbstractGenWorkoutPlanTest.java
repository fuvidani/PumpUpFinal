package sepm.ss15.grp16.service.training;

import org.junit.Test;
import sepm.ss15.grp16.entity.exercise.TrainingsCategory;
import sepm.ss15.grp16.entity.training.Gen_WorkoutplanPreferences;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.entity.user.WeightHistory;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exercise.CategoryService;
import sepm.ss15.grp16.service.user.UserService;
import sepm.ss15.grp16.service.user.WeightHistoryService;

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
     *
     * @param service a GeneratedWorkoutplanService
     */
    protected void setService(GeneratedWorkoutplanService service) {
        this.workoutplanService = service;
    }

    /**
     * Service injected by the Framework.
     *
     * @param service a CategoryService
     */
    protected void setService(CategoryService service) {
        this.categoryService = service;
    }

    /**
     * Service injected by the Framework.
     *
     * @param service a UserService
     */
    protected void setService(UserService service) {
        this.userService = service;
    }

    /**
     * Service injected by the Framework.
     *
     * @param service a WeightHistoryService
     */
    protected void setService(WeightHistoryService service) {
        this.weightHistoryService = service;
    }


    /**
     * Test of service.generate(.) with null used as parameter.
     * Should throw a service exception
     *
     * @throws ServiceException
     */
    @Test(expected = ServiceException.class)
    public void callWithNullShouldThrowException() throws ServiceException {
        workoutplanService.generate(null);
    }

    /**
     * Tests the unsupported method CREATE of this service.
     * Should throw an UnsupportedOperationException
     *
     * @throws ServiceException
     */
    @Test(expected = UnsupportedOperationException.class)
    public void callUnsupportedCreateShouldThrowException() throws ServiceException {
        workoutplanService.create(null);
    }

    /**
     * Tests the unsupported method FINDALL of this service.
     * Should throw an UnsupportedOperationException
     *
     * @throws ServiceException
     */
    @Test(expected = UnsupportedOperationException.class)
    public void callUnsupportedFindAllShouldThrowException() throws ServiceException {
        workoutplanService.create(null);
    }

    /**
     * Tests the unsupported method UPDATE of this service.
     * Should throw an UnsupportedOperationException
     *
     * @throws ServiceException
     */
    @Test(expected = UnsupportedOperationException.class)
    public void callUnsupportedUpdateShouldThrowException() throws ServiceException {
        workoutplanService.create(null);
    }

    /**
     * Tests the unsupported method DELETE of this service.
     * Should throw an UnsupportedOperationException
     *
     * @throws ServiceException
     */
    @Test(expected = UnsupportedOperationException.class)
    public void callUnsupportedDeleteShouldThrowException() throws ServiceException {
        workoutplanService.create(null);
    }

    /**
     * Test for service.generate(.) with valid parameters.
     * In all cases, the method must return a valid generated workout routine.
     *
     * @throws ServiceException
     */
    @Test
    public void callWithValidPreferences() throws ServiceException {
        // Dummy user 1 block start
        dummyUser1();
        assert (workoutplanService.generate(dummyPreferencesEndurance()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        assert (workoutplanService.generate(dummyPreferencesStrength()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        assert (workoutplanService.generate(dummyPreferencesBalance()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        assert (workoutplanService.generate(dummyPreferencesFlexibility()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        // Dummy user 1 block end

        // Dummy user 2 block start
        dummyUser2();
        assert (workoutplanService.generate(dummyPreferencesEndurance()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        assert (workoutplanService.generate(dummyPreferencesStrength()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        assert (workoutplanService.generate(dummyPreferencesBalance()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        assert (workoutplanService.generate(dummyPreferencesFlexibility()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        // Dummy user 2 block end

        // Dummy user 3 block start
        dummyUser3();
        assert (workoutplanService.generate(dummyPreferencesEndurance()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesStrength()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesBalance()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesFlexibility()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        // Dummy user 3 block end

        /**************************************************************************************************************************************/

        // Dummy user 4 block start
        dummyUser4();
        assert (workoutplanService.generate(dummyPreferencesEndurance()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        assert (workoutplanService.generate(dummyPreferencesStrength()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        assert (workoutplanService.generate(dummyPreferencesBalance()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        assert (workoutplanService.generate(dummyPreferencesFlexibility()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        // Dummy user 4 block end

        // Dummy user 5 block start
        dummyUser5();
        assert (workoutplanService.generate(dummyPreferencesEndurance()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        assert (workoutplanService.generate(dummyPreferencesStrength()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        assert (workoutplanService.generate(dummyPreferencesBalance()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        assert (workoutplanService.generate(dummyPreferencesFlexibility()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        // Dummy user 5 block end

        // Dummy user 6 block start
        dummyUser6();
        assert (workoutplanService.generate(dummyPreferencesEndurance()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesStrength()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesBalance()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesFlexibility()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        // Dummy user 6 block end

        /**************************************************************************************************************************************/

        // Dummy user 7 block start
        dummyUser7();
        assert (workoutplanService.generate(dummyPreferencesEndurance()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        assert (workoutplanService.generate(dummyPreferencesStrength()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        assert (workoutplanService.generate(dummyPreferencesBalance()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        assert (workoutplanService.generate(dummyPreferencesFlexibility()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        // Dummy user 7 block end

        // Dummy user 8 block start
        dummyUser8();
        assert (workoutplanService.generate(dummyPreferencesEndurance()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesStrength()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesBalance()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesFlexibility()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        // Dummy user 8 block end

        // Dummy user 9 block start
        dummyUser9();
        assert (workoutplanService.generate(dummyPreferencesEndurance()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesStrength()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesBalance()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesFlexibility()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        // Dummy user 9 block end

        /**************************************************************************************************************************************/

        // Dummy user 10 block start
        dummyUser10();
        assert (workoutplanService.generate(dummyPreferencesEndurance()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesStrength()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesBalance()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesFlexibility()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        // Dummy user 10 block end

        // Dummy user 11 block start
        dummyUser11();
        assert (workoutplanService.generate(dummyPreferencesEndurance()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesStrength()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesBalance()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesFlexibility()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        // Dummy user 11 block end

        // Dummy user 12 block start
        dummyUser12();
        assert (workoutplanService.generate(dummyPreferencesEndurance()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesStrength()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesBalance()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesFlexibility()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        // Dummy user 12 block end

    }

    /**
     * Creates a dummy workout preference for the main test.
     *
     * @return a valid workout preference for Endurance
     * @throws ServiceException
     */
    private Gen_WorkoutplanPreferences dummyPreferencesEndurance() throws ServiceException {
        return new Gen_WorkoutplanPreferences(1, new TrainingsCategory(0, "Ausdauer"), new ArrayList<>());
    }

    /**
     * Creates a dummy workout preference for the main test.
     *
     * @return a valid workout preference for Strength
     * @throws ServiceException
     */
    private Gen_WorkoutplanPreferences dummyPreferencesStrength() throws ServiceException {
        return new Gen_WorkoutplanPreferences(1, new TrainingsCategory(1, "Kraft"), new ArrayList<>());
    }

    /**
     * Creates a dummy workout preference for the main test.
     *
     * @return a valid workout preference for Balance
     * @throws ServiceException
     */
    private Gen_WorkoutplanPreferences dummyPreferencesBalance() throws ServiceException {
        return new Gen_WorkoutplanPreferences(1, new TrainingsCategory(2, "Balance"), new ArrayList<>());
    }

    /**
     * Creates a dummy workout preference for the main test.
     *
     * @return a valid workout preference for Flexibility
     * @throws ServiceException
     */
    private Gen_WorkoutplanPreferences dummyPreferencesFlexibility() throws ServiceException {
        return new Gen_WorkoutplanPreferences(1, new TrainingsCategory(3, "Flexibilität"), new ArrayList<>());
    }

    /**
     * Creates a dummy user that is used by the main test.
     * Normal weight user.
     *
     * @throws ServiceException
     */
    private void dummyUser1() throws ServiceException {
        User user = new User(50, "Daniel", true, 20, 182, false);
        userService.create(user);
        userService.setLoggedInUser(user);
        weightHistoryService.create(new WeightHistory(null, user.getUser_id(), 75, new Date(Calendar.DATE)));
    }

    /**
     * Creates a dummy user that is used by the main test.
     * Overweight user.
     *
     * @throws ServiceException
     */
    private void dummyUser2() throws ServiceException {
        User user = new User(51, "Daniel", true, 20, 182, false);
        userService.create(user);
        userService.setLoggedInUser(user);
        weightHistoryService.create(new WeightHistory(null, user.getUser_id(), 90, new Date(Calendar.DATE)));
    }

    /**
     * Creates a dummy user that is used by the main test.
     * Obese user.
     *
     * @throws ServiceException
     */
    private void dummyUser3() throws ServiceException {
        User user = new User(51, "Daniel", true, 20, 182, false);
        userService.create(user);
        userService.setLoggedInUser(user);
        weightHistoryService.create(new WeightHistory(null, user.getUser_id(), 110, new Date(Calendar.DATE)));
    }

    /**
     * ***************************************************************************************************************************************
     * *                                                                                                                                   * *
     * *                                             Dummy user with 30 years.                                                             * *
     * *                                                                                                                                   * *
     * ***************************************************************************************************************************************
     */

    /**
     * Creates a dummy user that is used by the main test.
     * Normal weight user.
     *
     * @throws ServiceException
     */
    private void dummyUser4() throws ServiceException {
        User user = new User(50, "Daniel", true, 30, 182, false);
        userService.create(user);
        userService.setLoggedInUser(user);
        weightHistoryService.create(new WeightHistory(null, user.getUser_id(), 75, new Date(Calendar.DATE)));
    }

    /**
     * Creates a dummy user that is used by the main test.
     * Overweight user.
     *
     * @throws ServiceException
     */
    private void dummyUser5() throws ServiceException {
        User user = new User(51, "Daniel", true, 30, 182, false);
        userService.create(user);
        userService.setLoggedInUser(user);
        weightHistoryService.create(new WeightHistory(null, user.getUser_id(), 90, new Date(Calendar.DATE)));
    }

    /**
     * Creates a dummy user that is used by the main test.
     * Obese user.
     *
     * @throws ServiceException
     */
    private void dummyUser6() throws ServiceException {
        User user = new User(51, "Daniel", true, 30, 182, false);
        userService.create(user);
        userService.setLoggedInUser(user);
        weightHistoryService.create(new WeightHistory(null, user.getUser_id(), 110, new Date(Calendar.DATE)));
    }


    /**
     * ***************************************************************************************************************************************
     * *                                                                                                                                   * *
     * *                                             Dummy user with 45 years.                                                             * *
     * *                                                                                                                                   * *
     * ***************************************************************************************************************************************
     */

    /**
     * Creates a dummy user that is used by the main test.
     * Normal weight user.
     *
     * @throws ServiceException
     */
    private void dummyUser7() throws ServiceException {
        User user = new User(50, "Daniel", true, 45, 182, false);
        userService.create(user);
        userService.setLoggedInUser(user);
        weightHistoryService.create(new WeightHistory(null, user.getUser_id(), 75, new Date(Calendar.DATE)));
    }

    /**
     * Creates a dummy user that is used by the main test.
     * Overweight user.
     *
     * @throws ServiceException
     */
    private void dummyUser8() throws ServiceException {
        User user = new User(51, "Daniel", true, 45, 182, false);
        userService.create(user);
        userService.setLoggedInUser(user);
        weightHistoryService.create(new WeightHistory(null, user.getUser_id(), 90, new Date(Calendar.DATE)));
    }

    /**
     * Creates a dummy user that is used by the main test.
     * Obese user.
     *
     * @throws ServiceException
     */
    private void dummyUser9() throws ServiceException {
        User user = new User(51, "Daniel", true, 45, 182, false);
        userService.create(user);
        userService.setLoggedInUser(user);
        weightHistoryService.create(new WeightHistory(null, user.getUser_id(), 110, new Date(Calendar.DATE)));
    }

    /**
     * ***************************************************************************************************************************************
     * *                                                                                                                                   * *
     * *                                             Dummy user with 55 years.                                                             * *
     * *                                                                                                                                   * *
     * ***************************************************************************************************************************************
     */

    /**
     * Creates a dummy user that is used by the main test.
     * Normal weight user.
     *
     * @throws ServiceException
     */
    private void dummyUser10() throws ServiceException {
        User user = new User(50, "Daniel", true, 55, 182, false);
        userService.create(user);
        userService.setLoggedInUser(user);
        weightHistoryService.create(new WeightHistory(null, user.getUser_id(), 75, new Date(Calendar.DATE)));
    }

    /**
     * Creates a dummy user that is used by the main test.
     * Overweight user.
     *
     * @throws ServiceException
     */
    private void dummyUser11() throws ServiceException {
        User user = new User(51, "Daniel", true, 55, 182, false);
        userService.create(user);
        userService.setLoggedInUser(user);
        weightHistoryService.create(new WeightHistory(null, user.getUser_id(), 90, new Date(Calendar.DATE)));
    }

    /**
     * Creates a dummy user that is used by the main test.
     * Obese user.
     *
     * @throws ServiceException
     */
    private void dummyUser12() throws ServiceException {
        User user = new User(51, "Daniel", true, 55, 182, false);
        userService.create(user);
        userService.setLoggedInUser(user);
        weightHistoryService.create(new WeightHistory(null, user.getUser_id(), 110, new Date(Calendar.DATE)));
    }
}
