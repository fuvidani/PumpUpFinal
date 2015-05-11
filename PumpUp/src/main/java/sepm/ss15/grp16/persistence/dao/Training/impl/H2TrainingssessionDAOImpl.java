package sepm.ss15.grp16.persistence.dao.Training.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sepm.ss15.grp16.entity.Training.ExerciseSet;
import sepm.ss15.grp16.entity.Training.TrainingsSession;
import sepm.ss15.grp16.persistence.dao.Training.ExerciseSetDAO;
import sepm.ss15.grp16.persistence.dao.Training.TrainingsSessionDAO;
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
public class H2TrainingssessionDAOImpl implements TrainingsSessionDAO {

	private static final Logger LOGGER = LogManager.getLogger(H2TrainingssessionDAOImpl.class);

	private Connection con;

	private PreparedStatement ps_create;
	private PreparedStatement ps_findAll;
	private PreparedStatement ps_findID;
	private PreparedStatement ps_find_user_plan;
	private PreparedStatement ps_find_user_noplan;
	private PreparedStatement ps_find_nouser_plan;
	private PreparedStatement ps_find_nouser_noplan;
	private PreparedStatement ps_update;
	private PreparedStatement ps_delete;

	private PreparedStatement ps_seq_TS;

	private H2TrainingsplanDAOImpl trainingsplanDAO;
	private H2ExerciseSetDAOImpl exerciseSetDAO;

	private H2TrainingssessionDAOImpl(DBHandler handler) throws PersistenceException {
		try {
			con = handler.getConnection();

			/** Trainingplan **/
			ps_create = con.prepareStatement("INSERT INTO TrainingsSession (ID_Plan, name, UID, isDeleted) " +
					"VALUES (?, ?, ?, ?)");
			ps_findAll = con.prepareStatement("SELECT * FROM TrainingsSession WHERE isDeleted = FALSE ");

			ps_findID = con.prepareStatement("SELECT * FROM TrainingsSession WHERE ID_Session = ?");
			ps_update = con.prepareStatement("UPDATE TrainingsSession " +
					"SET ID_Plan = ?, name = ?, UID = ?, isDeleted = ? WHERE ID_Session = ?");
			ps_delete = con.prepareStatement("UPDATE TrainingsSession " +
					"SET isDeleted = TRUE WHERE ID_Session = ?");

			/** Sequences **/
			ps_seq_TS = con.prepareStatement("SELECT currval('seq_TS')");


			/** build find string **/

			String start = "SELECT * FROM TrainingsSession WHERE";
			String plan = " ID_Plan=? ";
			String user = " UID = ? ";
			String end = " name LIKE ? ";

			ps_find_user_plan = con.prepareStatement(start + end + "AND" + plan + "AND" + user);
			ps_find_user_noplan = con.prepareStatement(start + end + "AND" + user);
			ps_find_nouser_plan = con.prepareStatement(start + end + "AND" + plan);
			ps_find_nouser_noplan = con.prepareStatement(start + end);

		} catch (DBException | SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed to prepare statements", e);
		}
	}

