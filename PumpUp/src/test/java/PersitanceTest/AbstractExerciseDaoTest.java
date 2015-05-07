package PersitanceTest;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;
import sepm.ss15.grp16.entity.Exercise;
import sepm.ss15.grp16.persistence.dao.ExerciseDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukas on 30.04.2015.
 */
public  abstract  class AbstractExerciseDaoTest {


    public abstract ExerciseDAO getExerciseDAO();

    @Test
    public void createValid(){
        try {
            List<String> gifList = new ArrayList<>();
            gifList.add("youtube");
            gifList.add("menshealth");
            Exercise liegestuetz = new Exercise("liegestuetz", "eine der besten uebungen ueberhaupt", 9.0, "", gifList, false);

            List<Exercise> exerciseList = getExerciseDAO().findAll();
            TestCase.assertFalse(exerciseList.contains(liegestuetz));
            Exercise e =  getExerciseDAO().create(liegestuetz);
            Assert.assertNotNull(e);
            exerciseList = getExerciseDAO().findAll();

            TestCase.assertTrue(exerciseList.contains(e));

        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void updateValid(){
        try {
            List<String> gifList = new ArrayList<>();
            gifList.add("youtube");
            gifList.add("menshealth");
            Exercise liegestuetz = new Exercise("liegestuetz", "eine der besten uebungen ueberhaupt", 9.0, "", gifList, false);

            List<Exercise> exerciseList = getExerciseDAO().findAll();
            TestCase.assertFalse(exerciseList.contains(liegestuetz));
            Exercise e =  getExerciseDAO().create(liegestuetz);
            Assert.assertNotNull(e);
            exerciseList = getExerciseDAO().findAll();
            TestCase.assertTrue(exerciseList.contains(e));

            e.setName("diamond Pull up");
            e.setDescription("perfect trainings exercice");
            e.setCalories(23.0);
            e.setGifLinks(gifList);

            getExerciseDAO().update(e);
            exerciseList = getExerciseDAO().findAll();
            TestCase.assertTrue(exerciseList.contains(e));

        } catch (PersistenceException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void deleteValid(){

        try {
            List<String> gifList = new ArrayList<>();
            gifList.add("youtube");
            gifList.add("menshealth");
            Exercise liegestuetz = new Exercise("liegestuetz", "eine der besten uebungen ueberhaupt", 9.0, "", gifList, false);

            List<Exercise> exerciseList = getExerciseDAO().findAll();
            TestCase.assertFalse(exerciseList.contains(liegestuetz));
            Exercise e =  getExerciseDAO().create(liegestuetz);
            Assert.assertNotNull(e);
            exerciseList = getExerciseDAO().findAll();
            TestCase.assertTrue(exerciseList.contains(e));
            getExerciseDAO().delete(e);
            exerciseList = getExerciseDAO().findAll();
            TestCase.assertFalse(exerciseList.contains(e));

        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }



}
