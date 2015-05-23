package sepm.ss15.grp16.persistence.dao.exercise.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sepm.ss15.grp16.entity.exercise.AbsractCategory;
import sepm.ss15.grp16.entity.exercise.EquipmentCategory;
import sepm.ss15.grp16.entity.exercise.MusclegroupCategory;
import sepm.ss15.grp16.entity.exercise.TrainingsCategory;
import sepm.ss15.grp16.persistence.dao.exercise.CategoryDAO;
import sepm.ss15.grp16.persistence.database.DBHandler;
import sepm.ss15.grp16.persistence.exception.DBException;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukas on 11.05.2015.
 */
public class H2CategoryDAOImpl implements CategoryDAO {

    private static final Logger LOGGER = LogManager.getLogger();
    private static Connection connection;
    private PreparedStatement readStatement;
    private PreparedStatement getAllEquipment;
    private PreparedStatement getAllMuscle;
    private PreparedStatement getAllTrainingstype;
    private PreparedStatement findByIDStatement;

    private H2CategoryDAOImpl(DBHandler handler) throws PersistenceException, DBException {
        try {
            this.connection = handler.getConnection();
            readStatement = connection.prepareStatement("SELECT * from CATEGORY ");
            getAllTrainingstype = connection.prepareStatement("SELECT * from CATEGORY  where type=0;");
            getAllMuscle = connection.prepareStatement("SELECT * from CATEGORY  where type=1;");
            getAllEquipment = connection.prepareStatement("SELECT * from CATEGORY  where type=2;");
            findByIDStatement = connection.prepareStatement("SELECT * FROM CATEGORY where id = ?;");
        } catch (SQLException e) {
            throw new PersistenceException("failed to prepare statements", e);
        }
    }


    /**
     * user can not create own categorys
     *
     * @param dto which shall be inserted into the underlying persistance layer.
     *            must not be null, id must be null
     * @return
     * @throws PersistenceException
     */
    @Override
    public AbsractCategory create(AbsractCategory dto) throws PersistenceException {
        throw new UnsupportedOperationException();
    }

    /**
     * no need for all categorys
     *
     * @return
     */
    @Override
    public List<AbsractCategory> findAll() {
        throw new UnsupportedOperationException();
    }

    /**
     * getting exactly one category specified by the given id
     *
     * @param identificaitonNumber of the category to search for
     * @return exactely one category
     * @throws PersistenceException
     */
    @Override
    public AbsractCategory searchByID(int identificaitonNumber) throws PersistenceException {
        try {
            findByIDStatement.setInt(1, identificaitonNumber);
            ResultSet rs = findByIDStatement.executeQuery();
            rs.next();
            Integer id = rs.getInt(1);
            String name = rs.getString(2);
            Integer type = rs.getInt(3);

            if (type == 0)
                return new TrainingsCategory(id, name);

            if (type == 1)
                return new MusclegroupCategory(id, name);

            if (type == 2)
                return new EquipmentCategory(id, name);

            return null;
        } catch (SQLException e) {
            throw new PersistenceException("unable to find category with this id: " + identificaitonNumber);
        }
    }


    @Override
    public AbsractCategory update(AbsractCategory dto) throws PersistenceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(AbsractCategory dto) throws PersistenceException {
        throw new UnsupportedOperationException();
    }

    /**
     * getting a list of all equipment categorys
     *
     * @return a list of all equipment categorys like, kurzhantel, langhantel, springschnur ...
     * @throws PersistenceException
     */
    @Override
    public List<EquipmentCategory> getAllEquipment() throws PersistenceException {
        LOGGER.info("finding all exercises from the h2 database");
        List<EquipmentCategory> categoryList = new ArrayList<>();
        try {
            ResultSet rs = getAllEquipment.executeQuery();
            while (rs.next()) {
                categoryList.add(extractEquipment(rs)); //getting an entity from the resultset
            }
            return categoryList;
        } catch (SQLException e) {
            throw new PersistenceException("failed to get exercises from database", e);
        }
    }

    /**
     * getting a list of all muscle groups
     *
     * @return a list of all muslce groups like, bauchmuskeln, oberschenkel, unterschenkel ...
     * @throws PersistenceException
     */
    @Override
    public List<MusclegroupCategory> getAllMusclegroup() throws PersistenceException {
        LOGGER.info("finding all exercises from the h2 database");
        List<MusclegroupCategory> categoryList = new ArrayList<>();
        try {
            ResultSet rs = getAllMuscle.executeQuery();
            while (rs.next()) {
                categoryList.add(extractMuscle(rs)); //getting an entity from the resultset
            }
            return categoryList;
        } catch (SQLException e) {
            throw new PersistenceException("failed to get exercises from database", e);
        }
    }


    /**
     * getting a list of all trainingstypes
     *
     * @return a list of all trainingstypes: ausdauer, kraft, balance, flexibilitaet
     * @throws PersistenceException
     */
    @Override
    public List<TrainingsCategory> getAllTrainingstype() throws PersistenceException {
        LOGGER.info("finding all exercises from the h2 database");
        List<TrainingsCategory> categoryList = new ArrayList<>();
        try {
            ResultSet rs = getAllTrainingstype.executeQuery();
            while (rs.next()) {
                categoryList.add(extractTraining(rs)); //getting an entity from the resultset
            }
            return categoryList;
        } catch (SQLException e) {
            throw new PersistenceException("failed to get exercises from database", e);
        }
    }

    /**
     * extracting a TrainingsCategory from a given resultstet
     *
     * @param rs the result set to extract from
     * @return one instance of a TrainingsCategory
     * @throws PersistenceException
     */
    private TrainingsCategory extractTraining(ResultSet rs) throws PersistenceException {
        LOGGER.debug("extracting a category from a given resultset in dao layer");
        try {
            Integer id = rs.getInt(1);
            String name = rs.getString(2);
            Integer category = rs.getInt(3);

            return new TrainingsCategory(id, name);
        } catch (SQLException e) {
            throw new PersistenceException("failed to extract category from resultset" + rs);
        }
    }


    /**
     * extracting a MusclegroupCategory from a given resultstet
     *
     * @param rs the result set to extract from
     * @return one instance of a MusclegroupCategory
     * @throws PersistenceException
     */
    private MusclegroupCategory extractMuscle(ResultSet rs) throws PersistenceException {
        LOGGER.debug("extracting a category from a given resultset in dao layer");
        try {
            Integer id = rs.getInt(1);
            String name = rs.getString(2);
            Integer category = rs.getInt(3);


            return new MusclegroupCategory(id, name);

        } catch (SQLException e) {
            throw new PersistenceException("failed to extract category from resultset" + rs);
        }
    }


    /**
     * extracting a EquipmentCategory from a given resultstet
     *
     * @param rs the result set to extract from
     * @return one instance of a EquipmentCategory
     * @throws PersistenceException
     */
    private EquipmentCategory extractEquipment(ResultSet rs) throws PersistenceException {
        LOGGER.debug("extracting a category from a given resultset in dao layer");
        try {
            Integer id = rs.getInt(1);
            String name = rs.getString(2);
            Integer category = rs.getInt(3);
            return new EquipmentCategory(id, name);

        } catch (SQLException e) {
            throw new PersistenceException("failed to extract category from resultset" + rs);
        }
    }

}
