package sepm.ss15.grp16.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.exercise.AbsractCategory;
import sepm.ss15.grp16.entity.exercise.EquipmentCategory;
import sepm.ss15.grp16.entity.exercise.Exercise;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.persistence.dao.ExerciseDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;
import sepm.ss15.grp16.service.ExerciseService;
import sepm.ss15.grp16.service.UserService;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukas on 01.05.2015.
 */
public class ExerciseServiceImpl implements ExerciseService {

    private static final Logger LOGGER = LogManager.getLogger();
    private ExerciseDAO exerciseDAO;
    private UserService userService;


    public ExerciseServiceImpl(ExerciseDAO exerciseDAO, UserService userService) throws ServiceException {
        if (exerciseDAO == null) {
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

            return exerciseDAO.create(exercise);
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Exercise> findAll() throws ServiceException {
        try {
            LOGGER.debug("trying to find all exercises in servic");
            List<Exercise> allExercises = exerciseDAO.findAll();
            List<Exercise> userExercises = new ArrayList<>();
            for (Exercise e : allExercises) {
                if (e.getUser() == null || e.getUser().equals(userService.getLoggedInUser())) {
                    userExercises.add(e);
                }
            }
            return userExercises;
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Exercise searchByID(int id) throws ServiceException {
        try {
            return exerciseDAO.searchByID(id);
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Exercise update(Exercise exercise) throws ServiceException {
        LOGGER.debug("updating exercise in service");
        validate(exercise);
        try {
            Exercise updated = exerciseDAO.update(exercise);
            validate(updated);
            return updated;
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(Exercise exercise) throws ServiceException {
        LOGGER.debug("deleting exercise in service");
        try {
            if (exercise == null || exercise.getId() == null)
                throw new ValidationException("can not validatate exercise with null value");

            if (exercise.getUser() != null && !userService.getLoggedInUser().equals(exercise.getUser()))
                throw new ValidationException("can not delete an exercise from another user or the system");

            exerciseDAO.delete(exercise);
        } catch (PersistenceException e) {
            throw new ServiceException("failed to delete ", e);
        }
    }

    /**
     * validating a given exercise
     *
     * @param exercise dto object to validate
     * @throws sepm.ss15.grp16.service.exception.ValidationException
     */
    @Override
    public void validate(Exercise exercise) throws sepm.ss15.grp16.service.exception.ValidationException {
        LOGGER.debug("validating exericse in service layer");
        if (exercise == null)
            throw new ValidationException("validation not passed. exercise is null");

        if (exercise.getName().equals("") || exercise.getName() == null || exercise.getName().isEmpty())
            throw new ValidationException("name can not be null");

        if (exercise.getCalories() <= 0)
            throw new ValidationException("validation not passed. calories can not be lower or equal to 0");
    }

    /**
     * returning a list of all exercises for the given user
     * inclusive the system exercises
     *
     * @param user the user to search for in the exercises
     * @return list of all exercises according to the given user and system
     * @throws ServiceException if there are any complications with the finding method
     */
    public List<Exercise> getExercisesByUserInclSystem(User user) throws ServiceException {
        List<Exercise> allExercises = this.findAll();
        List<Exercise> userExercises = new ArrayList<>();
        for (Exercise e : allExercises) {
            if (e.getUser() == null || e.getUser() == user)
                userExercises.add(e);
        }

        return userExercises;
    }

    /**
     * returning a list of all exercises only for the given user
     * system exercises will not be included in this list
     *
     * @param user the user to search for in the exercises
     * @return list of all exercises according to the given user
     * @throws ServiceException if there are any complications with the finding method
     */
    public List<Exercise> getExerciseByUserONLY(User user) throws ServiceException {
        List<Exercise> allExercises = this.findAll();
        List<Exercise> userExercises = new ArrayList<>();
        for (Exercise e : allExercises) {
            if (e.getUser() == user)
                userExercises.add(e);
        }
        return userExercises;
    }

    /**
     * get all exercises which train only endurance
     *
     * @return a list of all exercises with endurance purposes
     * @throws ServiceException
     */
    public List<Exercise> getAllEnduranceExercises() throws ServiceException {
        try {
            return exerciseDAO.getAllEnduranceExercises();
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * get all exercises which train only strength
     *
     * @return a list of all exercises with strength purposes
     * @throws ServiceException
     */
    public List<Exercise> getAllStrengthExercises() throws ServiceException {
        try {
            return exerciseDAO.getAllStrengthExercises();
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * get all exercises which train only balance
     *
     * @return a list of all exercises with balance purposes
     * @throws ServiceException
     */
    public List<Exercise> getAllBalanceExercises() throws ServiceException {
        try {
            return exerciseDAO.getAllBalanceExercises();
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * get all exercises which train only flexibility
     *
     * @return a list of all exercises with flexibility purposes
     * @throws ServiceException
     */
    public List<Exercise> getAllFlexibilityExercises() throws ServiceException {
        try {
            return exerciseDAO.getAllFlexibilityExercises();
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * getting all exercises which have the given category specified
     * by the categoryID
     *
     * @param id the id of the category specifying for the exercies
     * @return a list of all exercises which are qulifyed by the categoryID
     * @throws ServiceException
     */
    public List<Exercise> getAllExercisesWithCategoryID(Integer id) throws ServiceException {
        try {
            return exerciseDAO.getAllExercisesWithCategoryID(id);
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * a method to get a list of all exercises which do not need the equipments specified by the given list
     *
     * @param equipmentCategories a list of equipment which the user does not have
     * @return a list of all exercises which do not need the given equipment
     * @throws ServiceException
     */
    public List<Exercise> getWithoutCategory(List<EquipmentCategory> equipmentCategories) throws ServiceException {
        List<Exercise> exercises = this.findAll();
        if (equipmentCategories.isEmpty()) {
            return exercises;
        } else {
            List<EquipmentCategory> userHastNotEuipment = equipmentCategories;
            List<Exercise> exercisesWithEquipment = new ArrayList<>();

            for (Exercise e : exercises) {
                boolean valid = true;
                List<AbsractCategory> categories = e.getCategories();
                for (AbsractCategory category : categories) {
                    if (userHastNotEuipment.contains(category)) {
                        valid = false;
                        break;
                    }
                }
                if (valid)
                    exercisesWithEquipment.add(e);
            }
            return exercisesWithEquipment;
        }
    }
}
