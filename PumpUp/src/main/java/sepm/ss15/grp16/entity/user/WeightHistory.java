package sepm.ss15.grp16.entity.user;

import sepm.ss15.grp16.entity.DTO;

import java.util.Date;

/**
 * This class represents the DTO for a weighthistory
 *
 * @author Michael Sober
 * @version 1.0
 */
public class WeightHistory implements DTO {

    private Integer weightHistory_id;
    private Integer user_id;
    private Integer weight;
    private Date    date;

    public WeightHistory(Integer weightHistory_id, Integer user_id, Integer weight, Date date) {
        this.weightHistory_id = weightHistory_id;
        this.user_id = user_id;
        this.weight = weight;
        this.date = date;
    }

    public Integer getWeightHistory_id() {
        return weightHistory_id;
    }

    public void setWeightHistory_id(Integer weightHistory_id) {
        this.weightHistory_id = weightHistory_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public Integer getId() {
        return getWeightHistory_id();
    }

    @Override
    public void setId(Integer id) {
        setWeightHistory_id(id);
    }

    @Override
    public Boolean getIsDeleted() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setIsDeleted(Boolean deleted) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int hashCode() {
        int result = weightHistory_id != null ? weightHistory_id.hashCode() : 0;
        result = 31 * result + (user_id != null ? user_id.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeightHistory that = (WeightHistory) o;

        if (user_id != null ? !user_id.equals(that.user_id) : that.user_id != null) return false;
        if (weight != null ? !weight.equals(that.weight) : that.weight != null) return false;
        if (weightHistory_id != null ? !weightHistory_id.equals(that.weightHistory_id) : that.weightHistory_id != null)
            return false;

        return true;
    }

    @Override
    public String toString() {
        return "WeightHistory{" +
                "weightHistory_id=" + weightHistory_id +
                ", user_id=" + user_id +
                ", weight=" + weight +
                ", date=" + date +
                '}';
    }
}
