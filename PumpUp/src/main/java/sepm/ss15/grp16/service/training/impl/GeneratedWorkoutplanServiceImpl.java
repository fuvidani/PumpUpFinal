package sepm.ss15.grp16.service.training.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.exercise.EquipmentCategory;
import sepm.ss15.grp16.entity.exercise.Exercise;
import sepm.ss15.grp16.entity.exercise.TrainingsCategory;
import sepm.ss15.grp16.entity.training.Gen_WorkoutplanPreferences;
import sepm.ss15.grp16.entity.training.TrainingsSession;
import sepm.ss15.grp16.entity.training.Trainingsplan;
import sepm.ss15.grp16.entity.training.helper.ExerciseSet;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.entity.user.WeightHistory;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;
import sepm.ss15.grp16.service.exercise.ExerciseService;
import sepm.ss15.grp16.service.training.GeneratedWorkoutplanService;
import sepm.ss15.grp16.service.user.UserService;
import sepm.ss15.grp16.service.user.WeightHistoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Daniel Fuevesi on 15.05.15.
 * This service is responsible for creating the auto-generated workout plans based on the user's information and needs.
 */
public class GeneratedWorkoutplanServiceImpl implements GeneratedWorkoutplanService {

    private static final Logger LOGGER = LogManager.getLogger();
    private ExerciseService exerciseService;
    private UserService userService;
    private WeightHistoryService weightHistoryService;

    /**
     * Public constructor to be injected by Spring.
     *
     * @param exerciseService      service for the specific exercises
     * @param userService          service for the current logged in user
     * @param weightHistoryService service for the weight history of the user
     */
    public GeneratedWorkoutplanServiceImpl(ExerciseService exerciseService, UserService userService, WeightHistoryService weightHistoryService) {
        this.exerciseService = exerciseService;
        this.userService = userService;
        this.weightHistoryService = weightHistoryService;
    }

    @Override
    public Trainingsplan generate(Gen_WorkoutplanPreferences preferences) throws ServiceException {
        validate(preferences);
        LOGGER.info("Validation successful");
        TrainingsCategory goal = preferences.getGoal();
        User user = userService.getLoggedInUser();
        List<EquipmentCategory> equipment = preferences.getEquipment();
        if (goal.getId() == 0) {          // Endurance
            return generateForEndurance(equipment, user);
        } else if (goal.getId() == 1) {    // Strength
            return generateForStrength(equipment, user);
        } else if (goal.getId() == 2) {    // Balance
            return generateForBalance(equipment, user);
        } else {                         // Flexibility
            return generateForFlexibility(equipment, user);
        }
    }


