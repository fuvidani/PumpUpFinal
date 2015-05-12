package sepm.ss15.grp16.service.impl;

import sepm.ss15.grp16.entity.User;
import sepm.ss15.grp16.persistence.dao.UserDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.UserService;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;

import java.util.List;

/**
 * This class represents an implementation of a user service
 *
 * @author Michael Sober
 * @version 1.0
 */
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private User loggedInUser;

    public UserServiceImpl(UserDAO userDAO) throws ServiceException{
        if(userDAO == null){
            throw new ServiceException("UserDAO is null. Cannot be set in service layer");
        }
        this.userDAO = userDAO;
    }

    @Override
    public User create(User user) throws ServiceException {
        this.validate(user);
        try{
            return userDAO.create(user);
        }catch(PersistenceException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> findAll() throws ServiceException {
        try{
            return userDAO.findAll();
        }catch(PersistenceException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public User update(User user) throws ServiceException {
        this.validate(user);
        try{
            return userDAO.update(user);
        }catch(PersistenceException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(User user) throws ServiceException {
        try{
            userDAO.delete(user);
        }catch(PersistenceException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public void validate(User user) throws ValidationException {

        if(user == null){
            throw new ValidationException("Validation not passed. User is null");
        }else if(user.getUsername().length() > 25){
            throw new ValidationException("Validation not passed. Username is more than 25 characters long");
        }else if(user.getAge() < 0 || user.getAge() > 120){
            throw new ValidationException("Validation not passed. Age must be between 0 and 120");
        }else if(user.getHeight() < 0 || user.getHeight() > 300){
            throw new ValidationException("Validation not passed. Height must be between 0 and 300");
        }

    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
