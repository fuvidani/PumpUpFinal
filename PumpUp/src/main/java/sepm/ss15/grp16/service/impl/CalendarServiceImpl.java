package sepm.ss15.grp16.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.Appointment;
import sepm.ss15.grp16.entity.WorkoutplanExport;
import sepm.ss15.grp16.entity.training.TrainingsSession;
import sepm.ss15.grp16.persistence.dao.CalendarDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;
import sepm.ss15.grp16.service.CalendarService;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;

import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by David on 2015.05.16..
 */
public class CalendarServiceImpl implements CalendarService{

    private CalendarDAO calendarDAO;
    private static final Logger LOGGER = LogManager.getLogger(CalendarServiceImpl.class);

    public CalendarServiceImpl(CalendarDAO calendarDAO) {
        this.calendarDAO = calendarDAO;
    }

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
    public Appointment create(Appointment appointment) throws ServiceException {
        LOGGER.info("Creating a new appointment in service..");

        this.validate(appointment);

        try {
            return calendarDAO.create(appointment);
        } catch (PersistenceException e){
            throw new ServiceException(e);
        }
    }

    /**
     * Asks the persistence for all appointments, deleted appointments are ignored.
     *
     * @return List of all appointments returned form the persistence
     * @throws ServiceException if there are complications in the service or persistence tier.
     */
    @Override
    public List<Appointment> findAll() throws ServiceException {
        LOGGER.info("Finding all appointments in service..");

        try {
            return calendarDAO.findAll();
        }catch (PersistenceException e){
            throw new ServiceException(e);
        }
    }

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
    public Appointment update(Appointment appointment) throws ServiceException {
        LOGGER.info("Updating appointment in service..");

        this.validate(appointment);

        try {
            return calendarDAO.update(appointment);
        }catch (PersistenceException e){
            throw new ServiceException(e);
        }
    }

    /**
     * Sends a given appointment to the persistence for deleting.
     *
     * @param appointment which shall be deleted,
     *                    must not be null, id must not be null and must not be changed
     * @throws ValidationException if the given appointment can't be parse by validate.
     * @throws ServiceException    if there are complications in the service or persistence tier.
     */
    @Override
    public void delete(Appointment appointment) throws ServiceException {
        LOGGER.info("Deleting appointment in service..");

        this.validate(appointment);

        try {
            calendarDAO.delete(appointment);
        }catch (PersistenceException e){
            throw new ServiceException(e);
        }
    }

    /**
     * Checks if the appointment conforms all restrictions in its properties.
     *
     * @param appointment to check
     * @throws ValidationException if the appointment is not valid.
     */
    @Override
    public void validate(Appointment appointment) throws ValidationException {
        LOGGER.info("Validating appointment values..");

        if (appointment == null) throw new ValidationException("Appointment is null.");

    }

    /**
     * Exports the sessions from given trainingsplan into the calendar
     *
     * @param workoutplanExport dto, that contains the trainingsplan, allowed days, and start date.
     *                          NOTE: at least one day has to be allowed!!
     */
    @Override
    public void exportToCalendar(WorkoutplanExport workoutplanExport) throws ServiceException{
        Date date = workoutplanExport.getDatum();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        for (TrainingsSession session: workoutplanExport.getTrainingsplan().getTrainingsSessions()){
            for (DayOfWeek day: workoutplanExport.getDays()){
                if (day.equals(cal.DAY_OF_WEEK)){
                    this.create(new Appointment(null,cal.getTime(),session.getId_session(),workoutplanExport.getTrainingsplan().getUser().getUser_id(),false));
                    break;
                }
                cal.roll(Calendar.DATE, true); //increment day
            }
        }
    }

    /**
     * chatches an event from JS calendar
     */
    @Override
    public void updateEvent() {

    }
}