    /**
     * Auto-generates a workout plan based on the available equipment and the current logged in user for achieving a better endurance.
     *
     * @param equipment the equipment the user checked in as available. can be empty as well.
     * @param user      the current logged in user
     * @return a goal-driven, auto-generated workout plan
     */
    private Trainingsplan generateForEndurance(List<EquipmentCategory> equipment, User user) throws ServiceException {
        LOGGER.info("Entering the generating algorithm for an endurance workout plan.");

        double height = user.getHeight() / 100.0;
        WeightHistory history = weightHistoryService.getActualWeight(user.getUser_id());
        double weight = history.getWeight();
        double BMI = weight / (Math.pow(height, 2));
        int age = user.getAge();
        boolean male = user.isGender();
        List<Exercise> exercisesWithoutEquipment = exerciseService.getWithoutCategory(equipment);
        List<Exercise> exercisesForEndurance = exerciseService.getAllEnduranceExercises();
        List<Exercise> exercises = new ArrayList<>();
        for (Exercise exercise : exercisesWithoutEquipment) {
            if (exercisesForEndurance.contains(exercise)) {
                exercises.add(exercise);
            }
        }
        LOGGER.info("SIZE OF EXERCISES: " + exercises.size());
        Trainingsplan result;
        int weeklyCalorieGoal;
        int days;
        int numberOfExercises;
        double multiplier = 1;
        /**
         * Young user between 1 - 25 years.
         */
        if (age <= 25) {
            if (BMI <= 24.9) {                               // Underweight and Normal
                weeklyCalorieGoal = male ? 1000 : 750;
                days = numberOfExercises = 4;
            } else if (BMI >= 25 && BMI <= 29.9) {          // Overweight
                weeklyCalorieGoal = male ? 900 : 675;
                days = 3;
                numberOfExercises = 4;
            } else {                                        // Obese
                weeklyCalorieGoal = male ? 500 : 375;
                days = 3;
                numberOfExercises = 3;
            }
        }

        /**
         * Adult user between 26 - 35 years.
         */
        else if (age > 25 && age <= 35) {
            if (BMI <= 24.9) {                               // Underweight and Normal
                weeklyCalorieGoal = male ? 900 : 675;
                days = numberOfExercises = 4;
            } else if (BMI >= 25 && BMI <= 29.9) {          // Overweight
                weeklyCalorieGoal = male ? 800 : 600;
                days = 3;
                numberOfExercises = 4;
            } else {                                        // Obese
                weeklyCalorieGoal = male ? 500 : 375;
                days = numberOfExercises = 3;
            }
        }

        /**
         *  user between 36 - 50 years.
         */
        else if (age > 35 && age <= 50) {
            if (BMI <= 24.9) {                               // Underweight and Normal
                weeklyCalorieGoal = male ? 750 : 560;
                days = 3;
                numberOfExercises = 4;
            } else if (BMI >= 25 && BMI <= 29.9) {          // Overweight
                weeklyCalorieGoal = male ? 500 : 375;
                days = numberOfExercises = 3;
            } else {                                        // Obese
                weeklyCalorieGoal = male ? 300 : 225;
                days = numberOfExercises = 3;
            }
        }

        /**
         * Old user with more than 50 years.
         */
        else {
            if (BMI <= 24.9) {                               // Underweight and Normal
                weeklyCalorieGoal = male ? 300 : 225;
                days = 2;
                numberOfExercises = 3;
            } else if (BMI >= 25 && BMI <= 29.9) {          // Overweight
                weeklyCalorieGoal = male ? 200 : 150;
                days = 2;
                numberOfExercises = 3;
            } else {                                        // Obese
                weeklyCalorieGoal = male ? 180 : 135;
                days = 2;
                numberOfExercises = 3;
            }
        }

        double caloriesPerDay = weeklyCalorieGoal * 1.0 / days;
        double caloriesPerExercise = caloriesPerDay / numberOfExercises;
        Random random = new Random();
        List<TrainingsSession> sessions = new ArrayList<>();
        for (int i = 1; i <= days; i++) {
            List<ExerciseSet> sets = new ArrayList<>();
            List<Exercise> internExercises = new ArrayList<>();
            for (Exercise e : exercises) {
                internExercises.add(e);
            }
            for (int j = 1; j <= numberOfExercises; j++) {
                int rand = random.nextInt(internExercises.size());
                Exercise nextExercise = internExercises.get(rand);
                int duration = (int) Math.round((caloriesPerExercise / nextExercise.getCalories()) * multiplier);
                duration = Math.round(duration / 5) * 5;
                ExerciseSet set = new ExerciseSet(nextExercise, user, duration, ExerciseSet.SetType.time, j, false);
                sets.add(set);
                internExercises.remove(rand);
            }
            TrainingsSession session = new TrainingsSession(user, "Tag " + i, false, sets);
            sessions.add(session);
        }
        result = new Trainingsplan(user, "Generierter Trainingsplan f\u00fcr Ausdauer", "In diesem generierten Trainingsplan haben Sie unteschiedliche \u00dcbungen um Ihre Ausdauer zu trainieren.", false, 4, sessions);
        LOGGER.info("Workoutplan successfully generated!");
        return result;
    }

