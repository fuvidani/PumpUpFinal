package sepm.ss15.grp16.persistence.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sepm.ss15.grp16.entity.impl.TrainingsSession;
import sepm.ss15.grp16.entity.impl.Trainingsplan;
import sepm.ss15.grp16.entity.impl.TrainingsplanType;
import sepm.ss15.grp16.persistence.dao.TrainingsSessionDAO;
import sepm.ss15.grp16.persistence.dao.TrainingsplanDAO;
import sepm.ss15.grp16.persistence.dao.TrainingsplanTypeDAO;
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
public class H2TrainingsplanDAOImpl implements TrainingsplanDAO {

	private static final Logger LOGGER = LogManager.getLogger(H2TrainingsplanDAOImpl.class);

	private Connection con;

	private PreparedStatement ps_create;
	private PreparedStatement ps_findAll;
	private PreparedStatement ps_findID;
	private PreparedStatement ps_find_user;
	private PreparedStatement ps_find_nouser;
	private PreparedStatement ps_update;
	private PreparedStatement ps_delete;

	private PreparedStatement ps_seq_TP;

	private PreparedStatement ps_find_association;
	private PreparedStatement ps_create_association;
	private PreparedStatement ps_delete_association;

	private TrainingsplanTypeDAO trainingsplanTypeDAO;
	private TrainingsSessionDAO trainingsSessionDAO;

	private H2TrainingsplanDAOImpl(DBHandler handler) throws PersistenceException {
		try {
			ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
			trainingsplanTypeDAO = (TrainingsplanTypeDAO) context.getBean("trainingsplanTypeDAO");
			trainingsSessionDAO = (TrainingsSessionDAO) context.getBean("trainingsSessionDAO");

			con = handler.getConnection();

			/** Trainingplan **/
			ps_create = con.prepareStatement("INSERT INTO TrainingsPlan (UID, name, description, isDeleted)" +
					"VALUES (?, ?, ?, ?)");
			ps_findAll = con.prepareStatement("SELECT * FROM TrainingsPlan WHERE isDeleted = FALSE ");
			ps_find_user = con.prepareStatement("SELECT * FROM TrainingsPlan " +
					"WHERE UID = ? AND name LIKE ? AND isDeleted LIKE ?");
			ps_find_nouser = con.prepareStatement("SELECT * FROM TrainingsPlan " +
					"WHERE name LIKE ? AND isDeleted LIKE ?");
			ps_findID = con.prepareStatement("SELECT * FROM TrainingsPlan WHERE ID_Plan=? ");
			ps_update = con.prepareStatement("UPDATE TrainingsPlan " +
					"SET UID = ?, name = ?, description = ?, isDeleted = ? WHERE ID_Plan = ?");
			ps_delete = con.prepareStatement("UPDATE TrainingsPlan SET isDeleted = TRUE WHERE ID_Plan = ?");

			/** Sequences **/
			ps_seq_TP = con.prepareStatement("SELECT currval('seq_TP')");

			ps_find_association = con.prepareStatement("SELECT * FROM PlanHasType WHERE ID_Plan = ?");
			ps_create_association = con.prepareStatement("INSERT INTO PlanHasType VALUES (?, ?, FALSE)");
			ps_delete_association = con.prepareStatement("UPDATE PlanHasType SET isDeleted = TRUE " +
					"WHERE ID_Plan = ? AND ID_Type = ?");

		} catch (DBException | SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed to prepare statements", e);
		}
	}

	@Override
	public Trainingsplan create(Trainingsplan dto) throws PersistenceException {

		try {
			ps_create.setObject(1, dto.getUid() != null ? dto.getId() : null);
			ps_create.setString(2, dto.getName());
			ps_create.setString(3, dto.getDescr());
			ps_create.setBoolean(4, dto.getIsDeleted());

			executeUpdate(ps_create);
			ResultSet rs = executeQuery(ps_seq_TP);
			rs.next();
			dto.setId(rs.getInt(1));

			if (dto.getTrainingsplanTypes() != null) {
				for (TrainingsplanType type : dto.getTrainingsplanTypes()) {
					TrainingsplanType type_found = trainingsplanTypeDAO.searchByID(type.getId());
					if (type_found == null) {
						trainingsplanTypeDAO.create(type);
					}
				}
			}
			if (dto.getTrainingsSessions() != null) {

				for (TrainingsSession session : dto.getTrainingsSessions()) {
					trainingsSessionDAO.create(session);
				}
			}

		} catch (SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed create " + dto, e);
		}

		return dto;
	}

