package sepm.ss15.grp16.service.exercise;

import org.junit.Test;
import sepm.ss15.grp16.entity.exercise.AbsractCategory;
import sepm.ss15.grp16.entity.exercise.Exercise;
import sepm.ss15.grp16.entity.exercise.MusclegroupCategory;
import sepm.ss15.grp16.entity.exercise.TrainingsCategory;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.persistence.dao.DAO;
import sepm.ss15.grp16.persistence.dao.exercise.ExerciseDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;
import sepm.ss15.grp16.service.AbstractServiceTestMockito;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;
import sepm.ss15.grp16.service.user.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by lukas on 17.05.2015.
 */
public abstract class AbstractExerciseServiceTest extends AbstractServiceTestMockito<Exercise> {


    protected ExerciseService exerciseService;
    protected ExerciseDAO mockedExerciseDAO;

    @Override
    public Service<Exercise> getService() {
        return exerciseService;
    }

    @Override
    public DAO<Exercise> getMockedDAO() {
        return mockedExerciseDAO;
    }

    @Test
    public void createValid() throws Exception {
        Exercise exercise = getDummyExercise();
        exercise.setId(1);
        createTest(exercise);


    }

    @Test(expected = ServiceException.class)
    public void createWithNull() throws Exception {
        createTestFail(null);
    }


    @Test
    public void updateCaloriesValid() throws Exception {
        Exercise exercise1 = getDummyExercise();
        exercise1.setId(1000);
        updateTest(exercise1);
    }

    @Test
    public void updateNameValid() throws Exception {
        Exercise exercise1 = getDummyExercise();
        exercise1.setId(1000);
        updateTest(exercise1);
    }

    @Test
    public void updateDescrValid() throws Exception {
        Exercise exercise1 = getDummyExercise();
        exercise1.setId(1000);
        updateTest(exercise1);
    }

    @Test
    public void updateCatValid() throws Exception {
        Exercise exercise1 = getDummyExercise();
        exercise1.setId(1000);
        updateTest(exercise1);
    }

    @Test
    public void deleteValid() throws Exception {
        Exercise toDelte =getDummyExercise();
        toDelte.setId(1000);
        deleteTest(toDelte);
    }

    @Test(expected = ValidationException.class)
    public void deleteWithNull() throws Exception {
        deleteTest(null);
    }

    private Exercise getDummyExercise() throws Exception {
        List<String> gifList = new ArrayList<>();
        String url = this.getClass().getResource("/img/testbild.jpg").toURI().getPath();
        //gifList.add(url);
        List<AbsractCategory> categoryList = new ArrayList<>();
        categoryList.add(new MusclegroupCategory(5, "Trizeps"));
        categoryList.add(new TrainingsCategory(2, "Balance"));
        return new Exercise(null, "liegestuetz", "eine der besten uebungen ueberhaupt", 9.0, "", gifList, false, null, categoryList);
    }

}
