package sepm.ss15.grp16.entity.Training;

import sepm.ss15.grp16.entity.DTO;

/**
 * Author: Lukas
 * Date: 08.05.2015
 */
public class ExerciseSet implements DTO {

	Integer id;
	Integer id_exercise;
	Integer id_session;
	Integer uid;
	Integer repeat;
	Integer order_nr;
	Boolean isDeleted = false;

	public ExerciseSet() {
	}

	public ExerciseSet(Integer id, Integer id_exercise, Integer id_session, Integer uid, Integer repeat, Integer order_nr, Boolean isDeleted) {
		this.id = id;
		this.id_exercise = id_exercise;
		this.id_session = id_session;
		this.uid = uid;
		this.repeat = repeat;
		this.order_nr = order_nr;
		this.isDeleted = isDeleted;
	}

	public ExerciseSet(ExerciseSet exerciseSet) {
		this.id = exerciseSet.id;
		this.id_exercise = exerciseSet.id_exercise;
		this.id_session = exerciseSet.id_session;
		this.uid = exerciseSet.uid;
		this.repeat = exerciseSet.repeat;
		this.order_nr = exerciseSet.order_nr;
		this.isDeleted = exerciseSet.isDeleted;
	}

	public Integer getId_exercise() {
		return id_exercise;
	}

	public void setId_exercise(Integer id_exercise) {
		this.id_exercise = id_exercise;
	}

	public Integer getId_session() {
		return id_session;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
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

	public void setId_session(Integer id_session) {
		this.id_session = id_session;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ExerciseSet)) return false;

		ExerciseSet that = (ExerciseSet) o;

		return !(id != null ? !id.equals(that.id) : that.id != null) &&
				!(id_exercise != null ? !id_exercise.equals(that.id_exercise) : that.id_exercise != null) &&
				!(id_session != null ? !id_session.equals(that.id_session) : that.id_session != null) &&
				!(uid != null ? !uid.equals(that.uid) : that.uid != null) &&
				!(repeat != null ? !repeat.equals(that.repeat) : that.repeat != null) &&
				!(order_nr != null ? !order_nr.equals(that.order_nr) : that.order_nr != null) &&
				!(isDeleted != null ? !isDeleted.equals(that.isDeleted) : that.isDeleted != null);

	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (id_exercise != null ? id_exercise.hashCode() : 0);
		result = 31 * result + (id_session != null ? id_session.hashCode() : 0);
		result = 31 * result + (uid != null ? uid.hashCode() : 0);
		result = 31 * result + (repeat != null ? repeat.hashCode() : 0);
		result = 31 * result + (order_nr != null ? order_nr.hashCode() : 0);
		result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "ExerciseSet{" +
				"id=" + id +
				", id_exercise=" + id_exercise +
				", id_session=" + id_session +
				", uid=" + uid +
				", repeat=" + repeat +
				", order_nr=" + order_nr +
				", isDeleted=" + isDeleted +
				'}';
	}
}
