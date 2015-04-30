package java.PersitanceTest;

import java.sepm.ss15.grp16.persistence.ExerciseDAO;
import org.junit.Test;

/**
 * Created by lukas on 30.04.2015.
 */
public  abstract  class AbstractExerciseDaoTest {
    protected static ExerciseDAO exerciseDAO;

    protected static void setExerciseDAO(ExerciseDAO exerciseDAO){
        AbstractExerciseDaoTest.exerciseDAO = exerciseDAO;
    }

    @Test
    public void test(){
        System.out.println("hello world");
    }

}
