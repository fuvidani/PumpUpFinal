package sepm.ss15.grp16.persistence.dao;

import sepm.ss15.grp16.entity.DTO;
import sepm.ss15.grp16.entity.Exercise;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.List;

/**
 * Created by Maximilian on 03.05.2015.
 */
public interface DAO<T extends DTO> {

    /**
     * creating a new DTO from the generic type T
     * @param dto which shall be inserted into the underlying persistance layer.
     *                 must not be null, id must not be null
     * @return the given dto for further usage
     * @throws PersistenceException if there are complications with the persitance layer
     */
    T create(T dto) throws PersistenceException;

    /**
     * finding all dto stored in the underlying layer
     * @return List of all dtos stored so far
     * @throws PersistenceException  if there are complications with the persitance layer
     */
    List<T> findAll() throws PersistenceException;

    /**
     * @param id dto to search for
     * @return if the dto exists it is returned, otherwise null is the result.
     * @throws PersistenceException  if there are complications with the persitance layer
     */
    T searchByID(int id) throws PersistenceException;

    /**
     * updating one given dto with new values and returning it for further usage
     * @param dto which shall be updated
     *                 must not be null, id must not be null
     * @return given dto with updated values
     * @throws PersistenceException if there are complications with the persitance layer
     */
    T update(T dto) throws PersistenceException;

    /**
     * deleting a given exercise
     * @param dto which shall be deleted from the underlying persitance implementation layer
     * @throws PersistenceException if there are complications with the persitance layer
     */
    void delete(T dto) throws PersistenceException;
}
