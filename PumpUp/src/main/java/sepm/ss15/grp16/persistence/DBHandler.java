package sepm.ss15.grp16.persistence;

import sepm.ss15.grp16.persistence.exception.DBException;

import java.sql.Connection;

/**
 * Created by lukas on 30.04.2015.
 */
public interface DBHandler {

    /**
     * method to get exactely one connection to the underlying database implementation
     * @return Connection to database
     * @throws DBException if the driver is not set or database does not exist
     */
    Connection getConnection() throws DBException;


    /**
     * method to close single connection to database
     * @throws DBException if problems occure with the connection to the database
     */
    void closeConnection() throws DBException;

}
