package sepm.ss15.grp16.persistence.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.WeightHistory;
import sepm.ss15.grp16.persistence.dao.WeightHistoryDAO;
import sepm.ss15.grp16.persistence.database.DBHandler;
import sepm.ss15.grp16.persistence.database.impl.H2DBConnectorImpl;
import sepm.ss15.grp16.persistence.exception.DBException;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a DAO for weighthistories, which are stored in a H2-database
 *
 * @author Michael Sober
 * @version 1.0
 */
public class H2WeightHistoryDAOImpl implements WeightHistoryDAO {

    private Connection con;
    private PreparedStatement createStatement;
    private static final Logger LOGGER = LogManager.getLogger();

    public H2WeightHistoryDAOImpl(DBHandler handler) throws PersistenceException {

        try {
            this.con = handler.getConnection();
            this.createStatement = con.prepareStatement("INSERT INTO weighthistory VALUES(?, ?, ?, ?);");
        } catch (SQLException e) {
            throw new PersistenceException("Failed to prepare statements", e);
        } catch (DBException e) {
            throw new PersistenceException("Failed to get a connection", e);
        }

    }

    @Override
    public List<WeightHistory> searchByUserID(int user_id) {
        //TODO: Implement me
        return null;
    }

    @Override
    public WeightHistory create(WeightHistory weightHistory) throws PersistenceException {

        LOGGER.info("Creating new weightHistory...");

        if (weightHistory == null) {
            LOGGER.error("Failed to create new weightHistory");
            throw new PersistenceException("weightHistory isn't allowed to be null");
        }

        try {
            Statement weightHistoryNextValStatement = con.createStatement();
            ResultSet rs_weightHistoryNextVal = weightHistoryNextValStatement.executeQuery("SELECT NEXTVAL('weighthistory_seq');");
            rs_weightHistoryNextVal.next();
            weightHistory.setWeightHistory_id(rs_weightHistoryNextVal.getInt(1));
            weightHistory.setDate(new java.util.Date());

            createStatement.setInt(1, weightHistory.getWeightHistory_id());
            createStatement.setInt(2, weightHistory.getUser_id());
            createStatement.setInt(3, weightHistory.getWeight());
            createStatement.setDate(4, new java.sql.Date(weightHistory.getDate().getTime()));

            createStatement.execute();
        } catch (SQLException e) {
            LOGGER.error("Failed to create new weightHistory");
            throw new PersistenceException("Failed to create a new weightHistory", e);
        }

        LOGGER.info("Created weightHistory successfully");
        return weightHistory;
    }

    @Override
    public List<WeightHistory> findAll() throws PersistenceException {
        LOGGER.info("Finding all weighthistories...");

        List<WeightHistory> weightHistoryList = new ArrayList<>();

        try {

            Statement findAllStatement = con.createStatement();
            ResultSet rs_allWeightHistories = findAllStatement.executeQuery("SELECT * FROM weighthistory;");

            while (rs_allWeightHistories.next()) {
                WeightHistory foundWeightHistory = new WeightHistory(rs_allWeightHistories.getInt(1), rs_allWeightHistories.getInt(2),
                        rs_allWeightHistories.getInt(3), rs_allWeightHistories.getDate(4));
                weightHistoryList.add(foundWeightHistory);
            }

        } catch (SQLException e) {
            LOGGER.error("Failed to find all weighthistories");
            throw new PersistenceException("Failed to find all weighthistories", e);
        }

        LOGGER.info("Found all weighthistories successfully");
        return weightHistoryList;
    }

    @Override
    public WeightHistory searchByID(int id) throws PersistenceException {
        //TODO: Implement me
        return null;
    }

    @Override
    public WeightHistory update(WeightHistory dto) throws PersistenceException {
        //TODO: Implement me
        return null;
    }

    @Override
    public void delete(WeightHistory dto) throws PersistenceException {
        //TODO: Implement me
    }
}
