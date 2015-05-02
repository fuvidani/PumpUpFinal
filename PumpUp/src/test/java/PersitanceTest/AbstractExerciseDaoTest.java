package PersitanceTest;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.mockito.internal.matchers.NotNull;
import sepm.ss15.grp16.entity.Exercise;
import sepm.ss15.grp16.persistence.dao.ExerciseDAO;

import  sepm.ss15.grp16.persistence.exception.PersistenceException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertNotNull;

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
        try {
            List<String> gifList = new ArrayList<>();
            gifList.add("youtube");
            gifList.add("menshealth");
            Exercise liegestuetz = new Exercise("liegestuetz", "eine der besten uebungen ueberhaupt", 9.0, "", gifList, false);

            List<Exercise> exerciseList = exerciseDAO.findAll();
            TestCase.assertFalse(exerciseList.contains(liegestuetz));
            Exercise e =  exerciseDAO.create(liegestuetz);
            Assert.assertNotNull(e);
            exerciseList = exerciseDAO.findAll();

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

            List<Exercise> exerciseList = exerciseDAO.findAll();
            TestCase.assertFalse(exerciseList.contains(liegestuetz));
            Exercise e =  exerciseDAO.create(liegestuetz);
            Assert.assertNotNull(e);
            exerciseList = exerciseDAO.findAll();
            TestCase.assertTrue(exerciseList.contains(e));

            e.setName("diamond Pull up");
            e.setDescription("perfect trainings exercice");
            e.setCalories(23.0);
            e.setGifLinks(gifList);

            exerciseDAO.update(e);
            exerciseList = exerciseDAO.findAll();
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

            List<Exercise> exerciseList = exerciseDAO.findAll();
            TestCase.assertFalse(exerciseList.contains(liegestuetz));
            Exercise e =  exerciseDAO.create(liegestuetz);
            Assert.assertNotNull(e);
            exerciseList = exerciseDAO.findAll();
            TestCase.assertTrue(exerciseList.contains(e));
            exerciseDAO.delete(e);
            exerciseList = exerciseDAO.findAll();
            TestCase.assertFalse(exerciseList.contains(e));

        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }



}
