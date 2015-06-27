package sepm.ss15.grp16.entity.user;

import sepm.ss15.grp16.entity.DTO;

import java.util.Date;

/**
 * This class represents the DTO for a picturehistory
 *
 * @author Michael Sober
 * @version 1.0
 */
public class PictureHistory implements DTO {

    private Integer picturehistory_id;
    private Integer user_id;
    private String  location;
    private Date    date;
    private Boolean isDeleted;

    public PictureHistory(Integer picturehistory_id, Integer user_id, String location, Date date) {
        this.picturehistory_id = picturehistory_id;
        this.user_id = user_id;
        this.location = location;
        this.date = date;
        this.isDeleted = false;
    }

    public PictureHistory(Integer picturehistory_id, Integer user_id, String location, Date date, Boolean isDeleted) {
        this.picturehistory_id = picturehistory_id;
        this.user_id = user_id;
        this.location = location;
        this.date = date;
        this.isDeleted = isDeleted;
    }

    public Integer getPicturehistory_id() {
        return picturehistory_id;
    }

    public void setPicturehistory_id(Integer picturehistory_id) {
        this.picturehistory_id = picturehistory_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public Integer getId() {
        return getPicturehistory_id();
    }

    @Override
    public void setId(Integer id) {
        setPicturehistory_id(id);
    }

    @Override
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    @Override
    public void setIsDeleted(Boolean deleted) {
        this.isDeleted = deleted;
    }

    @Override
    public int hashCode() {
        int result = picturehistory_id != null ? picturehistory_id.hashCode() : 0;
        result = 31 * result + (user_id != null ? user_id.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PictureHistory that = (PictureHistory) o;

        if (picturehistory_id != null ? !picturehistory_id.equals(that.picturehistory_id) : that.picturehistory_id != null)
            return false;
        if (user_id != null ? !user_id.equals(that.user_id) : that.user_id != null) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        return !(isDeleted != null ? !isDeleted.equals(that.isDeleted) : that.isDeleted != null);

    }

    @Override
    public String toString() {
        return "PictureHistory{" +
                "picturehistory_id=" + picturehistory_id +
                ", user_id=" + user_id +
                ", location='" + location + '\'' +
                ", date=" + date +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
