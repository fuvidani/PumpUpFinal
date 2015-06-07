package sepm.ss15.grp16.persistence.dao.user.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.user.WeightHistory;
import sepm.ss15.grp16.persistence.dao.user.WeightHistoryDAO;
import sepm.ss15.grp16.persistence.database.DBHandler;
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

    private static final Logger LOGGER = LogManager.getLogger();
    private Connection con;
    private PreparedStatement createStatement;
    private PreparedStatement searchByUserIDStatement;
    private PreparedStatement searchByIDStatement;
    private PreparedStatement getActualWeightStatement;

    public H2WeightHistoryDAOImpl(DBHandler handler) throws PersistenceException {

        try {
            this.con = handler.getConnection();
            this.createStatement = con.prepareStatement("INSERT INTO weighthistory VALUES(?, ?, ?, ?);");
            this.searchByUserIDStatement = con.prepareStatement("SELECT * FROM weighthistory WHERE user_id = ?");
            this.searchByIDStatement = con.prepareStatement("SELECT * FROM weighthistory WHERE weighthistory_id = ?");
            this.getActualWeightStatement = con.prepareStatement("SELECT * FROM weighthistory WHERE user_id = ? AND " +
                    "weighthistory_id = (SELECT max(weighthistory_id) from weighthistory WHERE user_id = ?);");
        } catch (SQLException e) {
            throw new PersistenceException("Failed to prepare statements", e);
        } catch (DBException e) {
            throw new PersistenceException("Failed to get a connection", e);
        }

    }

    @Override
    public List<WeightHistory> searchByUserID(int user_id) throws PersistenceException {
        LOGGER.info("Searching weight from user with id: " + user_id);
        List<WeightHistory> weightHistoryList = new ArrayList<>();

        try {
            searchByUserIDStatement.setInt(1, user_id);
            ResultSet resultSet = searchByUserIDStatement.executeQuery();

            while (resultSet.next()) {
                WeightHistory foundWeightHistory = new WeightHistory(resultSet.getInt(1), resultSet.getInt(2),
                        resultSet.getInt(3), resultSet.getDate(4));
                weightHistoryList.add(foundWeightHistory);
            }

        } catch (SQLException e) {
            throw new PersistenceException("Failed to find weight from user with id: " + user_id, e);
        }

        LOGGER.info("Searching weight from user with id: " + user_id + " was successful");
        return weightHistoryList;
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
        LOGGER.info("Searching weighthistory with id: " + id);
        WeightHistory weightHistory = null;

        try {
            searchByIDStatement.setInt(1, id);
            ResultSet resultSet = searchByIDStatement.executeQuery();

            if (resultSet.next()) {
                weightHistory = new WeightHistory(resultSet.getInt(1), resultSet.getInt(2),
                        resultSet.getInt(3), resultSet.getDate(4));
            }

        } catch (SQLException e) {
            throw new PersistenceException("Failed to find weighthistory with id: " + id, e);
        }

        LOGGER.info("Searching weighthistory with id: " + id + " was successful");
        return weightHistory;
    }

    @Override
    public WeightHistory update(WeightHistory dto) throws PersistenceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(WeightHistory dto) throws PersistenceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public WeightHistory getActualWeight(int user_id) throws PersistenceException {

        LOGGER.info("Finding actual weighthistory...");
        WeightHistory foundWeightHistory = null;

        try {
            getActualWeightStatement.setInt(1, user_id);
            getActualWeightStatement.setInt(2, user_id);
            ResultSet rs = getActualWeightStatement.executeQuery();
            if (rs.next() == true) {
                foundWeightHistory = new WeightHistory(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getDate(4));
            }
        } catch (SQLException e) {
            throw new PersistenceException("Failed to get actual weight", e);
        }

        LOGGER.info("Successfully found actual weighthistory...");
        return foundWeightHistory;
    }
}
