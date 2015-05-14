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
    private static User loggedInUser;

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
            throw new ValidationException("User is null.");
        }

        String errorMsg = "";
        String username = user.getUsername();
        Integer age = user.getAge();
        Integer height = user.getHeight();
        String email = user.getEmail();

        if(username == null || username.length() > 25 || username.length() < 2){
            errorMsg += "Username is required and has to be between 2 and 25 characters long.";
        }

        if(age == null || age < 0){
            errorMsg += "Age is required and has to be a number greater than 0.";
        }

        if(height == null || height < 0){
            errorMsg += "Height is required and has to be a number greater than 0.";
        }

        if(email != null){
            if(email.length() > 320){
                errorMsg += "Email address can only be 320 characters long.";
            }
        }

        if(!errorMsg.isEmpty()){
            throw new ValidationException(errorMsg);
        }
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
