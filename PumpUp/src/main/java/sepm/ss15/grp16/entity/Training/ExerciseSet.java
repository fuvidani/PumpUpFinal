package sepm.ss15.grp16.entity.Training;

import sepm.ss15.grp16.entity.DTO;
import sepm.ss15.grp16.entity.Exercise;

/**
 * Author: Lukas
 * Date: 08.05.2015
 */
public class ExerciseSet implements DTO {

	Integer id;
	Integer uid;
	Integer repeat;
	Integer order_nr;
	Boolean isDeleted = false;

	Exercise exercise;
    TrainingsSession session;

	public ExerciseSet() {
	}

	public ExerciseSet(Integer id, Exercise exercise, TrainingsSession session, Integer uid, Integer repeat, Integer order_nr, Boolean isDeleted) {
		this.id = id;
		this.exercise = exercise;
		this.session = session;
		this.uid = uid;
		this.repeat = repeat;
		this.order_nr = order_nr;
		this.isDeleted = isDeleted;
	}

	public ExerciseSet(ExerciseSet exerciseSet) {
		this.id = exerciseSet.id;
		this.exercise = exerciseSet.exercise;
		this.session = exerciseSet.session;
		this.uid = exerciseSet.uid;
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

    public TrainingsSession getSession() {
        return session;
    }

    public void setSession(TrainingsSession session) {
        this.session = session;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExerciseSet that = (ExerciseSet) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (uid != null ? !uid.equals(that.uid) : that.uid != null) return false;
        if (repeat != null ? !repeat.equals(that.repeat) : that.repeat != null) return false;
        if (order_nr != null ? !order_nr.equals(that.order_nr) : that.order_nr != null) return false;
        if (isDeleted != null ? !isDeleted.equals(that.isDeleted) : that.isDeleted != null) return false;
        if (exercise != null ? !exercise.equals(that.exercise) : that.exercise != null) return false;
        return !(session != null ? !session.equals(that.session) : that.session != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        result = 31 * result + (repeat != null ? repeat.hashCode() : 0);
        result = 31 * result + (order_nr != null ? order_nr.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        result = 31 * result + (exercise != null ? exercise.hashCode() : 0);
        result = 31 * result + (session != null ? session.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ExerciseSet{" +
                "id=" + id +
                ", uid=" + uid +
                ", repeat=" + repeat +
                ", order_nr=" + order_nr +
                ", isDeleted=" + isDeleted +
                ", exercise=" + exercise +
                ", session=" + session +
                '}';
    }
}
