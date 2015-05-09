package sepm.ss15.grp16.persistence.dao.Training;

import sepm.ss15.grp16.entity.Training.ExerciseSet;
import sepm.ss15.grp16.persistence.dao.DAO;

import java.util.List;

/**
 * Author: Lukas
 * Date: 08.05.2015
 */
public interface ExerciseSetDAO extends DAO<ExerciseSet> {

	List<ExerciseSet> find(ExerciseSet exerciseSet);
}
