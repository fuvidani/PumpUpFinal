package sepm.ss15.grp16.persistence.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.User;
import sepm.ss15.grp16.persistence.dao.UserDAO;
import sepm.ss15.grp16.persistence.database.DBHandler;
import sepm.ss15.grp16.persistence.database.impl.H2DBConnectorImpl;
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

    private Connection con;
    private PreparedStatement createStatement;
    private PreparedStatement createWeightHistoryStatement;
    private PreparedStatement createBodyfatHistoryStatement;
    private PreparedStatement createPictureHistoryStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement deleteStatement;
    private static final Logger LOGGER = LogManager.getLogger();

    public H2UserDAOImpl() throws PersistenceException {

        try {
            DBHandler handler = H2DBConnectorImpl.getInstance();
            this.con = handler.getConnection();
            this.createStatement = con.prepareStatement("INSERT INTO user VALUES(?, ?, ?, ?, ?, ?);");
            this.createWeightHistoryStatement = con.prepareStatement("INSERT INTO weighthistory VALUES(?, ?, ?, ?);");
            this.createBodyfatHistoryStatement = con.prepareStatement("INSERT INTO bodyfathistory VALUES(?, ?, ?, ?);");
            this.createPictureHistoryStatement = con.prepareStatement("INSERT INTO picturehistory VALUES(?, ?, ?, ?);");
            this.deleteStatement = con.prepareStatement("UPDATE user SET isDeleted = ? WHERE user_id = ?");
        }catch(SQLException e){
            throw new PersistenceException("Failed to prepare statements", e);
        }catch(DBException e){
            throw new PersistenceException("Failed to get a connection", e);
        }
    }

    @Override
    public User create(User user) throws PersistenceException {

        LOGGER.info("Creating new user...");

        if(user == null){
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
            createStatement.setBoolean(6, user.getIsDeleted());

            createStatement.execute();
        }catch(SQLException e){
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

        try{

            Statement findAllStatement = con.createStatement();
            ResultSet rs_allUsers = findAllStatement.executeQuery("SELECT * FROM user;");

            while(rs_allUsers.next()){
                User foundUser = new User(rs_allUsers.getInt(1), rs_allUsers.getString(2), rs_allUsers.getBoolean(3),
                        rs_allUsers.getInt(4), rs_allUsers.getInt(5), rs_allUsers.getBoolean(6), null, null, null);
                userList.add(foundUser);
            }

        }catch(SQLException e){
            LOGGER.error("Failed to find all users");
            throw new PersistenceException("Failed to find all users", e);
        }

        LOGGER.info("Found all users successfully");
        return userList;
    }

    @Override
    public User update(User user) throws PersistenceException {
        return null;
    }

    @Override
    public User delete(User user) throws PersistenceException {
        return null;
    }
}
