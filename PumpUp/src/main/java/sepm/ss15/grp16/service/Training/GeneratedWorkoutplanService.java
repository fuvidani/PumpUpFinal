package sepm.ss15.grp16.service.Training;

import sepm.ss15.grp16.entity.Gen_WorkoutplanPreferences;
import sepm.ss15.grp16.entity.Training.Trainingsplan;
import sepm.ss15.grp16.service.Service;

/**
 * Created by Daniel Fuevesi on 15.05.15.
 * Interface for the automatically generated workout plans based on user's specifications and its condition.
 */
public interface GeneratedWorkoutplanService extends Service<Gen_WorkoutplanPreferences> {

    /**
     * Generates a workout plan based on the preferences of the user and the user itself.
     * Following parameters will make an effect on the outcoming result:
     * - user's age, weight and gender
     * - user's goal to reach with the help of the workoutplan
     * - user's available training devices ready to use
     * Workoutplans will be generated from 3-5 days in a week.
     * The number of sets and repetitions are influenced by the user's condition.
     * @param preferences the preferences the user made in the GUI
     * @return a generated workoutplan suitable for the user's needs and goals
     */
     Trainingsplan generate(Gen_WorkoutplanPreferences preferences);
}
