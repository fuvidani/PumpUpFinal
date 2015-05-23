package sepm.ss15.grp16.persistence.database;

import sepm.ss15.grp16.persistence.exception.DBException;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.sql.Connection;

/**
 * Created by lukas on 30.04.2015.
 */
public interface DBHandler {

    /**
     * opens a regular connection to a database
     *
     * @throws PersistenceException rising from the communication process with the database (SQLException, FileBotFoundException e.g.)
     */
    public void openConnection() throws DBException;

    /**
     * returns the regular connection of the db-handler
     * caller must first open the connection, otherwise he will get NULL
     *
     * @return the connection, if opened, otherwise NULL
     */
    public Connection getConnection() throws DBException;

    /**
     * closes the regular connection, should be called after openConnection and before program end
     *
     * @throws PersistenceException rising from the communication process with the database (SQLException, FileBotFoundException e.g.)
     */
    public void closeConnection() throws DBException;

    /**
     * activate the testmode for testing pupose (e.g. with testing data or no autocommit)
     *
     * @throws PersistenceException rising from the communication process with the database (SQLException, FileBotFoundException e.g.)
     */
    public void activateTestMode() throws DBException;

    /**
     * deactivate the testmode for testing pupose (e.g. with rollback)
     *
     * @throws PersistenceException rising from the communication process with the database (SQLException, FileBotFoundException e.g.)
     */
    public void deactivateTestMode() throws DBException;


}
