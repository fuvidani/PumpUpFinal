package sepm.ss15.grp16.persistence.dao.training.helper.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.training.helper.ExerciseSet;
import sepm.ss15.grp16.entity.training.TrainingsSession;
import sepm.ss15.grp16.entity.User;
import sepm.ss15.grp16.persistence.dao.ExerciseDAO;
import sepm.ss15.grp16.persistence.dao.training.helper.ExerciseSetHelperDAO;
import sepm.ss15.grp16.persistence.dao.training.helper.TrainingsSessionHelperDAO;
import sepm.ss15.grp16.persistence.dao.UserDAO;
import sepm.ss15.grp16.persistence.dao.impl.H2ExerciseDAOImpl;
import sepm.ss15.grp16.persistence.dao.impl.H2UserDAOImpl;
import sepm.ss15.grp16.persistence.database.DBHandler;
import sepm.ss15.grp16.persistence.exception.DBException;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Lukas
 * Date: 08.05.2015
 */
public class H2ExerciseSetHelperDAOImpl implements ExerciseSetHelperDAO {

	private static final Logger LOGGER = LogManager.getLogger(H2ExerciseSetHelperDAOImpl.class);

	private Connection con;

	private PreparedStatement ps_create;
	private PreparedStatement ps_findAll;
	private PreparedStatement ps_findID;
	private final PreparedStatement ps_find_ByUID;
	private final PreparedStatement ps_find_ByIDSession;
	private final PreparedStatement ps_find_IDSession;
	private PreparedStatement ps_update;
	private PreparedStatement ps_delete;

	private PreparedStatement ps_seq_ES;

	private ExerciseDAO exerciseDAO;

	private UserDAO userDAO;
	private TrainingsSessionHelperDAO trainingsSessionHelperDAO;

	private H2ExerciseSetHelperDAOImpl(DBHandler handler) throws PersistenceException {
		try {
			con = handler.getConnection();

			ps_create = con.prepareStatement("INSERT INTO ExerciseSet (ID_Exercise, UID, repeat, type, order_nr, ID_session, isDeleted) " +
					"VALUES (?, ?, ?, ?, ?, ?, ?)");
			ps_findAll = con.prepareStatement("SELECT * FROM ExerciseSet WHERE isDeleted = FALSE ");
			ps_findID = con.prepareStatement("SELECT * FROM ExerciseSet WHERE ID_Set = ?");
			ps_find_ByUID = con.prepareStatement("SELECT * FROM ExerciseSet WHERE UID = ?");
			ps_find_ByIDSession = con.prepareStatement("SELECT * FROM ExerciseSet WHERE ID_Session = ?");
			ps_find_IDSession = con.prepareStatement("SELECT ID_Session FROM ExerciseSet WHERE ID_Set = ?");
			ps_update = con.prepareStatement("UPDATE ExerciseSet " +
					"SET ID_Exercise = ?, UID = ?, repeat = ?, type = ?, order_nr = ?, ID_session = ?, isDeleted = ? " +
					"WHERE ID_Set = ?");
			ps_delete = con.prepareStatement("UPDATE ExerciseSet SET isDeleted = TRUE  WHERE ID_Set = ?");

			ps_seq_ES = con.prepareStatement("SELECT currval('seq_ES')");

		} catch (DBException | SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed to prepare statements", e);
		}
	}

	@Override
	public ExerciseSet create(ExerciseSet set, int ID_session) throws PersistenceException {
		try {
			ps_create.setInt(1, set.getExercise().getId());
			ps_create.setObject(2, set.getUser() != null ? set.getUser().getId() : null);
			ps_create.setInt(3, set.getRepeat());
			ps_create.setString(4, set.getType().toString());
			ps_create.setInt(5, set.getOrder_nr());
			ps_create.setInt(6, ID_session);
			ps_create.setBoolean(7, false);

			executeUpdate(ps_create);
			ResultSet rs = executeQuery(ps_seq_ES);
			rs.next();
			set.setId(rs.getInt(1));

			return set;

		} catch (SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed create " + set, e);
		}
	}

	@Override
	public List<ExerciseSet> findAll() throws PersistenceException {
		try {
			ResultSet rs = executeQuery(ps_findAll);
			List<ExerciseSet> sets = new ArrayList<>();

			while (rs.next()) {

				ExerciseSet set = new ExerciseSet();
				int id = rs.getInt("ID_Set");
				set.setId(id);
				set.setExercise(exerciseDAO.searchByID(rs.getInt("ID_Exercise")));

				Integer uid = (Integer) rs.getObject("UID");
				set.setUser(uid != null ? userDAO.searchByID(uid) : null);

				set.setIsDeleted(rs.getBoolean("isDeleted"));
				set.setRepeat(rs.getInt("repeat"));
				set.setType(ExerciseSet.SetType.getSetType(rs.getString("type")));
				set.setOrder_nr(rs.getInt("order_nr"));

				set.setExercise(exerciseDAO.searchByID(rs.getInt("ID_Exercise")));

				sets.add(set);
			}
			return sets;
		} catch (SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed to findAll ", e);
		}
	}

