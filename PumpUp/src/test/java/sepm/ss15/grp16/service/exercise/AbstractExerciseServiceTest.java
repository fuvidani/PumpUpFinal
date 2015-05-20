package sepm.ss15.grp16.service.exercise;

import sepm.ss15.grp16.entity.*;
import sepm.ss15.grp16.persistence.exception.PersistenceException;
import sepm.ss15.grp16.service.AbstractServiceTest;
import sepm.ss15.grp16.service.ExerciseService;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.UserService;
import org.junit.Test;
import org.junit.Assert;
import sepm.ss15.grp16.service.exception.ServiceException;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukas on 17.05.2015.
 */
public abstract class AbstractExerciseServiceTest extends AbstractServiceTest<Exercise> {


    public abstract UserService getUserService();

    public abstract ExerciseService getExerciseService();

    @Test
    public void createValid()throws ServiceException, URISyntaxException {

        //creating a user
        User lukas = new User(null, "lukas", true, 22, 178, false);
        lukas = getUserService().create(lukas);
        getUserService().setLoggedInUser(lukas);
        Assert.assertNotNull(lukas);
        Assert.assertNotNull(lukas.getId());

        List<String> gifList = new ArrayList<>();
        String url = this.getClass().getResource("/img/pushup.jpg").toURI().getPath();
        gifList.add(url);
        List<AbsractCategory> categoryList = new ArrayList<>();
        categoryList.add(new MusclegroupCategory(5, "Bizeps NEU"));
        categoryList.add(new TrainingsCategory(2, "kraft"));

        Exercise liegestuetz = new Exercise(null, "beinheben", "eine der besten uebungen ueberhaupt", 9.0, "", gifList, false , lukas, categoryList);

        List<Exercise> exerciseList = getExerciseService().findAll();
        Assert.assertFalse(exerciseList.contains(liegestuetz));
        Exercise e =  getExerciseService().create(liegestuetz);
        Assert.assertNotNull(e);
        exerciseList = getExerciseService().findAll();

        Assert.assertTrue(exerciseList.contains(e));
    }

    //    @Test(expected = PersistenceException.class)
    public void createWithNull() throws ServiceException{
        Exercise exercise = null;
        getExerciseService().create(exercise);

    }

            @Test
    public void updateValid()throws ServiceException, URISyntaxException {

        //creating a user
        User lukas = new User(null, "lukas", true, 22, 178, false);
        lukas = getUserService().create(lukas);
                getUserService().setLoggedInUser(lukas);
        Assert.assertNotNull(lukas);
        Assert.assertNotNull(lukas.getId());

        List<String> gifList = new ArrayList<>();
        String url = this.getClass().getResource("/img/pushup.jpg").toURI().getPath();
        gifList.add(url);
        List<AbsractCategory> categoryList = new ArrayList<>();
        categoryList.add(new MusclegroupCategory(5, "Bizeps NEU"));
        categoryList.add(new TrainingsCategory(2, "kraft"));
        Exercise liegestuetz = new Exercise(null, "liegestuetz", "eine der besten uebungen ueberhaupt", 9.0, "", gifList, false, lukas, categoryList);

        List<Exercise> exerciseList = getExerciseService().findAll();
        Assert.assertFalse(exerciseList.contains(liegestuetz));
        Exercise e = getExerciseService().create(liegestuetz);
        Assert.assertNotNull(e);
        exerciseList = getExerciseService().findAll();
                for(Exercise ex : exerciseList){
                    System.out.println("exercises: \n"+ex);
                }
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
        getExerciseService().update(e);
        exerciseList = getExerciseService().findAll();
        Assert.assertTrue(exerciseList.contains(e));
    }


        @Test
    public void findExercises()throws ServiceException{
        List<EquipmentCategory> equipmentCategories = new ArrayList<>();
        equipmentCategories.add(new EquipmentCategory(17, "Springschnur"));

       List<Exercise>  exercises = getExerciseService().getWithoutCategory(equipmentCategories);
        Exercise seilspringen  = getExerciseService().searchByID(10);
        Assert.assertFalse(exercises.contains(seilspringen));
    }

        @Test(expected = ServiceException.class)
    public void updateWithNull()throws ServiceException{
        getExerciseService().update(null);
    }

        @Test(expected = ServiceException.class)
    public void updateWithNoID() throws ServiceException, URISyntaxException{
        List<String> gifList = new ArrayList<>();
        String url = this.getClass().getResource("/img/pushup.jpg").toURI().getPath();
        gifList.add(url);
        Exercise liegestuetz = new Exercise(null, "liegestuetz", "eine der besten uebungen ueberhaupt", 9.0, "", gifList, false, null, null);
        Exercise e = getExerciseService().create(liegestuetz);
        e.setId(null);
        getExerciseService().update(e);
    }


        @Test
    public void deleteValid()throws ServiceException, URISyntaxException{


        List<String> gifList = new ArrayList<>();
        String url = this.getClass().getResource("/img/pushup.jpg").toURI().getPath();
        gifList.add(url);
        Exercise liegestuetz = new Exercise(null, "liegestuetz", "eine der besten uebungen ueberhaupt", 9.0, "", gifList, false, null, null);
        List<Exercise> exerciseList = getExerciseService().findAll();
        Assert.assertFalse(exerciseList.contains(liegestuetz));
        Exercise e =  getExerciseService().create(liegestuetz);
        Assert.assertNotNull(e);
        exerciseList = getExerciseService().findAll();
        Assert.assertTrue(exerciseList.contains(e));
        getExerciseService().delete(e);
        exerciseList = getExerciseService().findAll();
        Assert.assertFalse(exerciseList.contains(e));


    }

        @Test(expected = ServiceException.class)
    public void deleteWithNull()throws ServiceException{
        getExerciseService().delete(null);
    }

        @Test(expected = ServiceException.class)
    public void deleteWithNullID()throws ServiceException{
        Exercise e = new Exercise(null, "test", "beschreibung", 0.5,"video", null, false, null, null);
        getExerciseService().delete(e);
    }

        @Test
    public void getById()throws ServiceException, URISyntaxException{

        List<String> gifList = new ArrayList<>();
        String url = this.getClass().getResource("/img/pushup.jpg").toURI().getPath();
        gifList.add(url);
        Exercise liegestuetz = new Exercise(null, "liegestuetz", "eine der besten uebungen ueberhaupt", 9.0, "", gifList, false, null, null);
        List<Exercise> exerciseList = getExerciseService().findAll();
        Assert.assertFalse(exerciseList.contains(liegestuetz));
        Exercise e = getExerciseService().create(liegestuetz);
        Exercise byId = getExerciseService().searchByID(e.getId());
        Assert.assertNotNull(byId);


    }



}
