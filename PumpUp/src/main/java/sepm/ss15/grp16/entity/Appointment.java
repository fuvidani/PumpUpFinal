package sepm.ss15.grp16.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by David on 2015.05.15..
 */
public class Appointment implements DTO {

    private Integer appointment_id;
    private Date datum;
    private Integer session_id;
    private Integer user_id;
    private Boolean isDeleted;

    private String sessionName;
    private List<String> setNames = new ArrayList<>();

    public Appointment(Integer appointment_id, Date datum, Integer session_id, Integer user_id, Boolean isDeleted) {
        this.appointment_id = appointment_id;
        this.datum = datum;
        this.session_id = session_id;
        this.user_id = user_id;
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

    public List<String> getSetNames() {
        return setNames;
    }

    public void setSetNames(List<String> setNames) {
        this.setNames = setNames;
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
        return !(isDeleted != null ? !isDeleted.equals(that.isDeleted) : that.isDeleted != null);

    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointment_id=" + appointment_id +
                ", datum=" + datum +
                ", session_id=" + session_id +
                ", user_id=" + user_id +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
