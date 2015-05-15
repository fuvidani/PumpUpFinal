package sepm.ss15.grp16.service;

import sepm.ss15.grp16.entity.AbsractCategory;
import sepm.ss15.grp16.entity.EquipmentCategory;
import sepm.ss15.grp16.entity.MusclegroupCategory;
import sepm.ss15.grp16.entity.TrainingsCategory;
import sepm.ss15.grp16.service.exception.ServiceException;

import java.util.List;

/**
 * Created by lukas on 12.05.2015.
 */
public interface CategoryService extends Service<AbsractCategory> {

    List<EquipmentCategory> getAllEquipment() throws ServiceException;

    List<MusclegroupCategory> getAllMusclegroup() throws ServiceException;

    List<TrainingsCategory> getAllTrainingstype() throws ServiceException;

    AbsractCategory searchByID(Integer id) throws ServiceException;
}
