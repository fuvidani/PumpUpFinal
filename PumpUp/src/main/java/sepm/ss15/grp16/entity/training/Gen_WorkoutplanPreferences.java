package sepm.ss15.grp16.entity.training;

import sepm.ss15.grp16.entity.DTO;
import sepm.ss15.grp16.entity.exercise.EquipmentCategory;
import sepm.ss15.grp16.entity.exercise.TrainingsCategory;

import java.util.List;

/**
 * Created by Daniel Fuevesi on 15.05.15.
 */
public class Gen_WorkoutplanPreferences implements DTO {

    private int                     id;
    private TrainingsCategory       goal;
    private List<EquipmentCategory> equipment;

    public Gen_WorkoutplanPreferences(int id, TrainingsCategory goal, List<EquipmentCategory> equipment) {
        this.id = id;
        this.goal = goal;
        this.equipment = equipment;
    }

    public TrainingsCategory getGoal() {
        return goal;
    }

    public List<EquipmentCategory> getEquipment() {
        return equipment;
    }


    @Override
    public Integer getId() {
        return id;
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
        // no implementation
    }
}
