package sepm.ss15.grp16.service;

import sepm.ss15.grp16.entity.exercise.EquipmentCategory;
import sepm.ss15.grp16.entity.exercise.Exercise;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;

import java.util.List;

/**
 * Created by lukas on 01.05.2015.
 */
public interface ExerciseService extends Service<Exercise>{

    /**
     * creating a new exercise
     * @param exercise which shall be inserted into the underlying persistance layer
     *                 must not be null, id must not be null
     * @return the given exercise for further usage
     * @throws ServiceException if there are complications with the persitance layer
     */
    Exercise create(Exercise exercise )throws ServiceException;

    /**
     * finding all exercises stored in the underlying layer
     * @return List of all exercises stored so far
     * @throws ServiceException  if there are complications with the persitance layer
     */
    List<Exercise> findAll() throws ServiceException;

    /**
     * @param id exercixe to search for
     * @return if the exercixe exists it is returned, otherwise null is the result.
     * @throws ServiceException  if there are complications with the persitance layer
     */
    Exercise searchByID(int id) throws ServiceException;

    /**
     * updating one given exercise with new values and returning it for further usage
     * @param exercise which shall be updated
     *                 must not be null, id must not be null
     * @return given exercise with updated values
     * @throws ServiceException if there are complications with the persitance layer
     */
    Exercise update(Exercise exercise)throws ServiceException;

    /**
     * returning a list of all exercises for the given user
     * inclusive the system exercises
     * @param user the user to search for in the exercises
     * @return list of all exercises according to the given user and system
     * @throws ServiceException if there are any complications with the finding method
     */
     List<Exercise> getExercisesByUserInclSystem(User user) throws ServiceException;

    /**
     * returning a list of all exercises only for the given user
     * system exercises will not be included in this list
     * @param user the user to search for in the exercises
     * @return list of all exercises according to the given user
     * @throws ServiceException if there are any complications with the finding method
     */
     List<Exercise> getExerciseByUserONLY(User user) throws ServiceException;

    /**
     * get all exercises which train only endurance
     * @return a list of all exercises with endurance purposes
     * @throws ServiceException
     */
     List<Exercise> getAllEnduranceExercises() throws ServiceException;

    /**
     * get all exercises which train only strength
     * @return a list of all exercises with strength purposes
     * @throws ServiceException
     */
     List<Exercise> getAllStrengthExercises()throws ServiceException;

    /**
     * get all exercises which train only balance
     * @return a list of all exercises with balance purposes
     * @throws ServiceException
     */
     List<Exercise> getAllBalanceExercises()throws ServiceException;


    /**
     * get all exercises which train only flexibility
     * @return a list of all exercises with flexibility purposes
     * @throws ServiceException
     */
     List<Exercise> getAllFlexibilityExercises()throws ServiceException;


    /**
     * deleting a given exercise
     * @param exercise which shall be deleted from the underlying persitance implementation layer
     * @throws ServiceException if there are complications with the persitance layer
     */
    void delete(Exercise exercise) throws ServiceException;

    /**
     * validating a given exercise
     * @param exercise dto object to validate
     * @throws sepm.ss15.grp16.service.exception.ValidationException
     */
    void validate(Exercise exercise) throws ValidationException;

    /**
     * getting all exercises which have the given category specified
     * by the categoryID
     * @param id the id of the category specifying for the exercies
     * @return a list of all exercises which are qulifyed by the categoryID
     * @throws ServiceException
     */
     List<Exercise> getAllExercisesWithCategoryID(Integer id)throws ServiceException;

    /**
     * a method to get a list of all exercises which do not need the equipments specified by the given list
     * @param equipmentCategories a list of equipment which the user does not have
     * @return a list of all exercises which do not need the given equipment
     * @throws ServiceException
     */
    List<Exercise> getWithoutCategory(List<EquipmentCategory> equipmentCategories)throws ServiceException;

}