	@Override
	public TrainingsSession create(TrainingsSession dto) throws PersistenceException {

		try {
			ps_create.setInt(1, dto.getTrainingsplan().getId());
			ps_create.setString(2, dto.getName());
			ps_create.setObject(3, dto.getUid());
			ps_create.setBoolean(4, false);

			executeUpdate(ps_create);

			ResultSet rs = executeQuery(ps_seq_TS);
			rs.next();
			dto.setId(rs.getInt(1));

			if (dto.getExerciseSets() != null) {
				for (ExerciseSet set : dto.getExerciseSets()) {
					exerciseSetDAO.create(set);
				}
			}

			return dto;

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
				session.setTrainingsplan(trainingsplanDAO.searchByID(rs.getInt("ID_Plan")));
				session.setName(rs.getString("name"));
				session.setUid((Integer) rs.getObject("UID"));
				session.setIsDeleted(rs.getBoolean("isDeleted"));

				List<ExerciseSet> sets = exerciseSetDAO.find(new ExerciseSet(null, null, null, null, null, rs.getInt("ID_Session"), null));
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
				session.setTrainingsplan(trainingsplanDAO.searchByID(rs.getInt("ID_Plan")));
				session.setName(rs.getString("name"));
				session.setUid((Integer) rs.getObject("UID"));
				session.setIsDeleted(rs.getBoolean("isDeleted"));

				List<ExerciseSet> sets = exerciseSetDAO.find(new ExerciseSet(null, null, session, null, null, null, null));
				session.setExerciseSets(sets);
			}
			return session;

		} catch (SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed to prepare statements", e);
		}
	}

	@Override
	public TrainingsSession update(TrainingsSession dto) throws PersistenceException {

		try {
			ps_update.setInt(1, dto.getTrainingsplan().getId());
			ps_update.setString(2, dto.getName());
			ps_update.setObject(3, dto.getUid());
			ps_update.setBoolean(4, dto.getIsDeleted());
			ps_update.setInt(5, dto.getId());

			executeUpdate(ps_update);

			List<ExerciseSet> sets = exerciseSetDAO.find(new ExerciseSet(null, null, dto, null, null, null, null));

			if (sets != null &&
					dto.getExerciseSets() != null &&
					sets.size() != dto.getExerciseSets().size()) {

				List<ExerciseSet> sets_toDelete = new ArrayList<>();
				List<ExerciseSet> sets_toCreate = new ArrayList<>();

				sets_toDelete.addAll(sets.stream().filter(set ->
						!dto.getExerciseSets().contains(set)).collect(Collectors.toList()));

				sets_toCreate.addAll(dto.getExerciseSets().stream().filter(set ->
						!sets.contains(set)).collect(Collectors.toList()));

				for (ExerciseSet set : sets_toCreate) {
					exerciseSetDAO.create(set);
				}

				for (ExerciseSet set : sets_toDelete) {
					exerciseSetDAO.delete(set);
				}
			} else if (sets == null && dto.getExerciseSets() != null) {
				for (ExerciseSet set : dto.getExerciseSets()) {
					exerciseSetDAO.create(set);
				}
			} else if (dto.getExerciseSets() == null && sets != null) {
				for (ExerciseSet set : sets) {
					exerciseSetDAO.delete(set);
				}
			}

			return dto;

		} catch (SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed to prepare statements", e);
		}
	}

	@Override
	public void delete(TrainingsSession dto) throws PersistenceException {
		try {
			ps_delete.setInt(1, dto.getId());
			executeUpdate(ps_delete);

			if (dto.getExerciseSets() != null) {
				for (ExerciseSet set : dto.getExerciseSets()) {
					exerciseSetDAO.delete(set);
				}
			}

		} catch (SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed to prepare statements", e);
		}
	}

	@Override
	public List<TrainingsSession> find(TrainingsSession filter) throws PersistenceException {
		try {
			List<TrainingsSession> sessions = null;

			if (filter.getId() != null) {
				sessions = new ArrayList<>();
				sessions.add(searchByID(filter.getId()));
				return sessions;
			}

			PreparedStatement ps_find;

			if (filter.getUid() != null) {
				if (filter.getTrainingsplan() != null && filter.getTrainingsplan().getId() != null) {
					ps_find = ps_find_user_plan;
					ps_find_user_plan.setInt(2, filter.getTrainingsplan().getId());
					ps_find_user_plan.setInt(3, filter.getUid());
				} else {
					ps_find = ps_find_user_noplan;
					ps_find_user_noplan.setInt(2, filter.getUid());
				}
			} else {
				if (filter.getTrainingsplan() != null && filter.getTrainingsplan().getId() != null) {
					ps_find = ps_find_nouser_plan;
					ps_find_nouser_plan.setInt(2, filter.getTrainingsplan().getId());
				} else {
					ps_find = ps_find_nouser_noplan;
				}
			}
			if (filter.getName() != null) ps_find.setString(1, filter.getName());
			else ps_find.setString(1, "%");

			ResultSet rs = executeQuery(ps_find);

			if (rs.next()) {
				sessions = new ArrayList<>();
				do {
					TrainingsSession session = new TrainingsSession();
					int id = rs.getInt("ID_Session");
					session.setId(id);
//TODO					session.setTrainingsplan(trainingsplanDAO.searchByID(rs.getInt("ID_Plan")));
					session.setName(rs.getString("name"));
					session.setUid((Integer) rs.getObject("UID"));
					session.setIsDeleted(rs.getBoolean("isDeleted"));

					List<ExerciseSet> sets = exerciseSetDAO.find(new ExerciseSet(null, null, null, null, null, id, null));
					session.setExerciseSets(sets);

					sessions.add(session);
				} while (rs.next());
			}
			return sessions;

		} catch (SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed to find with filter" + filter, e);
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

	public void setTrainingsplanDAO(H2TrainingsplanDAOImpl trainingsplanDAO) {
		this.trainingsplanDAO = trainingsplanDAO;
	}

	public H2TrainingsplanDAOImpl getTrainingsplanDAO() {
		return trainingsplanDAO;
	}

	public void setExerciseSetDAO(H2ExerciseSetDAOImpl exerciseSetDAO) {
		this.exerciseSetDAO = exerciseSetDAO;
	}

	public H2ExerciseSetDAOImpl getExerciseSetDAO() {
		return exerciseSetDAO;
	}
}
