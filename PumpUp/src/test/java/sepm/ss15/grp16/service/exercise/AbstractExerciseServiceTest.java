package sepm.ss15.grp16.service.exercise;

import org.junit.Test;
import sepm.ss15.grp16.entity.exercise.AbsractCategory;
import sepm.ss15.grp16.entity.exercise.Exercise;
import sepm.ss15.grp16.entity.exercise.MusclegroupCategory;
import sepm.ss15.grp16.entity.exercise.TrainingsCategory;
import sepm.ss15.grp16.service.AbstractServiceTestMockito;
import sepm.ss15.grp16.service.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukas on 17.05.2015.
 */
public abstract class AbstractExerciseServiceTest extends AbstractServiceTestMockito<Exercise> {

    @Test
    public void createValid() throws Exception {
        createTest(getDummyExercise());
    }

    @Test(expected = ValidationException.class)
    public void createWithNull() throws Exception {
        createTestFail(null);
    }

    @Test
    public void updateValid() throws Exception {
        updateTest(getDummyExercise());
    }

    @Test(expected = ValidationException.class)
    public void updateWithNull() throws Exception {
        updateTestFail(null);
    }

    @Test(expected = ValidationException.class)
    public void updateNotValid() throws Exception {
        updateTest(getDummyExerciseInvalid());
    }

    @Test
    public void deleteValid() throws Exception {
        deleteTest(getDummyExercise());
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
        return new Exercise(1, "liegestuetz", "eine der besten uebungen ueberhaupt", 9.0, "", gifList, false, null, categoryList);
    }

    private Exercise getDummyExerciseInvalid() throws Exception {
        Exercise exercise = getDummyExercise();
        exercise.setCalories(null);
        return exercise;
    }
}
