package sepm.ss15.grp16.persistence.database.impl;

import org.h2.tools.RunScript;
import sepm.ss15.grp16.persistence.database.DBHandler;
import sepm.ss15.grp16.persistence.exception.DBException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Concrete db-handler for h2 database, implementing the singelton pattern for the connection, to ensure a single connection
 * <p/>
 * Author: Lukas Baronyai (lb)
 * Adapted: Lukas Kathrein (lk)
 */
public class H2DBConnectorImpl implements DBHandler {

	private static Connection con = null;
	private static H2DBConnectorImpl h2DBConnector;

	private H2DBConnectorImpl() {
	}

	public static H2DBConnectorImpl getInstance() throws DBException {
		if (h2DBConnector == null) {
			h2DBConnector = new H2DBConnectorImpl();
		}

		return h2DBConnector;

	}

	@Override
	public void openConnection() throws DBException {
		if (con == null) {

			try {
				Class.forName("org.h2.Driver");
				con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/pumpup", "sa", "");

				execScripts();

			} catch (ClassNotFoundException | SQLException | FileNotFoundException e) {
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
				con.close();
				con = null;     //for recognising the closed connection
			} catch (SQLException e) {
				throw new DBException("Failed to close connection", e);
			}
		}
	}

	@Override
	public void activateTestMode() throws DBException {
		try {
			Class.forName("org.h2.Driver");

			con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/pumpup", "sa", "");
			execScripts();

			con.setAutoCommit(false);
			populateTest();


		} catch (ClassNotFoundException | FileNotFoundException | SQLException e) {
			throw new DBException("Failed to activate testmode", e);
		}
	}

	@Override
	public void deactivateTestMode() throws DBException {
		try {
			con.rollback();

			Class.forName("org.h2.Driver");

			con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/pumpup", "sa", "");
			con.setAutoCommit(true);

		} catch (ClassNotFoundException | SQLException e) {
			throw new DBException("Failed to deactivate testmode", e);
		}
	}

	/**
	 * execute every sql script with "create" in the name in the ressource folder "sql"
	 *
	 * @throws FileNotFoundException
	 * @throws SQLException
	 */
	private void execScripts() throws FileNotFoundException, SQLException {
		String sql_url = getClass().getClassLoader().getResource("sql").getFile();

		if (sql_url != null) {

			File folder = new File(sql_url);
			File[] listOfFiles = folder.listFiles();

			for (File file : listOfFiles) {
				String fileName = file.getName();
				String filetype = fileName.substring(fileName.length() - 4);

				if (file.getName().toLowerCase().contains("create") && filetype.equals(".sql")) {
					RunScript.execute(con, new FileReader(file));
				}
			}
		}
	}

	/**
	 * populate database with testing datas (for testing purpose)
	 *
	 * @throws DBException
	 */
	private void populateTest() throws DBException {
		//TODO
	}
}
