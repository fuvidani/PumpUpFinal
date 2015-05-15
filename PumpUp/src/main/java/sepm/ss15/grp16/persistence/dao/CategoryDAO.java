package sepm.ss15.grp16.persistence.dao;

import sepm.ss15.grp16.entity.AbsractCategory;
import sepm.ss15.grp16.entity.EquipmentCategory;
import sepm.ss15.grp16.entity.MusclegroupCategory;
import sepm.ss15.grp16.entity.TrainingsCategory;
import sepm.ss15.grp16.persistence.exception.PersistenceException;

import java.util.List;

/**
 * Created by lukas on 11.05.2015.
 */
public interface CategoryDAO extends DAO<AbsractCategory> {

    AbsractCategory searchByID(int identificaitonNumber) throws PersistenceException;

    List<EquipmentCategory> getAllEquipment() throws PersistenceException;

    List<MusclegroupCategory> getAllMusclegroup()throws PersistenceException;

    List<TrainingsCategory> getAllTrainingstype()throws PersistenceException;

}
