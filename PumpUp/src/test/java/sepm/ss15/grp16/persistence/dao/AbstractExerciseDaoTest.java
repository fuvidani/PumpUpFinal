package sepm.ss15.grp16.persistence.dao;

import org.junit.Assert;
import org.junit.*;
import sepm.ss15.grp16.entity.exercise.AbsractCategory;
import sepm.ss15.grp16.entity.exercise.Exercise;
import sepm.ss15.grp16.entity.exercise.MusclegroupCategory;
import sepm.ss15.grp16.entity.exercise.TrainingsCategory;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukas on 30.04.2015.
 */
public  abstract  class AbstractExerciseDaoTest {

    protected ExerciseDAO exerciseDAO;
    protected UserDAO userDAO;

    public abstract ExerciseDAO getExerciseDAO();

    @Test
    public void createValid()throws PersistenceException, URISyntaxException{


        //creating a user
        User lukas = new User(null, "lukas", true, 22, 178, false);
        lukas = userDAO.create(lukas);
        Assert.assertNotNull(lukas);
        Assert.assertNotNull(lukas.getId());


        List<String> gifList = new ArrayList<>();
        String url = this.getClass().getResource("/img/pushup.jpg").toURI().getPath();
        gifList.add(url);
        List<AbsractCategory> categoryList = new ArrayList<>();
        categoryList.add(new MusclegroupCategory(5, "Bizeps NEU"));
        categoryList.add(new TrainingsCategory(2, "kraft"));

        Exercise liegestuetz = new Exercise(null, "beinheben", "eine der besten uebungen ueberhaupt", 9.0, "", gifList, false , lukas, categoryList);

        List<Exercise> exerciseList = getExerciseDAO().findAll();
        Assert.assertFalse(exerciseList.contains(liegestuetz));
        Exercise e =  exerciseDAO.create(liegestuetz);
        Assert.assertNotNull(e);
        exerciseList = getExerciseDAO().findAll();

        Assert.assertTrue(exerciseList.contains(e));


    }

    //    @Test(expected = PersistenceException.class)
    public void createWithNull() throws PersistenceException{
        Exercise exercise = null;
        exerciseDAO.create(exercise);

    }


    //        @Test
    public void updateValid()throws PersistenceException, URISyntaxException{

        //creating a user
        User lukas = new User(null, "lukas", true, 22, 178, false);
        lukas = userDAO.create(lukas);
        Assert.assertNotNull(lukas);
        Assert.assertNotNull(lukas.getId());

        List<String> gifList = new ArrayList<>();
        String url = this.getClass().getResource("/img/pushup.jpg").toURI().getPath();
        gifList.add(url);
        List<AbsractCategory> categoryList = new ArrayList<>();
        categoryList.add(new MusclegroupCategory(5, "Bizeps NEU"));
        categoryList.add(new TrainingsCategory(2, "kraft"));
        Exercise liegestuetz = new Exercise(null, "liegestuetz", "eine der besten uebungen ueberhaupt", 9.0, "", gifList, false , lukas, categoryList);

        List<Exercise> exerciseList = getExerciseDAO().findAll();
        Assert.assertFalse(exerciseList.contains(liegestuetz));
        Exercise e =  getExerciseDAO().create(liegestuetz);
        Assert.assertNotNull(e);
        exerciseList = getExerciseDAO().findAll();
        Assert.assertTrue(exerciseList.contains(e));

        e.setName("diamond Pull up");
        e.setDescription("perfect trainings exercice");
        e.setCalories(23.0);
        e.setGifLinks(gifList);
        gifList.add(url);
        categoryList = new ArrayList<>();
        categoryList.add(new MusclegroupCategory(3, "Bizeps NEU"));
        categoryList.add(new TrainingsCategory(4, "kraft"));
        e.setCategories(categoryList);
        getExerciseDAO().update(e);
        exerciseList = getExerciseDAO().findAll();
        Assert.assertTrue(exerciseList.contains(e));



    }

    //    @Test(expected = PersistenceException.class)
    public void updateWithNull()throws PersistenceException{
        exerciseDAO.update(null);
    }

    //    @Test(expected = PersistenceException.class)
    public void updateWithNoID() throws PersistenceException, URISyntaxException{
        List<String> gifList = new ArrayList<>();
        String url = this.getClass().getResource("/img/pushup.jpg").toURI().getPath();
        gifList.add(url);
        Exercise liegestuetz = new Exercise(null, "liegestuetz", "eine der besten uebungen ueberhaupt", 9.0, "", gifList, false, null, null);
        Exercise e = exerciseDAO.create(liegestuetz);
        e.setId(null);
        getExerciseDAO().update(e);
    }


    //    @Test
    public void deleteValid()throws PersistenceException, URISyntaxException{


        List<String> gifList = new ArrayList<>();
        String url = this.getClass().getResource("/img/pushup.jpg").toURI().getPath();
        gifList.add(url.toString());
        Exercise liegestuetz = new Exercise(null, "liegestuetz", "eine der besten uebungen ueberhaupt", 9.0, "", gifList, false, null, null);
        List<Exercise> exerciseList = getExerciseDAO().findAll();
        Assert.assertFalse(exerciseList.contains(liegestuetz));
        Exercise e =  getExerciseDAO().create(liegestuetz);
        Assert.assertNotNull(e);
        exerciseList = getExerciseDAO().findAll();
        Assert.assertTrue(exerciseList.contains(e));
        getExerciseDAO().delete(e);
        exerciseList = getExerciseDAO().findAll();
        Assert.assertFalse(exerciseList.contains(e));


    }

    //    @Test(expected = PersistenceException.class)
    public void deleteWithNull()throws PersistenceException{
        exerciseDAO.delete(null);
    }

    //    @Test(expected = PersistenceException.class)
    public void deleteWithNullID()throws PersistenceException{
        Exercise e = new Exercise(null, "test", "beschreibung", 0.5,"video", null, false, null, null);
        exerciseDAO.delete(e);
    }

    //    @Test
    public void getById()throws PersistenceException, URISyntaxException{

        List<String> gifList = new ArrayList<>();
        String url = this.getClass().getResource("/img/pushup.jpg").toURI().getPath();
        gifList.add(url);
        Exercise liegestuetz = new Exercise(null, "liegestuetz", "eine der besten uebungen ueberhaupt", 9.0, "", gifList, false, null, null);
        List<Exercise> exerciseList = exerciseDAO.findAll();
        Assert.assertFalse(exerciseList.contains(liegestuetz));
        Exercise e = exerciseDAO.create(liegestuetz);
        Exercise byId = exerciseDAO.searchByID(e.getId());
        Assert.assertNotNull(byId);


    }

}
