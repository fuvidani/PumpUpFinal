package sepm.ss15.grp16.persistence.dao.Training.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sepm.ss15.grp16.entity.Training.TrainingsSession;
import sepm.ss15.grp16.entity.Training.Trainingsplan;
import sepm.ss15.grp16.entity.Training.TrainingsplanType;
import sepm.ss15.grp16.persistence.dao.Training.ExerciseSetDAO;
import sepm.ss15.grp16.persistence.dao.Training.TrainingsSessionDAO;
import sepm.ss15.grp16.persistence.dao.Training.TrainingsplanDAO;
import sepm.ss15.grp16.persistence.dao.Training.TrainingsplanTypeDAO;
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
public class H2TrainingsplanTypeDAOImpl implements TrainingsplanTypeDAO {

	private static final Logger LOGGER = LogManager.getLogger(H2TrainingsplanTypeDAOImpl.class);

	private Connection con;

	private PreparedStatement ps_create;
	private PreparedStatement ps_findAll;
	private PreparedStatement ps_findID;
	private PreparedStatement ps_find_user;
	private PreparedStatement ps_find_nouser;
	private PreparedStatement ps_update;
	private PreparedStatement ps_delete;

	private PreparedStatement ps_seq_TPType;

	private PreparedStatement ps_find_association;
	private PreparedStatement ps_create_association;
	private PreparedStatement ps_delete_association;

	private TrainingsplanDAO trainingsplanDAO;


	private H2TrainingsplanTypeDAOImpl(DBHandler handler) throws PersistenceException {
		try {
			con = handler.getConnection();

			/** Trainingstype **/
			ps_create = con.prepareStatement("INSERT INTO TrainingsPlanType (UID, name, description, isDeleted) " +
					"VALUES (?, ?, ?, ? )");
			ps_findAll = con.prepareStatement("SELECT * FROM TrainingsPlanType");
			ps_findID = con.prepareStatement("SELECT * FROM TrainingsPlanType WHERE ID_Type=? ");
			ps_find_user = con.prepareStatement("SELECT * FROM TrainingsPlanType " +
					"WHERE UID = ? AND name LIKE ?");
			ps_find_nouser = con.prepareStatement("SELECT * FROM TrainingsPlanType " +
					"WHERE name LIKE ?");
			ps_update = con.prepareStatement("UPDATE TrainingsPlanType SET UID=?, name=?, description=?, isDeleted=? WHERE ID_Type=?");
			ps_delete = con.prepareCall("UPDATE TrainingsPlanType SET isDeleted=TRUE WHERE ID_Type=?");

			/** Sequences **/
			ps_seq_TPType = con.prepareStatement("SELECT currval('seq_TPType')");

			/** Trainingtype refer plan **/
			ps_find_association = con.prepareStatement("SELECT * FROM PlanHasType WHERE ID_Type = ?");
			ps_create_association = con.prepareStatement("INSERT INTO PlanHasType VALUES (?, ?, FALSE)");
			ps_delete_association = con.prepareStatement("DELETE FROM PlanHasType WHERE ID_Plan = ? AND ID_Type = ?");

		} catch (DBException | SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed to prepare statements", e);
		}
	}

	@Override
	public TrainingsplanType create(TrainingsplanType dto) throws PersistenceException {
		try {
			ps_create.setObject(1, dto.getUid() != null ? dto.getId() : null);
			ps_create.setString(2, dto.getName());
			ps_create.setString(3, dto.getDescr());
			ps_create.setBoolean(4, false);

			executeUpdate(ps_create);
			ResultSet rs = executeQuery(ps_seq_TPType);
			rs.next();
			dto.setId(rs.getInt(1));

			if (dto.getTrainingsplans() != null) {
				for (Trainingsplan plan : dto.getTrainingsplans()) {
					Trainingsplan plan_found = trainingsplanDAO.searchByID(plan.getId());
					if (plan_found == null) {
						trainingsplanDAO.create(plan);
					}
				}
			}

		} catch (SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed create " + dto, e);
		}

		return dto;
	}

	@Override
	public List<TrainingsplanType> findAll() throws PersistenceException {
		try {
			ResultSet rs = executeQuery(ps_findAll);
			List<TrainingsplanType> types = new ArrayList<>();

			while (rs.next()) {

				TrainingsplanType type = new TrainingsplanType();
				int id = rs.getInt("ID_Type");
				type.setId(id);
				type.setUid((Integer) rs.getObject("UID"));
				type.setIsDeleted(rs.getBoolean("isDeleted"));
				type.setName(rs.getString("name"));
				type.setDescr(rs.getString("description"));

				type.setTrainingsplans(findAssociatedPlans(id));

				types.add(type);
			}
			return types;
		} catch (SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed to findAll ", e);
		}
	}

	@Override
	public TrainingsplanType searchByID(int id) throws PersistenceException {
		try {
			ps_findID.setInt(1, id);
			ResultSet rs = executeQuery(ps_findID);
			TrainingsplanType type = null;

			if (rs.next()) {

				type = new TrainingsplanType();
				type.setId(id);
				type.setUid((Integer) rs.getObject("UID"));
				type.setIsDeleted(rs.getBoolean("isDeleted"));
				type.setName(rs.getString("name"));
				type.setDescr(rs.getString("description"));

				type.setTrainingsplans(findAssociatedPlans(id));
			}
			return type;
		} catch (SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed to searchByID " + id, e);
		}
	}

