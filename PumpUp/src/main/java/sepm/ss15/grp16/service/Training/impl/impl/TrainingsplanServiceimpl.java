package sepm.ss15.grp16.service.Training.impl.impl;

import sepm.ss15.grp16.entity.Training.Trainingsplan;
import sepm.ss15.grp16.persistence.dao.Training.TrainingsplanDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.Training.impl.TrainingsplanService;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;

import java.util.List;

/**
 * Created by lukas on 11.05.15.
 */
public class TrainingsplanServiceimpl implements TrainingsplanService {

    private TrainingsplanDAO trainingsplanDAO;



    public TrainingsplanServiceimpl(TrainingsplanDAO trainingsplanDAO) throws ServiceException {
        if(trainingsplanDAO == null){
            throw new ServiceException("TrainingsplanDAO is null. Cannot be set in service layer");
        }
        this.trainingsplanDAO = trainingsplanDAO;
    }

    @Override
    public Trainingsplan create(Trainingsplan dto) throws ServiceException {
        this.validate(dto);
        try{
            return trainingsplanDAO.create(dto);
        }catch(PersistenceException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Trainingsplan> findAll() throws ServiceException {
        try{
            return trainingsplanDAO.findAll();
        }catch(PersistenceException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public Trainingsplan update(Trainingsplan dto) throws ServiceException {
        this.validate(dto);
        try{
            return trainingsplanDAO.update(dto);
        }catch(PersistenceException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(Trainingsplan dto) throws ServiceException {
        this.validate(dto);
        try{
            trainingsplanDAO.delete(dto);
        }catch(PersistenceException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public void validate(Trainingsplan dto) throws ValidationException {

    }
}
