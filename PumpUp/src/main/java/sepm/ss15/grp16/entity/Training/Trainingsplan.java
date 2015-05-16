package sepm.ss15.grp16.entity.training;

import sepm.ss15.grp16.entity.DTO;
import sepm.ss15.grp16.entity.User;

import java.util.List;

/**
 * Author: Lukas
 * Date: 08.05.2015
 */
public class Trainingsplan implements DTO {

	private Integer id;
	private String name;
	private String descr;
	private Boolean isDeleted = false;

	private User user;

	private List<TrainingsSession> trainingsSessions;

	public Trainingsplan() {
	}

	public Trainingsplan(Integer id, User user, String name, String descr, Boolean isDeleted, List<TrainingsSession> trainingsSessions) {
		this.id = id;
		this.user = user;
		this.name = name;
		this.descr = descr;
		this.isDeleted = isDeleted;
		this.trainingsSessions = trainingsSessions;
	}

	public Trainingsplan(User user, String name, String descr, Boolean isDeleted, List<TrainingsSession> trainingsSessions) {
		this.id = null;
		this.user = user;
		this.name = name;
		this.descr = descr;
		this.isDeleted = isDeleted;
		this.trainingsSessions = trainingsSessions;
	}

	public Trainingsplan(Trainingsplan trainingsplan) {
		this.id = trainingsplan.id;
		this.user = trainingsplan.user;
		this.name = trainingsplan.name;
		this.descr = trainingsplan.descr;
		this.isDeleted = trainingsplan.isDeleted;
		this.trainingsSessions = trainingsplan.trainingsSessions;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Trainingsplan)) return false;

		Trainingsplan that = (Trainingsplan) o;

		return !(id != null ? !id.equals(that.id) : that.id != null) &&
				!(user != null ? !user.equals(that.user) : that.user != null) &&
				!(name != null ? !name.equals(that.name) : that.name != null) &&
				!(descr != null ? !descr.equals(that.descr) : that.descr != null) &&
				!(isDeleted != null ? !isDeleted.equals(that.isDeleted) : that.isDeleted != null) &&
				equalLists(trainingsSessions, that.trainingsSessions);

	}

	public boolean equalLists(List<TrainingsSession> one, List<TrainingsSession> two) {
		if (one == null && two == null) {
			return true;
		}

		if ((one == null && two != null)
				|| one != null && two == null
				|| one.size() != two.size()) {
			return false;
		}
		return one.containsAll(two);
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (user != null ? user.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (descr != null ? descr.hashCode() : 0);
		result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
		result = 31 * result + (trainingsSessions != null ? trainingsSessions.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "Trainingsplan{" +
				"id=" + id +
				", user=" + user +
				", name='" + name + '\'' +
				", descr='" + descr + '\'' +
				", isDeleted=" + isDeleted +
				", trainingsSessions=" + trainingsSessions +
				'}';
	}
}
