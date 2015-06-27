package sepm.ss15.grp16.persistence.dao.user.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.persistence.dao.user.UserDAO;
import sepm.ss15.grp16.persistence.database.DBHandler;
import sepm.ss15.grp16.persistence.exception.DBException;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a DAO for users, which are stored in a H2-database
 *
 * @author Michael Sober
 * @version 1.0
 */
public class H2UserDAOImpl implements UserDAO {

    private static final Logger LOGGER = LogManager.getLogger();
    private Connection        con;
    private PreparedStatement createStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement searchByIDStatement;
    private PreparedStatement deleteStatement;

    public H2UserDAOImpl(DBHandler handler) throws PersistenceException {

        try {
            this.con = handler.getConnection();
            this.createStatement = con.prepareStatement("INSERT INTO user VALUES(?, ?, ?, ?, ?, ?, ?, ?);");
            this.updateStatement = con.prepareStatement("UPDATE user SET username = ?, gender = ? , age = ?, height = ?," + " email = ?, playlist = ?, isDeleted = ? WHERE user_id = ?");
            this.searchByIDStatement = con.prepareStatement("SELECT * FROM user WHERE user_id = ?");
            this.deleteStatement = con.prepareStatement("UPDATE user SET isDeleted = ? WHERE user_id = ?");
        } catch (SQLException e) {
            throw new PersistenceException("Failed to prepare statements", e);
        } catch (DBException e) {
            throw new PersistenceException("Failed to get a connection", e);
        }
    }

    @Override
    public User create(User user) throws PersistenceException {

        LOGGER.info("Creating new user...");

        if (user == null) {
            LOGGER.error("Failed to create new user");
            throw new PersistenceException("user isn't allowed to be null");
        }

        try {
            Statement userNextValStatement = con.createStatement();
            ResultSet rs_userNextVal = userNextValStatement.executeQuery("SELECT NEXTVAL('user_seq');");
            rs_userNextVal.next();
            user.setUser_id(rs_userNextVal.getInt(1));

            createStatement.setInt(1, user.getUser_id());
            createStatement.setString(2, user.getUsername());
            createStatement.setBoolean(3, user.isGender());
            createStatement.setInt(4, user.getAge());
            createStatement.setInt(5, user.getHeight());
            createStatement.setString(6, user.getEmail());
            createStatement.setString(7, user.getPlaylist());
            createStatement.setBoolean(8, user.getIsDeleted());

            createStatement.execute();
        } catch (SQLException e) {
            LOGGER.error("Failed to create new user");
            throw new PersistenceException("Failed to create a new user", e);
        }

        LOGGER.info("Created new user successfully");
        return user;
    }

    @Override
    public List<User> findAll() throws PersistenceException {

        LOGGER.info("Finding all users...");

        List<User> userList = new ArrayList<>();

        try {

            Statement findAllStatement = con.createStatement();
            ResultSet rs_allUsers = findAllStatement.executeQuery("SELECT * FROM user WHERE isDeleted = false;");

            while (rs_allUsers.next()) {
                User foundUser = new User(rs_allUsers.getInt(1), rs_allUsers.getString(2), rs_allUsers.getBoolean(3), rs_allUsers.getInt(4), rs_allUsers.getInt(5), rs_allUsers.getString(6), rs_allUsers.getString(7), rs_allUsers.getBoolean(8));
                userList.add(foundUser);
            }

        } catch (SQLException e) {
            LOGGER.error("Failed to find all users");
            throw new PersistenceException("Failed to find all users", e);
        }

        LOGGER.info("Found all users successfully");
        return userList;
    }

    @Override
    public User searchByID(int id) throws PersistenceException {

        LOGGER.info("Searching user with id: " + id + "...");

        User foundUser;
        try {
            searchByIDStatement.setInt(1, id);

            ResultSet rs_user = searchByIDStatement.executeQuery();
            rs_user.next();

            foundUser = new User(rs_user.getInt(1), rs_user.getString(2), rs_user.getBoolean(3), rs_user.getInt(4), rs_user.getInt(5), rs_user.getString(6), rs_user.getString(7), rs_user.getBoolean(8));

        } catch (SQLException e) {
            LOGGER.error("Searching the user with id: " + id + " failed");
            throw new PersistenceException("Couldn't search the user", e);
        }

        LOGGER.info("user successfully searched");

        return foundUser;
    }

    @Override
    public User update(User user) throws PersistenceException {

        LOGGER.info("Updating a user...");

        if (user == null) {
            LOGGER.error("Updating with null failed");
            throw new PersistenceException("user isn't allowed to be null");
        }

        try {
            updateStatement.setString(1, user.getUsername());
            updateStatement.setBoolean(2, user.isGender());
            updateStatement.setInt(3, user.getAge());
            updateStatement.setInt(4, user.getHeight());
            updateStatement.setString(5, user.getEmail());
            updateStatement.setString(6, user.getPlaylist());
            updateStatement.setBoolean(7, user.getIsDeleted());
            updateStatement.setInt(8, user.getUser_id());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Updating the user in the database failed");
            throw new PersistenceException("Couldn't update the user", e);
        }

        LOGGER.info("Updated a user successfully");
        return user;
    }

    @Override
    public void delete(User user) throws PersistenceException {

        LOGGER.info("Deleting a user...");

        if (user == null) {
            LOGGER.error("Deleting with null failed");
            throw new PersistenceException("user isn't allowed to be null");
        }

        try {
            deleteStatement.setBoolean(1, true);
            deleteStatement.setInt(2, user.getUser_id());
            deleteStatement.executeUpdate();
            user.setIsDeleted(true);
        } catch (SQLException e) {
            LOGGER.error("Deleting the user from the database failed");
            throw new PersistenceException("Couldn't delete the user", e);
        }

        LOGGER.info("user successfully deleted");
    }
}