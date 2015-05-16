package sepm.ss15.grp16.service.Training.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.*;
import sepm.ss15.grp16.entity.Training.Helper.ExerciseSet;
import sepm.ss15.grp16.entity.Training.TrainingsSession;
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
    public Trainingsplan generate(Gen_WorkoutplanPreferences preferences) throws ServiceException{
        validate(preferences);
        LOGGER.info("Validation successful");
        TrainingsCategory goal = preferences.getGoal();
        User user = userService.getLoggedInUser();
        List<EquipmentCategory> equipments = preferences.getEquipments();
        if(goal.getId() == 0){  // Endurance
            return generateForEndurance(equipments, user);
        }else if(goal.getId() == 1){    // Strength
            return generateForStrength(equipments, user);
        }else if(goal.getId() == 2){    // Balance
            return generateForBalance(equipments, user);
        }else {     // Flexibility
            return generateForFlexibility(equipments, user);
        }
    }


    private Trainingsplan generateForEndurance(List<EquipmentCategory> equipments, User user){

        LOGGER.info("Workoutplan successfully generated!");
        return null;
    }

    private Trainingsplan generateForStrength(List<EquipmentCategory> equipments, User user){

        LOGGER.info("Workoutplan successfully generated!");
        return null;
    }

    private Trainingsplan generateForBalance(List<EquipmentCategory> equipments, User user){

        LOGGER.info("Workoutplan successfully generated!");
        return null;
    }

    private Trainingsplan generateForFlexibility(List<EquipmentCategory> equipments, User user){

        LOGGER.info("Workoutplan successfully generated!");
        return null;
    }



    @Override
    public void validate(Gen_WorkoutplanPreferences dto) throws ValidationException {
        LOGGER.info("Entering validation in service.");
        TrainingsCategory goal = dto.getGoal();
        if(goal == null){
            throw new ValidationException("Bitte wählen Sie eines von den 4 Trainingszielen aus!");
        }
        List<EquipmentCategory> equipments = dto.getEquipments();
        if(equipments == null){
            throw new ValidationException("Ein Problem tritt auf während der Ausführung...");
        }
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
