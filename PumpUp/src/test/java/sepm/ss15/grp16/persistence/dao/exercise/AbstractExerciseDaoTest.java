package sepm.ss15.grp16.persistence.dao.exercise;

import org.junit.Test;
import sepm.ss15.grp16.entity.exercise.AbsractCategory;
import sepm.ss15.grp16.entity.exercise.Exercise;
import sepm.ss15.grp16.entity.exercise.MusclegroupCategory;
import sepm.ss15.grp16.entity.exercise.TrainingsCategory;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.persistence.dao.AbstractDAOTest;
import sepm.ss15.grp16.persistence.dao.user.UserDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukas on 30.04.2015.
 */
public abstract class AbstractExerciseDaoTest extends AbstractDAOTest<Exercise> {

    public abstract UserDAO getUserDAO();

    @Test
    public void createValid() throws PersistenceException, URISyntaxException {
        createValid(getDummyExercise());
    }

    @Test(expected = PersistenceException.class)
    public void createWithNull() throws PersistenceException {
        createValid(null);

    }


    @Test
    public void updateCaloriesValid() throws PersistenceException, URISyntaxException {
        Exercise exercise1 = getDummyExercise();
        Exercise exercise2 = new Exercise(exercise1);
        exercise2.setCalories(1.0);

        updateValid(exercise1, exercise2);
    }

    @Test
    public void updateNameValid() throws PersistenceException, URISyntaxException {
        Exercise exercise1 = getDummyExercise();
        Exercise exercise2 = new Exercise(exercise1);
        exercise2.setName("test");

        updateValid(exercise1, exercise2);
    }

    @Test
    public void updateDescrValid() throws PersistenceException, URISyntaxException {
        Exercise exercise1 = getDummyExercise();
        Exercise exercise2 = new Exercise(exercise1);
        exercise2.setDescription("test");

        updateValid(exercise1, exercise2);
    }

    @Test
    public void updateLinkValid() throws PersistenceException, URISyntaxException {
        Exercise exercise1 = getDummyExercise();
        Exercise exercise2 = new Exercise(exercise1);
        exercise2.setVideolink("test");

        updateValid(exercise1, exercise2);
    }

/*    @Test
    public void updateGifLinksValid() throws PersistenceException, URISyntaxException {
        Exercise exercise1 = getDummyExercise();
        Exercise exercise2 = new Exercise(exercise1);
        exercise2.setGifLinks(null);

        updateValid(exercise1, exercise2);
    }*/

    @Test
    public void updateCatValid() throws PersistenceException, URISyntaxException {
        Exercise exercise1 = getDummyExercise();
        Exercise exercise2 = new Exercise(exercise1);
        exercise2.setCategories(new ArrayList<>());

        updateValid(exercise1, exercise2);
    }

    @Test
    public void deleteValid() throws PersistenceException, URISyntaxException {
        deleteValid(getDummyExercise());
    }

    @Test(expected = PersistenceException.class)
    public void deleteWithNull() throws PersistenceException {
        deleteValid(null);
    }

    @Test
    public void getById() throws PersistenceException, URISyntaxException {
        searchByIDValid(getDummyExercise());
    }

    private User getDummyUser() throws PersistenceException {
        User lukas = new User(null, "lukas", true, 22, 178, false);
        return getUserDAO().create(lukas);
    }

    private Exercise getDummyExercise() throws URISyntaxException, PersistenceException {
        List<String> gifList = new ArrayList<>();
        String url = this.getClass().getResource("/img/testbild.jpg").toURI().getPath();
        //gifList.add(url);
        List<AbsractCategory> categoryList = new ArrayList<>();
        categoryList.add(new MusclegroupCategory(5, "Trizeps"));
        categoryList.add(new TrainingsCategory(2, "Balance"));
        return new Exercise(null, "liegestuetz", "eine der besten uebungen ueberhaupt", 9.0, "", gifList, false, getDummyUser(), categoryList);
    }

}
