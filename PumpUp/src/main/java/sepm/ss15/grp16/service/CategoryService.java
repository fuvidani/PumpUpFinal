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

    /**
     * getting a list of all equipment categorys
     * @return a list of all equipment categorys like, kurzhantel, langhantel, springschnur ...
     * @throws ServiceException
     */
    List<EquipmentCategory> getAllEquipment() throws ServiceException;

    /**
     * getting a list of all muscle groups
     * @return a list of all muslce groups like, bauchmuskeln, oberschenkel, unterschenkel ...
     * @throws ServiceException
     */
    List<MusclegroupCategory> getAllMusclegroup() throws ServiceException;

    /**
     * getting a list of all trainingstypes
     * @return a list of all trainingstypes: ausdauer, kraft, balance, flexibilitaet
     * @throws ServiceException
     */
    List<TrainingsCategory> getAllTrainingstype() throws ServiceException;

    /**
     * getting exactly one category specified by the given id
     * @param id of the category to search for
     * @return exactely one category
     * @throws ServiceException
     */
    AbsractCategory searchByID(Integer id) throws ServiceException;
}
