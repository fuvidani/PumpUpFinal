package sepm.ss15.grp16.persistence.dao.training.helper.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.training.TrainingsSession;
import sepm.ss15.grp16.entity.training.Trainingsplan;
import sepm.ss15.grp16.entity.training.helper.ExerciseSet;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.persistence.dao.UserDAO;
import sepm.ss15.grp16.persistence.dao.impl.H2UserDAOImpl;
import sepm.ss15.grp16.persistence.dao.training.TrainingsplanDAO;
import sepm.ss15.grp16.persistence.dao.training.helper.ExerciseSetHelperDAO;
import sepm.ss15.grp16.persistence.dao.training.helper.TrainingsSessionHelperDAO;
import sepm.ss15.grp16.persistence.dao.training.impl.H2TrainingsplanDAOImpl;
import sepm.ss15.grp16.persistence.database.DBHandler;
import sepm.ss15.grp16.persistence.exception.DBException;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: Lukas
 * Date: 08.05.2015
 */
public class H2TrainingssessionHelperDAOImpl implements TrainingsSessionHelperDAO {

    private static final Logger LOGGER = LogManager.getLogger(H2TrainingssessionHelperDAOImpl.class);
    private final PreparedStatement ps_create;
    private final PreparedStatement ps_findAll;
    private final PreparedStatement ps_findID;
    private final PreparedStatement ps_find_ByUID;
    private final PreparedStatement ps_find_ByIDPlan;
    private final PreparedStatement ps_find_IDPlan;
    private final PreparedStatement ps_delete;
    private final PreparedStatement ps_update;
    private Connection con;
    private PreparedStatement ps_seq_TS;

    private ExerciseSetHelperDAO exerciseSetHelperDAO;
    private UserDAO userDAO;
    private TrainingsplanDAO trainingsplanDAO;

    private H2TrainingssessionHelperDAOImpl(DBHandler handler) throws PersistenceException {
        try {
            con = handler.getConnection();

            /** Trainingplan **/
            ps_create = con.prepareStatement("INSERT INTO TrainingsSession (ID_Plan, name, UID, isDeleted) " +
                    "VALUES (?, ?, ?, ?)");
            ps_findAll = con.prepareStatement("SELECT * FROM TrainingsSession WHERE isDeleted = FALSE ");

            ps_findID = con.prepareStatement("SELECT * FROM TrainingsSession WHERE ID_Session = ? AND isDeleted = FALSE");
            ps_delete = con.prepareStatement("UPDATE TrainingsSession SET isDeleted = TRUE WHERE ID_Session = ?");
            ps_update = con.prepareStatement("UPDATE TrainingsSession " +
                    "SET ID_Plan = ?, name = ?, UID = ?, isDeleted = ? WHERE ID_Session = ?");

            ps_find_ByUID = con.prepareStatement("SELECT * FROM TrainingsSession WHERE UID = ? AND isDeleted = FALSE");
            ps_find_ByIDPlan = con.prepareStatement("SELECT * FROM TrainingsSession WHERE ID_Plan = ? AND isDeleted = FALSE");
            ps_find_IDPlan = con.prepareStatement("SELECT ID_Plan FROM TrainingsSession WHERE ID_Session = ? AND isDeleted = FALSE");

            /** Sequences **/
            ps_seq_TS = con.prepareStatement("SELECT currval('seq_TS')");

        } catch (DBException | SQLException e) {
            LOGGER.error("" + e.getMessage());
            throw new PersistenceException("failed to prepare statements", e);
        }
    }

    @Override
    public TrainingsSession create(TrainingsSession session, int id_plan) throws PersistenceException {
        try {
            ps_create.setInt(1, id_plan);
            ps_create.setString(2, session.getName());
            ps_create.setObject(3, session.getUser() != null ? session.getUser().getId() : null);
            ps_create.setBoolean(4, false);

            executeUpdate(ps_create);

            ResultSet rs = executeQuery(ps_seq_TS);
            rs.next();
            session.setId(rs.getInt(1));

            if (session.getExerciseSets() != null) {
                for (ExerciseSet set : session.getExerciseSets()) {
                    exerciseSetHelperDAO.create(set, session.getId());
                }
            }

            return session;

        } catch (SQLException e) {
            LOGGER.error("" + e.getMessage());
            throw new PersistenceException("failed to prepare statements", e);
        }
    }

