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
    public List<WeightHistory> searchByUserID(int user_id) throws ServiceException {
        try{
            return weightHistoryDAO.searchByUserID(user_id);
        }catch(PersistenceException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public WeightHistory getActualWeight(int user_id) throws ServiceException {
        try{
            return weightHistoryDAO.getActualWeight(user_id);
        }catch(PersistenceException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public void validate(WeightHistory weightHistory) throws ValidationException {

        if(weightHistory == null){
            throw new ValidationException("WeightHistory is null.");
        }

        String errorMsg = "";
        Integer user_id = weightHistory.getUser_id();
        Integer weight = weightHistory.getWeight();

        if(user_id == null){
            errorMsg += "Die UserID muss angegeben werden und eine gültige Zahl sein.\n";
        }

        if(weight == null || weight < 0){
            errorMsg += "Das Gewicht muss eine gültige Zahl größer 0 sein.";
        }

        if(!errorMsg.isEmpty()){
            throw new ValidationException(errorMsg);
        }

    }
}
