package sepm.ss15.grp16.persistence.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.util.IOUtils;
import sepm.ss15.grp16.entity.Exercise;
import sepm.ss15.grp16.persistence.dao.ExerciseDAO;
import sepm.ss15.grp16.persistence.database.DBHandler;
import sepm.ss15.grp16.persistence.database.impl.H2DBConnectorImpl;
import sepm.ss15.grp16.persistence.exception.DBException;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by lukas on 30.04.2015.
 */
public class H2ExerciseDAOImpl implements ExerciseDAO {

    private static H2ExerciseDAOImpl h2ExerciseDAOImpl;
    private PreparedStatement createStatement;
    private PreparedStatement readStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement insertGifStatement;
    private PreparedStatement nextvalExercise;
    private PreparedStatement nextvalGif;
    private PreparedStatement readGifStatement;
    private static final Logger LOGGER = LogManager.getLogger();


    private static Connection CONNECTION;

    private H2ExerciseDAOImpl()throws PersistenceException{
        try{
            DBHandler handler = H2DBConnectorImpl.getInstance();
            CONNECTION = handler.getConnection();
            createStatement = CONNECTION.prepareStatement("insert into exercise VALUES (?, ?, ?, ?, ?, ?);");
            readStatement = CONNECTION.prepareStatement("SELECT * from exercise where isdeleted=false;");
            readGifStatement = CONNECTION.prepareStatement("select * from gif where exerciseid =?");
            updateStatement = CONNECTION.prepareStatement("UPDATE exercise set name=?, descripion=?, calories=?, videolink=?, isdeleted=? where id=?;");
            insertGifStatement = CONNECTION.prepareStatement("insert into gif values(?, ?, ?);");
            nextvalExercise  = CONNECTION.prepareStatement("SELECT NEXTVAL('EXERCISESEQUENCE')");
            nextvalGif  = CONNECTION.prepareStatement("SELECT NEXTVAL('GIFSEQUENCE')");

        }catch (SQLException e){
            throw new PersistenceException("failed to prepare statements", e);
        }catch (DBException e){
            throw new PersistenceException("failed to get connection to database", e);
        }
    }


    public static H2ExerciseDAOImpl getInstance() throws PersistenceException{
        if(h2ExerciseDAOImpl ==null){
            h2ExerciseDAOImpl = new H2ExerciseDAOImpl();
        }

        return h2ExerciseDAOImpl;
    }


    @Override
    public Exercise create(Exercise exercise) throws PersistenceException {
        try {
            if(exercise==null)
                throw new PersistenceException("exercise must not be null");

            ResultSet rs = nextvalExercise.executeQuery();
            rs.next();

            Integer id = rs.getInt(1);
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
            createStatement.setBoolean(6, isDeleted);
            createStatement.execute();
            List<String> gifNames = new ArrayList<>();

            for(String s : gifLinks){
                try {
                    String directoryPath = getClass().getClassLoader().getResource("img").toString().substring(6);
                    File directory = new File(directoryPath);
                    if (!directory.exists()) {
                        directory.mkdir();
                    }
                    GregorianCalendar calendar = new GregorianCalendar();
                    String ownName = "/img_" + (calendar.getTimeInMillis()) + Math.abs(s.hashCode());
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

            return  new Exercise(id, name, description, calories, videolink, gifNames, isDeleted);
        }catch (SQLException e){
            throw new PersistenceException("failed to insert excerisce into database", e);
        }
    }

    @Override
    public List<Exercise> findAll() throws PersistenceException {
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



    @Override
    public Exercise update(Exercise exercise) throws PersistenceException {

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
            updateStatement.setInt(6, id);
            updateStatement.setString(1, name);
            updateStatement.setString(2, description);
            updateStatement.setDouble(3, calories);
            updateStatement.setString(4, videolink);
            updateStatement.setBoolean(5, isDeleted);
            updateStatement.execute();
            return exercise;
        }catch (SQLException e){
            throw new PersistenceException("failed to update given exercise", e);
        }
    }

    @Override
    public void delete(Exercise exercise) throws PersistenceException {
       if(exercise==null)
           throw new PersistenceException("exercise must not be null!");



        exercise.setIsDeleted(true);
        update(exercise); //DTO boolean  isdeleted = true
    }


    private Exercise extractExcercise(ResultSet rs) throws PersistenceException{
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