    /**
     * Auto-generates a workout plan based on the available equipment and the current logged in user for achieving a better strength.
     *
     * @param equipment the equipment the user checked in as available. can be empty as well.
     * @param user      the current logged in user
     * @return a goal-driven, auto-generated workout plan
     */
    private Trainingsplan generateForStrength(List<EquipmentCategory> equipment, User user) throws ServiceException {
        LOGGER.info("Entering the generating algorithm for an strength workout plan.");

        double height = user.getHeight() / 100.0;
        WeightHistory history = weightHistoryService.getActualWeight(user.getUser_id());
        double weight = history.getWeight();
        double BMI = weight / (Math.pow(height, 2));
        int age = user.getAge();
        boolean male = user.isGender();
        List<Exercise> exercisesWithoutEquipment = exerciseService.getWithoutCategory(equipment);
        List<Exercise> exercisesForEndurance = exerciseService.getAllStrengthExercises();
        List<Exercise> exercises = new ArrayList<>();
        for (Exercise exercise : exercisesWithoutEquipment) {
            if (exercisesForEndurance.contains(exercise)) {
                exercises.add(exercise);
            }
        }
        LOGGER.info("SIZE OF EXERCISES: " + exercises.size());
        Trainingsplan result;
        int weeklyCalorieGoal;
        int days;
        int numberOfExercises;
        double multiplier = 1;

        /**
         * Young user between 1 - 25 years.
         */
        if (age <= 25) {
            if (BMI <= 24.9) {                               // Underweight and Normal
                weeklyCalorieGoal = male ? 1000 : 750;
                days = numberOfExercises = 4;
            } else if (BMI >= 25 && BMI <= 29.9) {          // Overweight
                weeklyCalorieGoal = male ? 900 : 675;
                days = 3;
                numberOfExercises = 4;
            } else {                                        // Obese
                weeklyCalorieGoal = male ? 500 : 375;
                days = 3;
                numberOfExercises = 3;
            }
        }

        /**
         * Adult user between 26 - 35 years.
         */
        else if (age > 25 && age <= 35) {
            if (BMI <= 24.9) {                               // Underweight and Normal
                weeklyCalorieGoal = male ? 900 : 675;
                days = numberOfExercises = 4;
            } else if (BMI >= 25 && BMI <= 29.9) {          // Overweight
                weeklyCalorieGoal = male ? 800 : 600;
                days = 3;
                numberOfExercises = 4;
            } else {                                        // Obese
                weeklyCalorieGoal = male ? 500 : 375;
                days = numberOfExercises = 3;
            }
        }

        /**
         *  user between 36 - 50 years.
         */
        else if (age > 35 && age <= 50) {
            if (BMI <= 24.9) {                               // Underweight and Normal
                weeklyCalorieGoal = male ? 750 : 560;
                days = 3;
                numberOfExercises = 4;
            } else if (BMI >= 25 && BMI <= 29.9) {          // Overweight
                weeklyCalorieGoal = male ? 500 : 375;
                days = numberOfExercises = 3;
            } else {                                        // Obese
                weeklyCalorieGoal = male ? 300 : 225;
                days = numberOfExercises = 3;
            }
        }

        /**
         * Old user with more than 50 years.
         */
        else {
            if (BMI <= 24.9) {                               // Underweight and Normal
                weeklyCalorieGoal = male ? 300 : 225;
                days = 2;
                numberOfExercises = 3;
            } else if (BMI >= 25 && BMI <= 29.9) {          // Overweight
                weeklyCalorieGoal = male ? 200 : 150;
                days = 2;
                numberOfExercises = 3;
            } else {                                        // Obese
                weeklyCalorieGoal = male ? 180 : 135;
                days = 2;
                numberOfExercises = 3;
            }
        }

        double caloriesPerDay = weeklyCalorieGoal * 1.0 / days;
        double caloriesPerExercise = caloriesPerDay / numberOfExercises;
        Random random = new Random();
        List<TrainingsSession> sessions = new ArrayList<>();
        for (int i = 1; i <= days; i++) {
            List<ExerciseSet> sets = new ArrayList<>();
            List<Exercise> internExercises = new ArrayList<>();
            for (Exercise e : exercises) {
                internExercises.add(e);
            }
            for (int j = 1; j <= numberOfExercises; j++) {
                int rand = random.nextInt(internExercises.size());
                Exercise nextExercise = internExercises.get(rand);
                int duration = (int) Math.round((caloriesPerExercise / nextExercise.getCalories()) * multiplier);
                duration = Math.round(duration / 5) * 5;
                ExerciseSet set = new ExerciseSet(nextExercise, user, duration, ExerciseSet.SetType.repeat, j, false);
                sets.add(set);
                internExercises.remove(rand);
            }
            TrainingsSession session = new TrainingsSession(user, "Tag " + i, false, sets);
            sessions.add(session);
        }
        result = new Trainingsplan(user, "Generierter Trainingsplan f\u00dcr Kraft", "In diesem generierten Trainingsplan haben Sie unteschiedliche \u00fcbungen um Ihre Kraft zu trainieren.", false, 4, sessions);
        LOGGER.info("Workoutplan successfully generated!");
        return result;
    }

