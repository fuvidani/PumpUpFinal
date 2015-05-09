package sepm.ss15.grp16.persistence.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.impl.TrainingsSession;
import sepm.ss15.grp16.persistence.dao.TrainingsSessionDAO;
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
public class H2TrainingssessionDAOImpl implements TrainingsSessionDAO {

	private static final Logger LOGGER = LogManager.getLogger(H2TrainingssessionDAOImpl.class);

	private Connection con;

	private PreparedStatement ps_create;
	private PreparedStatement ps_findAll;
	private PreparedStatement ps_find;
	private PreparedStatement ps_update;
	private PreparedStatement ps_delete;

	private H2TrainingssessionDAOImpl(DBHandler handler) throws PersistenceException {
		try {
			con = handler.getConnection();

			ps_create = con.prepareStatement("");
			ps_findAll = con.prepareStatement("");
			ps_find = con.prepareStatement("");
			ps_update = con.prepareStatement("");
			ps_delete = con.prepareStatement("");

		} catch (DBException | SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed to prepare statements", e);
		}
	}

	@Override
	public List<TrainingsSession> find(TrainingsSession trainingsSession) {
		return null;
	}

	@Override
	public TrainingsSession create(TrainingsSession dto) throws PersistenceException {
		return null;
	}

	@Override
	public List<TrainingsSession> findAll() throws PersistenceException {
		return null;
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

	private void executeUpdate(PreparedStatement ps) throws SQLException {
		LOGGER.info("execute: " + ps);
		ps.executeUpdate();
	}

	private ResultSet executeQuery(PreparedStatement ps) throws SQLException {
		LOGGER.info("execute: " + ps);
		return ps.executeQuery();
	}
}
