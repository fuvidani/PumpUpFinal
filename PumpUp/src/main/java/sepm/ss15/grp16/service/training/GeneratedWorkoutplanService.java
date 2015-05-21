package sepm.ss15.grp16.service.training;

import sepm.ss15.grp16.entity.training.Gen_WorkoutplanPreferences;
import sepm.ss15.grp16.entity.training.Trainingsplan;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.exception.ServiceException;

/**
 * Created by Daniel Fuevesi on 15.05.15.
 * Interface for the automatically generated workout plans based on user's specifications and its condition.
 */
public interface GeneratedWorkoutplanService extends Service<Gen_WorkoutplanPreferences> {

    /**
     * Generates a workout plan based on the preferences of the user and the user itself.
     * Following parameters will make an effect on the final result:
     * - user's age, weight and gender
     * - user's goal to reach with the help of the workout plan
     * - user's available training devices ready to use
     * Workout plans will be generated from 3-5 days in a week.
     * The number of sets and repetitions are influenced by the user's condition.
     * @param preferences the preferences the user made in the GUI
     * @return a generated workout plan suitable for the user's needs and goals
     */
     Trainingsplan generate(Gen_WorkoutplanPreferences preferences) throws ServiceException;
}
