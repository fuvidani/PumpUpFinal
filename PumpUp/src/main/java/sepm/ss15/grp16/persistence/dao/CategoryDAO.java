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

    /**
     * getting exactly one category specified by the given id
     * @param identificaitonNumber of the category to search for
     * @return exactely one category
     * @throws PersistenceException
     */
    AbsractCategory searchByID(int identificaitonNumber) throws PersistenceException;

    /**
     * getting a list of all equipment categorys
     * @return a list of all equipment categorys like, kurzhantel, langhantel, springschnur ...
     * @throws PersistenceException
     */
    List<EquipmentCategory> getAllEquipment() throws PersistenceException;

    /**
     * getting a list of all muscle groups
     * @return a list of all muslce groups like, bauchmuskeln, oberschenkel, unterschenkel ...
     * @throws PersistenceException
     */
    List<MusclegroupCategory> getAllMusclegroup()throws PersistenceException;

    /**
     * getting a list of all trainingstypes
     * @return a list of all trainingstypes: ausdauer, kraft, balance, flexibilitaet
     * @throws PersistenceException
     */
    List<TrainingsCategory> getAllTrainingstype()throws PersistenceException;

}
