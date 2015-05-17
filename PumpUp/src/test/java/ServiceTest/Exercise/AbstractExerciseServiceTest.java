package ServiceTest.Exercise;

import ServiceTest.AbstractServiceTest;
import sepm.ss15.grp16.entity.EquipmentCategory;
import sepm.ss15.grp16.entity.Exercise;
import sepm.ss15.grp16.service.ExerciseService;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.UserService;
import org.junit.Test;
import sepm.ss15.grp16.service.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukas on 17.05.2015.
 */
public abstract class AbstractExerciseServiceTest extends AbstractServiceTest<Exercise> {


    public abstract UserService getUserService();

    public abstract ExerciseService getExerciseService();

    @Test
    public void findExercises()throws ServiceException{
        List<EquipmentCategory> equipmentCategories = new ArrayList<>();
        equipmentCategories.add(new EquipmentCategory(17, "Springschnur"));

       for(Exercise e :  getExerciseService().getWithoutCategory(equipmentCategories)){
           System.out.println("Exercise: " + e+"\n");
       }

    }
}