	@Override
	public TrainingsplanType update(TrainingsplanType dto) throws PersistenceException {
		try {
			ps_update.setObject(1, dto.getUid());
			ps_update.setString(2, dto.getName());
			ps_update.setString(3, dto.getDescr());
			ps_update.setBoolean(4, dto.getIsDeleted());
			ps_update.setInt(5, dto.getId());

			executeUpdate(ps_update);

			List<Trainingsplan> plans = findAssociatedPlans(dto.getId());

			if (plans != null &&
					dto.getTrainingsplans() != null &&
					plans.size() != dto.getTrainingsplans().size()) {

				List<Trainingsplan> plans_toDelete = new ArrayList<>();
				List<Trainingsplan> plans_toCreate = new ArrayList<>();

				plans_toDelete.addAll(plans.stream().filter(plan ->
						!dto.getTrainingsplans().contains(plan)).collect(Collectors.toList()));

				plans_toCreate.addAll(dto.getTrainingsplans().stream().filter(plan ->
						!plans.contains(plan)).collect(Collectors.toList()));

				for (Trainingsplan plan : plans_toCreate) {
					trainingsplanDAO.create(plan);
					createAssociationToPlan(dto.getId(), plan.getId());
				}

				for (Trainingsplan plan : plans_toDelete) {
					trainingsplanDAO.delete(plan);
					deleteAssociationToPlan(dto.getId(), plan.getId());
				}
			} else if (plans == null && dto.getTrainingsplans() != null) {
				for (Trainingsplan plan : dto.getTrainingsplans()) {
					trainingsplanDAO.create(plan);
					createAssociationToPlan(dto.getId(), plan.getId());
				}
			} else if (dto.getTrainingsplans() == null && plans != null) {
				for (Trainingsplan plan : plans) {
					trainingsplanDAO.delete(plan);
					deleteAssociationToPlan(dto.getId(), plan.getId());
				}
			}

			return dto;
		} catch (SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed to update " + dto, e);
		}
	}

	@Override
	public void delete(TrainingsplanType dto) throws PersistenceException {
		try {
			ps_delete.setInt(1, dto.getId());
			executeUpdate(ps_delete);

			if (findAssociatedPlans(dto.getId()) != null) {
				for (Trainingsplan plan : findAssociatedPlans(dto.getId())) {
					trainingsplanDAO.delete(plan);
					deleteAssociationToPlan(dto.getId(), plan.getId());
				}
			}

		} catch (SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed to prepare statements", e);
		}
	}

	@Override
	public List<TrainingsplanType> find(TrainingsplanType filter) throws PersistenceException {
		try {
			List<TrainingsplanType> types = null;

			if (filter.getId() != null) {
				types = new ArrayList<>();
				types.add(searchByID(filter.getId()));
				return types;
			}

			PreparedStatement ps_find;

			if (filter.getUid() != null) {
				ps_find_user.setInt(1, filter.getUid());

				if (filter.getName() != null) ps_find_user.setString(2, filter.getName());
				else ps_find_user.setString(2, "%");

				ps_find = ps_find_user;
			} else {
				if (filter.getName() != null) ps_find_nouser.setString(1, filter.getName());
				else ps_find_nouser.setString(1, "%");

				ps_find = ps_find_nouser;
			}

			ResultSet rs = executeQuery(ps_find);

			if (rs.next()) {
				types = new ArrayList<>();
				do {
					TrainingsplanType type = new TrainingsplanType();
					int id = rs.getInt("ID_Type");
					type.setId(id);
					type.setUid((Integer) rs.getObject("UID"));
					type.setIsDeleted(rs.getBoolean("isDeleted"));
					type.setName(rs.getString("name"));
					type.setDescr(rs.getString("description"));

					type.setTrainingsplans(findAssociatedPlans(id));

					types.add(type);
				} while (rs.next());
			}
			return types;

		} catch (SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed to find with filter" + filter, e);
		}
	}

	@Override
	public List<Trainingsplan> findAssociatedPlans(int id) throws PersistenceException {
		try {
			ps_find_association.setInt(1, id);
			ResultSet rs = executeQuery(ps_find_association);
			List<Trainingsplan> plans = null;

			if (rs.next()) {
				plans = new ArrayList<>();
				do {
					plans.add(trainingsplanDAO.searchByID(rs.getInt("ID_Plan")));
				} while (rs.next());
			}
			return plans;

		} catch (SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed to find associated Types", e);
		}
	}

	@Override
	public void createAssociationToPlan(int id_plan, int id_type) throws PersistenceException {
		try {
			ps_create_association.setInt(1, id_plan);
			ps_create_association.setInt(2, id_type);
			executeUpdate(ps_create_association);
		} catch (SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed to create associated Types", e);
		}
	}

	@Override
	public void deleteAssociationToPlan(int id_plan, int id_type) throws PersistenceException {
		try {
			ps_delete_association.setInt(1, id_plan);
			ps_delete_association.setInt(2, id_type);
			executeUpdate(ps_delete_association);
		} catch (SQLException e) {
			LOGGER.error("" + e.getMessage());
			throw new PersistenceException("failed to delete associated Types", e);
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

	public TrainingsplanDAO getTrainingsplanDAO() {
		return trainingsplanDAO;
	}
}
