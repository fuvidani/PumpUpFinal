package sepm.ss15.grp16.service;

import sepm.ss15.grp16.entity.Exercise;
import sepm.ss15.grp16.persistence.dao.ExerciseDAO;
import sepm.ss15.grp16.service.exception.ServiceException;

import java.util.List;

/**
 * Created by lukas on 01.05.2015.
 */
public interface ExerciseService {

    Exercise create(Exercise exercise )throws ServiceException;

    List<Exercise> findAll() throws ServiceException;

    Exercise update(Exercise exercise)throws ServiceException;

    void delete(Exercise exercise) throws ServiceException;


    void setExerciseDAO(ExerciseDAO exerciseDAO)throws ServiceException;
}