	@Override
	public List<Trainingsplan> findAll() throws PersistenceException {
		try {
			ResultSet rs = executeQuery(ps_findAll);
			List<Trainingsplan> plans = new ArrayList<>();

			while (rs.next()) {

				Trainingsplan plan = new Trainingsplan();
				int id = rs.getInt("ID_Plan");
				plan.setId(id);
				plan.setUid((Integer) rs.getObject("UID"));
				plan.setIsDeleted(rs.getBoolean("isDeleted"));
				plan.setName(rs.getString("name"));
				plan.setDescr(rs.getString("description"));

				List<TrainingsSession> sessions = trainingsSessionDAO.find(new TrainingsSession(null, id, null, null, null));
				plan.setTrainingsSessions(sessions);

				plan.setTrainingsplanTypes(findAssociatedTypes(id));

				plans.add(plan);
			}
			return plans;
		} catch (SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed to findAll ", e);
		}
	}

	@Override
	public Trainingsplan searchByID(int id) throws PersistenceException {
		try {
			ps_findID.setInt(1, id);
			ResultSet rs = executeQuery(ps_findID);
			Trainingsplan plan = null;

			if (rs.next()) {

				plan = new Trainingsplan();
				plan.setId(id);
				plan.setUid((Integer) rs.getObject("UID"));
				plan.setIsDeleted(rs.getBoolean("isDeleted"));
				plan.setName(rs.getString("name"));
				plan.setDescr(rs.getString("description"));

				List<TrainingsSession> sessions = trainingsSessionDAO.find(new TrainingsSession(null, id, null, false, null));
				plan.setTrainingsSessions(sessions);

				plan.setTrainingsplanTypes(findAssociatedTypes(id));
			}
			return plan;
		} catch (SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed to searchByID " + id, e);
		}
	}

	@Override
	public Trainingsplan update(Trainingsplan dto) throws PersistenceException {

		try {
			ps_update.setObject(1, dto.getUid());
			ps_update.setString(2, dto.getName());
			ps_update.setString(3, dto.getDescr());
			ps_update.setBoolean(4, dto.getIsDeleted());
			ps_update.setInt(5, dto.getId());

			executeUpdate(ps_update);

			List<TrainingsSession> sessions = trainingsSessionDAO.find(new TrainingsSession(null, dto.getId(), null, false, null));

			if (sessions != null && sessions.size() != dto.getTrainingsSessions().size()) {
				List<TrainingsSession> sessions_toDelete = new ArrayList<>();
				List<TrainingsSession> sessions_toCreate = new ArrayList<>();

				sessions_toDelete.addAll(sessions.stream().filter(session ->
						!dto.getTrainingsSessions().contains(session)).collect(Collectors.toList()));

				sessions_toCreate.addAll(dto.getTrainingsSessions().stream().filter(session ->
						!sessions.contains(session)).collect(Collectors.toList()));

				for (TrainingsSession session : sessions_toCreate) {
					trainingsSessionDAO.create(session);
				}

				for (TrainingsSession session : sessions_toDelete) {
					trainingsSessionDAO.delete(session);
				}
			}
			List<TrainingsplanType> types = findAssociatedTypes(dto.getId());

			if (types != null && dto.getTrainingsplanTypes() != null) {
				if (types.size() != dto.getTrainingsplanTypes().size()) {
					List<TrainingsplanType> types_toDelete = new ArrayList<>();
					List<TrainingsplanType> types_toCreate = new ArrayList<>();

					types_toDelete.addAll(types.stream().filter(type ->
							!dto.getTrainingsplanTypes().contains(type)).collect(Collectors.toList()));

					types_toCreate.addAll(dto.getTrainingsplanTypes().stream().filter(type ->
							!types.contains(type)).collect(Collectors.toList()));

					for (TrainingsplanType type : types_toCreate) {
						trainingsplanTypeDAO.create(type);
						createAssociationToType(dto.getId(), type.getId());
					}

					for (TrainingsplanType type : types_toDelete) {
						trainingsplanTypeDAO.delete(type);
						deleteAssociationToType(dto.getId(), type.getId());
					}
				}
			}

			return dto;
		} catch (SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed to update " + dto, e);
		}
	}

