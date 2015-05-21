package sepm.ss15.grp16.service.impl;

import sepm.ss15.grp16.entity.user.PictureHistory;
import sepm.ss15.grp16.persistence.dao.PictureHistoryDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;
import sepm.ss15.grp16.service.PictureHistoryService;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;

import java.util.List;

/**
 * This class represents an implementation of a picturehistory service
 *
 * @author Michael Sober
 * @version 1.0
 */
public class PictureHistoryServiceImpl implements PictureHistoryService {

    private PictureHistoryDAO pictureHistoryDAO;

    public PictureHistoryServiceImpl(PictureHistoryDAO pictureHistoryDAO) throws ServiceException {
        if (pictureHistoryDAO == null) {
            throw new ServiceException("PictureHistoryDAO is null. Cannot be set in service layer");
        }
        this.pictureHistoryDAO = pictureHistoryDAO;
    }

    @Override
    public PictureHistory create(PictureHistory pictureHistory) throws ServiceException {
        this.validate(pictureHistory);
        try {
            return pictureHistoryDAO.create(pictureHistory);
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<PictureHistory> findAll() throws ServiceException {
        try {
            return pictureHistoryDAO.findAll();
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public PictureHistory update(PictureHistory pictureHistory) throws ServiceException {
        //TODO: Implement me
        return null;
    }

    @Override
    public void delete(PictureHistory pictureHistory) throws ServiceException {
        //TODO: Implement me
    }

    @Override
    public List<PictureHistory> searchByUserID(int user_id) throws ServiceException {
        //TODO: Implement me
        return null;
    }

    @Override
    public PictureHistory getActualPicture(int user_id) throws ServiceException {
        try {
            return pictureHistoryDAO.getActualPicture(user_id);
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void validate(PictureHistory pictureHistory) throws ValidationException {

        if (pictureHistory == null) {
            throw new ValidationException("Validation not passed. PictureHistory is null");
        } else if (pictureHistory.getUser_id() == null) {
            throw new ValidationException("Validation not passed. No User_id");
        }

    }
}
