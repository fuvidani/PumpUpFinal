package sepm.ss15.grp16.service.user.impl;

import sepm.ss15.grp16.entity.user.BodyfatHistory;
import sepm.ss15.grp16.persistence.dao.user.BodyfatHistoryDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;
import sepm.ss15.grp16.service.user.BodyfatHistoryService;

import java.util.List;

/**
 * This class represents an implementation of a bodyfathistory service
 *
 * @author Michael Sober
 * @version 1.0
 */
public class BodyfatHistoryServiceImpl implements BodyfatHistoryService {

    private BodyfatHistoryDAO bodyfatHistoryDAO;

    public BodyfatHistoryServiceImpl(BodyfatHistoryDAO bodyfatHistoryDAO) throws ServiceException {
        if (bodyfatHistoryDAO == null) {
            throw new ServiceException("BodyfatHistoryDAO is null. Cannot be set in service layer");
        }
        this.bodyfatHistoryDAO = bodyfatHistoryDAO;
    }

    @Override
    public BodyfatHistory create(BodyfatHistory bodyfatHistory) throws ServiceException {
        this.validate(bodyfatHistory);
        try {
            return bodyfatHistoryDAO.create(bodyfatHistory);
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<BodyfatHistory> findAll() throws ServiceException {
        try {
            return bodyfatHistoryDAO.findAll();
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public BodyfatHistory update(BodyfatHistory bodyfatHistory) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(BodyfatHistory bodyfatHistory) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void validate(BodyfatHistory bodyfatHistory) throws ValidationException {

        if (bodyfatHistory == null) {
            throw new ValidationException("WeightHistory is null.");
        }

        String errorMsg = "";
        Integer user_id = bodyfatHistory.getUser_id();
        Integer bodyfat = bodyfatHistory.getBodyfat();

        if (user_id == null) {
            errorMsg += "Die UserID muss angegeben werden und eine g\u00fcltige Zahl sein.\n";
        }

        if (bodyfat == null || bodyfat < 0 || bodyfat > 100) {
            errorMsg += "Der K\u00f6rperfettanteil muss eine g\u00fcltige Zahl zwischen 0 und 100 sein.";
        }

        if (!errorMsg.isEmpty()) {
            throw new ValidationException(errorMsg);
        }

    }

    @Override
    public List<BodyfatHistory> searchByUserID(int user_id) throws ServiceException {
        try {
            return bodyfatHistoryDAO.searchByUserID(user_id);
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public BodyfatHistory getActualBodyfat(int user_id) throws ServiceException {
        try {
            return bodyfatHistoryDAO.getActualBodyfat(user_id);
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }
}
