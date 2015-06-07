package sepm.ss15.grp16.persistence.dao.calendar.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.calendar.Appointment;
import sepm.ss15.grp16.entity.training.helper.ExerciseSet;
import sepm.ss15.grp16.persistence.dao.calendar.CalendarDAO;
import sepm.ss15.grp16.persistence.dao.training.TrainingsSessionDAO;
import sepm.ss15.grp16.persistence.database.DBHandler;
import sepm.ss15.grp16.persistence.exception.DBException;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 2015.05.15..
 */
public class H2CalendarDAOImpl implements CalendarDAO {

    private static final Logger LOGGER = LogManager.getLogger(H2CalendarDAOImpl.class);
    private PreparedStatement createStm;
    private PreparedStatement findAllStm;
    private PreparedStatement searchByIDStm;
    private PreparedStatement updateStm;
    private PreparedStatement deleteStm;
    private TrainingsSessionDAO trainingsSessionDAO;
    private Connection connection;

    public H2CalendarDAOImpl(DBHandler handler) throws PersistenceException {

        try {
            this.connection = handler.getConnection();

            this.createStm = connection.prepareStatement("INSERT INTO appointment VALUES (?,?,?,?,?)");
            this.findAllStm = connection.prepareStatement("SELECT * FROM appointment WHERE isDeleted = FALSE");
            this.searchByIDStm = connection.prepareStatement("SELECT * FROM appointment WHERE appointment_id = ?");
            this.updateStm = connection.prepareStatement("UPDATE appointment SET datum = ?, session_id = ?, user_id = ?, isDeleted = ? WHERE appointment_id = ?");
            this.deleteStm = connection.prepareStatement("UPDATE appointment SET isDeleted = TRUE WHERE appointment_id = ?");

        } catch (SQLException e) {
            throw new PersistenceException("Failed to prepare statement.", e);
        } catch (DBException e) {
            throw new PersistenceException("Failed to get db connection.", e);
        }
    }

    /**
     * Creates a new appointment.
     *
     * @param appointment which shall be inserted into the underlying persistance layer.
     *                    must not be null, id must be null
     * @return the given appointment for further usage
     * @throws PersistenceException if there are complications with the persitance layer
     */
    @Override
    public Appointment create(Appointment appointment) throws PersistenceException {
        LOGGER.info("Creating a new appointment in db.. " + appointment);
        try {
            if (appointment == null) {
                LOGGER.error("Create parameter (appointment) was null.");
                throw new PersistenceException("Appointment to be create must not be null");
            }

            Statement appointmentNextValStm = connection.createStatement();
            ResultSet rs_appointmentNextVal = appointmentNextValStm.executeQuery("SELECT NEXTVAL('appointment_seq')");
            rs_appointmentNextVal.next();
            appointment.setId(rs_appointmentNextVal.getInt(1));

            createStm.setInt(1, appointment.getId());
            createStm.setDate(2, new java.sql.Date(appointment.getDatum().getTime()));
            createStm.setInt(3, appointment.getSession_id());
            createStm.setInt(4, appointment.getUser_id());
            createStm.setBoolean(5, appointment.getIsDeleted());

            createStm.execute();
        } catch (SQLException e) {
            LOGGER.error("Failed to create record into appointment table. - " + e.getMessage());
            throw new PersistenceException("Failed to create record into appointment table.", e);
        }

        LOGGER.info("Record successfully created in appointment table.");
        return appointment;
    }

    /**
     * Finds all appointments in the persistence, deleted appointments are ignored.
     *
     * @return List of all appointments stored in the persistence
     * @throws PersistenceException if there are complications with the persitance layer
     */
    @Override
    public List<Appointment> findAll() throws PersistenceException {
        LOGGER.info("Finding all records in appointment table..");
        try {
            ResultSet rs_findAll = findAllStm.executeQuery();
            List<Appointment> result = new ArrayList<>();

            while (rs_findAll.next()) {
                Appointment appointment = new Appointment(rs_findAll.getInt(1), rs_findAll.getDate(2), rs_findAll.getInt(3), rs_findAll.getInt(4), rs_findAll.getBoolean(5));

                appointment.setSessionName(trainingsSessionDAO.searchByID(appointment.getSession_id()).getName());
                String setNames = "";
                for (ExerciseSet exerciseSet : trainingsSessionDAO.searchByID(appointment.getSession_id()).getExerciseSets()) {
                    setNames += (exerciseSet.getRepeat() + " " + exerciseSet.getExercise().getName() + '\n');
                }
                appointment.setSetNames(setNames);

                appointment.setSession(trainingsSessionDAO.searchByID(appointment.getSession_id()));

                result.add(appointment);
            }

            LOGGER.info("All record successfully read from appointment table.");
            return result;

        } catch (SQLException e) {
            LOGGER.error("Failed to find all records in appointment table. - " + e.getMessage());
            throw new PersistenceException("Failed to find all records in appointment table.", e);
        }

    }

