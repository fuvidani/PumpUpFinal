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
import com.google.api.client.util.store.FileDataStoreFactory;
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
 */
public class CalendarServiceImpl implements CalendarService {

    private static final Logger LOGGER = LogManager.getLogger(CalendarServiceImpl.class);
    private CalendarDAO calendarDAO;
    private UserService userService;

    /** Application name. */
    private static final String APPLICATION_NAME =
            "PumpUp!";

    /** Directory to store user credentials. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/calendar-api-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart. */
    private static final List<String> SCOPES =
            Arrays.asList(CalendarScopes.CALENDAR);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace(); //TODO change
        }
    }

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

        for (int i = 0; i < workoutplanExport.getTrainingsplan().getDuration(); i++){
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
                            this.create(new Appointment(null, cal.getTime(), session.getId_session(), userService.getLoggedInUser().getUser_id(), false));
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

       /* while (true) {
            int day;

            if (cal.get(Calendar.DAY_OF_WEEK) == 1){
                day = 7;
            } else {
                day = cal.get(Calendar.DAY_OF_WEEK) - 1;
            }

            if (day > workoutplanExport.getDays()[0].getValue()) {
                cal.add(Calendar.DATE, 1); //increment day
            } else {
                break;
            }
        }


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
                            this.create(new Appointment(null, cal.getTime(), session.getId_session(), workoutplanExport.getTrainingsplan().getUser().getUser_id(), false));
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

            if (workoutplanExport.getTrainingsplan().getTrainingsSessions().size() < workoutplanExport.getDays().length) {
                while (true) {
                    if (cal.get(Calendar.DAY_OF_WEEK) > workoutplanExport.getDays()[0].getValue() + 1) {
                        cal.add(Calendar.DATE, 1); //increment day
                    } else {
                        break;
                    }
                }
            }
        }*/
    }

    /**
     * chatches an event from JS calendar
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

    @Override
    public void deleteAllAppointments() throws ServiceException{
        try {
            for (Appointment appointment: calendarDAO.findAll()){
                calendarDAO.delete(appointment);
            }
        } catch (PersistenceException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public Appointment getCurrentAppointment() throws ServiceException{
        List<Appointment> allAppointment = this.findAll();
        Appointment currentAppointment = null;

        //filter old appointments
        for (Appointment appointment: this.findAll()){
            Calendar appointmentDate = Calendar.getInstance();
            appointmentDate.setLenient(false);
            appointmentDate.setTime(appointment.getDatum());
            appointmentDate.set(Calendar.HOUR_OF_DAY, 0);
            appointmentDate.set(Calendar.MINUTE,0);
            appointmentDate.set(Calendar.SECOND,0);
            appointmentDate.set(Calendar.MILLISECOND,0);

            Calendar today = Calendar.getInstance();
            today.setLenient(false);
            today.set(Calendar.HOUR_OF_DAY, 0);
            today.set(Calendar.MINUTE,0);
            today.set(Calendar.SECOND,0);
            today.set(Calendar.MILLISECOND,0);


            if (appointmentDate.before(today)){
                allAppointment.remove(appointment);
            }
        }

        //search for next appointment
        for (Appointment appointment: allAppointment){
            if (currentAppointment == null || appointment.getDatum().before(currentAppointment.getDatum())){
                currentAppointment = appointment;
            }
        }

        return currentAppointment;
    }

    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
                CalendarServiceImpl.class.getResourceAsStream("client_secret.json");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType("offline")
                        .build();
        Credential credential = null;
        try {
            credential = new AuthorizationCodeInstalledApp(
                    flow, new LocalServerReceiver()).authorize("user");
        } catch (Exception e) {
            e.printStackTrace(); //TODO change
        }
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Calendar client service.
     * @return an authorized Calendar client service
     * @throws IOException
     */
    public static com.google.api.services.calendar.Calendar
    getCalendarService() throws IOException {
        Credential credential = authorize();
        return new com.google.api.services.calendar.Calendar.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    @Override
    public void exportToGoogle(){
        // Build a new authorized API client service.
        // Note: Do not confuse this class with the
        //   com.google.api.services.calendar.model.Calendar class.
        com.google.api.services.calendar.Calendar service =
                null;
        try {
            service = getCalendarService();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            for (Appointment appointment: findAll()){
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
                System.out.printf("Event created: %s\n", event.getHtmlLink());
            }
        } catch (ServiceException e) {
            e.printStackTrace();  //TODO change
        }

    }


    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
