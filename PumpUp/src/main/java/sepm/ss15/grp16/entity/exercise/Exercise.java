package sepm.ss15.grp16.entity.exercise;

import sepm.ss15.grp16.entity.DTO;
import sepm.ss15.grp16.entity.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukas on 30.04.2015.
 */
public class Exercise implements DTO {

    private Integer id;
    private String  name;
    private String  description;
    private Double  calories;
    private String  videolink;
    private List<String> gifLinks = new ArrayList<>();
    private Boolean isDeleted;
    private User    user;
    private List<AbsractCategory> categories = new ArrayList<>();


    public Exercise(Integer id, String name, String description, Double calories, String videolink, List<String> gifLinks, Boolean isDeleted, User user, List<AbsractCategory> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.calories = calories;
        this.videolink = videolink;
        this.gifLinks = gifLinks;
        this.isDeleted = isDeleted;
        this.user = user;
        this.categories = categories;
    }

    public Exercise(String name, String description, Double calories, String videolink, List<String> gifLinks, Boolean isDeleted, User user, List<AbsractCategory> categories) {
        this.name = name;
        this.description = description;
        this.calories = calories;
        this.videolink = videolink;
        this.gifLinks = gifLinks;
        this.isDeleted = isDeleted;
        this.user = user;
        this.categories = categories;
    }

    public Exercise(Exercise exercise) {
        this.id = exercise.id;
        this.name = exercise.name;
        this.description = exercise.description;
        this.calories = exercise.calories;
        this.videolink = exercise.videolink;
        this.gifLinks = exercise.gifLinks;
        this.isDeleted = exercise.isDeleted;
        this.user = exercise.user;
        this.categories = exercise.categories;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public String getVideolink() {
        return videolink;
    }

    public void setVideolink(String videolink) {
        this.videolink = videolink;
    }

    public List<String> getGifLinks() {
        return gifLinks;
    }

    public void setGifLinks(List<String> gifLinks) {
        this.gifLinks = gifLinks;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<AbsractCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<AbsractCategory> categories) {
        this.categories = categories;
    }

    private boolean equalListsString(List<String> one, List<String> two) {
        if (one == null && two == null) {
            return true;
        }

        if ((one == null && two != null) || one != null && two == null || one.size() != two.size()) {
            return false;
        }
        return one.containsAll(two);
    }

    private boolean equalListsCat(List<AbsractCategory> one, List<AbsractCategory> two) {
        if (one == null && two == null) {
            return true;
        }

        if ((one == null && two != null) || one != null && two == null || one.size() != two.size()) {
            return false;
        }
        return one.containsAll(two);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (calories != null ? calories.hashCode() : 0);
        result = 31 * result + (videolink != null ? videolink.hashCode() : 0);
        result = 31 * result + (gifLinks != null ? gifLinks.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (categories != null ? categories.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Exercise)) return false;

        Exercise exercise = (Exercise) o;

        return !(id != null ? !id.equals(exercise.id) : exercise.id != null) && !(name != null ? !name.equals(exercise.name) : exercise.name != null) && !(description != null ? !description.equals(exercise.description) : exercise.description != null) && !(calories != null ? !calories.equals(exercise.calories) : exercise.calories != null) && !(videolink != null ? !videolink.equals(exercise.videolink) : exercise.videolink != null) && equalListsString(gifLinks, exercise.gifLinks) && !(isDeleted != null ? !isDeleted.equals(exercise.isDeleted) : exercise.isDeleted != null) && !(user != null ? !user.equals(exercise.user) : exercise.user != null) && equalListsCat(categories, exercise.categories);

    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", videolink='" + videolink + '\'' +
                ", gifLinks=" + gifLinks +
                ", isDeleted=" + isDeleted +
                ", user=" + user +
                ", categories=" + categories +
                '}';
    }
}