    @Override
    public List<TrainingsSession> findAll() throws PersistenceException {

        try {
            ResultSet rs = executeQuery(ps_findAll);
            List<TrainingsSession> sessions = new ArrayList<>();

            while (rs.next()) {
                TrainingsSession session = new TrainingsSession();
                session.setId(rs.getInt("ID_Session"));
                session.setName(rs.getString("name"));

                Integer uid = (Integer) rs.getObject("UID");
                session.setUser(uid != null ? userDAO.searchByID(uid) : null);

                session.setIsDeleted(rs.getBoolean("isDeleted"));

                List<ExerciseSet> sets = exerciseSetHelperDAO.searchBySessionID(rs.getInt("ID_Session"));
                session.setExerciseSets(sets);

                sessions.add(session);
            }

            return sessions;
        } catch (SQLException e) {
            LOGGER.error("" + e.getMessage());
            throw new PersistenceException("failed to prepare statements", e);
        }
    }

    @Override
    public TrainingsSession searchByID(int id) throws PersistenceException {
        try {
            ps_findID.setInt(1, id);
            ResultSet rs = executeQuery(ps_findID);
            TrainingsSession session = null;

            if (rs.next()) {
                session = new TrainingsSession();
                session.setId(rs.getInt("ID_Session"));
                session.setName(rs.getString("name"));

                Integer uid = (Integer) rs.getObject("UID");
                session.setUser(uid != null ? userDAO.searchByID(uid) : null);

                session.setIsDeleted(rs.getBoolean("isDeleted"));

                List<ExerciseSet> sets = exerciseSetHelperDAO.searchBySessionID(rs.getInt("ID_Session"));
                session.setExerciseSets(sets);
            }
            return session;

        } catch (SQLException e) {
            LOGGER.error("" + e.getMessage());
            throw new PersistenceException("failed to prepare statements", e);
        }
    }

    @Override
    public TrainingsSession update(TrainingsSession session, int id_plan) throws PersistenceException {
        try {
            ps_update.setInt(1, id_plan);
            ps_update.setString(2, session.getName());
            ps_update.setObject(3, session.getUser() != null ? session.getUser().getId() : null);
            ps_update.setBoolean(4, session.getIsDeleted());
            ps_update.setInt(5, session.getId());

            executeUpdate(ps_update);

            List<ExerciseSet> sets = exerciseSetHelperDAO.searchBySessionID(session.getId());

            if (sets != null &&
                    session.getExerciseSets() != null &&
                    sets.size() != session.getExerciseSets().size()) {

                List<ExerciseSet> sets_toDelete = new ArrayList<>();
                List<ExerciseSet> sets_toCreate = new ArrayList<>();

                sets_toDelete.addAll(sets.stream().filter(set ->
                        !session.getExerciseSets().contains(set)).collect(Collectors.toList()));

                sets_toCreate.addAll(session.getExerciseSets().stream().filter(set ->
                        !sets.contains(set)).collect(Collectors.toList()));

                for (ExerciseSet set : sets_toCreate) {
                    exerciseSetHelperDAO.create(set, session.getId());
                }

                for (ExerciseSet set : sets_toDelete) {
                    exerciseSetHelperDAO.delete(set);
                }
            } else if (sets == null && session.getExerciseSets() != null) {
                for (ExerciseSet set : session.getExerciseSets()) {
                    exerciseSetHelperDAO.create(set, session.getId());
                }
            } else if (session.getExerciseSets() == null && sets != null) {
                for (ExerciseSet set : sets) {
                    exerciseSetHelperDAO.delete(set);
                }
            }

            return session;

        } catch (SQLException e) {
            LOGGER.error("" + e.getMessage());
            throw new PersistenceException("failed to prepare statements", e);
        }
    }

