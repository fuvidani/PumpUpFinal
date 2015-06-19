package sepm.ss15.grp16.service.training;

import org.junit.Test;
import org.mockito.Mockito;
import sepm.ss15.grp16.entity.exercise.AbsractCategory;
import sepm.ss15.grp16.entity.exercise.Exercise;
import sepm.ss15.grp16.entity.exercise.TrainingsCategory;
import sepm.ss15.grp16.entity.training.Gen_WorkoutplanPreferences;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.entity.user.WeightHistory;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exercise.CategoryService;
import sepm.ss15.grp16.service.exercise.ExerciseService;
import sepm.ss15.grp16.service.user.UserService;
import sepm.ss15.grp16.service.user.WeightHistoryService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import static org.mockito.Mockito.when;

/**
 * Created by Daniel Fuevesi on 19.05.15.
 * Abstract class that defines the tests for the functionality supported by GeneratedWorkoutPlanService.
 */
public abstract class AbstractGenWorkoutPlanTest {


    protected GeneratedWorkoutplanService workoutplanService;
    protected CategoryService mockedCategoryService;
    protected UserService mockedUserService;
    protected WeightHistoryService mockedWeightHistoryService;
    protected ExerciseService mockedExerciseService;

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
        ArrayList<Exercise> exercises = new ArrayList<>();
        exercises.add(new Exercise(1,"Armrotation", "Descr.", 0.3, "armup.mp4", new LinkedList<String>(),false,new User(50, "Daniel", true, 20, 182, false), new LinkedList<AbsractCategory>()));
        exercises.add(new Exercise(6,"Auf der Stelle laufen", "Descr.", 0.3, "armup.mp4", new LinkedList<String>(),false,new User(50, "Daniel", true, 20, 182, false), new LinkedList<AbsractCategory>()));
        exercises.add(new Exercise(11,"Boxen", "Descr.", 0.3, "armup.mp4", new LinkedList<String>(),false,new User(50, "Daniel", true, 20, 182, false), new LinkedList<AbsractCategory>()));
        exercises.add(new Exercise(28,"Armrotation", "Descr.", 0.2, "armup.mp4", new LinkedList<String>(),false,new User(50, "Daniel", true, 20, 182, false), new LinkedList<AbsractCategory>()));
        exercises.add(new Exercise(30,"Armrotation", "Descr.", 1.0, "armup.mp4", new LinkedList<String>(),false,new User(50, "Daniel", true, 20, 182, false), new LinkedList<AbsractCategory>()));
        exercises.add(new Exercise(34,"Armrotation", "Descr.", 0.2, "armup.mp4", new LinkedList<String>(),false,new User(50, "Daniel", true, 20, 182, false), new LinkedList<AbsractCategory>()));
        exercises.add(new Exercise(35,"Armrotation", "Descr.", 1.5, "armup.mp4", new LinkedList<String>(),false,new User(50, "Daniel", true, 20, 182, false), new LinkedList<AbsractCategory>()));
        exercises.add(new Exercise(36,"Armrotation", "Descr.", 0.5, "armup.mp4", new LinkedList<String>(),false,new User(50, "Daniel", true, 20, 182, false), new LinkedList<AbsractCategory>()));
        exercises.add(new Exercise(37,"Armrotation", "Descr.", 0.3, "armup.mp4", new LinkedList<String>(),false,new User(50, "Daniel", true, 20, 182, false), new LinkedList<AbsractCategory>()));

        /**
         * ***************************************************************************************************************************************
         * *                                                                                                                                   * *
         * *                                             Dummy user with 20 years.                                                             * *
         * *                                                                                                                                   * *
         * ***************************************************************************************************************************************
         */

        // Dummy user 1 block start
        when(mockedUserService.getLoggedInUser()).thenReturn(new User(50, "Daniel", true, 20, 182, false));
        when(mockedWeightHistoryService.getActualWeight(50)).thenReturn(new WeightHistory(null, 50, 75, new Date(Calendar.DATE)));

        when(mockedExerciseService.getWithoutCategory(new ArrayList<>())).thenReturn(exercises);
        when(mockedExerciseService.getAllEnduranceExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllStrengthExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllBalanceExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllFlexibilityExercises()).thenReturn(exercises);

