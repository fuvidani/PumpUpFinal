package sepm.ss15.grp16.entity.impl;

import sepm.ss15.grp16.entity.DTO;

import java.util.List;

/**
 * Author: Lukas
 * Date: 08.05.2015
 */
public class Trainingsplan implements DTO {

	Integer id;
	Integer uid;
	String name;
	String descr;
	Boolean isDeleted;

	List<TrainingsSession> trainingsSessions;
	List<TrainingsplanType> trainingsplanTypes;

	public Trainingsplan() {
	}

	public Trainingsplan(Integer id, Integer uid, String name, String descr, Boolean isDeleted, List<TrainingsSession> trainingsSessions, List<TrainingsplanType> trainingsplanTypes) {
		this.id = id;
		this.uid = uid;
		this.name = name;
		this.descr = descr;
		this.isDeleted = isDeleted;
		this.trainingsSessions = trainingsSessions;
		this.trainingsplanTypes = trainingsplanTypes;
	}

	public Trainingsplan(Trainingsplan trainingsplan) {
		this.id = trainingsplan.id;
		this.uid = trainingsplan.uid;
		this.name = trainingsplan.name;
		this.descr = trainingsplan.descr;
		this.isDeleted = trainingsplan.isDeleted;
		this.trainingsSessions = trainingsplan.trainingsSessions;
		this.trainingsplanTypes = trainingsplan.trainingsplanTypes;
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

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Override
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	@Override
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
		if (trainingsSessions != null) {
			for (TrainingsSession session : trainingsSessions) session.setIsDeleted(isDeleted);
		}
	}

	public List<TrainingsSession> getTrainingsSessions() {
		return trainingsSessions;
	}

	public void setTrainingsSessions(List<TrainingsSession> trainingsSessions) {
		this.trainingsSessions = trainingsSessions;
	}

	public List<TrainingsplanType> getTrainingsplanTypes() {
		return trainingsplanTypes;
	}

	public void setTrainingsplanTypes(List<TrainingsplanType> trainingsplanTypes) {
		this.trainingsplanTypes = trainingsplanTypes;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
		if (trainingsSessions != null) {
			for (TrainingsSession session : trainingsSessions) session.setId_plan(id);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Trainingsplan)) return false;

		Trainingsplan that = (Trainingsplan) o;

		return !(id != null ? !id.equals(that.id) : that.id != null) &&
				!(uid != null ? !uid.equals(that.uid) : that.uid != null) &&
				!(name != null ? !name.equals(that.name) : that.name != null) &&
				!(descr != null ? !descr.equals(that.descr) : that.descr != null) &&
				!(isDeleted != null ? !isDeleted.equals(that.isDeleted) : that.isDeleted != null) &&
				!(trainingsSessions != null ? !trainingsSessions.equals(that.trainingsSessions) : that.trainingsSessions != null) &&
				!(trainingsplanTypes != null ? !trainingsplanTypes.equals(that.trainingsplanTypes) : that.trainingsplanTypes != null);

	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (uid != null ? uid.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (descr != null ? descr.hashCode() : 0);
		result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
		result = 31 * result + (trainingsSessions != null ? trainingsSessions.hashCode() : 0);
		result = 31 * result + (trainingsplanTypes != null ? trainingsplanTypes.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Trainingsplan{" +
				"id=" + id +
				", uid=" + uid +
				", name='" + name + '\'' +
				", descr='" + descr + '\'' +
				", isDeleted=" + isDeleted +
				", trainingsSessions=" + trainingsSessions +
				", trainingsplanTypes=" + trainingsplanTypes +
				'}';
	}
}