    /**
     * Auto-generates a workout plan based on the available equipment and the current logged in user for achieving a better balance.
     *
     * @param equipment the equipment the user checked in as available. can be empty as well.
     * @param user      the current logged in user
     * @return a goal-driven, auto-generated workout plan
     */
    private Trainingsplan generateForBalance(List<EquipmentCategory> equipment, User user) throws ServiceException {
        LOGGER.info("Entering the generating algorithm for an balance workout plan.");
        double height = user.getHeight() / 100.0;
        WeightHistory history = weightHistoryService.getActualWeight(user.getUser_id());
        double weight = history.getWeight();
        double BMI = weight / (Math.pow(height, 2));
        int age = user.getAge();
        boolean male = user.isGender();
        List<Exercise> exercisesWithoutEquipment = exerciseService.getWithoutCategory(equipment);
        List<Exercise> exercisesForEndurance = exerciseService.getAllBalanceExercises();
        List<Exercise> exercises = new ArrayList<>();
        for (Exercise exercise : exercisesWithoutEquipment) {
            if (exercisesForEndurance.contains(exercise)) {
                exercises.add(exercise);
            }
        }
        LOGGER.info("SIZE OF EXERCISES: " + exercises.size());
        Trainingsplan result;
        int weeklyCalorieGoal;
        int days;
        int numberOfExercises;
        double multiplier = 1;
        /**
         * Young user between 1 - 25 years.
         */
        if (age <= 25) {
            if (BMI <= 24.9) {                               // Underweight and Normal
                weeklyCalorieGoal = male ? 1000 : 750;
                days = numberOfExercises = 4;
            } else if (BMI >= 25 && BMI <= 29.9) {          // Overweight
                weeklyCalorieGoal = male ? 900 : 675;
                days = 3;
                numberOfExercises = 4;
            } else {                                        // Obese
                weeklyCalorieGoal = male ? 500 : 375;
                days = 3;
                numberOfExercises = 3;
            }
        }

        /**
         * Adult user between 26 - 35 years.
         */
        else if (age > 25 && age <= 35) {
            if (BMI <= 24.9) {                               // Underweight and Normal
                weeklyCalorieGoal = male ? 900 : 675;
                days = numberOfExercises = 4;
            } else if (BMI >= 25 && BMI <= 29.9) {          // Overweight
                weeklyCalorieGoal = male ? 800 : 600;
                days = 3;
                numberOfExercises = 4;
            } else {                                        // Obese
                weeklyCalorieGoal = male ? 500 : 375;
                days = numberOfExercises = 3;
            }
        }

        /**
         *  user between 36 - 50 years.
         */
        else if (age > 35 && age <= 50) {
            if (BMI <= 24.9) {                               // Underweight and Normal
                weeklyCalorieGoal = male ? 750 : 560;
                days = 3;
                numberOfExercises = 4;
            } else if (BMI >= 25 && BMI <= 29.9) {          // Overweight
                weeklyCalorieGoal = male ? 500 : 375;
                days = numberOfExercises = 3;
            } else {                                        // Obese
                weeklyCalorieGoal = male ? 300 : 225;
                days = numberOfExercises = 3;
            }
        }

        /**
         * Old user with more than 50 years.
         */
        else {
            if (BMI <= 24.9) {                               // Underweight and Normal
                weeklyCalorieGoal = male ? 300 : 225;
                days = 2;
                numberOfExercises = 3;
            } else if (BMI >= 25 && BMI <= 29.9) {          // Overweight
                weeklyCalorieGoal = male ? 200 : 150;
                days = 2;
                numberOfExercises = 3;
            } else {                                        // Obese
                weeklyCalorieGoal = male ? 180 : 135;
                days = 2;
                numberOfExercises = 3;
            }
        }

        double caloriesPerDay = weeklyCalorieGoal * 1.0 / days;
        double caloriesPerExercise = caloriesPerDay / numberOfExercises;
        Random random = new Random();
        List<TrainingsSession> sessions = new ArrayList<>();
        for (int i = 1; i <= days; i++) {
            List<ExerciseSet> sets = new ArrayList<>();
            List<Exercise> internExercises = new ArrayList<>();
            for (Exercise e : exercises) {
                internExercises.add(e);
            }
            for (int j = 1; j <= numberOfExercises; j++) {
                int rand = random.nextInt(internExercises.size());
                Exercise nextExercise = internExercises.get(rand);
                int duration = (int) Math.round((caloriesPerExercise / nextExercise.getCalories()) * multiplier);
                duration = Math.round(duration / 5) * 5;
                ExerciseSet set = new ExerciseSet(nextExercise, user, duration, ExerciseSet.SetType.time, j, false);
                sets.add(set);
                internExercises.remove(rand);
            }
            TrainingsSession session = new TrainingsSession(user, "Tag " + i, false, sets);
            sessions.add(session);
        }
        result = new Trainingsplan(user, "Generierter Trainingsplan f\u00fcr Balance", "In diesem generierten Trainingsplan haben Sie unteschiedliche \u00dcbungen um Ihre Balance zu trainieren.", false, 4, sessions);
        LOGGER.info("Workoutplan successfully generated!");
        return result;
    }

