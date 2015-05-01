package test.java.PersitanceTest;

import main.java.sepm.ss15.grp16.entity.Exercise;
import main.java.sepm.ss15.grp16.persistence.ExerciseDAO;

import main.java.sepm.ss15.grp16.persistence.ExerciseDAO;
import main.java.sepm.ss15.grp16.persistence.exception.PersistenceException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukas on 30.04.2015.
 */
public  abstract  class AbstractExerciseDaoTest {
    protected static ExerciseDAO exerciseDAO;


    protected static void setExerciseDAO(ExerciseDAO exerciseDAO){
        AbstractExerciseDaoTest.exerciseDAO = exerciseDAO;
    }

    @Test
    public void createValid(){
        List<String> gifList = new ArrayList<>();
        gifList.add("youtube");
        gifList.add("menshealth");
        Exercise liegestuetz = new Exercise(2,"situp", "eine der besten uebungen ueberhaupt", 9.0, "", gifList);

        try {
            exerciseDAO.create(liegestuetz);
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }

}
