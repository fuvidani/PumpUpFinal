package sepm.ss15.grp16.persistence.dao.training.helper;

import sepm.ss15.grp16.entity.training.helper.DTOHelper;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.List;

/**
 * Author: Lukas
 * Date: 12.05.2015
 */
public interface TrainingsHelperDAO<T extends DTOHelper> {

    /**
     * creating a new DTOHelper from the generic type T
     *
     * @param dtohelper which shall be inserted into the underlying persistance layer.
     *                  must not be null, id must not be null
     * @param id_partof id of the dto, which contains the dtohelper to create
     * @return the given dto for further usage
     * @throws PersistenceException if there are complications with the persitance layer
     */
    T create(T dtohelper, int id_partof) throws PersistenceException;

    /**
     * finding all dto stored in the underlying layer
     *
     * @return List of all dtos stored so far
     * @throws PersistenceException if there are complications with the persitance layer
     */
    List<T> findAll() throws PersistenceException;

    /**
     * @param id dto to search for
     * @return if the dto exists it is returned, otherwise null is the result.
     * @throws PersistenceException if there are complications with the persitance layer
     */
    T searchByID(int id) throws PersistenceException;

    /**
     * updating one given dto with new values and returning it for further usage
     *
     * @param dtohelper which shall be updated
     *                  must not be null, id must not be null
     * @param id_partof id of the dto, which contains the dtohelper to create
     * @return given dto with updated values
     * @throws PersistenceException if there are complications with the persitance layer
     */
    T update(T dtohelper, int id_partof) throws PersistenceException;

    /**
     * deleting a given exercise
     *
     * @param id_partof id of the dto, which contains the dtohelper to create
     * @param dtohelper which shall be deleted from the underlying persitance implementation layer
     * @throws PersistenceException if there are complications with the persitance layer
     */
    void delete(T dtohelper) throws PersistenceException;

    /**
     * find all trainingssession of an user
     *
     * @param user to search, can be null, UID must not be null
     * @return found sessions, null if nothing was found
     * @throws PersistenceException if there are complications with the persitance layer
     */
    List<T> searchByUser(User user) throws PersistenceException;
}
