package sepm.ss15.grp16.persistence.database.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;
import sepm.ss15.grp16.persistence.database.DBHandler;
import sepm.ss15.grp16.persistence.exception.DBException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Concrete db-handler for h2 database, implementing the singelton pattern for the connection, to ensure a single connection
 * <p/>
 * Author: Lukas Baronyai (lb)
 * Adapted: Lukas Kathrein (lk)
 */
public class H2DBConnectorImpl implements DBHandler {
    private static final Logger LOGGER = LogManager.getLogger(H2DBConnectorImpl.class);

    private static Connection con = null;
    private static JdbcDataSource ds;

    private String path;
    private String user;
    private String password;

    public H2DBConnectorImpl(String path, String user, String password) {
        this.path = path;
        this.user = user;
        this.password = password;
    }

    @Override
    public void openConnection() throws DBException {
        if (con == null) {

            try {
                LOGGER.info("try to get connection to database");
                Class.forName("org.h2.Driver");

                ds = new JdbcDataSource();
                ds.setURL(path);
                ds.setUser(user);
                ds.setPassword(password);
                con = ds.getConnection();
                //con = DriverManager.getConnection(path, user, password);
                LOGGER.info("connection successful established");

                con.setAutoCommit(false);
                execScripts();
                con.setAutoCommit(true);

            } catch (ClassNotFoundException | SQLException | FileNotFoundException | URISyntaxException e) {
                LOGGER.error(e.getMessage());
                throw new DBException("Failed to open connection", e);
            }
        }
    }

    @Override
    public Connection getConnection() throws DBException {
        if (con == null) openConnection();
        return con;
    }

    @Override
    public void closeConnection() throws DBException {
        if (con != null) {
            try {
                LOGGER.info("try to close connection to database");
                con.close();
                con = null;     //for recognising the closed connection
                ds = null;
                LOGGER.info("connection successful closed");
            } catch (SQLException e) {
                LOGGER.error(e.getMessage());
                throw new DBException("Failed to close connection", e);
            }
        }
    }

    /**
     * execute every sql script with "create" in the name in the ressource folder "sql"
     *
     * @throws FileNotFoundException
     * @throws SQLException
     */
    private void execScripts() throws FileNotFoundException, SQLException, URISyntaxException {
        LOGGER.info("execute SQL-Scripts from ressources/sql for create & insert");
        URL url_create = getClass().getClassLoader().getResource("sql/create");
        URL url_insert = getClass().getClassLoader().getResource("sql/insert");

        if (url_create != null && url_insert != null) {
            String sql_url_create = url_create.toURI().getPath();
            String sql_url_insert = url_insert.toURI().getPath();

            File folder_create = new File(sql_url_create);
            File folder_insert = new File(sql_url_insert);

            File[] listOfFiles_create = folder_create.listFiles();
            File[] listOfFiles_insert = folder_insert.listFiles();

            if (listOfFiles_create != null && listOfFiles_insert != null) {
                File[] listOfFiles = new File[listOfFiles_create.length + listOfFiles_insert.length];
                System.arraycopy(listOfFiles_create, 0, listOfFiles, 0, listOfFiles_create.length);
                System.arraycopy(listOfFiles_insert, 0, listOfFiles, listOfFiles_create.length, listOfFiles_insert.length);

                for (File file : listOfFiles) {
                    String fileName = file.getName().toLowerCase();
                    String filetype = fileName.substring(fileName.length() - 4);

                    if ((fileName.contains("create") || fileName.contains("insert")) && filetype.equals(".sql")) {
                        LOGGER.debug("exec:" + fileName);
                        RunScript.execute(con, new FileReader(file));
                    }
                }
            }
        }
    }
}
