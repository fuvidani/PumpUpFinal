package sepm.ss15.grp16.entity.Training;

import sepm.ss15.grp16.entity.DTO;

import java.util.List;

/**
 * Author: Lukas
 * Date: 08.05.2015
 */
public class TrainingsSession implements DTO {

	Integer id_session;
	Integer id_plan;
	Integer uid;
	String name;
	Boolean isDeleted = false;

	List<ExerciseSet> exerciseSets;

	public TrainingsSession() {
	}

	public TrainingsSession(Integer id_session, Integer id_plan, Integer uid, String name, Boolean isDeleted, List<ExerciseSet> exerciseSets) {
		this.id_session = id_session;
		this.id_plan = id_plan;
		this.uid = uid;
		this.name = name;
		this.isDeleted = isDeleted;
		this.exerciseSets = exerciseSets;
	}

	public TrainingsSession(TrainingsSession trainingsSession) {
		this.id_session = trainingsSession.id_session;
		this.id_plan = trainingsSession.id_plan;
		this.uid = trainingsSession.uid;
		this.name = trainingsSession.name;
		this.isDeleted = trainingsSession.isDeleted;
		this.exerciseSets = trainingsSession.exerciseSets;
	}

	public Integer getId_session() {
		return id_session;
	}

	public void setId_session(Integer id_session) {
		this.id_session = id_session;
		if (exerciseSets != null) {
			for (ExerciseSet exerciseSet : exerciseSets) exerciseSet.setId_session(id_session);
		}
	}

	public Integer getId_plan() {
		return id_plan;
	}

	public void setId_plan(Integer id_plan) {
		this.id_plan = id_plan;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	@Override
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
		if (exerciseSets != null) {
			for (ExerciseSet exerciseSet : exerciseSets) exerciseSet.setIsDeleted(isDeleted);
		}
	}

	public List<ExerciseSet> getExerciseSets() {
		return exerciseSets;
	}

	public void setExerciseSets(List<ExerciseSet> exerciseSets) {
		this.exerciseSets = exerciseSets;
	}

	@Override
	public Integer getId() {
		return getId_session();
	}

	@Override
	public void setId(Integer id) {
		setId_session(id);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof TrainingsSession)) return false;

		TrainingsSession that = (TrainingsSession) o;

		return !(id_session != null ? !id_session.equals(that.id_session) : that.id_session != null) &&
				!(id_plan != null ? !id_plan.equals(that.id_plan) : that.id_plan != null) &&
				!(uid != null ? !uid.equals(that.uid) : that.uid != null) &&
				!(name != null ? !name.equals(that.name) : that.name != null) &&
				!(isDeleted != null ? !isDeleted.equals(that.isDeleted) : that.isDeleted != null) &&
				!(exerciseSets != null ? !exerciseSets.equals(that.exerciseSets) : that.exerciseSets != null);

	}

	@Override
	public int hashCode() {
		int result = id_session != null ? id_session.hashCode() : 0;
		result = 31 * result + (id_plan != null ? id_plan.hashCode() : 0);
		result = 31 * result + (uid != null ? uid.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
		result = 31 * result + (exerciseSets != null ? exerciseSets.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "TrainingsSession{" +
				"id_session=" + id_session +
				", id_plan=" + id_plan +
				", uid=" + uid +
				", name='" + name + '\'' +
				", isDeleted=" + isDeleted +
				", exerciseSets=" + exerciseSets +
				'}';
	}
}
