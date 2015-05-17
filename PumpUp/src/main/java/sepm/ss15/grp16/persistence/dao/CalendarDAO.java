package sepm.ss15.grp16.persistence.dao;

import sepm.ss15.grp16.entity.Appointment;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.List;

/**
 * Created by David on 2015.05.15..
 */
public interface CalendarDAO extends DAO<Appointment> {

    /**
     * Creates a new appointment.
     * @param appointment which shall be inserted into the underlying persistance layer.
     *                 must not be null, id must be null
     * @return the given appointment for further usage
     * @throws PersistenceException if there are complications with the persitance layer
     */
    Appointment create(Appointment appointment) throws PersistenceException;

    /**
     * Finds all appointments in the persistence, deleted appointments are ignored.
     * @return List of all appointments stored in the persistence
     * @throws PersistenceException if there are complications with the persitance layer
     */
    List<Appointment> findAll() throws PersistenceException;

    /**
     * @param id appointment id to search for
     * @return if the appointment exists it is returned, otherwise null is the result.
     * @throws PersistenceException if there are complications with the persitance layer
     */
    Appointment searchByID(int id) throws PersistenceException;

    /**
     * Updating a given appointment with new values in the persistence.
     * @param appointment which shall be updated
     *                 must not be null, id must not be null and must not be changed
     * @return given appointment with updated values
     * @throws PersistenceException if there are complications with the persitance layer
     */
    Appointment update(Appointment appointment) throws PersistenceException;

    /**
     * Deleting a given appointment in the persistence.
     * @param appointment which shall be deleted,
     *            must not be null, id must not be null and must not be changed
     * @throws PersistenceException if there are complications with the persitance layer
     */
    void delete(Appointment appointment) throws PersistenceException;
}
