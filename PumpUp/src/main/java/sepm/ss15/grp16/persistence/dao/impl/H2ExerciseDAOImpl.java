package sepm.ss15.grp16.persistence.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.util.IOUtils;
import sepm.ss15.grp16.entity.AbsractCategory;
import sepm.ss15.grp16.entity.Exercise;
import sepm.ss15.grp16.persistence.dao.ExerciseDAO;
import sepm.ss15.grp16.persistence.database.DBHandler;
import sepm.ss15.grp16.persistence.database.impl.H2DBConnectorImpl;
import sepm.ss15.grp16.persistence.exception.DBException;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by lukas on 30.04.2015.
 *
 */
public class H2ExerciseDAOImpl implements ExerciseDAO {

    private PreparedStatement createStatement;
    private PreparedStatement createCategoryStatement;
    private PreparedStatement readStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement insertGifStatement;
    private PreparedStatement nextvalExercise;
    private PreparedStatement nextvalGif;
    private PreparedStatement readGifStatement;
    private PreparedStatement searchByIDStatement;
    private PreparedStatement deleteCategoryStatement;
    private static final Logger LOGGER = LogManager.getLogger();

    private static Connection connection;

    /**
     * creating an instance of the H2 implementaiton of the exercise
     * also preparing all the statements in the constructor for better performance
     * @param handler handels all the operations for the connection to the database
     * @throws PersistenceException if
     * @throws DBException
     */
    private H2ExerciseDAOImpl(DBHandler handler)throws PersistenceException, DBException{
        try{
            LOGGER.info("creating new instance of H2ExerciseDAOImpl");

            this.connection = handler.getConnection();
            createStatement = connection.prepareStatement("INSERT into EXERCISE VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
            createCategoryStatement =connection.prepareStatement("INSERT INTO exercise_category values(?, ?);");
            readStatement = connection.prepareStatement("SELECT * FROM EXERCISE where isdeleted=false;");
            searchByIDStatement = connection.prepareStatement("SELECT * FROM EXERCISE where id=?;");
            readGifStatement = connection.prepareStatement("SELECT * FROM GIF where exerciseid =?");
            updateStatement = connection.prepareStatement("UPDATE EXERCISE SET name=?, descripion=?, calories=?, videolink=?, timebased=?, isdeleted=? where id=?;");
            insertGifStatement = connection.prepareStatement("insert into gif values(?, ?, ?);");
            deleteCategoryStatement = connection.prepareStatement("delete from exercise_category where exerciseid=?");
            nextvalExercise  = connection.prepareStatement("SELECT NEXTVAL('EXERCISE_SEQ')");
            nextvalGif  = connection.prepareStatement("SELECT NEXTVAL('GIF_SEQ')");


        }catch (SQLException e) {
            throw new PersistenceException("failed to prepare statements", e);
        }
    }


    /**
     * creating a new exercise and storing it with all the given pictures into the database
     * @param exercise which shall be inserted into the underlying persistance layer
     *                 must not be null, id must be null
     * @return the exercise for further usag
     * @throws PersistenceException if there are any problems with the pictures or the database
     */
    @Override
    public Exercise create(Exercise exercise) throws PersistenceException {
        try {

            LOGGER.info("crating a new exercise" + exercise);
            if(exercise==null)
                throw new PersistenceException("exercise must not be null");

            ResultSet rs = nextvalExercise.executeQuery();
            rs.next();

            Integer id = rs.getInt(1);
            LOGGER.debug("id set for new exercise " + id);
            String name = exercise.getName();
            String description = exercise.getDescription();
            Double calories = exercise.getCalories();
            String videolink = exercise.getVideolink();
            List<String> gifLinks = exercise.getGifLinks();
            Boolean isDeleted = exercise.getIsDeleted();

            createStatement.setInt(1, id);
            createStatement.setString(2, name);
            createStatement.setString(3, description);
            createStatement.setDouble(4, calories);
            createStatement.setString(5, videolink);
            //DTO isDeleted = false always when create gets called
            createStatement.setObject(6, null);
            createStatement.setBoolean(7, exercise.getTimeBased());
            createStatement.setBoolean(8, isDeleted);
            createStatement.execute();
            List<String> gifNames = new ArrayList<>();

            LOGGER.info("inserting exercise pictures into table");
            for(String s : gifLinks){
                try {
                    String directoryPath = getClass().getClassLoader().getResource("img").toString().substring(6);
                    File directory = new File(directoryPath);
                    if (!directory.exists()) {
                        directory.mkdir();
                    }
                    GregorianCalendar calendar = new GregorianCalendar();
                    String ownName = "/img_ex_" + (calendar.getTimeInMillis()) + Math.abs(s.hashCode());
                    FileInputStream inputStream = new FileInputStream(s);
                    String storingPath = getClass().getClassLoader().getResource("img").toString().substring(6);
                    File file1 = new File(storingPath + ownName+".jpg"); //file storing
                    FileOutputStream out = new FileOutputStream(file1);
                    IOUtils.copy(inputStream, out); //copy content from input to output
                    out.close();
                    inputStream.close();
                    gifNames.add(ownName+".jpg");
                }catch (Exception e){
                    throw new PersistenceException(e);
                }
            }


            for(String s : gifNames){
                rs = nextvalGif.executeQuery();
                rs.next();
                Integer gifId = rs.getInt(1);
                insertGifStatement.setInt(1, gifId);
                insertGifStatement.setInt(2, id);
                insertGifStatement.setString(3, s);
                insertGifStatement.execute();
                LOGGER.debug(s);
            }

            for(AbsractCategory a : exercise.getCategories()){
                createCategoryStatement.setInt(1, id);
                createCategoryStatement.setInt(2, a.getId());
                createCategoryStatement.execute();
            }

            Exercise created = new Exercise(id, name, description, calories, videolink, gifNames, isDeleted);
            LOGGER.debug("new Exercise after insertion into h2 database" + created);
            return  created;
        }catch (SQLException e){
            throw new PersistenceException("failed to insert excerisce into database", e);
        }
    }

    /**
     * searching for all the dtos stored in the underlying persitance layer
     * @return list of all exercies stored in the database
     * @throws PersistenceException if an exercise causes problems withi the extraction
     */
    @Override
    public List<Exercise> findAll() throws PersistenceException {
        LOGGER.info("finding all exercises from the h2 database");
        List<Exercise> exerciseList = new ArrayList<>();

        try{
            ResultSet rs = readStatement.executeQuery();
            while(rs.next()){
                exerciseList.add(extractExcercise(rs)); //getting an entity from the resultset
            }
            return exerciseList;
        }catch (SQLException e){
            throw new PersistenceException("failed to get exercises from database", e);
        }
    }


    /**
     * method to search after exactely one exercise
     * id is primarykey
     * @param id exercixe to search for
     * @return one exercise exactly due to prim key nature of id
     * @throws PersistenceException if the given id is lower 0
     */
    @Override
    public Exercise searchByID(int id) throws PersistenceException {
        LOGGER.info("searching after an exercise in dao layer with id: " + id);
        try{
            if(id <0 )
                throw new PersistenceException("id must not be lower 0!");

            searchByIDStatement.setInt(1, id);
            ResultSet rs = searchByIDStatement.executeQuery();
            rs.next();
            return extractExcercise(rs);
        }catch (SQLException e){
            throw new PersistenceException("can not find given exercise by this id: " + id);
        }
    }

    /**
     * updating a given exercise to new values
     * @param exercise which shall be updated
     *                 must not be null, id must not be null
     * @return the updated exercise which is now stored in the database
     * @throws PersistenceException if there are any troubles with the database
     */
    @Override
    public Exercise update(Exercise exercise) throws PersistenceException {
        LOGGER.debug("updating a given exercise in dao layer" + exercise);
        if(exercise==null)
            throw new PersistenceException("exercise to update must not be null");

        if(exercise.getId()==null)
            throw new PersistenceException("exercise id to update must not be null");

        try{
            Integer id = exercise.getId();
            String name = exercise.getName();
            String description = exercise.getDescription();
            Double calories = exercise.getCalories();
            String videolink = exercise.getVideolink();
            Boolean isDeleted = exercise.getIsDeleted();
            Boolean timebased = exercise.getTimeBased();
            updateStatement.setInt(7, id);
            updateStatement.setString(1, name);
            updateStatement.setString(2, description);
            updateStatement.setDouble(3, calories);
            updateStatement.setString(4, videolink);
            updateStatement.setBoolean(5, isDeleted);
            updateStatement.setBoolean(6, timebased);
            updateStatement.execute();

            deleteCategoryStatement.setInt(1, exercise.getId());
            deleteCategoryStatement.execute();
            for(AbsractCategory a : exercise.getCategories()){
                createCategoryStatement.setInt(1, id);
                createCategoryStatement.setInt(2, a.getId());
                createCategoryStatement.execute();
            }
            return exercise;
        }catch (SQLException e){
            throw new PersistenceException("failed to update given exercise", e);
        }
    }

    /**
     * deleting a given exercise --> setting isdeleted flag to TRUE
     * @param exercise which shall be deleted from the underlying persitance implementation layer
     * @throws PersistenceException if the exercise is null or the id is null
     */
    @Override
    public void delete(Exercise exercise) throws PersistenceException {
        if(exercise.getUser()!=null)
            throw new PersistenceException("cannot delete SYSTEM exercises!");


        LOGGER.debug("deleting an exercise in dao layer" + exercise);
       if(exercise==null)
           throw new PersistenceException("exercise must not be null!");

        if(exercise.getId()==null)
            throw new PersistenceException("exercise ID must not be null for deletion!");

        exercise.setIsDeleted(true);
        update(exercise); //DTO boolean  isdeleted = true
    }


    public List<Exercise> getByCategory(Integer id){
        return null;
    }


    /**
     * extracting an exercise out of one line from the given resultset
     * @param rs a resultset which comes from a prepared statement containing all information
     *           for one exercise
     * @return a new instance of an exercise with the attributes from the given resultset
     * @throws PersistenceException if there are any problems with the extraction from the resultset
     */
    private Exercise extractExcercise(ResultSet rs) throws PersistenceException{
        LOGGER.debug("extracting an exercise from a given resultset in dao layer");
        try{
            Integer id = rs.getInt(1);
            String name = rs.getString(2);
            String description = rs.getString(3);
            Double calories = rs.getDouble(4);
            String videoLink = rs.getString(5);
            Boolean isDeleted = rs.getBoolean(6);
            readGifStatement.setInt(1, id);
            ResultSet gifResults = readGifStatement.executeQuery();
            List<String> gifLinks = new ArrayList<>();
            while(gifResults.next()){
                gifLinks.add(gifResults.getString(3));
            }

            return new Exercise(id, name, description, calories,videoLink, gifLinks, isDeleted);
        }catch (SQLException e){
            throw new PersistenceException("failed to extract exercise from given restultset" , e);
        }

    }



}
