package sepm.ss15.grp16.persistence.dao;

import org.junit.Test;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

/**
 * This class provides methods for testing UserDAOs
 *
 * @author Michael Sober
 * @version 1.0
 */
public abstract class AbstractUserDaoTest extends AbstractDAOTest<User> {

    protected UserDAO userDAO;

    @Override
    public DAO<User> getDAO() {
        return userDAO;
    }

    @Test(expected = PersistenceException.class)
    public void createWithNullShouldThrowException() throws Exception {
        userDAO.create(null);
    }

    @Test
    public void createWithValidUserShouldPersist() throws Exception {
        User testUser = new User(null, "msober", true, 20, 194, "michael.sober@ymail.com", "/path/playlist/", false);
        createValid(testUser);
    }

    @Test(expected = PersistenceException.class)
    public void deleteWithNullShouldThrowException() throws Exception {
        userDAO.delete(null);
    }

    @Test
    public void deleteWithValidUserShouldPersist() throws Exception {
        User testUser = new User(null, "msober", true, 20, 194, "michael.p.sober@gmail.com", "/path/playlist/", false);
        deleteValid(testUser);
    }

    @Test(expected = PersistenceException.class)
    public void updateWithNullShouldThrowException() throws Exception {
        userDAO.update(null);
    }

    @Test
    public void updateWithValidParametersShouldPersist() throws Exception {
        User userBeforeUpdate = new User(null, "msober", true, 20, 194, "michael.p.sober@gmail.com", "/path/playlist/", false);
        User userAfterUpdate = new User(null, "ksober", false, 18, 174, "michael.sober@ymail.com", "/newpath/playlist", false);
        userAfterUpdate.setUser_id(userBeforeUpdate.getUser_id());
        updateValid(userBeforeUpdate, userAfterUpdate);
    }

    @Test
    public void searchByIDShouldFindUser() throws Exception {
        User testUser = new User(null, "msober", true, 20, 194, "michael.sober@ymail.com", "/path/playlist/", false);
        searchByIDValid(testUser);
    }

}
