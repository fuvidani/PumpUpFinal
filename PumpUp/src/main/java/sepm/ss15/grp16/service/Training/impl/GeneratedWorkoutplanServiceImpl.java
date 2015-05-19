package sepm.ss15.grp16.service.Training.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.*;
import sepm.ss15.grp16.entity.training.Trainingsplan;
import sepm.ss15.grp16.service.ExerciseService;
import sepm.ss15.grp16.service.Training.GeneratedWorkoutplanService;
import sepm.ss15.grp16.service.Training.TrainingsplanService;
import sepm.ss15.grp16.service.UserService;
import sepm.ss15.grp16.service.WeightHistoryService;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Fuevesi on 15.05.15.
 * This service is responsible for creating the auto-generated workout plans based on the user's information and needs.
 */
public class GeneratedWorkoutplanServiceImpl implements GeneratedWorkoutplanService{

    private static final Logger LOGGER = LogManager.getLogger();
    private TrainingsplanService trainingsplanService;
    private ExerciseService exerciseService;
    private UserService userService;
    private WeightHistoryService weightHistoryService;

    /**
     * Public constructor to be injected by Spring.
     * @param trainingsplanService service for the workout plans
     * @param exerciseService service for the specific exercises
     * @param userService service for the current logged in user
     * @param weightHistoryService service for the weight history of the user
     */
    public GeneratedWorkoutplanServiceImpl(TrainingsplanService trainingsplanService, ExerciseService exerciseService, UserService userService, WeightHistoryService weightHistoryService){
        this.trainingsplanService = trainingsplanService;
        this.exerciseService = exerciseService;
        this.userService = userService;
        this.weightHistoryService = weightHistoryService;
    }

    @Override
    public Trainingsplan generate(Gen_WorkoutplanPreferences preferences) throws ServiceException{
        validate(preferences);
        LOGGER.info("Validation successful");
        TrainingsCategory goal = preferences.getGoal();
        User user = userService.getLoggedInUser();
        List<EquipmentCategory> equipment = preferences.getEquipment();
        if(goal.getId() == 0){          // Endurance
            return generateForEndurance(equipment, user);
        }else if(goal.getId() == 1){    // Strength
            return generateForStrength(equipment, user);
        }else if(goal.getId() == 2){    // Balance
            return generateForBalance(equipment, user);
        }else {                         // Flexibility
            return generateForFlexibility(equipment, user);
        }
    }


    /**
     * Auto-generates a workout plan based on the available equipment and the current logged in user for achieving a better endurance.
     * @param equipment the equipment the user checked in as available. can be empty as well.
     * @param user the current logged in user
     * @return a goal-driven, auto-generated workout plan
     */
    private Trainingsplan generateForEndurance(List<EquipmentCategory> equipment, User user) throws ServiceException{
        // TODO: implement me
        LOGGER.info("Entering the generating algorithms for an endurance workout plan.");

        double height = user.getHeight()/100.0;
        LOGGER.info("HEIGHT: " + height);
        WeightHistory history = weightHistoryService.getActualWeight(user.getUser_id());
        double weight = history.getWeight();
        LOGGER.info("WEIGHT: " + weight);
        double BMI = weight/(Math.pow(height,2));
        LOGGER.info("BMI: " + BMI);
        int age = user.getAge();
        boolean male = user.isGender();
        List<Exercise> exercisesWithoutEquipment = exerciseService.getWithoutCategory(equipment);
        List<Exercise> exercisesForEndurance = exerciseService.getAllEnduranceExercises();
        List<Exercise> exercises = new ArrayList<Exercise>();
        for(Exercise exercise: exercisesWithoutEquipment){
            if(exercisesForEndurance.contains(exercise)){
                exercises.add(exercise);
            }
        }
        Trainingsplan result;
        int weeklyCalorieGoal;
        int days;
        int numberOfExercises;
        double multiplier = 1;
        /**
         * Young user between 1 - 25 years.
         */
        if(age <= 25) {
            if (BMI <= 24.9) {                               // Underweight and Normal
                weeklyCalorieGoal = male ? 1800 : 1350;
                days = numberOfExercises = 4;
            } else if (BMI >= 25 && BMI <= 29.9) {          // Overweight
                weeklyCalorieGoal = male ? 1050 : 800;
                days = 3;
                numberOfExercises = 4;
            } else {                                        // Obese
                
            }
        }

        /**
         * Adult user between 26 - 35 years.
         */
        else if (age > 25 && age <= 35){
            if (BMI < 18.5) {                               // Underweight

            } else if (BMI >= 18.5 && BMI <= 24.9) {        // Normal

            } else if (BMI >= 25 && BMI <= 29.9) {          // Overweight

            } else {                                        // Obese

            }
        }

        /**
         *  User between 36 - 50 years.
         */
        else if (age > 35 && age <= 50){
            if (BMI < 18.5) {                               // Underweight

            } else if (BMI >= 18.5 && BMI <= 24.9) {        // Normal


            } else if (BMI >= 25 && BMI <= 29.9) {          // Overweight

            } else {                                        // Obese

            }
        }

        /**
         * Old user with more than 50 years.
         */
        else {
            if (BMI < 18.5) {                               // Underweight

            } else if (BMI >= 18.5 && BMI <= 24.9) {        // Normal


            } else if (BMI >= 25 && BMI <= 29.9) {          // Overweight

            } else {                                        // Obese

            }
        }
        LOGGER.info("Workoutplan successfully generated!");
        return null;
    }

    /**
     * Auto-generates a workout plan based on the available equipment and the current logged in user for achieving a better strength.
     * @param equipment the equipment the user checked in as available. can be empty as well.
     * @param user the current logged in user
     * @return a goal-driven, auto-generated workout plan
     */
    private Trainingsplan generateForStrength(List<EquipmentCategory> equipment, User user){
        // TODO: implement me
        LOGGER.info("Workoutplan successfully generated!");
        return null;
    }

    /**
     * Auto-generates a workout plan based on the available equipment and the current logged in user for achieving a better balance.
     * @param equipment the equipment the user checked in as available. can be empty as well.
     * @param user the current logged in user
     * @return a goal-driven, auto-generated workout plan
     */
    private Trainingsplan generateForBalance(List<EquipmentCategory> equipment, User user){
        // TODO: implement me
        LOGGER.info("Workoutplan successfully generated!");
        return null;
    }

    /**
     * Auto-generates a workout plan based on the available equipment and the current logged in user for achieving a better flexibility.
     * @param equipment the equipment the user checked in as available. can be empty as well.
     * @param user the current logged in user
     * @return a goal-driven, auto-generated workout plan
     */
    private Trainingsplan generateForFlexibility(List<EquipmentCategory> equipment, User user){
        // TODO: implement me
        LOGGER.info("Workoutplan successfully generated!");
        return null;
    }



    @Override
    public void validate(Gen_WorkoutplanPreferences dto) throws ValidationException {
        LOGGER.info("Entering validation in service.");
        if(dto == null){
            throw new ValidationException("Es wurden leere Parameter übergeben!");
        }
        TrainingsCategory goal = dto.getGoal();
        if(goal == null){
            throw new ValidationException("Bitte wählen Sie eines von den 4 Trainingszielen aus!");
        }
        List<EquipmentCategory> equipment = dto.getEquipment();
        if(equipment == null){
            throw new ValidationException("Ein Problem tritt auf während der Ausführung...");
        }
    }

    /***************************************************************************************************************************************
     **                                                                                                                                   **
     **                              The following methods are not supported by this module                                               **
     **                                                                                                                                   **
     ***************************************************************************************************************************************/

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