    @Override
    public void delete(TrainingsSession session) throws PersistenceException {
        try {
            ps_delete.setInt(1, session.getId());
            executeUpdate(ps_delete);

            if (session.getExerciseSets() != null) {
                for (ExerciseSet set : session.getExerciseSets()) {
                    exerciseSetHelperDAO.delete(set);
                }
            }

            session.setIsDeleted(true);

        } catch (SQLException e) {
            LOGGER.error("" + e.getMessage());
            throw new PersistenceException("failed to prepare statements", e);
        }
    }

    @Override
    public List<TrainingsSession> searchByUser(User user) throws PersistenceException {
        try {
            ps_find_ByUID.setObject(1, user.getId());
            ResultSet rs = executeQuery(ps_find_ByUID);
            List<TrainingsSession> sessions = null;

            if (rs.next()) {
                sessions = new ArrayList<>();
                do {
                    TrainingsSession session = new TrainingsSession();
                    session.setId(rs.getInt("ID_Session"));
                    session.setName(rs.getString("name"));

                    Integer uid = (Integer) rs.getObject("UID");
                    session.setUser(uid != null ? userDAO.searchByID(uid) : null);

                    session.setIsDeleted(rs.getBoolean("isDeleted"));

                    List<ExerciseSet> sets = exerciseSetHelperDAO.searchBySessionID(rs.getInt("ID_Session"));
                    session.setExerciseSets(sets);

                    sessions.add(session);
                } while (rs.next());
            }
            return sessions;
        } catch (SQLException e) {
            LOGGER.error("" + e.getMessage());
            throw new PersistenceException("failed to prepare statements", e);
        }
    }

    @Override
    public List<TrainingsSession> searchByPlanID(int ID_plan) throws PersistenceException {
        try {
            ps_find_ByIDPlan.setObject(1, ID_plan);
            ResultSet rs = executeQuery(ps_find_ByIDPlan);
            List<TrainingsSession> sessions = null;

            if (rs.next()) {
                sessions = new ArrayList<>();
                do {
                    TrainingsSession session = new TrainingsSession();
                    session.setId(rs.getInt("ID_Session"));
                    session.setName(rs.getString("name"));

                    Integer uid = (Integer) rs.getObject("UID");
                    session.setUser(uid != null ? userDAO.searchByID(uid) : null);

                    session.setIsDeleted(rs.getBoolean("isDeleted"));

                    List<ExerciseSet> sets = exerciseSetHelperDAO.searchBySessionID(rs.getInt("ID_Session"));
                    session.setExerciseSets(sets);

                    sessions.add(session);
                } while (rs.next());
            }
            return sessions;
        } catch (SQLException e) {
            LOGGER.error("" + e.getMessage());
            throw new PersistenceException("failed to prepare statements", e);
        }
    }

    @Override
    public Trainingsplan getPlanBySession(TrainingsSession session) throws PersistenceException {
        try {
            ps_find_IDPlan.setInt(1, session.getId());
            Trainingsplan trainingsplan = null;

            ResultSet rs = executeQuery(ps_find_IDPlan);

            if (rs.next()) {
                int id = rs.getInt(1);

                trainingsplan = trainingsplanDAO.searchByID(id);
            }

            return trainingsplan;

        } catch (SQLException e) {
            LOGGER.error("" + e.getMessage());
            throw new PersistenceException("failed to create Plan from Session", e);
        }
    }

    private void executeUpdate(PreparedStatement ps) throws SQLException {
        LOGGER.info("execute: " + ps);
        ps.executeUpdate();
    }

    private ResultSet executeQuery(PreparedStatement ps) throws SQLException {
        LOGGER.info("execute: " + ps);
        return ps.executeQuery();
    }

    public void setExerciseSetHelperDAO(H2ExerciseSetHelperDAOImpl exerciseSetHelperDAO) {
        this.exerciseSetHelperDAO = exerciseSetHelperDAO;
    }

    public void setUserDAO(H2UserDAOImpl userDAO) {
        this.userDAO = userDAO;
    }

    public void setTrainingsplanDAO(H2TrainingsplanDAOImpl trainingsplanDAO) {
        this.trainingsplanDAO = trainingsplanDAO;
    }
}
