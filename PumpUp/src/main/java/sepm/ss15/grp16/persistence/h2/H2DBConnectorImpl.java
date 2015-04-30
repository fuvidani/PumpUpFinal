package main.java.sepm.ss15.grp16.persistence.h2;

import main.java.sepm.ss15.grp16.persistence.DBHandler;
import main.java.sepm.ss15.grp16.persistence.exception.DBException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by lukas on 30.04.2015.
 */
public class H2DBConnectorImpl implements DBHandler {

    private static Connection CONNECTION;
    private  static H2DBConnectorImpl connector;


    private H2DBConnectorImpl(){

    }

    public static H2DBConnectorImpl getInstance(){
        if(connector == null){
            connector = new H2DBConnectorImpl();
        }
        return connector;
    }

    @Override
    public Connection getConnection() throws DBException {
        if (CONNECTION == null) {
            CONNECTION = this.openConnection();
        }
        return CONNECTION;
    }


    private Connection openConnection() throws DBException{
        try {

            if (CONNECTION == null) {
                Class.forName("org.h2.Driver");
                CONNECTION = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/pumpup", "sa", "");

            }
             return CONNECTION;

        } catch (ClassNotFoundException e) {
            throw new DBException("unable to load database driver", e);
        } catch (SQLException e) {
            throw new DBException("unable to connect to database", e);

        }
    }

    @Override
    public void closeConnection() throws DBException {
        try {
            if (CONNECTION != null) {
                CONNECTION.close();
            }
        } catch (SQLException e) {
            throw new DBException("failed to cloese CONNECTION", e);
        }
    }
}
