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
        double BMI = user.getHeight(); // TODO: change this
        int age = user.getAge();
        boolean male = user.isGender();
        List<Exercise> exercises = exerciseService.findAll();
        List<Exercise> enduranceExercises = new ArrayList<Exercise>();
        for(Exercise exercise: exercises){
            TrainingsCategory category = new TrainingsCategory(0,"");
            List<AbsractCategory> categories = exercise.getCategories();
            for(AbsractCategory Abscategory: categories){
                if(Abscategory instanceof TrainingsCategory){
                    category = (TrainingsCategory)Abscategory;
                    break;
                }
            }
            if(category.getId() == 0){
                enduranceExercises.add(exercise);
            }
        }
        Trainingsplan result;
        /**
         * Young user between 0 - 25 years.
         */
        if(age <= 25) {
            if (BMI < 18.5) {                               // Underweight
                if(equipment.isEmpty()){
                    // no equipment available

                }else {

                }
            } else if (BMI >= 18.5 && BMI <= 24.9) {        // Normal
                if(equipment.isEmpty()){
                    // no equipment available

                }else {

                }

            } else if (BMI >= 25 && BMI <= 29.9) {          // Overweight
                if(equipment.isEmpty()){
                    // no equipment available

                }else {

                }

            } else {                                        // Obese
                if(equipment.isEmpty()){
                    // no equipment available

                }else {

                }

            }
        }

        /**
         * Adult user between 26 - 35 years.
         */
        else if (age > 25 && age <= 35){
            if (BMI < 18.5) {                               // Underweight
                if(equipment.isEmpty()){
                    // no equipment available

                }else {

                }

            } else if (BMI >= 18.5 && BMI <= 24.9) {        // Normal
                if(equipment.isEmpty()){
                    // no equipment available

                }else {

                }

            } else if (BMI >= 25 && BMI <= 29.9) {          // Overweight
                if(equipment.isEmpty()){
                    // no equipment available

                }else {

                }

            } else {                                        // Obese
                if(equipment.isEmpty()){
                    // no equipment available

                }else {

                }

            }
        }

        /**
         *  User between 36 - 50 years.
         */
        else if (age > 35 && age <= 50){
            if (BMI < 18.5) {                               // Underweight
                if(equipment.isEmpty()){
                    // no equipment available

                }else {

                }

            } else if (BMI >= 18.5 && BMI <= 24.9) {        // Normal
                if(equipment.isEmpty()){
                    // no equipment available

                }else {

                }

            } else if (BMI >= 25 && BMI <= 29.9) {          // Overweight
                if(equipment.isEmpty()){
                    // no equipment available

                }else {

                }

            } else {                                        // Obese
                if(equipment.isEmpty()){
                    // no equipment available

                }else {

                }

            }
        }

        /**
         * Old user with more than 50 years.
         */
        else {
            if (BMI < 18.5) {                               // Underweight
                if(equipment.isEmpty()){
                    // no equipment available

                }else {

                }

            } else if (BMI >= 18.5 && BMI <= 24.9) {        // Normal
                if(equipment.isEmpty()){
                    // no equipment available

                }else {

                }

            } else if (BMI >= 25 && BMI <= 29.9) {          // Overweight
                if(equipment.isEmpty()){
                    // no equipment available

                }else {

                }

            } else {                                        // Obese
                if(equipment.isEmpty()){
                    // no equipment available

                }else {

                }

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
        TrainingsCategory goal = dto.getGoal();
        if(goal == null){
            throw new ValidationException("Bitte wählen Sie eines von den 4 Trainingszielen aus!");
        }
        List<EquipmentCategory> equipment = dto.getEquipment();
        if(equipment == null){
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
