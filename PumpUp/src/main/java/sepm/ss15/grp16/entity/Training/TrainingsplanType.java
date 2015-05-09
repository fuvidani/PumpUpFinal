package sepm.ss15.grp16.entity.Training;

import sepm.ss15.grp16.entity.DTO;

import java.util.List;

/**
 * Author: Lukas
 * Date: 08.05.2015
 */
public class TrainingsplanType implements DTO {

	Integer id;
	Integer uid;
	String name;
	String descr;
	Boolean isDeleted = false;

	List<Trainingsplan> trainingsplans;

	public TrainingsplanType(){
	}

	public TrainingsplanType(Integer id, Integer uid, String name, String descr, Boolean isDeleted, List<Trainingsplan> trainingsplans) {
		this.id = id;
		this.uid = uid;
		this.name = name;
		this.descr = descr;
		this.isDeleted = isDeleted;
		this.trainingsplans = trainingsplans;
	}

	public TrainingsplanType(TrainingsplanType trainingsplanType) {
		this.id = trainingsplanType.id;
		this.uid = trainingsplanType.uid;
		this.name = trainingsplanType.name;
		this.descr = trainingsplanType.descr;
		this.trainingsplans = trainingsplanType.trainingsplans;
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

	public List<Trainingsplan> getTrainingsplans() {
		return trainingsplans;
	}

	public void setTrainingsplans(List<Trainingsplan> trainingsplans) {
		this.trainingsplans = trainingsplans;
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
		return isDeleted;
	}

	@Override
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof TrainingsplanType)) return false;

		TrainingsplanType that = (TrainingsplanType) o;

		return !(id != null ? !id.equals(that.id) : that.id != null) &&
				!(uid != null ? !uid.equals(that.uid) : that.uid != null) &&
				!(name != null ? !name.equals(that.name) : that.name != null) &&
				!(descr != null ? !descr.equals(that.descr) : that.descr != null) &&
				!(isDeleted != null ? !isDeleted.equals(that.isDeleted) : that.isDeleted != null) &&
				!(trainingsplans != null ? !trainingsplans.equals(that.trainingsplans) : that.trainingsplans != null);

	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (uid != null ? uid.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (descr != null ? descr.hashCode() : 0);
		result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
		result = 31 * result + (trainingsplans != null ? trainingsplans.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "TrainingsplanType{" +
				"id=" + id +
				", uid=" + uid +
				", name='" + name + '\'' +
				", descr='" + descr + '\'' +
				", isDeleted=" + isDeleted +
				", trainingsplans=" + trainingsplans +
				'}';
	}
}
