package sepm.ss15.grp16.service.calendar;

import sepm.ss15.grp16.entity.calendar.Appointment;
import sepm.ss15.grp16.entity.calendar.WorkoutplanExport;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;

import java.util.List;

/**
 * Created by David on 2015.05.16..
 */
public interface CalendarService extends Service<Appointment> {
    /**
     * Validates the given appointment and sends it to the the persistence for creation.
     * The ID is set to the new generated one.
     *
     * @param appointment which shall be sent to the persistence.
     *                    must not be null, id must be null
     * @return the given appointment for further usage
     * @throws ValidationException if the given appointment can't be parse by validate.
     * @throws ServiceException    if there are complications in the service or persistence tier.
     */
    @Override
    Appointment create(Appointment appointment) throws ServiceException;

    /**
     * Asks the persistence for all appointments, deleted appointments are ignored.
     *
     * @return List of all appointments returned form the persistence
     * @throws ServiceException if there are complications in the service or persistence tier.
     */
    @Override
    List<Appointment> findAll() throws ServiceException;

    /**
     * Validates an appointment and sends it to the persistence for updating.
     *
     * @param appointment which shall be updated
     *                    must not be null, id must not be null and must not be changed
     * @return given appointment with updated values
     * @throws ValidationException if the given appointment can't be parse by validate.
     * @throws ServiceException    if there are complications in the service or persistence tier.
     */
    @Override
    Appointment update(Appointment appointment) throws ServiceException;

    /**
     * Sends a given appointment to the persistence for deleting.
     *
     * @param appointment which shall be deleted,
     *                    must not be null, id must not be null and must not be changed
     * @throws ValidationException if the given appointment can't be parse by validate.
     * @throws ServiceException    if there are complications in the service or persistence tier.
     */
    @Override
    void delete(Appointment appointment) throws ServiceException;

    /**
     * Checks if the appointment conforms all restrictions in its properties.
     *
     * @param appointment to check
     * @throws ValidationException if the appointment is not valid.
     */
    @Override
    void validate(Appointment appointment) throws ValidationException;

    /**
     * Exports the sessions from given trainingsplan into the calendar
     *
     * @param workoutplanExport dto, that contains the trainingsplan, allowed days, and start date.
     *                          NOTE: at least one day has to be allowed!!
     */
    void exportToCalendar(WorkoutplanExport workoutplanExport) throws ServiceException;

    /**
     * chatches an event from JS calendar
     */
    void updateEvent(int appointmentID, String newDate) throws ServiceException;

    /**
     * removes all appointments in service
     */
    void deleteAllAppointments() throws ServiceException;

    /**
     * Returns the current appointment
     *
     * @return current Appointment. if calendar is empty or there is no appointment after today returns null.
     * @throws ServiceException
     */
    Appointment getCurrentAppointment() throws ServiceException;

    /**
     * exports all appointments to google calendar
     */
    void exportToGoogle() throws ServiceException;
}
