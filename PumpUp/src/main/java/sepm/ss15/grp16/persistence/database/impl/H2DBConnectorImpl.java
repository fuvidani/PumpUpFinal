package sepm.ss15.grp16.persistence.database.impl;

import sepm.ss15.grp16.persistence.database.DBHandler;
import sepm.ss15.grp16.persistence.exception.DBException;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by lukas on 30.04.2015.
 */
public class H2DBConnectorImpl implements DBHandler {

    private static Connection con = null;
    private static H2DBConnectorImpl h2DBConnector;

    private String path;
    private String user;
    private String password;

    public H2DBConnectorImpl(String path, String user, String password){
        this.path = path;
        this.user = user;
        this.password = password;
    }

    @Override
    public void openConnection() throws DBException {
        if (con == null) {

            try {
                Class.forName("org.h2.Driver");

                con = DriverManager.getConnection(path, user, password);


            } catch (ClassNotFoundException  | SQLException e) {
                throw new DBException("Failed to open connection", e);
            }
        }
    }

    @Override
    public Connection getConnection() throws DBException{
        if(con==null){
            openConnection();
        }
        return con;
    }

    @Override
    public void closeConnection() throws DBException {
        if (con != null) {
            try {
                con.close();
                con = null;     //for recognising the closed connection
            } catch (SQLException e) {
                throw new DBException("Failed to close connection", e);
            }
        }
    }

    /**
     * activate test mode with:
     * - a test database with same structure as the regulare database
     * - populated with test datas
     * - no autocommit
     *
     * @throws PersistenceException
     */
    @Override
    public void activateTestMode() throws DBException {
       /* try {
            Class.forName("org.h2.Driver");

            con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/pumpup", "sa", "");
            RunScript.execute(con, new FileReader("sql/create.sql"));

            con.setAutoCommit(false);
            populateTest();


        } catch (ClassNotFoundException | FileNotFoundException | SQLException e) {
            throw new PersistenceException("Failed to activate testmode", e);
        }*/
    }

    /**
     * deactivate test mode with:
     * - switching to regular database
     * - rollback
     *
     * @throws PersistenceException
     */
    @Override
    public void deactivateTestMode() throws DBException {
       /* try {
            con.rollback();

            Class.forName("org.h2.Driver");

            con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/pumpup", "sa", "");
            con.setAutoCommit(true);

        } catch (ClassNotFoundException | SQLException e) {
            throw new PersistenceException("Failed to deactivate testmode", e);
        }*/
    }

    /**
     * populate database with testing datas (for testing purpose)
     *
     * @throws PersistenceException
     */
    private void populateTest() throws DBException {
       /* try {
            RunScript.execute(con, new FileReader("sql/testinsert.sql"));
        } catch (FileNotFoundException | SQLException e) {
            throw new PersistenceException("Failed to populate database with test data. Error: " + e.getMessage(), e);
        }*/
    }
}
