package sepm.ss15.grp16.service.training;

import sepm.ss15.grp16.entity.training.TrainingsSession;
import sepm.ss15.grp16.entity.training.Trainingsplan;
import sepm.ss15.grp16.entity.training.helper.ExerciseSet;
import sepm.ss15.grp16.entity.user.User;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.exception.ServiceException;

import java.util.List;

/**
 * Created by lukas on 11.05.15.
 */
public interface TrainingsplanService extends Service<Trainingsplan> {

    /**
     * find all with filter matching trainingsplans
     *
     * @param filter for the search, must not be valid, you can search after name and user
     *               (not null user - use getDefaultPlans() for default plans)
     * @return found trainingsplan, null if nothing was found
     * @throws ServiceException fthere are complications with the service layer
     */
    List<Trainingsplan> find(Trainingsplan filter) throws ServiceException;

    /**
     * get all default plans
     *
     * @return list of default plans
     * @throws ServiceException
     */
    List<Trainingsplan> getDefaultPlans() throws ServiceException;

    /**
     * generates the corresponding trainingsplan for a trainingssession
     *
     * @param session must has a valid ID
     * @return the corresponding plan_interClassCommunication, null if nothing was found
     * @throws ServiceException fthere are complications with the service layer
     */
    Trainingsplan getPlanBySession(TrainingsSession session) throws ServiceException;

    /**
     * generates the corresponding trainingsplan for an ExerciseSet
     *
     * @param set must has a valid ID
     * @return the corresponding plan_interClassCommunication, null if nothing was found
     * @throws ServiceException fthere are complications with the service layer
     */
    Trainingsplan getPlanBySet(ExerciseSet set) throws ServiceException;

    /**
     * find all trainingssession of an user
     *
     * @param user to search, can be null, UID must not be null
     * @return found sessions, null if nothing was found
     * @throws ServiceException fthere are complications with the service layer
     */

    List<TrainingsSession> searchByUser(User user) throws ServiceException;

    /**
     * find all trainingssession of a corresponding plan_interClassCommunication
     *
     * @param ID_plan to search, must not be null
     * @return found sessions, null if nothing was found
     * @throws ServiceException fthere are complications with the service layer
     */
    List<TrainingsSession> searchByPlanID(int ID_plan) throws ServiceException;

    /**
     * increase the difficulty of the given plan by the factor of 0.25
     *
     * @param plan to increase
     */
    void increaseDifficulty(Trainingsplan plan);

    /**
     * decrease the difficulty of the given plan by the factor of 0.25
     *
     * @param plan to decrease
     */
    void decreaseDifficulty(Trainingsplan plan);

    /**
     * increase the difficulty of the given session by the factor of 0.25
     *
     * @param plan to increase
     */
    void increaseDifficulty(TrainingsSession session);

    /**
     * decrease the difficulty of the given session by the factor of 0.25
     *
     * @param plan to decrease
     */
    void decreaseDifficulty(TrainingsSession session);

    /**
     * increase the difficulty of the given set by the factor of 0.25
     *
     * @param plan to increase
     */
    void increaseDifficulty(ExerciseSet set);

    /**
     * decrease the difficulty of the given set by the factor of 0.25
     *
     * @param plan to decrease
     */
    void decreaseDifficulty(ExerciseSet set);
}