package main.java.sepm.ss15.grp16.persistence.h2;

import main.java.sepm.ss15.grp16.entity.Exercise;
import main.java.sepm.ss15.grp16.persistence.DBHandler;
import main.java.sepm.ss15.grp16.persistence.ExerciseDAO;
import main.java.sepm.ss15.grp16.persistence.exception.DBException;
import main.java.sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukas on 30.04.2015.
 */
public class H2ExerciseDAOImpl implements ExerciseDAO {

    private static H2ExerciseDAOImpl h2ExerciseDAOImpl;
    private PreparedStatement createStatement;
    private PreparedStatement readStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement deleteStatement;
    private PreparedStatement insertGifStatement;

    private static Connection CONNECTION;

    private H2ExerciseDAOImpl()throws PersistenceException{
        try{
            DBHandler handler = H2DBConnectorImpl.getInstance();
            CONNECTION = handler.getConnection();
            createStatement = CONNECTION.prepareStatement("insert into exercise VALUES (?, ?, ?, ?, ?);");
            readStatement = CONNECTION.prepareStatement("SELECT * from exercise;");
            updateStatement = CONNECTION.prepareStatement("UPDATE exercise set name=?, descripion=?, calories=?, videolink=? where id=?;");
            deleteStatement = CONNECTION.prepareStatement("");
            insertGifStatement = CONNECTION.prepareStatement("insert into gif(EXERCISEID, LOCATION ) values( ?, ?);");


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
        if(exercise==null)
            throw new PersistenceException("exercise must not be null");

        if(exercise.getId()==null)
            throw new PersistenceException("exercise ID must not be null");


        Integer id = exercise.getId();
        String name = exercise.getName();
        String description = exercise.getDescription();
        Double calories = exercise.getCalories();
        String videolink = exercise.getVideolink();
        List<String> gifLinks = exercise.getGifLinks();
        try {
            createStatement.setInt(1, id);
            createStatement.setString(2, name);
            createStatement.setString(3, description);
            createStatement.setDouble(4, calories);
            createStatement.setString(5, videolink);
            createStatement.execute();

            for(String s : gifLinks){
                insertGifStatement.setInt(1, id);
                insertGifStatement.setString(2, s);
                insertGifStatement.execute();
            }

            return  exercise;
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

            updateStatement.setInt(5, id);
            updateStatement.setString(1, name);
            updateStatement.setString(2, description);
            updateStatement.setDouble(3, calories);
            updateStatement.setString(4, videolink);
            updateStatement.execute();
            return exercise;
        }catch (SQLException e){
            throw new PersistenceException("failed to update given exercise", e);
        }
    }

    @Override
    public void delete(Exercise exercise) throws PersistenceException {
        throw new PersistenceException("not implemented yet");
    }


    private Exercise extractExcercise(ResultSet rs) throws PersistenceException{
        try{
            Integer id = rs.getInt(1);
            String name = rs.getString(2);
            String description = rs.getString(3);
            Double calories = rs.getDouble(4);
            String videoLink = rs.getString(5);
            return new Exercise(id, name, description, calories,videoLink);
        }catch (SQLException e){
            throw new PersistenceException("failed to extract exercise from given restultset" , e);
        }
    }
}
