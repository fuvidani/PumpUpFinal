package sepm.ss15.grp16.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukas on 30.04.2015.
 */
public class Exercise {

    private Integer id;
    private String name;
    private String description;
    private Double calories;
    private String videolink;
    private List<String> gifLinks = new ArrayList<>();

    public Exercise(Integer id, String name, String description, Double calories, String videolink, List<String> gifLinks) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.calories = calories;
        this.videolink = videolink;
        this.gifLinks = gifLinks;
    }

    public Exercise(Integer id, String name, String description, Double calories, String videolink) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.calories = calories;
        this.videolink = videolink;
        gifLinks.add("empty");
    }

    public Exercise(String name, String description, Double calories, String videolink, List<String> gifLinks) {
        this.name = name;
        this.description = description;
        this.calories = calories;
        this.videolink = videolink;
        this.gifLinks = gifLinks;
    }

    public Exercise(String name, String description, Double calories, String videolink) {
        this.name = name;
        this.description = description;
        this.calories = calories;
        this.videolink = videolink;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
