package sepm.ss15.grp16.service.Training;

import sepm.ss15.grp16.entity.Training.Helper.ExerciseSet;
import sepm.ss15.grp16.entity.Training.TrainingsSession;
import sepm.ss15.grp16.entity.Training.Trainingsplan;
import sepm.ss15.grp16.entity.User;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.exception.ServiceException;

import java.util.List;

/**
 * Created by lukas on 11.05.15.
 */
public interface TrainingsplanService extends Service<Trainingsplan> {

	List<Trainingsplan> find(Trainingsplan trainingsplan) throws ServiceException;

	Trainingsplan getPlanBySession(TrainingsSession session) throws ServiceException;

	Trainingsplan getPlanBySet(ExerciseSet set) throws ServiceException;

	List<TrainingsSession> searchByUser(User user) throws ServiceException;

	List<TrainingsSession> searchByPlanID(int ID_plan) throws ServiceException;

}
