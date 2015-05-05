package sepm.ss15.grp16.entity;

import java.util.Date;
import java.util.HashMap;

/**
 * This class represents the DTO for a user
 *
 * @author Michael Sober
 * @version 1.0
 */
public class User {

    private Integer user_id;
    private String username;
    private Boolean gender;
    private Integer age;
    private Integer height;
    private Boolean isDeleted;
    private HashMap<Date, Integer> weightHistory;
    private HashMap<Date, Integer> bodyfatHistory;
    private HashMap<Date, String> pictureHistory;

    public User(Integer user_id, String username, Boolean gender, Integer age, Integer height, Boolean isDeleted, HashMap<Date, Integer> weightHistory, HashMap<Date, Integer> bodyfatHistory, HashMap<Date, String> pictureHistory) {
        this.user_id = user_id;
        this.username = username;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.isDeleted = isDeleted;
        this.weightHistory = weightHistory;
        this.bodyfatHistory = bodyfatHistory;
        this.pictureHistory = pictureHistory;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean isGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public HashMap<Date, Integer> getWeightHistory() {
        return weightHistory;
    }

    public void setWeightHistory(HashMap<Date, Integer> weightHistory) {
        this.weightHistory = weightHistory;
    }

    public HashMap<Date, Integer> getBodyfatHistory() {
        return bodyfatHistory;
    }

    public void setBodyfatHistory(HashMap<Date, Integer> bodyfatHistory) {
        this.bodyfatHistory = bodyfatHistory;
    }

    public HashMap<Date, String> getPictureHistory() {
        return pictureHistory;
    }

    public void setPictureHistory(HashMap<Date, String> pictureHistory) {
        this.pictureHistory = pictureHistory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (age != null ? !age.equals(user.age) : user.age != null) return false;
        if (gender != null ? !gender.equals(user.gender) : user.gender != null) return false;
        if (height != null ? !height.equals(user.height) : user.height != null) return false;
        if (isDeleted != null ? !isDeleted.equals(user.isDeleted) : user.isDeleted != null) return false;
        if (user_id != null ? !user_id.equals(user.user_id) : user.user_id != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = user_id != null ? user_id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        return result;
    }
}
