package sepm.ss15.grp16.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.Exercise;
import sepm.ss15.grp16.entity.User;
import sepm.ss15.grp16.persistence.dao.ExerciseDAO;
import sepm.ss15.grp16.persistence.dao.impl.H2ExerciseDAOImpl;
import sepm.ss15.grp16.persistence.database.DBHandler;
import sepm.ss15.grp16.persistence.database.impl.H2DBConnectorImpl;
import sepm.ss15.grp16.persistence.exception.PersistenceException;
import sepm.ss15.grp16.service.ExerciseService;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.UserService;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukas on 01.05.2015.
 *
 */
public class ExerciseServiceImpl implements ExerciseService {

    private ExerciseDAO exerciseDAO;
    private UserService userService;
    private static final Logger LOGGER = LogManager.getLogger();


    public ExerciseServiceImpl(ExerciseDAO exerciseDAO, UserService userService)throws ServiceException{
        if(exerciseDAO==null) {
            throw new ServiceException("exerciseDAO is null. can not be set in Service Layer");
        }
        this.exerciseDAO = exerciseDAO;
        this.userService = userService;
    }

    @Override
    public Exercise create(Exercise exercise) throws ServiceException, ValidationException {
        LOGGER.debug("creating new Exercise in service");
        validate(exercise);
        try {

            return  exerciseDAO.create(exercise);
        }catch (PersistenceException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Exercise> findAll() throws ServiceException {
        try{
            LOGGER.debug("trying to find all exercises in servic");
            return exerciseDAO.findAll();
        }catch (PersistenceException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public Exercise update(Exercise exercise) throws ServiceException {
        LOGGER.debug("updating exercise in service");
        validate(exercise);
        try{
            Exercise updated = exerciseDAO.update(exercise);
            validate(updated);
            return updated;
        }catch (PersistenceException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(Exercise exercise) throws ServiceException {
        LOGGER.debug("deleting exercise in service");
        try{

            if(userService.getLoggedInUser()!=exercise.getUser())
                throw new ValidationException("can not delete an exercise from another user or the system");

            exerciseDAO.delete(exercise);
        }catch (PersistenceException e){
            throw new ServiceException("failed to delete ",e);
        }
    }

    @Override
    public void validate(Exercise exercise)throws sepm.ss15.grp16.service.exception.ValidationException{
        LOGGER.debug("validating exericse in service layer");
        if(exercise ==null)
            throw new ValidationException("validation not passed. exercise is null");

        if(exercise.getName().equals("") || exercise.getName()==null || exercise.getName().isEmpty())
            throw new ValidationException("name can not be null");

        if(exercise.getCalories()<= 0)
            throw new ValidationException("validation not passed. calories can not be lower or equal to 0");
    }

    /**
     * returning a list of all exercises for the given user
     * inclusive the system exercises
     * @param user the user to search for in the exercises
     * @return list of all exercises according to the given user and system
     * @throws ServiceException if there are any complications with the finding method
     */
    public List<Exercise> getExercisesByUserInclSystem(User user) throws ServiceException{
        List<Exercise> allExercises = this.findAll();
        List<Exercise> userExercises = new ArrayList<>();
        for(Exercise e : allExercises){
                if(e.getUser()==null || e.getUser() == user)
                    userExercises.add(e);
        }

        return userExercises;
    }

    /**
     * returning a list of all exercises only for the given user
     * system exercises will not be included in this list
     * @param user the user to search for in the exercises
     * @return list of all exercises according to the given user
     * @throws ServiceException if there are any complications with the finding method
     */
    public List<Exercise> getExerciseByUserONLY(User user) throws ServiceException{
        List<Exercise> allExercises = this.findAll();
        List<Exercise> userExercises = new ArrayList<>();
        for(Exercise e : allExercises){
            if(e.getUser() == user)
                userExercises.add(e);
        }
        return userExercises;
    }
}
