package sepm.ss15.grp16.service.Training.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.Gen_WorkoutplanPreferences;
import sepm.ss15.grp16.entity.Training.Trainingsplan;
import sepm.ss15.grp16.service.ExerciseService;
import sepm.ss15.grp16.service.Training.GeneratedWorkoutplanService;
import sepm.ss15.grp16.service.Training.TrainingsplanService;
import sepm.ss15.grp16.service.UserService;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;

import java.util.List;

/**
 * Created by Daniel Fuevesi on 15.05.15.
 *
 */
public class GeneratedWorkoutplanServiceImpl implements GeneratedWorkoutplanService{

    private static final Logger LOGGER = LogManager.getLogger();
    private TrainingsplanService trainingsplanService;
    private ExerciseService exerciseService;
    private UserService userService;

    public GeneratedWorkoutplanServiceImpl(TrainingsplanService trainingsplanService, ExerciseService exerciseService, UserService userService){
        this.trainingsplanService = trainingsplanService;
        this.exerciseService = exerciseService;
        this.userService = userService;
    }

    @Override
    public Trainingsplan generate(Gen_WorkoutplanPreferences preferences) {
        return null;
    }



    @Override
    public void validate(Gen_WorkoutplanPreferences dto) throws ValidationException {

    }

    /*
     * These methods are not supported by this functionality
     */
    @Override
    public Gen_WorkoutplanPreferences create(Gen_WorkoutplanPreferences dto) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Gen_WorkoutplanPreferences> findAll() throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Gen_WorkoutplanPreferences update(Gen_WorkoutplanPreferences dto) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Gen_WorkoutplanPreferences dto) throws ServiceException {
        throw new UnsupportedOperationException();
    }
}