    /**
     * Auto-generates a workout plan based on the available equipment and the current logged in user for achieving a better flexibility.
     *
     * @param equipment the equipment the user checked in as available. can be empty as well.
     * @param user      the current logged in user
     * @return a goal-driven, auto-generated workout plan
     */
    private Trainingsplan generateForFlexibility(List<EquipmentCategory> equipment, User user) throws ServiceException {
        LOGGER.info("Entering the generating algorithm for an balance workout plan.");

        double height = user.getHeight() / 100.0;
        WeightHistory history = weightHistoryService.getActualWeight(user.getUser_id());
        double weight = history.getWeight();
        double BMI = weight / (Math.pow(height, 2));
        int age = user.getAge();
        boolean male = user.isGender();
        List<Exercise> exercisesWithoutEquipment = exerciseService.getWithoutCategory(equipment);
        List<Exercise> exercisesForBalance = exerciseService.getAllFlexibilityExercises();
        List<Exercise> exercises = new ArrayList<>();
        for (Exercise exercise : exercisesWithoutEquipment) {
            if (exercisesForBalance.contains(exercise)) {
                exercises.add(exercise);
            }
        }
        LOGGER.info("SIZE OF EXERCISES: " + exercises.size());
        Trainingsplan result;
        int weeklyCalorieGoal;
        int days;
        int numberOfExercises;
        double multiplier = 1;
        /**
         * Young user between 1 - 25 years.
         */
        if (age <= 25) {
            if (BMI <= 24.9) {                               // Underweight and Normal
                weeklyCalorieGoal = male ? 500 : 375;
                days = numberOfExercises = 4;
            } else if (BMI >= 25 && BMI <= 29.9) {          // Overweight
                weeklyCalorieGoal = male ? 500 : 375;
                days = 3;
                numberOfExercises = 4;
            } else {                                        // Obese
                weeklyCalorieGoal = male ? 250 : 200;
                days = 3;
                numberOfExercises = 3;
            }
        }

        /**
         * Adult user between 26 - 35 years.
         */
        else if (age > 25 && age <= 35) {
            if (BMI <= 24.9) {                               // Underweight and Normal
                weeklyCalorieGoal = male ? 400 : 300;
                days = numberOfExercises = 4;
            } else if (BMI >= 25 && BMI <= 29.9) {          // Overweight
                weeklyCalorieGoal = male ? 350 : 340;
                days = 3;
                numberOfExercises = 4;
            } else {                                        // Obese
                weeklyCalorieGoal = male ? 200 : 150;
                days = numberOfExercises = 3;
            }
        }

        /**
         *  user between 36 - 50 years.
         */
        else if (age > 35 && age <= 50) {
            if (BMI <= 24.9) {                               // Underweight and Normal
                weeklyCalorieGoal = male ? 400 : 300;
                days = 3;
                numberOfExercises = 4;
            } else if (BMI >= 25 && BMI <= 29.9) {          // Overweight
                weeklyCalorieGoal = male ? 250 : 200;
                days = numberOfExercises = 3;
            } else {                                        // Obese
                weeklyCalorieGoal = male ? 100 : 75;
                days = numberOfExercises = 3;
            }
        }

        /**
         * Old user with more than 50 years.
         */
        else {
            if (BMI <= 24.9) {                               // Underweight and Normal
                weeklyCalorieGoal = male ? 150 : 125;
                days = 2;
                numberOfExercises = 3;
            } else if (BMI >= 25 && BMI <= 29.9) {          // Overweight
                weeklyCalorieGoal = male ? 100 : 75;
                days = 2;
                numberOfExercises = 3;
            } else {                                        // Obese
                weeklyCalorieGoal = male ? 50 : 40;
                days = 2;
                numberOfExercises = 3;
            }
        }

        double caloriesPerDay = weeklyCalorieGoal * 1.0 / days;
        double caloriesPerExercise = caloriesPerDay / numberOfExercises;
        Random random = new Random();
        List<TrainingsSession> sessions = new ArrayList<>();
        for (int i = 1; i <= days; i++) {
            List<ExerciseSet> sets = new ArrayList<>();
            List<Exercise> internExercises = new ArrayList<>();
            for (Exercise e : exercises) {
                internExercises.add(e);
            }
            for (int j = 1; j <= numberOfExercises; j++) {
                int rand = random.nextInt(internExercises.size());
                Exercise nextExercise = internExercises.get(rand);
                int duration = (int) Math.round((caloriesPerExercise / nextExercise.getCalories()) * multiplier);
                duration = Math.round(duration / 5) * 5;
                ExerciseSet set = new ExerciseSet(nextExercise, user, duration, ExerciseSet.SetType.time, j, false);
                sets.add(set);
                internExercises.remove(rand);
            }
            TrainingsSession session = new TrainingsSession(user, "Tag " + i, false, sets);
            sessions.add(session);
        }
        result = new Trainingsplan(user, "Generierter Trainingsplan f\u00fcr Flexibilit\u00e4t", "In diesem generierten Trainingsplan haben Sie unteschiedliche \u00dcbungen um Ihre Flexibilit\u00e4t zu trainieren.", false, 4, sessions);
        LOGGER.info("Workoutplan successfully generated!");
        return result;
    }


    @Override
    public void validate(Gen_WorkoutplanPreferences dto) throws ValidationException {
        LOGGER.info("Entering validation in service.");
        if (dto == null) {
            throw new ValidationException("Es wurden leere Parameter \u00fcbergeben!");
        }
        TrainingsCategory goal = dto.getGoal();
        if (goal == null) {
            throw new ValidationException("Bitte w\u00e4hlen Sie eines von den 4 Trainingszielen aus!");
        }
        List<EquipmentCategory> equipment = dto.getEquipment();
        if (equipment == null) {
            throw new ValidationException("Ein Problem tritt auf w\u00e4hrend der Ausf\u00fchrung...");
        }
    }

    /**
     * ***************************************************************************************************************************************
     * *                                                                                                                                   * *
     * *                              The following methods are not supported by this module                                               * *
     * *                                                                                                                                   * *
     * ***************************************************************************************************************************************
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
