package ServiceTest.Exercise;

import ServiceTest.AbstractServiceTest;
import sepm.ss15.grp16.entity.*;
import sepm.ss15.grp16.persistence.exception.PersistenceException;
import sepm.ss15.grp16.service.ExerciseService;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.UserService;
import org.junit.Test;
import org.junit.Assert;
import sepm.ss15.grp16.service.exception.ServiceException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukas on 17.05.2015.
 */
public abstract class AbstractExerciseServiceTest extends AbstractServiceTest<Exercise> {


    public abstract UserService getUserService();

    public abstract ExerciseService getExerciseService();

    private ExerciseService exerciseService = getExerciseService();
    private UserService userService = getUserService();

    @Test
    public void createValid()throws ServiceException {

        //creating a user
        User lukas = new User(null, "lukas", true, 22, 178, false);
        lukas = userService.create(lukas);
        Assert.assertNotNull(lukas);
        Assert.assertNotNull(lukas.getId());

        List<String> gifList = new ArrayList<>();
        URL url = this.getClass().getResource("/img/pushup.jpg");
        gifList.add(url.toString().substring(6));
        List<AbsractCategory> categoryList = new ArrayList<>();
        categoryList.add(new MusclegroupCategory(5, "Bizeps NEU"));
        categoryList.add(new TrainingsCategory(2, "kraft"));

        Exercise liegestuetz = new Exercise(null, "beinheben", "eine der besten uebungen ueberhaupt", 9.0, "", gifList, false , lukas, categoryList);

        List<Exercise> exerciseList = exerciseService.findAll();
        Assert.assertFalse(exerciseList.contains(liegestuetz));
        Exercise e =  exerciseService.create(liegestuetz);
        Assert.assertNotNull(e);
        exerciseList = exerciseService.findAll();

        Assert.assertTrue(exerciseList.contains(e));
    }

    //    @Test(expected = PersistenceException.class)
    public void createWithNull() throws ServiceException{
        Exercise exercise = null;
        exerciseService.create(exercise);

    }

    //        @Test
    public void updateValid()throws ServiceException {

        //creating a user
        User lukas = new User(null, "lukas", true, 22, 178, false);
        lukas = userService.create(lukas);
        Assert.assertNotNull(lukas);
        Assert.assertNotNull(lukas.getId());

        List<String> gifList = new ArrayList<>();
        URL url = this.getClass().getResource("/img/pushup.jpg");
        gifList.add(url.toString().substring(6));
        List<AbsractCategory> categoryList = new ArrayList<>();
        categoryList.add(new MusclegroupCategory(5, "Bizeps NEU"));
        categoryList.add(new TrainingsCategory(2, "kraft"));
        Exercise liegestuetz = new Exercise(null, "liegestuetz", "eine der besten uebungen ueberhaupt", 9.0, "", gifList, false, lukas, categoryList);

        List<Exercise> exerciseList = exerciseService.findAll();
        Assert.assertFalse(exerciseList.contains(liegestuetz));
        Exercise e = exerciseService.create(liegestuetz);
        Assert.assertNotNull(e);
        exerciseList = exerciseService.findAll();
        Assert.assertTrue(exerciseList.contains(e));

        e.setName("diamond Pull up");
        e.setDescription("perfect trainings exercice");
        e.setCalories(23.0);
        e.setGifLinks(gifList);
        gifList.add(url.toString().substring(6));
        categoryList = new ArrayList<>();
        categoryList.add(new MusclegroupCategory(3, "Bizeps NEU"));
        categoryList.add(new TrainingsCategory(4, "kraft"));
        e.setCategories(categoryList);
        exerciseService.update(e);
        exerciseList = exerciseService.findAll();
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

    //    @Test(expected = ServiceException.class)
    public void updateWithNull()throws ServiceException{
        exerciseService.update(null);
    }

    //    @Test(expected = ServiceException.class)
    public void updateWithNoID() throws ServiceException{
        List<String> gifList = new ArrayList<>();
        URL url = this.getClass().getResource("/img/pushup.jpg");
        gifList.add(url.toString().substring(6));
        Exercise liegestuetz = new Exercise(null, "liegestuetz", "eine der besten uebungen ueberhaupt", 9.0, "", gifList, false, null, null);
        Exercise e = exerciseService.create(liegestuetz);
        e.setId(null);
        exerciseService.update(e);
    }


    //    @Test
    public void deleteValid()throws ServiceException{


        List<String> gifList = new ArrayList<>();
        URL url = this.getClass().getResource("/img/pushup.jpg");
        gifList.add(url.toString().substring(6));
        Exercise liegestuetz = new Exercise(null, "liegestuetz", "eine der besten uebungen ueberhaupt", 9.0, "", gifList, false, null, null);
        List<Exercise> exerciseList = exerciseService.findAll();
        Assert.assertFalse(exerciseList.contains(liegestuetz));
        Exercise e =  exerciseService.create(liegestuetz);
        Assert.assertNotNull(e);
        exerciseList = exerciseService.findAll();
        Assert.assertTrue(exerciseList.contains(e));
        exerciseService.delete(e);
        exerciseList = exerciseService.findAll();
        Assert.assertFalse(exerciseList.contains(e));


    }

    //    @Test(expected = ServiceException.class)
    public void deleteWithNull()throws ServiceException{
        exerciseService.delete(null);
    }

    //    @Test(expected = ServiceException.class)
    public void deleteWithNullID()throws ServiceException{
        Exercise e = new Exercise(null, "test", "beschreibung", 0.5,"video", null, false, null, null);
        exerciseService.delete(e);
    }

    //    @Test
    public void getById()throws ServiceException{

        List<String> gifList = new ArrayList<>();
        URL url = this.getClass().getResource("/img/pushup.jpg");
        gifList.add(url.toString().substring(6));
        Exercise liegestuetz = new Exercise(null, "liegestuetz", "eine der besten uebungen ueberhaupt", 9.0, "", gifList, false, null, null);
        List<Exercise> exerciseList = exerciseService.findAll();
        Assert.assertFalse(exerciseList.contains(liegestuetz));
        Exercise e = exerciseService.create(liegestuetz);
        Exercise byId = exerciseService.searchByID(e.getId());
        Assert.assertNotNull(byId);


    }



}