	@Override
	public ExerciseSet searchByID(int id) throws PersistenceException {
		try {
			ps_findID.setInt(1, id);
			ResultSet rs = executeQuery(ps_findID);
			ExerciseSet set = null;

			if (rs.next()) {
				set = new ExerciseSet();
				set.setId(id);
				set.setExercise(exerciseDAO.searchByID(rs.getInt("ID_Exercise")));

				Integer uid = (Integer) rs.getObject("UID");
				set.setUser(uid != null ? userDAO.searchByID(uid) : null);

				set.setIsDeleted(rs.getBoolean("isDeleted"));
				set.setRepeat(rs.getInt("repeat"));
				set.setType(ExerciseSet.SetType.getSetType(rs.getString("type")));
				set.setOrder_nr(rs.getInt("order_nr"));

				set.setExercise(exerciseDAO.searchByID(rs.getInt("ID_Exercise")));
			}

			return set;
		} catch (SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed to searchByID " + id, e);
		}
	}

	@Override
	public ExerciseSet update(ExerciseSet set, int ID_session) throws PersistenceException {
		try {
			ps_update.setInt(1, set.getExercise().getId());
			ps_update.setObject(2, set.getUser() != null ? set.getUser().getId() : null);
			ps_update.setInt(3, set.getRepeat());
			ps_create.setString(4, set.getType().toString());
			ps_update.setInt(5, set.getOrder_nr());
			ps_update.setInt(6, ID_session);
			ps_update.setBoolean(7, set.getIsDeleted());

			ps_update.setInt(8, set.getId());

			executeUpdate(ps_update);

			return set;
		} catch (SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed to update " + set, e);
		}
	}

	@Override
	public void delete(ExerciseSet set) throws PersistenceException {
		try {
			ps_delete.setInt(1, set.getId());
			executeUpdate(ps_delete);

		} catch (SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed to prepare statements", e);
		}
	}

	@Override
	public List<ExerciseSet> searchByUser(User user) throws PersistenceException {
		try {
			ps_find_ByUID.setObject(1, user.getId());
			ResultSet rs = executeQuery(ps_find_ByUID);
			List<ExerciseSet> sets = null;

			if (rs.next()) {
				sets = new ArrayList<>();
				do {

					ExerciseSet set = new ExerciseSet();
					int id = rs.getInt("ID_Set");
					set.setId(id);
					set.setExercise(exerciseDAO.searchByID(rs.getInt("ID_Exercise")));

					Integer uid = (Integer) rs.getObject("UID");
					set.setUser(uid != null ? userDAO.searchByID(uid) : null);

					set.setIsDeleted(rs.getBoolean("isDeleted"));
					set.setType(ExerciseSet.SetType.getSetType(rs.getString("type")));
					set.setRepeat(rs.getInt("repeat"));
					set.setOrder_nr(rs.getInt("order_nr"));

					set.setExercise(exerciseDAO.searchByID(rs.getInt("ID_Exercise")));

					sets.add(set);
				} while (rs.next());
			}
			return sets;
		} catch (SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed to findAll ", e);
		}
	}

	@Override
	public List<ExerciseSet> searchBySessionID(int ID_Session) throws PersistenceException {
		try {
			ps_find_ByIDSession.setObject(1, ID_Session);
			ResultSet rs = executeQuery(ps_find_ByIDSession);
			List<ExerciseSet> sets = null;

			if (rs.next()) {
				sets = new ArrayList<>();
				do {

					ExerciseSet set = new ExerciseSet();
					int id = rs.getInt("ID_Set");
					set.setId(id);
					set.setExercise(exerciseDAO.searchByID(rs.getInt("ID_Exercise")));

					Integer uid = (Integer) rs.getObject("UID");
					set.setUser(uid != null ? userDAO.searchByID(uid) : null);

					set.setIsDeleted(rs.getBoolean("isDeleted"));
					set.setType(ExerciseSet.SetType.getSetType(rs.getString("type")));
					set.setRepeat(rs.getInt("repeat"));
					set.setOrder_nr(rs.getInt("order_nr"));

					set.setExercise(exerciseDAO.searchByID(rs.getInt("ID_Exercise")));

					sets.add(set);
				} while (rs.next());
			}
			return sets;
		} catch (SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed to findAll ", e);
		}
	}

	@Override
	public TrainingsSession getSessionBySet(ExerciseSet set) throws PersistenceException {
		try {
			ps_find_IDSession.setInt(1, set.getId());
			TrainingsSession session = null;

			ResultSet rs = executeQuery(ps_find_IDSession);

			if (rs.next()) {
				int id = rs.getInt(1);

				session = trainingsSessionHelperDAO.searchByID(id);
			}
			return session;

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

	public void setExerciseDAO(H2ExerciseDAOImpl exerciseDAO) {
		this.exerciseDAO = exerciseDAO;
	}

	public void setUserDAO(H2UserDAOImpl userDAO) {
		this.userDAO = userDAO;
	}

	public void setTrainingsSessionHelperDAO(TrainingsSessionHelperDAO trainingsSessionHelperDAO) {
		this.trainingsSessionHelperDAO = trainingsSessionHelperDAO;
	}
}
