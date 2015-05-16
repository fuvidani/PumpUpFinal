package sepm.ss15.grp16.persistence.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.BodyfatHistory;
import sepm.ss15.grp16.entity.WeightHistory;
import sepm.ss15.grp16.persistence.dao.BodyfatHistoryDAO;
import sepm.ss15.grp16.persistence.database.DBHandler;
import sepm.ss15.grp16.persistence.database.impl.H2DBConnectorImpl;
import sepm.ss15.grp16.persistence.exception.DBException;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class represents a DAO for bodyfathistories, which are stored in a H2-database
 *
 * @author Michael Sober
 * @version 1.0
 */
public class H2BodyfatHistoryDAOImpl implements BodyfatHistoryDAO {

    private Connection con;
    private PreparedStatement createStatement;
    private PreparedStatement getActualBodyfatStatement;
    private static final Logger LOGGER = LogManager.getLogger();

    public H2BodyfatHistoryDAOImpl(DBHandler handler) throws PersistenceException {

        try {
            this.con = handler.getConnection();
            this.createStatement = con.prepareStatement("INSERT INTO bodyfathistory VALUES(?, ?, ?, ?);");
            this.getActualBodyfatStatement = con.prepareStatement("SELECT * FROM bodyfathistory WHERE user_id = ? AND " +
                    "bodyfathistory_id = (SELECT max(bodyfathistory_id) from bodyfathistory);");
        } catch (SQLException e) {
            throw new PersistenceException("Failed to prepare statements", e);
        } catch (DBException e) {
            throw new PersistenceException("Failed to get a connection", e);
        }

    }

    @Override
    public List<BodyfatHistory> searchByUserID(int user_id) {
        //TODO: Implement me
        return null;
    }

    @Override
    public BodyfatHistory create(BodyfatHistory bodyfatHistory) throws PersistenceException {

        LOGGER.info("Creating new bodyfathistory...");

        if (bodyfatHistory == null) {
            LOGGER.error("Failed to create new bodyfathistory");
            throw new PersistenceException("bodyfathistory isn't allowed to be null");
        }

        try {
            Statement bodyfatHistoryNextValStatement = con.createStatement();
            ResultSet rs_bodyfatHistoryNextVal = bodyfatHistoryNextValStatement.executeQuery("SELECT NEXTVAL('bodyfathistory_seq');");
            rs_bodyfatHistoryNextVal.next();
            bodyfatHistory.setBodyfathistory_id(rs_bodyfatHistoryNextVal.getInt(1));
            bodyfatHistory.setDate(new Date());

            createStatement.setInt(1, bodyfatHistory.getBodyfathistory_id());
            createStatement.setInt(2, bodyfatHistory.getUser_id());
            createStatement.setInt(3, bodyfatHistory.getBodyfat());
            createStatement.setDate(4, new java.sql.Date(bodyfatHistory.getDate().getTime()));

            createStatement.execute();
        } catch (SQLException e) {
            LOGGER.error("Failed to create new bodyfathistory");
            throw new PersistenceException("Failed to create a new bodyfathistory", e);
        }

        LOGGER.info("Created bodyfathistory successfully");
        return bodyfatHistory;

    }

    @Override
    public List<BodyfatHistory> findAll() throws PersistenceException {
        LOGGER.info("Finding all bodyfathistories...");

        List<BodyfatHistory> bodyfatHistoryList = new ArrayList<>();

        try {

            Statement findAllStatement = con.createStatement();
            ResultSet rs_allBodyfatHistories = findAllStatement.executeQuery("SELECT * FROM bodyfathistory;");

            while (rs_allBodyfatHistories.next()) {
                BodyfatHistory foundBodyfatHistory = new BodyfatHistory(rs_allBodyfatHistories.getInt(1), rs_allBodyfatHistories.getInt(2),
                        rs_allBodyfatHistories.getInt(3), rs_allBodyfatHistories.getDate(4));
                bodyfatHistoryList.add(foundBodyfatHistory);
            }

        } catch (SQLException e) {
            LOGGER.error("Failed to find all bodyfathistories");
            throw new PersistenceException("Failed to find all bodyfathistories", e);
        }

        LOGGER.info("Found all bodyfathistories successfully");
        return bodyfatHistoryList;
    }

    @Override
    public BodyfatHistory searchByID(int id) throws PersistenceException {
        //TODO: Implement me
        return null;
    }

    @Override
    public BodyfatHistory update(BodyfatHistory dto) throws PersistenceException {
        //TODO: Implement me
        return null;
    }

    @Override
    public void delete(BodyfatHistory dto) throws PersistenceException {
        //TODO: Implement me
    }

    @Override
    public BodyfatHistory getActualBodyfat(int user_id) throws PersistenceException {

        LOGGER.info("Finding actual bodyfathistory...");
        BodyfatHistory foundBodyfatHistory = null;

        try {
            getActualBodyfatStatement.setInt(1, user_id);
            ResultSet rs = getActualBodyfatStatement.executeQuery();
            if(rs.next() == true) {
                foundBodyfatHistory = new BodyfatHistory(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getDate(4));
            }

        }catch(SQLException e){
            throw new PersistenceException("Failed to get actual bodyfat", e);
        }

        LOGGER.info("Successfully found actual bodyfathistory...");
        return foundBodyfatHistory;
    }
}