    /**
     * @param id appointment id to search for
     * @return if the appointment exists it is returned, otherwise null is the result.
     * @throws PersistenceException if there are complications with the persitance layer
     */
    @Override
    public Appointment searchByID(int id) throws PersistenceException {
        LOGGER.info("Searching record in appointment table with id: " + id + "..");

        try {
            searchByIDStm.setInt(1, id);
            ResultSet rs_searchByID = searchByIDStm.executeQuery();
            rs_searchByID.next();

            Appointment foundAppointment = null;
            foundAppointment = new Appointment(rs_searchByID.getInt(1), rs_searchByID.getDate(2), rs_searchByID.getInt(3), rs_searchByID.getInt(4), rs_searchByID.getBoolean(5));

            if (foundAppointment != null){
                foundAppointment.setSessionName(trainingsSessionDAO.searchByID(foundAppointment.getSession_id()).getName());
                String setNames = "";
                for (ExerciseSet exerciseSet : trainingsSessionDAO.searchByID(foundAppointment.getSession_id()).getExerciseSets()) {
                    setNames += (exerciseSet.getRepeat() + " " + exerciseSet.getExercise().getName() + '\n');
                }
                foundAppointment.setSetNames(setNames);

                foundAppointment.setSession(trainingsSessionDAO.searchByID(foundAppointment.getSession_id()));

            }

            LOGGER.info("Appointment with id: " + id + ", successfully read from database." + foundAppointment);
            return foundAppointment;
        } catch (SQLException e) {
            LOGGER.error("Failed to search for an appointment with id: " + id + ". - " + e.getMessage());
            throw new PersistenceException("Failed to search for an appointment with id: " + id + ".");
        }
    }

    /**
     * Updating a given appointment with new values in the persistence.
     *
     * @param appointment which shall be updated
     *                    must not be null, id must not be null and must not be changed
     * @return given appointment with updated values
     * @throws PersistenceException if there are complications with the persitance layer
     */
    @Override
    public Appointment update(Appointment appointment) throws PersistenceException {
        LOGGER.info("Updating record in appointment table..");
        try {
            if (appointment == null) {
                LOGGER.error("Update parameter (appointment) was null.");
                throw new PersistenceException("Appointment to be updated must not be null");
            }

            updateStm.setDate(1, new java.sql.Date(appointment.getDatum().getTime()));
            updateStm.setInt(2, appointment.getSession_id());
            updateStm.setInt(3, appointment.getUser_id());
            updateStm.setBoolean(4, appointment.getIsDeleted());
            updateStm.setInt(5, appointment.getId());

            updateStm.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Failed to update record in appointment table. - " + e.getMessage());
            throw new PersistenceException("Failed to update record in appointment table.", e);
        }

        LOGGER.info("Record successfully updated in appointment table. " + appointment);
        return appointment;
    }

    /**
     * Deleting a given appointment in the persistence.
     *
     * @param appointment which shall be deleted,
     *                    must not be null, id must not be null and must not be changed
     * @throws PersistenceException if there are complications with the persitance layer
     */
    @Override
    public void delete(Appointment appointment) throws PersistenceException {
        LOGGER.info("Deleting appointment in appointment table..");
        try {
            if (appointment == null) {
                LOGGER.error("Delete parameter (appointment) was null.");
                throw new PersistenceException("Appointment to be deleted must not be null");
            }

            deleteStm.setInt(1, appointment.getId());
            deleteStm.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Failed to delete record in appointment table. - " + e.getMessage());
            throw new PersistenceException("Failed to delete record in appointment table.", e);
        }

        LOGGER.info("Appointment successfully deleted in appointment table.");
    }

    public void setSessionDAO(TrainingsSessionDAO trainingsSessionDAO) {
        this.trainingsSessionDAO = trainingsSessionDAO;
    }
}
