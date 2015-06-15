package sepm.ss15.grp16.service.exercise;

import org.junit.Assert;
import org.junit.Test;
import sepm.ss15.grp16.entity.exercise.*;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.persistence.exception.PersistenceException;
import sepm.ss15.grp16.service.AbstractServiceTest;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;
import sepm.ss15.grp16.service.user.UserService;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukas on 17.05.2015.
 */
public abstract class AbstractExerciseServiceTest extends AbstractServiceTest<Exercise> {


    public abstract UserService getUserService();

    public abstract ExerciseService getExerciseService();

    @Test
    public void createValid() throws ServiceException, URISyntaxException {
        createTest(getDummyExercise());
    }

    @Test(expected = ValidationException.class)
    public void createWithNull() throws ServiceException {
        createTest(null);

    }


    @Test
    public void updateCaloriesValid() throws ServiceException, URISyntaxException {
        Exercise exercise1 = getDummyExercise();
        Exercise exercise2 = new Exercise(exercise1);
        exercise2.setCalories(1.0);

        updateTest(exercise1, exercise2);
    }

    @Test
    public void updateNameValid() throws ServiceException, URISyntaxException {
        Exercise exercise1 = getDummyExercise();
        Exercise exercise2 = new Exercise(exercise1);
        exercise2.setName("test");

        updateTest(exercise1, exercise2);
    }

    @Test
    public void updateDescrValid() throws ServiceException, URISyntaxException {
        Exercise exercise1 = getDummyExercise();
        Exercise exercise2 = new Exercise(exercise1);
        exercise2.setDescription("test");

        updateTest(exercise1, exercise2);
    }

    @Test
    public void updateLinkValid() throws ServiceException, URISyntaxException {
        Exercise exercise1 = getDummyExercise();
        Exercise exercise2 = new Exercise(exercise1);
        exercise2.setVideolink("test");

        updateTest(exercise1, exercise2);
    }

    @Test
    public void updateCatValid() throws ServiceException, URISyntaxException {
        Exercise exercise1 = getDummyExercise();
        Exercise exercise2 = new Exercise(exercise1);
        exercise2.setCategories(new ArrayList<>());

        updateTest(exercise1, exercise2);
    }

    @Test
    public void deleteValid() throws ServiceException, URISyntaxException {
        deleteTest(getDummyExercise());
    }

    @Test(expected = ValidationException.class)
    public void deleteWithNull() throws ServiceException {
        deleteTest(null);
    }

    private Exercise getDummyExercise() throws URISyntaxException, ServiceException {
        List<String> gifList = new ArrayList<>();
        String url = this.getClass().getResource("/img/testbild.jpg").toURI().getPath();
        //gifList.add(url);
        List<AbsractCategory> categoryList = new ArrayList<>();
        categoryList.add(new MusclegroupCategory(5, "Trizeps"));
        categoryList.add(new TrainingsCategory(2, "Balance"));
        return new Exercise(null, "liegestuetz", "eine der besten uebungen ueberhaupt", 9.0, "", gifList, false, null, categoryList);
    }


}
