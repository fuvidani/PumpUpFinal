package sepm.ss15.grp16.persistence.dao.Training.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.Training.TrainingsplanType;
import sepm.ss15.grp16.persistence.dao.Training.TrainingsplanTypeDAO;
import sepm.ss15.grp16.persistence.database.DBHandler;
import sepm.ss15.grp16.persistence.exception.DBException;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Author: Lukas
 * Date: 08.05.2015
 */
public class H2TrainingsplanTypeDAOImpl implements TrainingsplanTypeDAO {

	private static final Logger LOGGER = LogManager.getLogger(H2TrainingsplanTypeDAOImpl.class);

	private Connection con;

	private PreparedStatement ps_create;
	private PreparedStatement ps_findAll;
	private PreparedStatement ps_findID;
	private PreparedStatement ps_find;
	private PreparedStatement ps_update;
	private PreparedStatement ps_delete;

	private PreparedStatement ps_seq_TPType;

	private H2TrainingsplanTypeDAOImpl(DBHandler handler) throws PersistenceException {
		try {
			con = handler.getConnection();

			/** Trainingstype **/
			ps_create = con.prepareStatement("INSERT INTO TrainingsPlanType (UID, name, description, isDeleted) " +
					"VALUES (?, ?, ?, ? )");
			ps_findAll = con.prepareStatement("SELECT * FROM TrainingsPlanType");
			ps_findID = con.prepareStatement("SELECT * FROM TrainingsPlanType WHERE ID_Type=? ");
			ps_find = con.prepareStatement("SELECT * FROM TrainingsPlanType " +
					"WHERE UID LIKE ? " +
					"AND name LIKE ? " +
					"AND  description LIKE ? " +
					"AND  isDeleted LIKE ? ");
			ps_update = con.prepareStatement("Update TrainingsPlanType SET UID=?, name=?, description=?, isDeleted=? WHERE ID_Type=?");
			ps_delete = con.prepareCall("Update TrainingsPlanType SET isDeleted=TRUE WHERE ID_Type=?");

			/** Sequences **/
			ps_seq_TPType = con.prepareStatement("SELECT currval('seq_TPType')");

		} catch (DBException | SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed to prepare statements", e);
		}
	}

	@Override
	public List<TrainingsplanType> find(TrainingsplanType trainingsplanType) {
		return null;
	}

	@Override
	public TrainingsplanType create(TrainingsplanType dto) throws PersistenceException {
		return null;
	}

	@Override
	public List<TrainingsplanType> findAll() throws PersistenceException {
		return null;
	}

	@Override
	public TrainingsplanType searchByID(int id) throws PersistenceException {
		return null;
	}

	@Override
	public TrainingsplanType update(TrainingsplanType dto) throws PersistenceException {
		return null;
	}

	@Override
	public void delete(TrainingsplanType dto) throws PersistenceException {

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
