package sepm.ss15.grp16.entity;

import java.util.Date;

/**
 * This class represents the DTO for a bodyfathistory
 *
 * @author Michael Sober
 * @version 1.0
 */
public class BodyfatHistory implements DTO {

    private Integer bodyfathistory_id;
    private Integer user_id;
    private Integer bodyfat;
    private Date date;

    public BodyfatHistory(Integer bodyfathistory_id, Integer user_id, Integer bodyfat, Date date) {
        this.bodyfathistory_id = bodyfathistory_id;
        this.user_id = user_id;
        this.bodyfat = bodyfat;
        this.date = date;
    }

    public Integer getBodyfathistory_id() {
        return bodyfathistory_id;
    }

    public void setBodyfathistory_id(Integer bodyfathistory_id) {
        this.bodyfathistory_id = bodyfathistory_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getBodyfat() {
        return bodyfat;
    }

    public void setBodyfat(Integer bodyfat) {
        this.bodyfat = bodyfat;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public void setId(Integer id) {

    }

    @Override
    public Boolean getIsDeleted() {
        return null;
    }

    @Override
    public void setIsDeleted(Boolean deleted) {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BodyfatHistory that = (BodyfatHistory) o;

        if (bodyfat != null ? !bodyfat.equals(that.bodyfat) : that.bodyfat != null) return false;
        if (bodyfathistory_id != null ? !bodyfathistory_id.equals(that.bodyfathistory_id) : that.bodyfathistory_id != null)
            return false;
        if (user_id != null ? !user_id.equals(that.user_id) : that.user_id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = bodyfathistory_id != null ? bodyfathistory_id.hashCode() : 0;
        result = 31 * result + (user_id != null ? user_id.hashCode() : 0);
        result = 31 * result + (bodyfat != null ? bodyfat.hashCode() : 0);
        return result;
    }
}
