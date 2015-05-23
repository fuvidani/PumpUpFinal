package sepm.ss15.grp16.persistence.dao;

import sepm.ss15.grp16.entity.DTO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.List;

/**
 * Created by Maximilian on 03.05.2015.
 */
public interface DAO<T extends DTO> {

    /**
     * Creates the given DTO of the generic type in the persictence and sets the new ID to the DTO.
     *
     * @param dto which shall be inserted into the underlying persistance layer.
     *            must not be null, id must be null
     * @return the given dto for further usage
     * @throws PersistenceException if there are complications with the persitance layer
     */
    T create(T dto) throws PersistenceException;

    /**
     * Finds all DTOs of the generic type in the persistence, deleted DTOs are ignored.
     *
     * @return List of all DTOs stored in the persistence
     * @throws PersistenceException if there are complications with the persitance layer
     */
    List<T> findAll() throws PersistenceException;

    /**
     * @param id DTO of the generic Type to search for
     * @return if the dto exists it is returned, otherwise null is the result.
     * @throws PersistenceException if there are complications with the persitance layer
     */
    T searchByID(int id) throws PersistenceException;

    /**
     * Updating a given DTO of the generic type with new values in the persistence.
     *
     * @param dto which shall be updated
     *            must not be null, id must not be null and must not be changed
     * @return given dto with updated values
     * @throws PersistenceException if there are complications with the persitance layer
     */
    T update(T dto) throws PersistenceException;

    /**
     * Deleting a given DTO ot the generic type in the persistence.
     *
     * @param dto which shall be deleted,
     *            must not be null, id must not be null and must not be changed
     * @throws PersistenceException if there are complications with the persitance layer
     */
    void delete(T dto) throws PersistenceException;
}
