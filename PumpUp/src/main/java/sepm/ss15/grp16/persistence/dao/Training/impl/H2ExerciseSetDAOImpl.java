package sepm.ss15.grp16.persistence.dao.Training.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.Training.ExerciseSet;
import sepm.ss15.grp16.entity.Training.TrainingsSession;
import sepm.ss15.grp16.entity.Training.Trainingsplan;
import sepm.ss15.grp16.entity.Training.TrainingsplanType;
import sepm.ss15.grp16.persistence.dao.Training.ExerciseSetDAO;
import sepm.ss15.grp16.persistence.dao.impl.H2ExerciseDAOImpl;
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
public class H2ExerciseSetDAOImpl implements ExerciseSetDAO {

	private static final Logger LOGGER = LogManager.getLogger(H2ExerciseSetDAOImpl.class);

	private Connection con;

	private PreparedStatement ps_create;
	private PreparedStatement ps_findAll;
	private PreparedStatement ps_findID;
	private PreparedStatement ps_find_user;
	private PreparedStatement ps_find_nouser;
	private PreparedStatement ps_update;
	private PreparedStatement ps_delete;

	private PreparedStatement ps_seq_ES;

	private H2ExerciseDAOImpl exerciseDAO;
	private H2TrainingssessionDAOImpl trainingsSessionDAO;

	private H2ExerciseSetDAOImpl(DBHandler handler) throws PersistenceException {
		try {
			con = handler.getConnection();

			ps_create = con.prepareStatement("INSERT INTO ExerciseSet (ID_Exercise, UID, repeat, order_nr, ID_session, isDeleted) " +
					"VALUES (?, ?, ?, ?, ?, ?)");
			ps_findAll = con.prepareStatement("SELECT * FROM ExerciseSet WHERE isDeleted = FALSE ");
			ps_findID = con.prepareStatement("SELECT * FROM ExerciseSet WHERE ID_Set = ?");
			ps_find_user = con.prepareStatement("");
			ps_find_nouser = con.prepareStatement("");
			ps_update = con.prepareStatement("UPDATE ExerciseSet " +
					"SET ID_Exercise = ?, UID = ?, repeat = ?, order_nr = ?, ID_session = ?, isDeleted = ? " +
					"WHERE ID_Set = ?");
			ps_delete = con.prepareStatement("UPDATE ExerciseSet SET isDeleted = TRUE  WHERE ID_Set = ?");

			ps_seq_ES = con.prepareStatement("SELECT currval('seq_ES')");

		} catch (DBException | SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed to prepare statements", e);
		}
	}

	@Override
	public ExerciseSet create(ExerciseSet dto) throws PersistenceException {

		try {
			ps_create.setInt(1, dto.getExercise().getId());
			ps_create.setObject(2, dto.getUid() != null ? dto.getId() : null);
			ps_create.setInt(3, dto.getRepeat());
			ps_create.setInt(4, dto.getOrder_nr());
			ps_create.setInt(5, dto.getSession().getId());
			ps_create.setBoolean(6, false);

			executeUpdate(ps_create);
			ResultSet rs = executeQuery(ps_seq_ES);
			rs.next();
			dto.setId(rs.getInt(1));

			return dto;

		}catch (SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed create " + dto, e);
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
				set.setUid((Integer) rs.getObject("UID"));
				set.setIsDeleted(rs.getBoolean("isDeleted"));
				set.setRepeat(rs.getInt("repeat"));
				set.setOrder_nr(rs.getInt("order_nr"));

				set.setSession(trainingsSessionDAO.searchByID(rs.getInt("ID_Session")));
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

			if(rs.next()){
				set = new ExerciseSet();
				set.setId(id);
				set.setExercise(exerciseDAO.searchByID(rs.getInt("ID_Exercise")));
				set.setUid((Integer) rs.getObject("UID"));
				set.setIsDeleted(rs.getBoolean("isDeleted"));
				set.setRepeat(rs.getInt("repeat"));
				set.setOrder_nr(rs.getInt("order_nr"));

				set.setSession(trainingsSessionDAO.searchByID(rs.getInt("ID_Session")));
				set.setExercise(exerciseDAO.searchByID(rs.getInt("ID_Exercise")));
			}

			return set;
		} catch (SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed to searchByID " + id, e);
		}
	}

	@Override
	public ExerciseSet update(ExerciseSet dto) throws PersistenceException {
		try {
			ps_update.setInt(1, dto.getExercise().getId());
			ps_update.setObject(2, dto.getUid() != null ? dto.getId() : null);
			ps_update.setInt(3, dto.getRepeat());
			ps_update.setInt(4, dto.getOrder_nr());
			ps_update.setInt(5, dto.getSession().getId());
			ps_update.setBoolean(6, dto.getIsDeleted());

			ps_update.setInt(7, dto.getId());

			executeUpdate(ps_update);

			return dto;
		} catch (SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed to update " + dto, e);
		}
	}

	@Override
	public void delete(ExerciseSet dto) throws PersistenceException {
		try {
			ps_delete.setInt(1, dto.getId());
			executeUpdate(ps_delete);

		} catch (SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed to prepare statements", e);
		}
	}

	@Override
	public List<ExerciseSet> find(ExerciseSet exerciseSet) {
		return null;
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

	public void setTrainingsSessionDAO(H2TrainingssessionDAOImpl trainingsSessionDAO) {
		this.trainingsSessionDAO = trainingsSessionDAO;
	}
}
