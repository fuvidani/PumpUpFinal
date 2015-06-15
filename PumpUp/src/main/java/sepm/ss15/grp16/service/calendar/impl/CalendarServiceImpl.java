package sepm.ss15.grp16.service.calendar.impl;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.calendar.Appointment;
import sepm.ss15.grp16.entity.calendar.WorkoutplanExport;
import sepm.ss15.grp16.entity.training.TrainingsSession;
import sepm.ss15.grp16.persistence.dao.calendar.CalendarDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;
import sepm.ss15.grp16.service.calendar.CalendarService;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;
import sepm.ss15.grp16.service.user.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.*;

/**
 * Created by David on 2015.05.16..
 *
 * Implementation of CalenderService interface. It provides methods of business layer.
 */
public class CalendarServiceImpl implements CalendarService {

    private static final Logger LOGGER = LogManager.getLogger(CalendarServiceImpl.class);
    /**
     * Application name.
     */
    private static final String APPLICATION_NAME =
            "PumpUp!";
    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();
    /**
     * Global instance of the scopes required by this quickstart.
     */
    private static final List<String> SCOPES =
            Arrays.asList(CalendarScopes.CALENDAR);

    /**
     * Global instance of the HTTP transport.
     */
    private static HttpTransport HTTP_TRANSPORT;

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Throwable t) {
            LOGGER.error("Error on http transport/data store factory. - " + t.getMessage());
        }
    }

    private CalendarDAO calendarDAO;
    private UserService userService;

    public CalendarServiceImpl(CalendarDAO calendarDAO) {
        this.calendarDAO = calendarDAO;
    }

    /**
     * Authorizes the user with his google account in the browser.
     * @return Credential Object with access token
     * @throws Exception
     */
    public static Credential authorize() throws Exception {
        // Load client secrets.
        InputStream in =
                CalendarServiceImpl.class.getClassLoader().getResourceAsStream("calendar/google_export/client_secret.json");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setAccessType("offline")
                        .build();
        Credential credential = null;

        try {
            credential = new AuthorizationCodeInstalledApp(
                    flow, new LocalServerReceiver()).authorize("user");
        } catch (Exception e) {
            LOGGER.error("Failed on authorizing. - " + e.getMessage());
            throw new ServiceException("Failed on authorizing.");
        }

        return credential;
    }

    /**
     * Build and return an authorized Calendar client service.
     *
     * @return an authorized Calendar client service
     * @throws IOException
     */
    public static com.google.api.services.calendar.Calendar
    getCalendarService() throws Exception {
        Credential credential = null;
        try {
            credential = authorize();
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
        return new com.google.api.services.calendar.Calendar.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
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
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Asks the persistence for all appointments for logged-in-user, deleted appointments are ignored.
     *
     * @return List of all appointments for logged-in-user returned from the persistence.
     * @throws ServiceException if there are complications in the service or persistence tier.
     */
    @Override
    public List<Appointment> findAll() throws ServiceException {
        LOGGER.info("Finding all appointments for logged-in-user in service..");

        try {
            List<Appointment> filteredAppointments = new ArrayList<>();

            for (Appointment appointment : calendarDAO.findAll()) {
                if (appointment.getUser_id().equals(userService.getLoggedInUser().getId()) || appointment.getUser_id() == null) {
                    filteredAppointments.add(appointment);
                }
            }

            return filteredAppointments;

        } catch (PersistenceException e) {
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
        } catch (PersistenceException e) {
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
        } catch (PersistenceException e) {
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
    public void exportToCalendar(WorkoutplanExport workoutplanExport) throws ServiceException {
        Date date = workoutplanExport.getDatum();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        for (int i = 0; i < workoutplanExport.getTrainingsplan().getDuration(); i++) {
            for (TrainingsSession session : workoutplanExport.getTrainingsplan().getTrainingsSessions()) {
                boolean set = false;

                while (!set) {
                    for (DayOfWeek day : workoutplanExport.getDays()) {

                        int dayNum;
                        if (day.getValue() + 1 < 8) {
                            dayNum = day.getValue() + 1;
                        } else {
                            dayNum = 1;
                        }
                        if (dayNum == cal.get(Calendar.DAY_OF_WEEK)) {
                            this.create(new Appointment(null, cal.getTime(), session.getId_session(), userService.getLoggedInUser().getUser_id(), false, false));
                            set = true;
                            cal.add(Calendar.DATE, 1);
                            break;
                        }
                    }
                    if (!set) {
                        cal.add(Calendar.DATE, 1);
                    }
                }
            }

        }
    }

    /**
     * Catches an event from JS calendar and executes the update in the DAO layer.
     */
    @Override
    public void updateEvent(int appointmentID, String newDate) throws ServiceException {
        LOGGER.debug("event updated: id: " + appointmentID + ", new date: " + newDate);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = formatter.parse(newDate);
        } catch (ParseException e) {
            throw new ServiceException(e);
        }

        try {
            Appointment appointment = calendarDAO.searchByID(appointmentID);
            appointment.setDatum(date);

            calendarDAO.update(appointment);

        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Removes all appointments in service layer.
     */
    @Override
    public void deleteAllAppointments() throws ServiceException {
        try {
            for (Appointment appointment : calendarDAO.findAll()) {
                calendarDAO.delete(appointment);
            }
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Returns the current appointment.
     *
     * @return current Appointment. if calendar is empty or there is no appointment after today or
     * all appointments after today are already trained, returns null
     * @throws ServiceException
     */
    @Override
    public Appointment getCurrentAppointment() throws ServiceException {
        List<Appointment> allAppointment = this.findAll();
        Appointment currentAppointment = null;

        //filter old appointments
        for (Appointment appointment : this.findAll()) {
            Calendar appointmentDate = Calendar.getInstance();
            appointmentDate.setLenient(false);
            appointmentDate.setTime(appointment.getDatum());
            appointmentDate.set(Calendar.HOUR_OF_DAY, 0);
            appointmentDate.set(Calendar.MINUTE, 0);
            appointmentDate.set(Calendar.SECOND, 0);
            appointmentDate.set(Calendar.MILLISECOND, 0);

            Calendar today = Calendar.getInstance();
            today.setLenient(false);
            today.set(Calendar.HOUR_OF_DAY, 0);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);
            today.set(Calendar.MILLISECOND, 0);


            if (appointmentDate.before(today)) {
                allAppointment.remove(appointment);
            }
        }

        //search for next appointment
        for (Appointment appointment : allAppointment) {
            if (currentAppointment == null || appointment.getDatum().before(currentAppointment.getDatum())) {
                if (!appointment.getIsTrained()) {
                    currentAppointment = appointment;
                }
            }
        }

        return currentAppointment;
    }

    /**
     * Exports all appointments to google calendar.
     */
    @Override
    public void exportToGoogle() throws ServiceException {
        // Build a new authorized API client service.
        // Note: Do not confuse this class with the
        //   com.google.api.services.calendar.model.Calendar class.
        com.google.api.services.calendar.Calendar service =
                null;
        try {
            service = getCalendarService();
        } catch (Exception e) {
            throw new ServiceException(e);
        }

        try {
            for (Appointment appointment : findAll()) {
                Event event = new Event()
                        .setSummary(appointment.getSessionName())
                        .setDescription(appointment.getSetNames());

                Date startDate = appointment.getDatum();
                Date endDate = new Date(startDate.getTime() + 86400000); // An all-day event is 1 day (or 86400000 ms) long

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String startDateStr = dateFormat.format(startDate);
                String endDateStr = dateFormat.format(endDate);

                // Out of the 6 methods for creating a DateTime object with no time element, only the String version works
                DateTime startDateTime = new DateTime(startDateStr);
                DateTime endDateTime = new DateTime(endDateStr);

                // Must use the setDate() method for an all-day event (setDateTime() is used for timed events)
                EventDateTime startEventDateTime = new EventDateTime().setDate(startDateTime);
                EventDateTime endEventDateTime = new EventDateTime().setDate(endDateTime);

                event.setStart(startEventDateTime);
                event.setEnd(endEventDateTime);

                String calendarId = "primary";
                try {
                    event = service.events().insert(calendarId, event).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                LOGGER.info("Event created: %s\n", event.getHtmlLink());
            }
        } catch (ServiceException e) {
            throw new ServiceException(e);
        }

    }

    /**
     * Sets an appointment with id as trained.
     * @param appointment_id to be set as trained
     * @throws ServiceException
     */
    @Override
    public void setAppointmentAsTrained(int appointment_id) throws ServiceException {
        try {
            Appointment appointment = calendarDAO.searchByID(appointment_id);
            appointment.setIsTrained(true);
            calendarDAO.update(appointment);
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }


    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
