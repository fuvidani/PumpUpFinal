package sepm.ss15.grp16.service.user;

import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.service.Service;

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
