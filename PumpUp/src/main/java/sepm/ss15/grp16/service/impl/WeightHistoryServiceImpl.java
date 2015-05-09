package sepm.ss15.grp16.service.impl;

import sepm.ss15.grp16.entity.WeightHistory;
import sepm.ss15.grp16.persistence.dao.WeightHistoryDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;
import sepm.ss15.grp16.service.WeightHistoryService;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;

import java.util.List;

/**
 * This class represents an implementation of a weighthistory service
 *
 * @author Michael Sober
 * @version 1.0
 */
public class WeightHistoryServiceImpl implements WeightHistoryService{

    private WeightHistoryDAO weightHistoryDAO;

    public WeightHistoryServiceImpl(WeightHistoryDAO weightHistoryDAO) throws ServiceException {
        if(weightHistoryDAO == null){
            throw new ServiceException("WeightHistoryDAO is null. Cannot be set in service layer");
        }
        this.weightHistoryDAO = weightHistoryDAO;
    }

    @Override
    public WeightHistory create(WeightHistory weightHistory) throws ServiceException {
        this.validate(weightHistory);
        try{
            return weightHistoryDAO.create(weightHistory);
        }catch(PersistenceException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public List<WeightHistory> findAll() throws ServiceException {
        try{
            return weightHistoryDAO.findAll();
        }catch(PersistenceException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public WeightHistory update(WeightHistory weightHistory) throws ServiceException {
        //TODO: Implement me
        return null;
    }

    @Override
    public void delete(WeightHistory weightHistory) throws ServiceException {
        //TODO: Implement me
    }

    @Override
    public void validate(WeightHistory weightHistory) throws ValidationException {

        if(weightHistory == null){
            throw new ValidationException("Validation not passed. WeightHistory is null");
        }else if(weightHistory.getWeight() < 0 || weightHistory.getWeight() > 500){
            throw new ValidationException("Validation not passed. Weight must be between 0 and 500");
        }else if(weightHistory.getUser_id() == null){
            throw new ValidationException("Validation not passed. No User_id");
        }

    }
}
