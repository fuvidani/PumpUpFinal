package sepm.ss15.grp16.gui.controller.exercises;

import sepm.ss15.grp16.entity.exercise.Exercise;

/**
 * Created by lukas on 10.06.2015.
 * interface which guarantees that an exercise can be
 * extracted out of the callers controller
 */
public interface VideoPlayable {

    /**
     * getting an exercise DTO object which contains
     * the link to a video
     *
     * @return an exercise DTO object
     */
    Exercise getExercise();
}
