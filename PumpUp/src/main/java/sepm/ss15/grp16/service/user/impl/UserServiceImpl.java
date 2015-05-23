package sepm.ss15.grp16.service.user.impl;

import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.persistence.dao.user.UserDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;
import sepm.ss15.grp16.service.user.UserService;
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

    private static User loggedInUser;
    private UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) throws ServiceException {
        if (userDAO == null) {
            throw new ServiceException("UserDAO is null. Cannot be set in service layer");
        }
        this.userDAO = userDAO;
    }

    @Override
    public User create(User user) throws ServiceException {
        this.validate(user);
        try {
            return userDAO.create(user);
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> findAll() throws ServiceException {
        try {
            return userDAO.findAll();
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User update(User user) throws ServiceException {
        this.validate(user);
        try {
            return userDAO.update(user);
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(User user) throws ServiceException {
        try {
            userDAO.delete(user);
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void validate(User user) throws ValidationException {

        if (user == null) {
            throw new ValidationException("user is null.");
        }

        String errorMsg = "";
        String username = user.getUsername();
        Integer age = user.getAge();
        Integer height = user.getHeight();
        String email = user.getEmail();

        if (username == null || username.length() > 25 || username.length() < 2) {
            errorMsg += "Der Username muss angegeben und zwischen 2 und 25 Zeichen lang sein.\n";
        }

        if (age == null || age < 0) {
            errorMsg += "Das Alter muss eine gültige Zahl größer 0 sein.\n";
        }

        if (height == null || height < 0) {
            errorMsg += "Die Größe muss eine gültige Zahl größer 0 sein.\n";
        }

        if (email != null) {
            if (email.length() > 320) {
                errorMsg += "Die E-Mail-Adresse darf maximal 320 Zeichen lang sein.\n";
            }
        }

        if (!errorMsg.isEmpty()) {
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
