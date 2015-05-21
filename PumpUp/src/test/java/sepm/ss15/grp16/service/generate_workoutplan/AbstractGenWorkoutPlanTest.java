package sepm.ss15.grp16.service.generate_workoutplan;

import org.junit.Test;
import sepm.ss15.grp16.entity.exercise.TrainingsCategory;
import sepm.ss15.grp16.entity.training.Gen_WorkoutplanPreferences;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.entity.user.WeightHistory;
import sepm.ss15.grp16.service.*;
import sepm.ss15.grp16.service.training.GeneratedWorkoutplanService;
import sepm.ss15.grp16.service.exception.ServiceException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Daniel Fuevesi on 19.05.15.
 *
 */
public abstract class AbstractGenWorkoutPlanTest {


    protected GeneratedWorkoutplanService workoutplanService;
    protected CategoryService categoryService;
    protected UserService userService;
    protected WeightHistoryService weightHistoryService;

    protected void setService(GeneratedWorkoutplanService service){
        this.workoutplanService = service;
    }

    protected void setService(CategoryService service){
        this.categoryService = service;
    }

    protected void setService(UserService service){
        this.userService = service;
    }

    protected void setService(WeightHistoryService service){
        this.weightHistoryService = service;
    }


    @Test(expected = ServiceException.class)
    public void callWithNullShouldThrowException() throws ServiceException{
        workoutplanService.generate(null);
    }


    @Test
    public void callWithValidPreferences()throws ServiceException{
        dummyUser();
        workoutplanService.generate(dummyPreferences());
    }

    private Gen_WorkoutplanPreferences dummyPreferences()throws ServiceException{
        return new Gen_WorkoutplanPreferences(1,new TrainingsCategory(0,"Ausdauer"), new ArrayList<>());
    }

    private void dummyUser() throws ServiceException{
        User user  = new User(50,"Daniel", true, 20, 182, false);
        userService.create(user);
        userService.setLoggedInUser(user);
        weightHistoryService.create(new WeightHistory(null,user.getUser_id(),75,new Date(Calendar.DATE)));
    }


}
