package sepm.ss15.grp16.entity;

import java.util.Date;
import java.util.HashMap;

/**
 * This class represents the DTO for a user
 *
 * @author Michael Sober
 * @version 1.0
 */
public class User implements DTO{

    private Integer user_id;
    private String username;
    private Boolean gender;
    private Integer age;
    private Integer height;
    private String email;
    private String playlist;
    private Boolean isDeleted;

    public User(Integer user_id, String username, Boolean gender, Integer age, Integer height, Boolean isDeleted) {
        this.user_id = user_id;
        this.username = username;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.isDeleted = isDeleted;
    }

    public User(Integer user_id, String username, Boolean gender, Integer age, Integer height, String email, String playlist, Boolean isDeleted) {
        this.user_id = user_id;
        this.username = username;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.email = email;
        this.playlist = playlist;
        this.isDeleted = isDeleted;
    }

    @Override
    public Integer getId() {
        return getUser_id();
    }

    @Override
    public void setId(Integer id) {
        setUser_id(id);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlaylist() {
        return playlist;
    }

    public void setPlaylist(String playlist) {
        this.playlist = playlist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (user_id != null ? !user_id.equals(user.user_id) : user.user_id != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (gender != null ? !gender.equals(user.gender) : user.gender != null) return false;
        if (age != null ? !age.equals(user.age) : user.age != null) return false;
        if (height != null ? !height.equals(user.height) : user.height != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (playlist != null ? !playlist.equals(user.playlist) : user.playlist != null) return false;
        return !(isDeleted != null ? !isDeleted.equals(user.isDeleted) : user.isDeleted != null);

    }

    @Override
    public int hashCode() {
        int result = user_id != null ? user_id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (playlist != null ? playlist.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", height=" + height +
                ", email='" + email + '\'' +
                ", playlist='" + playlist + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
