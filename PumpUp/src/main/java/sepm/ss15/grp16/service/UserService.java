package sepm.ss15.grp16.service;

import sepm.ss15.grp16.entity.user.User;

/**
 * This interface defines all operations for a user service
 *
 * @author Michael Sober
 * @version 1.0
 */
public interface UserService extends Service<User> {

    User getLoggedInUser();

    void setLoggedInUser(User loggedInUser);

}
