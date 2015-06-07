package sepm.ss15.grp16.persistence.dao.user.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.util.IOUtils;
import sepm.ss15.grp16.entity.user.PictureHistory;
import sepm.ss15.grp16.persistence.dao.user.PictureHistoryDAO;
import sepm.ss15.grp16.persistence.database.DBHandler;
import sepm.ss15.grp16.persistence.exception.DBException;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.io.*;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a DAO for picturehistories, which are stored in a H2-database
 *
 * @author Michael Sober
 * @version 1.0
 */
public class H2PictureHistoryDAOImpl implements PictureHistoryDAO {

    private static final Logger LOGGER = LogManager.getLogger();
    private Connection con;
    private PreparedStatement createStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement getActualPictureStatement;
    private PreparedStatement searchByUserIDStatement;
    private PreparedStatement searchByIDStatement;
    private PreparedStatement deleteStatement;

    public H2PictureHistoryDAOImpl(DBHandler handler) throws PersistenceException {

        try {
            this.con = handler.getConnection();
            this.createStatement = con.prepareStatement("INSERT INTO picturehistory VALUES(?, ?, ?, ?, ?);");
            this.updateStatement = con.prepareStatement("UPDATE picturehistory SET user_id = ?, location = ? , date = ?," +
                    " isDeleted = ? WHERE picturehistory_id = ?");
            this.searchByUserIDStatement = con.prepareStatement("SELECT * FROM picturehistory WHERE user_id = ? AND isDeleted = false");
            this.searchByIDStatement = con.prepareStatement("SELECT * FROM picturehistory WHERE picturehistory_id = ?");
            this.deleteStatement = con.prepareStatement("UPDATE picturehistory SET isDeleted = ? WHERE picturehistory_id = ?");
            this.getActualPictureStatement = con.prepareStatement("SELECT * FROM picturehistory WHERE user_id = ? AND " +
                    "picturehistory_id = (SELECT max(picturehistory_id) from picturehistory WHERE user_id = ?) AND isDeleted = false;");
        } catch (SQLException e) {
            throw new PersistenceException("Failed to prepare statements", e);
        } catch (DBException e) {
            throw new PersistenceException("Failed to get a connection", e);
        }

    }

    @Override
    public List<PictureHistory> searchByUserID(int user_id) throws PersistenceException {

        LOGGER.info("Searching pictures from user with id: " + user_id);
        List<PictureHistory> pictureHistoryList = new ArrayList<>();

        try{
            searchByUserIDStatement.setInt(1, user_id);
            ResultSet resultSet = searchByUserIDStatement.executeQuery();

            while(resultSet.next()){
                PictureHistory pictureHistory = new PictureHistory(resultSet.getInt(1), resultSet.getInt(2),
                        resultSet.getString(3), resultSet.getDate(4), resultSet.getBoolean(5));
                pictureHistoryList.add(pictureHistory);
            }

        }catch(SQLException e){
            throw new PersistenceException("Failed to find pictures from user with id: " + user_id, e);
        }

        LOGGER.info("Searching pictures from user with id: " + user_id + " was successful");
        return pictureHistoryList;
    }

