package sepm.ss15.grp16.entity.calendar;

import sepm.ss15.grp16.entity.DTO;
import sepm.ss15.grp16.entity.training.TrainingsSession;

import java.util.Date;

/**
 * Created by David on 2015.05.15..
 */
public class Appointment implements DTO {

    private Integer appointment_id;
    private Date datum;
    private Integer session_id;
    private Integer user_id;
    private Boolean isTrained;
    private Boolean isDeleted;

    private String sessionName;
    private String setNames;
    private TrainingsSession session;

    public Appointment(Integer appointment_id, Date datum, Integer session_id, Integer user_id, Boolean isTrained, Boolean isDeleted) {
        this.appointment_id = appointment_id;
        this.datum = datum;
        this.session_id = session_id;
        this.user_id = user_id;
        this.isTrained = isTrained;
        this.isDeleted = isDeleted;
    }

    @Override
    public Integer getId() {
        return appointment_id;
    }

    @Override
    public void setId(Integer id) {
        this.appointment_id = id;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Integer getSession_id() {
        return session_id;
    }

    public void setSession_id(Integer session_id) {
        this.session_id = session_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Boolean getIsTrained() {
        return isTrained;
    }

    public void setIsTrained(Boolean isTrained) {
        this.isTrained = isTrained;
    }

    @Override
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    @Override
    public void setIsDeleted(Boolean deleted) {
        this.isDeleted = deleted;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getSetNames() {
        return setNames;
    }

    public void setSetNames(String setNames) {
        this.setNames = setNames;
    }

    public TrainingsSession getSession() {
        return session;
    }

    public void setSession(TrainingsSession session) {
        this.session = session;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Appointment)) return false;

        Appointment that = (Appointment) o;

        if (appointment_id != null ? !appointment_id.equals(that.appointment_id) : that.appointment_id != null)
            return false;
        if (datum != null ? !datum.equals(that.datum) : that.datum != null) return false;
        if (session_id != null ? !session_id.equals(that.session_id) : that.session_id != null) return false;
        if (user_id != null ? !user_id.equals(that.user_id) : that.user_id != null) return false;
        if (isTrained != null ? !isTrained.equals(that.isTrained) : that.isTrained != null) return false;
        return !(isDeleted != null ? !isDeleted.equals(that.isDeleted) : that.isDeleted != null);

    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointment_id=" + appointment_id +
                ", datum=" + datum +
                ", session_id=" + session_id +
                ", user_id=" + user_id +
                ", isTrained=" + isTrained +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
