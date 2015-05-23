package sepm.ss15.grp16.service;

import sepm.ss15.grp16.entity.DTO;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;

import java.util.List;

/**
 * Created by Maximilian on 04.05.2015.
 */
public interface Service<T extends DTO> {

    /**
     * Validates the given DTO of the generic type and sends it to the the persistence for creation.
     * The ID is set to the new generated one.
     *
     * @param dto which shall be sent to the persistence.
     *            must not be null, id must be null
     * @return the given dto for further usage
     * @throws ValidationException if the given DTO can't be parse by validate.
     * @throws ServiceException    if there are complications in the service or persistence tier.
     */
    T create(T dto) throws ServiceException;


    /**
     * Asks the persistence for all DTOs of the generic type, deleted DTOs are ignored.
     *
     * @return List of all DTOs returned form the persistence
     * @throws ServiceException if there are complications in the service or persistence tier.
     */
    List<T> findAll() throws ServiceException;

    /**
     * Validates a DTO of the generic type and sends it to the persistence for updating.
     *
     * @param dto which shall be updated
     *            must not be null, id must not be null and must not be changed
     * @return given dto with updated values
     * @throws ValidationException if the given DTO can't be parse by validate.
     * @throws ServiceException    if there are complications in the service or persistence tier.
     */
    T update(T dto) throws ServiceException;

    /**
     * Sends a given DTO ot the generic type to the persistence for deleting.
     *
     * @param dto which shall be deleted,
     *            must not be null, id must not be null and must not be changed
     * @throws ValidationException if the given DTO can't be parse by validate.
     * @throws ServiceException    if there are complications in the service or persistence tier.
     */
    void delete(T dto) throws ServiceException;


    /**
     * Checks if the DTO conforms all restrictions in its properties.
     *
     * @param dto to check
     * @throws ValidationException if the dto is not valid.
     */
    void validate(T dto) throws ValidationException;
}
