package sepm.ss15.grp16.entity.Training.Helper;

import sepm.ss15.grp16.entity.Exercise;
import sepm.ss15.grp16.entity.User;

/**
 * Author: Lukas
 * Date: 08.05.2015
 */
public class ExerciseSet implements DTOHelper {

	Integer id;
	Integer repeat;
	Integer order_nr;
	Boolean isDeleted = false;

	User user;

	Exercise exercise;

	public ExerciseSet() {
	}

	public ExerciseSet(Integer id, Exercise exercise, User user, Integer repeat, Integer order_nr, Boolean isDeleted) {
		this.id = id;
		this.exercise = exercise;
		this.user = user;
		this.repeat = repeat;
		this.order_nr = order_nr;
		this.isDeleted = isDeleted;
	}

	public ExerciseSet(ExerciseSet exerciseSet) {
		this.id = exerciseSet.id;
		this.exercise = exerciseSet.exercise;
		this.user = exerciseSet.user;
		this.repeat = exerciseSet.repeat;
		this.order_nr = exerciseSet.order_nr;
		this.isDeleted = exerciseSet.isDeleted;
	}

	public Exercise getExercise() {
		return exercise;
	}

	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getRepeat() {
		return repeat;
	}

	public void setRepeat(Integer repeat) {
		this.repeat = repeat;
	}

	public Integer getOrder_nr() {
		return order_nr;
	}

	public void setOrder_nr(Integer order_nr) {
		this.order_nr = order_nr;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	@Override
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ExerciseSet that = (ExerciseSet) o;

		return !(id != null ? !id.equals(that.id) : that.id != null) &&
				!(user != null ? !user.equals(that.user) : that.user != null) &&
				!(repeat != null ? !repeat.equals(that.repeat) : that.repeat != null) &&
				!(order_nr != null ? !order_nr.equals(that.order_nr) : that.order_nr != null) &&
				!(isDeleted != null ? !isDeleted.equals(that.isDeleted) : that.isDeleted != null) &&
				!(exercise != null ? !exercise.equals(that.exercise) : that.exercise != null);

	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (user != null ? user.hashCode() : 0);
		result = 31 * result + (repeat != null ? repeat.hashCode() : 0);
		result = 31 * result + (order_nr != null ? order_nr.hashCode() : 0);
		result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
		result = 31 * result + (exercise != null ? exercise.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "ExerciseSet{" +
				"id=" + id +
				", user=" + user +
				", repeat=" + repeat +
				", order_nr=" + order_nr +
				", isDeleted=" + isDeleted +
				", exercise=" + exercise +
				'}';
	}
}