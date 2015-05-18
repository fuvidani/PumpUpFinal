package sepm.ss15.grp16.persistence.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.util.IOUtils;
import sepm.ss15.grp16.entity.AbsractCategory;
import sepm.ss15.grp16.entity.Exercise;
import sepm.ss15.grp16.entity.User;
import sepm.ss15.grp16.persistence.dao.CategoryDAO;
import sepm.ss15.grp16.persistence.dao.ExerciseDAO;
import sepm.ss15.grp16.persistence.dao.UserDAO;
import sepm.ss15.grp16.persistence.database.DBHandler;
import sepm.ss15.grp16.persistence.exception.DBException;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.io.*;
import java.net.URISyntaxException;
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
    private PreparedStatement getCategoryIDStatement;
    private PreparedStatement updateStatement;
    private PreparedStatement insertGifStatement;
    private PreparedStatement nextvalExercise;
    private PreparedStatement nextvalGif;
    private PreparedStatement readGifStatement;
    private PreparedStatement searchByIDStatement;
    private PreparedStatement deleteCategoryStatement;
    private PreparedStatement deleteGifStatement;
    private PreparedStatement getExerciseWithCategoryStatement;


    private static final Logger LOGGER = LogManager.getLogger();

    private static Connection connection;
    private static CategoryDAO categoryDAO;
    private static UserDAO userDAO;


    /**
     * creating an instance of the H2 implementaiton of the exercise
     * also preparing all the statements in the constructor for better performance
     * @param handler handels all the operations for the connection to the database
     * @throws PersistenceException if
     * @throws DBException
     */
    private H2ExerciseDAOImpl(DBHandler handler, CategoryDAO categoryDAO, UserDAO userDAO)throws PersistenceException, DBException{
        try{
            LOGGER.info("creating new instance of H2ExerciseDAOImpl");

            this.connection = handler.getConnection();
            this.categoryDAO = categoryDAO;
            this.userDAO = userDAO;

            //create statements
            createStatement = connection.prepareStatement("INSERT into EXERCISE VALUES (?, ?, ?, ?, ?, ?, ?);");
            createCategoryStatement =connection.prepareStatement("INSERT INTO exercise_category values(?, ?);");
            insertGifStatement = connection.prepareStatement("insert into gif values(?, ?, ?);");

            //update statements
            updateStatement = connection.prepareStatement("UPDATE EXERCISE SET name=?, descripion=?, calories=?, videolink=?, isdeleted=? where id=?;");

            //search/find/read statements
            readStatement = connection.prepareStatement("SELECT * FROM EXERCISE where isdeleted=false;");
            searchByIDStatement = connection.prepareStatement("SELECT * FROM EXERCISE where id=?;");
            readGifStatement = connection.prepareStatement("SELECT * FROM GIF where exerciseid =?");
            getCategoryIDStatement = connection.prepareStatement("select * from exercise_category where exerciseid=?");
            getExerciseWithCategoryStatement = connection.prepareStatement("select e.ID , e.NAME , e.DESCRIPION , e.CALORIES , e.VIDEOLINK , e.USERID , e.ISDELETED from exercise e, exercise_category c where e.id = c.EXERCISEID and c.CATEGORYID =?;");

            //delete statements
            deleteCategoryStatement = connection.prepareStatement("delete from exercise_category where exerciseid=?");
            deleteGifStatement = connection.prepareStatement("delete from gif where location=?");

            //sequence statements
            nextvalExercise  = connection.prepareStatement("SELECT NEXTVAL('EXERCISE_SEQ')");
            nextvalGif  = connection.prepareStatement("SELECT NEXTVAL('GIF_SEQ')");
        }catch (SQLException e) {
            throw new PersistenceException("failed to prepare statements", e);
        }
    }


    /**
     * creating a new exercise and storing it with all the given pictures into the database
     * @param exercise which shall be inserted into the underlying persistance layer
     *                 must not be null, exerciseID must be null
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
            LOGGER.debug("exerciseID set for new exercise " + id);
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
           if(exercise.getUser() == null){
               createStatement.setObject(6, null);
           }else{
               createStatement.setObject(6, exercise.getUser().getId());
           }

            createStatement.setBoolean(7, isDeleted);
            createStatement.execute();
            List<String> gifNames = new ArrayList<>();

            LOGGER.info("inserting exercise pictures into table");

            if(gifLinks!=null && !gifLinks.isEmpty()) {
                for (String s : gifLinks) {
                    this.createPictures(s, id);
                }
            }
            if(exercise.getCategories()!=null && !exercise.getCategories().isEmpty()) {
                for (AbsractCategory a : exercise.getCategories()) {
                    createCategoryStatement.setInt(1, id);
                    createCategoryStatement.setInt(2, a.getId());
                    createCategoryStatement.execute();
                }
            }
            Exercise created = new Exercise(name, description, calories, videolink, gifNames, isDeleted, exercise.getUser(), exercise.getCategories());
            created.setId(id);
            LOGGER.debug("new Exercise after insertion into h2 database" + created);
            return  created;
        }catch (SQLException e){
            throw new PersistenceException("failed to insert excerisce into database", e);
        }
    }

    /**
     * searching for all the dtos stored in the underlying persitance layer
     * which are not deleted
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
     * exerciseID is primarykey also gets dtos which are already deleted
     * @param id exercixe to search for
     * @return one exercise exactly due to prim key nature of exerciseID
     * @throws PersistenceException if the given exerciseID is lower 0
     */
    @Override
    public Exercise searchByID(int id) throws PersistenceException {
        LOGGER.info("searching after an exercise in dao layer with exerciseID: " + id);
        try{
            if(id <0 )
                throw new PersistenceException("exerciseID must not be lower 0!");

            searchByIDStatement.setInt(1, id);
            ResultSet rs = searchByIDStatement.executeQuery();
            rs.next();
            return extractExcercise(rs);
        }catch (SQLException e){
            throw new PersistenceException("can not find given exercise by this exerciseID: " + id);
        }
    }

    /**
     * updating a given exercise to new values
     * @param exercise which shall be updated
     *                 must not be null, exerciseID must not be null
     * @return the updated exercise which is now stored in the database
     * @throws PersistenceException if there are any troubles with the database
     */
    @Override
    public Exercise update(Exercise exercise) throws PersistenceException {
        LOGGER.debug("updating a given exercise in dao layer" + exercise);
        if(exercise==null)
            throw new PersistenceException("exercise to update must not be null");

        if(exercise.getId()==null)
            throw new PersistenceException("exercise exerciseID to update must not be null");

        try{
            Exercise oldExercise = searchByID(exercise.getId());

            Integer id = exercise.getId();
            String name = exercise.getName();
            String description = exercise.getDescription();
            Double calories = exercise.getCalories();
            String videolink = exercise.getVideolink();
            Boolean isDeleted = exercise.getIsDeleted();

            updateStatement.setString(1, name);
            updateStatement.setString(2, description);
            updateStatement.setDouble(3, calories);
            updateStatement.setString(4, videolink);
            updateStatement.setBoolean(5, isDeleted);
            updateStatement.setInt(6, id);
            updateStatement.execute();

//            File toDelete = null;
            String storingPath = getClass().getClassLoader().getResource("img").toString().substring(6);
            for(String s : oldExercise.getGifLinks()){
                if(!exercise.getGifLinks().contains(s)){ //picture removed
                    LOGGER.debug("deleting file: " + s);
                    deleteGifStatement.setString(1, s);
                    deleteGifStatement.execute();
                    //evtl file loeschen
                  /*  toDelete = new File(storingPath.concat(s.substring(1)));
                    toDelete.setWritable(true);
                    toDelete.deleteOnExit();*/
                }
            }
            //creating new pictures
            List<String> gifNames = new ArrayList<>();
            for(String s : exercise.getGifLinks()){
                if(!oldExercise.getGifLinks().contains(s)){
                   this.createPictures(s, exercise.getId());
                }//end if
            }
            ResultSet rs = null;

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
     * @throws PersistenceException if the exercise is null or the exerciseID is null
     */
    @Override
    public void delete(Exercise exercise) throws PersistenceException {

        /**
         * if current logedin user != exercise user
         * don't allow to delete otherwise delete --> set flag
         */



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
            Integer userid = (Integer) rs.getObject(6);
            Boolean isDeleted = rs.getBoolean(7);
            readGifStatement.setInt(1, id);
            ResultSet gifResults = readGifStatement.executeQuery();
            List<String> gifLinks = new ArrayList<>();
            while(gifResults.next()){
                gifLinks.add(gifResults.getString(3));
            }

            List<AbsractCategory> categoryList = new ArrayList<>();
            getCategoryIDStatement.setInt(1, id);
            ResultSet categorySet = getCategoryIDStatement.executeQuery();
            while (categorySet.next()){
               Integer categoryID =  categorySet.getInt(2);
                AbsractCategory absractCategory = categoryDAO.searchByID(categoryID);
                categoryList.add(absractCategory);
            }
            User user = null;
            if(userid!=null)
                user = userDAO.searchByID(userid);


            return new Exercise(id, name, description, calories,videoLink, gifLinks, isDeleted, user, categoryList);
        }catch (SQLException e){
            throw new PersistenceException("failed to extract exercise from given restultset" , e);
        }

    }

    /**
     * creating the pictures and copying the files to our datastorage
     * in the img folder
     * @param originalName name/path on the users computer for copying
     * @param exerciseID categoryID of the exercise for linking purposes
     * @return a new string containing our structure/name of the file
     * @throws PersistenceException
     */
    private String createPictures(String originalName, Integer exerciseID) throws PersistenceException{
        try {

            String pathToResource = getClass().getClassLoader().getResource("img").toURI().getPath();

            File directory = new File(pathToResource);
            if (!directory.exists()) {
                directory.mkdir();
            }
            GregorianCalendar calendar = new GregorianCalendar();
            String ownName = "img_ex_" + (calendar.getTimeInMillis()) + Math.abs(originalName.hashCode());
            FileInputStream inputStream = new FileInputStream(originalName);
            File file1 = new File(pathToResource + ownName+".jpg"); //file storing
            FileOutputStream out = new FileOutputStream(file1);
            IOUtils.copy(inputStream, out); //copy content from input to output
            out.close();
            inputStream.close();
            ResultSet rs = nextvalGif.executeQuery();
            rs.next();
            Integer gifId = rs.getInt(1);
            insertGifStatement.setInt(1, gifId);
            insertGifStatement.setInt(2, exerciseID);
            insertGifStatement.setString(3, ownName.concat(".jpg"));
            insertGifStatement.execute();
            return ownName.concat(".jpg");
        }catch (FileNotFoundException e){
            throw new PersistenceException(e);
        }catch (IOException e){
            throw new PersistenceException(e);
        }catch(SQLException e){
            throw new PersistenceException(e);
        }catch(URISyntaxException e){
            throw new PersistenceException(e);
        }

    }

    /**
     * get all exercises which train only endurance
     * @return a list of all exercises with endurance purposes
     * @throws PersistenceException
     */
    public List<Exercise> getAllEnduranceExercises()throws PersistenceException{
        try{
            getExerciseWithCategoryStatement.setInt(1, 0);
            ResultSet rs = getExerciseWithCategoryStatement.executeQuery();
            List<Exercise> exercises = new ArrayList<>();
            while(rs.next()){
                exercises.add(this.extractExcercise(rs));
            }
            return exercises;
        }catch (SQLException e){
            throw new PersistenceException(e);
        }
    }

    /**
     * get all exercises which train only strength
     * @return a list of all exercises with strength purposes
     * @throws PersistenceException
     */
    public List<Exercise> getAllStrengthExercises()throws PersistenceException{
        try{
            getExerciseWithCategoryStatement.setInt(1, 1);
            ResultSet rs = getExerciseWithCategoryStatement.executeQuery();
            List<Exercise> exercises = new ArrayList<>();
            while(rs.next()){
                exercises.add(this.extractExcercise(rs));
            }
            return exercises;
        }catch (SQLException e){
            throw new PersistenceException(e);
        }
    }


    /**
     * get all exercises which train only balance
     * @return a list of all exercises with balance purposes
     * @throws PersistenceException
     */
    public List<Exercise> getAllBalanceExercises()throws PersistenceException{
        try{
            getExerciseWithCategoryStatement.setInt(1, 2);
            ResultSet rs = getExerciseWithCategoryStatement.executeQuery();
            List<Exercise> exercises = new ArrayList<>();
            while(rs.next()){
                exercises.add(this.extractExcercise(rs));
            }
            return exercises;
        }catch (SQLException e){
            throw new PersistenceException(e);
        }
    }

    /**
     * get all exercises which train only flexibility
     * @return a list of all exercises with flexibility purposes
     * @throws PersistenceException
     */
    public List<Exercise> getAllFlexibilityExercises()throws PersistenceException{
        try{
            getExerciseWithCategoryStatement.setInt(1, 3);
            ResultSet rs = getExerciseWithCategoryStatement.executeQuery();
            List<Exercise> exercises = new ArrayList<>();
            while(rs.next()){
                exercises.add(this.extractExcercise(rs));
            }
            return exercises;
        }catch (SQLException e){
            throw new PersistenceException(e);
        }
    }


    /**
     * getting all exercises which have the given category specified
     * by the categoryID
     * @param categoryID the id of the category specifying for the exercies
     * @return a list of all exercises which are qulifyed by the categoryID
     * @throws PersistenceException
     */
    public List<Exercise> getAllExercisesWithCategoryID(Integer categoryID)throws PersistenceException{
        try{
            getExerciseWithCategoryStatement.setInt(1, categoryID);
            ResultSet rs = getExerciseWithCategoryStatement.executeQuery();
            List<Exercise> exercises = new ArrayList<>();
            while(rs.next()){
                exercises.add(this.extractExcercise(rs));
            }
            return exercises;
        }catch (SQLException e){
            throw new PersistenceException(e);
        }
    }
}