    @Override
    public PictureHistory create(PictureHistory pictureHistory) throws PersistenceException {

        LOGGER.info("Creating new pictureHistory...");

        if (pictureHistory == null) {
            LOGGER.error("Failed to create new pictureHistory");
            throw new PersistenceException("pictureHistory isn't allowed to be null");
        }

        try {

            Statement pictureHistoryNextValStatement = con.createStatement();
            ResultSet rs_pictureHistoryNextVal = pictureHistoryNextValStatement.executeQuery("SELECT NEXTVAL('pictureHistory_seq');");
            rs_pictureHistoryNextVal.next();
            pictureHistory.setPicturehistory_id(rs_pictureHistoryNextVal.getInt(1));
            pictureHistory.setDate(new java.util.Date());

            LOGGER.debug("Saving image...");
            String pathToResource = getClass().getClassLoader().getResource("img").toURI().getPath();

            LOGGER.debug("Saving in resources: " + pathToResource);
            String saveName = "/u" + pictureHistory.getUser_id() + "p" + pictureHistory.getPicturehistory_id() + ".jpg";
            String pathOfNewImage = pathToResource + saveName;
            LOGGER.debug("Saving image with path: " + pathOfNewImage);
            File picture = new File(pathOfNewImage);
            InputStream inputStream = new FileInputStream(pictureHistory.getLocation());
            OutputStream outputSteam = new FileOutputStream(picture);
            IOUtils.copy(inputStream, outputSteam);
            outputSteam.close();
            inputStream.close();
            LOGGER.debug("Saved image with name: " + saveName);
            pictureHistory.setLocation(saveName);

            createStatement.setInt(1, pictureHistory.getPicturehistory_id());
            createStatement.setInt(2, pictureHistory.getUser_id());
            createStatement.setString(3, pictureHistory.getLocation());
            createStatement.setDate(4, new java.sql.Date(pictureHistory.getDate().getTime()));
            createStatement.setBoolean(5, pictureHistory.getIsDeleted());

            createStatement.execute();

        } catch (SQLException e) {
            LOGGER.error("Failed to create new pictureHistory");
            throw new PersistenceException("Failed to create a new pictureHistory", e);
        } catch (FileNotFoundException e) {
            LOGGER.error("Failed to create new pictureHistory. File not found");
            throw new PersistenceException("Failed to create a new pictureHistory", e);
        } catch (IOException e) {
            LOGGER.error("Failed to create new pictureHistory. IO failed");
            throw new PersistenceException("Failed to create a new pictureHistory", e);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        LOGGER.info("Created pictureHistory successfully");
        return pictureHistory;

    }

    @Override
    public List<PictureHistory> findAll() throws PersistenceException {

        LOGGER.info("Finding all pictureHistories...");

        List<PictureHistory> pictureHistoryList = new ArrayList<>();

        try {

            Statement findAllStatement = con.createStatement();
            ResultSet rs_allPictureHistories = findAllStatement.executeQuery("SELECT * FROM picturehistory WHERE isDeleted = false;");

            while (rs_allPictureHistories.next()) {
                PictureHistory foundPictureHistory = new PictureHistory(rs_allPictureHistories.getInt(1), rs_allPictureHistories.getInt(2),
                        rs_allPictureHistories.getString(3), rs_allPictureHistories.getDate(4), rs_allPictureHistories.getBoolean(5));
                pictureHistoryList.add(foundPictureHistory);
            }

        } catch (SQLException e) {
            LOGGER.error("Failed to find all pictureHistories");
            throw new PersistenceException("Failed to find all pictureHistories", e);
        }

        LOGGER.info("Found all pictureHistories successfully");
        return pictureHistoryList;

    }

    @Override
    public PictureHistory searchByID(int id) throws PersistenceException {

        LOGGER.info("Searching picturehistory with id: " + id);
        PictureHistory pictureHistory = null;

        try{
            searchByIDStatement.setInt(1, id);
            ResultSet resultSet = searchByIDStatement.executeQuery();

            if(resultSet.next()){
                pictureHistory = new PictureHistory(resultSet.getInt(1), resultSet.getInt(2),
                        resultSet.getString(3), resultSet.getDate(4), resultSet.getBoolean(5));
            }

        }catch(SQLException e){
            throw new PersistenceException("Failed to find picturehistory with id: " + id, e);
        }

        LOGGER.info("Searching picturehistory with id: " + id + " was successful");
        return pictureHistory;
    }

    @Override
    public PictureHistory update(PictureHistory pictureHistory) throws PersistenceException {

        LOGGER.info("Updating a picturehistory...");

        if (pictureHistory == null) {
            LOGGER.error("Updating with null failed");
            throw new PersistenceException("pictureHistory isn't allowed to be null");
        }

        try {

            LOGGER.debug("Saving image...");
            String pathToResource = getClass().getClassLoader().getResource("img").toURI().getPath();

            LOGGER.debug("Saving in resources: " + pathToResource);
            String saveName = "/u" + pictureHistory.getUser_id() + "p" + pictureHistory.getPicturehistory_id() + ".jpg";
            String pathOfNewImage = pathToResource + saveName;
            LOGGER.debug("Saving image with path: " + pathOfNewImage);
            File picture = new File(pathOfNewImage);
            InputStream inputStream = new FileInputStream(pictureHistory.getLocation());
            OutputStream outputSteam = new FileOutputStream(picture);
            IOUtils.copy(inputStream, outputSteam);
            outputSteam.close();
            inputStream.close();
            LOGGER.debug("Saved image with name: " + saveName);
            pictureHistory.setLocation(saveName);

            updateStatement.setInt(1, pictureHistory.getUser_id());
            updateStatement.setString(2, pictureHistory.getLocation());
            updateStatement.setDate(3, new java.sql.Date(pictureHistory.getDate().getTime()));
            updateStatement.setBoolean(4, pictureHistory.getIsDeleted());
            updateStatement.setInt(5, pictureHistory.getPicturehistory_id());

            updateStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Updating the picturehistory in the database failed");
            throw new PersistenceException("Couldn't update the picturehistory", e);
        } catch (FileNotFoundException e) {
            LOGGER.error("Failed to update pictureHistory. File not found");
            throw new PersistenceException("Failed to create a new pictureHistory", e);
        } catch (IOException e) {
            LOGGER.error("Failed to update pictureHistory. IO failed");
            throw new PersistenceException("Failed to create a new pictureHistory", e);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        LOGGER.info("Updated a picturehistory successfully");
        return pictureHistory;
    }

    @Override
    public void delete(PictureHistory pictureHistory) throws PersistenceException {

        LOGGER.info("Deleting a picturehistory...");

        if (pictureHistory == null) {
            LOGGER.error("Deleting with null failed");
            throw new PersistenceException("picturehistory isn't allowed to be null");
        }

        try {
            deleteStatement.setBoolean(1, true);
            deleteStatement.setInt(2, pictureHistory.getId());
            deleteStatement.executeUpdate();
            pictureHistory.setIsDeleted(true);
        } catch (SQLException e) {
            LOGGER.error("Deleting the picturehistory from the database failed");
            throw new PersistenceException("Couldn't delete the picturehistory", e);
        }

        LOGGER.info("picturehistory successfully deleted");
    }

    @Override
    public PictureHistory getActualPicture(int user_id) throws PersistenceException {

        LOGGER.info("Finding actual picturehistory...");
        PictureHistory foundPictureHistory = null;

        try {
            getActualPictureStatement.setInt(1, user_id);
            getActualPictureStatement.setInt(2, user_id);
            ResultSet rs = getActualPictureStatement.executeQuery();
            if (rs.next()) {
                foundPictureHistory = new PictureHistory(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getDate(4), rs.getBoolean(5));
            }
        } catch (SQLException e) {
            throw new PersistenceException("Failed to get actual picture", e);
        }

        LOGGER.info("Successfully found actual picturehistory...");
        return foundPictureHistory;
    }
}
