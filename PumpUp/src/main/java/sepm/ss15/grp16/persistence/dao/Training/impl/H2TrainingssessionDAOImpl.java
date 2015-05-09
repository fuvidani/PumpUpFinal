package sepm.ss15.grp16.persistence.dao.Training.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.Training.TrainingsSession;
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
	private PreparedStatement ps_find_user;
	private PreparedStatement ps_find_nouser;
	private PreparedStatement ps_update;
	private PreparedStatement ps_delete;

	private PreparedStatement ps_seq_TS;

	private H2TrainingssessionDAOImpl(DBHandler handler) throws PersistenceException {
		try {
			con = handler.getConnection();

			/** Trainingplan **/
			ps_create = con.prepareStatement("INSERT INTO TrainingsSession (ID_Plan, name, UID, isDeleted) " +
					"VALUES (?, ?, ?, ?)");
			ps_findAll = con.prepareStatement("SELECT * FROM TrainingsSession WHERE isDeleted = FALSE ");
			ps_find_user = con.prepareStatement("SELECT * FROM TrainingsSession " +
					"WHERE ID_Plan=? AND name LIKE ? AND UID = ? AND isDeleted=?");
			ps_find_nouser = con.prepareStatement("SELECT * FROM TrainingsSession " +
					"WHERE ID_Plan=? AND name LIKE ? AND isDeleted=?");
			ps_findID = con.prepareStatement("SELECT * FROM TrainingsSession WHERE ID_Session = ?");
			ps_update = con.prepareStatement("UPDATE TrainingsSession " +
					"SET ID_Plan = ?, name = ?, UID = ?, isDeleted = ? WHERE ID_Session = ?");
			ps_delete = con.prepareStatement("UPDATE TrainingsSession " +
					"SET isDeleted = TRUE WHERE ID_Session = ?");

			/** Sequences **/
			ps_seq_TS = con.prepareStatement("SELECT currval('seq_TS')");

		} catch (DBException | SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed to prepare statements", e);
		}
	}

	@Override
	public TrainingsSession create(TrainingsSession dto) throws PersistenceException {

		try {
			ps_create.setInt(1, dto.getId_plan());
			ps_create.setString(2, dto.getName());
			ps_create.setObject(3, dto.getUid());
			ps_create.setBoolean(4, false);

			executeUpdate(ps_create);

			ResultSet rs = executeQuery(ps_seq_TS);
			rs.next();
			dto.setId(rs.getInt(1));

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

			while (rs.next()){
				TrainingsSession session = new TrainingsSession();
				session.setId(rs.getInt("ID_Session"));
				session.setId_plan(rs.getInt("ID_Plan"));
				session.setName(rs.getString("name"));
				session.setUid((Integer) rs.getObject("UID"));
				session.setIsDeleted(rs.getBoolean("isDeleted"));

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
		return null;
	}

	@Override
	public TrainingsSession update(TrainingsSession dto) throws PersistenceException {
		return null;
	}

	@Override
	public void delete(TrainingsSession dto) throws PersistenceException {

	}

	@Override
	public List<TrainingsSession> find(TrainingsSession trainingsSession) {
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
}
