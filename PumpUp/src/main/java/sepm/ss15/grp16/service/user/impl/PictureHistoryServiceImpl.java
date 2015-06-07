package sepm.ss15.grp16.service.user.impl;

import sepm.ss15.grp16.entity.user.PictureHistory;
import sepm.ss15.grp16.persistence.dao.user.PictureHistoryDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;
import sepm.ss15.grp16.service.user.PictureHistoryService;

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
        this.validate(pictureHistory);
        try {
            return pictureHistoryDAO.update(pictureHistory);
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(PictureHistory pictureHistory) throws ServiceException {
        this.validate(pictureHistory);
        try {
            pictureHistoryDAO.delete(pictureHistory);
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<PictureHistory> searchByUserID(int user_id) throws ServiceException {
        try {
            return pictureHistoryDAO.searchByUserID(user_id);
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
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
        }

        String errorMsg = "";
        Integer user_id = pictureHistory.getUser_id();
        String picturePath = pictureHistory.getLocation();

        if (user_id == null || user_id < 0) {
            errorMsg += "Die UserID muss angegeben werden und eine gültige Zahl sein.\n";
        }

        if (picturePath == null || picturePath.isEmpty()) {
            errorMsg += "Es muss ein Bild ausgewählt worden sein.";
        }

        if (!errorMsg.isEmpty()) {
            throw new ValidationException(errorMsg);
        }

    }
}