        assert (workoutplanService.generate(dummyPreferencesEndurance()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        assert (workoutplanService.generate(dummyPreferencesStrength()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        assert (workoutplanService.generate(dummyPreferencesBalance()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        assert (workoutplanService.generate(dummyPreferencesFlexibility()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        reSet();
        // Dummy user 1 block end

        // Dummy user 2 block start
        when(mockedUserService.getLoggedInUser()).thenReturn(new User(51, "Daniel", true, 20, 182, false));
        when(mockedWeightHistoryService.getActualWeight(51)).thenReturn(new WeightHistory(null, 51, 90, new Date(Calendar.DATE)));

        when(mockedExerciseService.getWithoutCategory(new ArrayList<>())).thenReturn(exercises);
        when(mockedExerciseService.getAllEnduranceExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllStrengthExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllBalanceExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllFlexibilityExercises()).thenReturn(exercises);

        assert (workoutplanService.generate(dummyPreferencesEndurance()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        assert (workoutplanService.generate(dummyPreferencesStrength()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        assert (workoutplanService.generate(dummyPreferencesBalance()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        assert (workoutplanService.generate(dummyPreferencesFlexibility()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        reSet();
        // Dummy user 2 block end

        // Dummy user 3 block start
        when(mockedUserService.getLoggedInUser()).thenReturn(new User(51, "Daniel", true, 20, 182, false));
        when(mockedWeightHistoryService.getActualWeight(51)).thenReturn(new WeightHistory(null, 51, 110, new Date(Calendar.DATE)));

        when(mockedExerciseService.getWithoutCategory(new ArrayList<>())).thenReturn(exercises);
        when(mockedExerciseService.getAllEnduranceExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllStrengthExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllBalanceExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllFlexibilityExercises()).thenReturn(exercises);
        assert (workoutplanService.generate(dummyPreferencesEndurance()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesStrength()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesBalance()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesFlexibility()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        reSet();
        // Dummy user 3 block end

        /**
         * ***************************************************************************************************************************************
         * *                                                                                                                                   * *
         * *                                             Dummy user with 30 years.                                                             * *
         * *                                                                                                                                   * *
         * ***************************************************************************************************************************************
         */


        // Dummy user 4 block start
        when(mockedUserService.getLoggedInUser()).thenReturn(new User(51, "Daniel", true, 30, 182, false));
        when(mockedWeightHistoryService.getActualWeight(51)).thenReturn(new WeightHistory(null, 50, 75, new Date(Calendar.DATE)));

        when(mockedExerciseService.getWithoutCategory(new ArrayList<>())).thenReturn(exercises);
        when(mockedExerciseService.getAllEnduranceExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllStrengthExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllBalanceExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllFlexibilityExercises()).thenReturn(exercises);
        assert (workoutplanService.generate(dummyPreferencesEndurance()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        assert (workoutplanService.generate(dummyPreferencesStrength()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        assert (workoutplanService.generate(dummyPreferencesBalance()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        assert (workoutplanService.generate(dummyPreferencesFlexibility()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        reSet();
        // Dummy user 4 block end

        // Dummy user 5 block start
        when(mockedUserService.getLoggedInUser()).thenReturn(new User(51, "Daniel", true, 30, 182, false));
        when(mockedWeightHistoryService.getActualWeight(51)).thenReturn(new WeightHistory(null, 51, 90, new Date(Calendar.DATE)));

        when(mockedExerciseService.getWithoutCategory(new ArrayList<>())).thenReturn(exercises);
        when(mockedExerciseService.getAllEnduranceExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllStrengthExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllBalanceExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllFlexibilityExercises()).thenReturn(exercises);
        assert (workoutplanService.generate(dummyPreferencesEndurance()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        assert (workoutplanService.generate(dummyPreferencesStrength()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        assert (workoutplanService.generate(dummyPreferencesBalance()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        assert (workoutplanService.generate(dummyPreferencesFlexibility()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        reSet();
        // Dummy user 5 block end

        // Dummy user 6 block start
        when(mockedUserService.getLoggedInUser()).thenReturn(new User(51, "Daniel", true, 30, 182, false));
        when(mockedWeightHistoryService.getActualWeight(51)).thenReturn(new WeightHistory(null, 51, 110, new Date(Calendar.DATE)));

        when(mockedExerciseService.getWithoutCategory(new ArrayList<>())).thenReturn(exercises);
        when(mockedExerciseService.getAllEnduranceExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllStrengthExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllBalanceExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllFlexibilityExercises()).thenReturn(exercises);
        assert (workoutplanService.generate(dummyPreferencesEndurance()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesStrength()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesBalance()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesFlexibility()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        reSet();
        // Dummy user 6 block end

        /**
         * ***************************************************************************************************************************************
         * *                                                                                                                                   * *
         * *                                             Dummy user with 45 years.                                                             * *
         * *                                                                                                                                   * *
         * ***************************************************************************************************************************************
         */


        // Dummy user 7 block start
        when(mockedUserService.getLoggedInUser()).thenReturn(new User(51, "Daniel", true, 45, 182, false));
        when(mockedWeightHistoryService.getActualWeight(51)).thenReturn(new WeightHistory(null, 51, 75, new Date(Calendar.DATE)));

        when(mockedExerciseService.getWithoutCategory(new ArrayList<>())).thenReturn(exercises);
        when(mockedExerciseService.getAllEnduranceExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllStrengthExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllBalanceExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllFlexibilityExercises()).thenReturn(exercises);
        assert (workoutplanService.generate(dummyPreferencesEndurance()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        assert (workoutplanService.generate(dummyPreferencesStrength()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        assert (workoutplanService.generate(dummyPreferencesBalance()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        assert (workoutplanService.generate(dummyPreferencesFlexibility()).getTrainingsSessions().get(0).getExerciseSets().size() == 4);
        reSet();
        // Dummy user 7 block end

        // Dummy user 8 block start
        when(mockedUserService.getLoggedInUser()).thenReturn(new User(51, "Daniel", true, 45, 182, false));
        when(mockedWeightHistoryService.getActualWeight(51)).thenReturn(new WeightHistory(null, 51, 90, new Date(Calendar.DATE)));

        when(mockedExerciseService.getWithoutCategory(new ArrayList<>())).thenReturn(exercises);
        when(mockedExerciseService.getAllEnduranceExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllStrengthExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllBalanceExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllFlexibilityExercises()).thenReturn(exercises);
        assert (workoutplanService.generate(dummyPreferencesEndurance()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesStrength()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesBalance()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesFlexibility()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        reSet();
        // Dummy user 8 block end

        // Dummy user 9 block start
        when(mockedUserService.getLoggedInUser()).thenReturn(new User(51, "Daniel", true, 45, 182, false));
        when(mockedWeightHistoryService.getActualWeight(51)).thenReturn(new WeightHistory(null, 51, 110, new Date(Calendar.DATE)));

        when(mockedExerciseService.getWithoutCategory(new ArrayList<>())).thenReturn(exercises);
        when(mockedExerciseService.getAllEnduranceExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllStrengthExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllBalanceExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllFlexibilityExercises()).thenReturn(exercises);
        assert (workoutplanService.generate(dummyPreferencesEndurance()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesStrength()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesBalance()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesFlexibility()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        reSet();
        // Dummy user 9 block end

        /**
         * ***************************************************************************************************************************************
         * *                                                                                                                                   * *
         * *                                             Dummy user with 55 years.                                                             * *
         * *                                                                                                                                   * *
         * ***************************************************************************************************************************************
         */


        // Dummy user 10 block start
        when(mockedUserService.getLoggedInUser()).thenReturn(new User(51, "Daniel", true, 55, 182, false));
        when(mockedWeightHistoryService.getActualWeight(51)).thenReturn(new WeightHistory(null, 51, 75, new Date(Calendar.DATE)));

        when(mockedExerciseService.getWithoutCategory(new ArrayList<>())).thenReturn(exercises);
        when(mockedExerciseService.getAllEnduranceExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllStrengthExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllBalanceExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllFlexibilityExercises()).thenReturn(exercises);
        assert (workoutplanService.generate(dummyPreferencesEndurance()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesStrength()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesBalance()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesFlexibility()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        reSet();
        // Dummy user 10 block end

        // Dummy user 11 block start
        when(mockedUserService.getLoggedInUser()).thenReturn(new User(51, "Daniel", true, 55, 182, false));
        when(mockedWeightHistoryService.getActualWeight(51)).thenReturn(new WeightHistory(null, 51, 90, new Date(Calendar.DATE)));

        when(mockedExerciseService.getWithoutCategory(new ArrayList<>())).thenReturn(exercises);
        when(mockedExerciseService.getAllEnduranceExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllStrengthExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllBalanceExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllFlexibilityExercises()).thenReturn(exercises);
        assert (workoutplanService.generate(dummyPreferencesEndurance()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesStrength()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesBalance()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesFlexibility()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        reSet();
        // Dummy user 11 block end

        // Dummy user 12 block start
        when(mockedUserService.getLoggedInUser()).thenReturn(new User(51, "Daniel", true, 55, 182, false));
        when(mockedWeightHistoryService.getActualWeight(51)).thenReturn(new WeightHistory(null, 51, 110, new Date(Calendar.DATE)));

        when(mockedExerciseService.getWithoutCategory(new ArrayList<>())).thenReturn(exercises);
        when(mockedExerciseService.getAllEnduranceExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllStrengthExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllBalanceExercises()).thenReturn(exercises);
        when(mockedExerciseService.getAllFlexibilityExercises()).thenReturn(exercises);
        assert (workoutplanService.generate(dummyPreferencesEndurance()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesStrength()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesBalance()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        assert (workoutplanService.generate(dummyPreferencesFlexibility()).getTrainingsSessions().get(0).getExerciseSets().size() == 3);
        // Dummy user 12 block end
    }

    /**
     * Resets all mocked services.
     */
    private void reSet(){
        Mockito.reset(mockedCategoryService);
        Mockito.reset(mockedUserService);
        Mockito.reset(mockedWeightHistoryService);
        Mockito.reset(mockedExerciseService);
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

}