	@Override
	public void delete(Trainingsplan dto) throws PersistenceException {

		try {
			ps_delete.setInt(1, dto.getId());
			executeUpdate(ps_delete);

			if (dto.getTrainingsSessions() != null) {
				for (TrainingsSession session : dto.getTrainingsSessions()) {
					trainingsSessionDAO.delete(session);
				}
			}
			if (findAssociatedTypes(dto.getId()) != null) {
				for (TrainingsplanType type : findAssociatedTypes(dto.getId())) {
					trainingsplanTypeDAO.delete(type);
					deleteAssociationToType(dto.getId(), type.getId());
				}
			}

		} catch (SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed to prepare statements", e);
		}
	}

	@Override
	public List<Trainingsplan> find(Trainingsplan filter) throws PersistenceException {
		try {
			List<Trainingsplan> plans = new ArrayList<>();

			if (filter.getId() != null) {
				plans.add(searchByID(filter.getId()));
				return plans;
			}

			PreparedStatement ps_find;

			if(filter.getUid() != null){
				ps_find_user.setInt(1, filter.getUid());

				if (filter.getName() != null) ps_find_user.setString(2, filter.getName());
				else ps_find_user.setString(2, "%");
				if (filter.getIsDeleted() != null) ps_find_user.setBoolean(4, filter.getIsDeleted());
				else ps_find_user.setString(3, "%");

				ps_find = ps_find_user;
			} else {
				if (filter.getName() != null) ps_find_nouser.setString(1, filter.getName());
				else ps_find_nouser.setString(1, "%");
				if (filter.getIsDeleted() != null) ps_find_nouser.setBoolean(2, filter.getIsDeleted());
				else ps_find_nouser.setString(2, "%");

				ps_find = ps_find_nouser;
			}

			ResultSet rs = executeQuery(ps_find);

			while (rs.next()) {

				Trainingsplan plan = new Trainingsplan();
				int id = rs.getInt("ID_Plan");
				plan.setId(id);
				plan.setUid((Integer) rs.getObject("UID"));
				plan.setIsDeleted(rs.getBoolean("isDeleted"));
				plan.setName(rs.getString("name"));
				plan.setDescr(rs.getString("description"));

				List<TrainingsSession> sessions = trainingsSessionDAO.find(new TrainingsSession(null, id, null, null, null));
				plan.setTrainingsSessions(sessions);

				plan.setTrainingsplanTypes(findAssociatedTypes(id));

				plans.add(plan);
			}
			return plans;

		} catch (SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed to find with filter" + filter, e);
		}
	}

	private List<TrainingsplanType> findAssociatedTypes(int id) throws SQLException, PersistenceException {
		ps_find_association.setInt(1, id);
		ResultSet rs = executeQuery(ps_find_association);
		List<TrainingsplanType> types = null;

		if (rs.next()) {
			types = new ArrayList<>();
			do {
				types.add(trainingsplanTypeDAO.searchByID(rs.getInt("ID_Type")));
			} while (rs.next());
		}
		return types;
	}

	private void createAssociationToType(int id_plan, int id_type) throws SQLException, PersistenceException {
		ps_create_association.setInt(1, id_plan);
		ps_create_association.setInt(2, id_type);
		executeUpdate(ps_create_association);
	}

	private void deleteAssociationToType(int id_plan, int id_type) throws SQLException, PersistenceException {
		ps_delete_association.setInt(1, id_plan);
		ps_delete_association.setInt(2, id_type);
		executeUpdate(ps_delete_association);
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
