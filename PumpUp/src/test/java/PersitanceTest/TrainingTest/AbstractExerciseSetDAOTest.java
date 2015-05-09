package PersitanceTest.TrainingTest;

import org.junit.Test;
import sepm.ss15.grp16.entity.Exercise;
import sepm.ss15.grp16.persistence.dao.ExerciseDAO;
import sepm.ss15.grp16.persistence.dao.Training.ExerciseSetDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Lukas
 * Date: 09.05.2015
 */
public abstract class AbstractExerciseSetDAOTest {

	public abstract ExerciseDAO getExerciseDAO();

	public abstract ExerciseSetDAO getExerciseSetDAO();

	@Test
	public void createValid() {
		//TODO
	}


	@Test
	public void updateValid() {
		//TODO

	}


	@Test
	public void deleteValid() {
		//TODO
	}

	private Exercise createExercise() throws PersistenceException {

		List<String> gifList = new ArrayList<>();
		gifList.add("youtube");
		gifList.add("menshealth");
		Exercise liegestuetz = new Exercise("liegestuetz", "eine der besten uebungen ueberhaupt", 9.0, "", gifList, false);
		return getExerciseDAO().create(liegestuetz);

	}
}
