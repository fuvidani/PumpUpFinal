package PersitanceTest;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;
import sepm.ss15.grp16.entity.AbsractCategory;
import sepm.ss15.grp16.entity.Exercise;
import sepm.ss15.grp16.entity.MusclegroupCategory;
import sepm.ss15.grp16.entity.User;
import sepm.ss15.grp16.persistence.dao.ExerciseDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukas on 30.04.2015.
 */
public  abstract  class AbstractExerciseDaoTest {

    protected ExerciseDAO exerciseDAO;

    public abstract ExerciseDAO getExerciseDAO();

//        @Test
    public void createValid(){
        try {
            List<String> gifList = new ArrayList<>();
            URL url = this.getClass().getResource("/img/pushup.jpg");
            gifList.add(url.toString().substring(6));
            List<AbsractCategory> categoryList = new ArrayList<>();
            categoryList.add(new MusclegroupCategory(1, "Bizeps"));

            Exercise liegestuetz = new Exercise("liegestuetz", "eine der besten uebungen ueberhaupt", 9.0, "", gifList, false ,new User(null, "hallo", true, 1, 1, false), false, categoryList);
            List<Exercise> exerciseList = getExerciseDAO().findAll();
            TestCase.assertFalse(exerciseList.contains(liegestuetz));
            Exercise e =  exerciseDAO.create(liegestuetz);
            Assert.assertNotNull(e);
            exerciseList = getExerciseDAO().findAll();

            TestCase.assertTrue(exerciseList.contains(e));

        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }

    //    @Test(expected = PersistenceException.class)
    public void createWithNull() throws PersistenceException{
        Exercise exercise = null;
        exerciseDAO.create(exercise);

    }


        @Test
    public void updateValid(){
        try {
            List<String> gifList = new ArrayList<>();
            URL url = this.getClass().getResource("/img/pushup.jpg");
            gifList.add(url.toString().substring(6));

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

    //    @Test(expected = PersistenceException.class)
    public void updateWithNull()throws PersistenceException{
        exerciseDAO.update(null);
    }

    //    @Test(expected = PersistenceException.class)
    public void updateWithNoID() throws PersistenceException{
        List<String> gifList = new ArrayList<>();
        URL url = this.getClass().getResource("/img/pushup.jpg");
        gifList.add(url.toString().substring(6));
        Exercise liegestuetz = new Exercise("liegestuetz", "eine der besten uebungen ueberhaupt", 9.0, "", gifList, false);
        Exercise e = exerciseDAO.create(liegestuetz);
        e.setId(null);
        getExerciseDAO().update(e);
    }


    //    @Test
    public void deleteValid(){

        try {
            List<String> gifList = new ArrayList<>();
            URL url = this.getClass().getResource("/img/pushup.jpg");
            gifList.add(url.toString().substring(6));
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

//    @Test(expected = PersistenceException.class)
    public void deleteWithNull()throws PersistenceException{
        exerciseDAO.delete(null);
    }

//    @Test(expected = PersistenceException.class)
    public void deleteWithNullID()throws PersistenceException{
        Exercise e = new Exercise("test", "beschreibung", 0.5,"video", false);
        exerciseDAO.delete(e);
    }

//    @Test
    public void getById(){
        try {
            List<String> gifList = new ArrayList<>();
            URL url = this.getClass().getResource("/img/pushup.jpg");
            gifList.add(url.toString().substring(6));
            Exercise liegestuetz = new Exercise("liegestuetz", "eine der besten uebungen ueberhaupt", 9.0, "", gifList, false);
            List<Exercise> exerciseList = exerciseDAO.findAll();
            TestCase.assertFalse(exerciseList.contains(liegestuetz));
            Exercise e = exerciseDAO.create(liegestuetz);
            Exercise byId = exerciseDAO.searchByID(e.getId());
            Assert.assertNotNull(byId);

        }catch (PersistenceException e){
            e.printStackTrace();
        }

    }

}
