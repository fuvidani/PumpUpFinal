package sepm.ss15.grp16.service.exercise.impl;

import sepm.ss15.grp16.entity.exercise.AbsractCategory;
import sepm.ss15.grp16.entity.exercise.EquipmentCategory;
import sepm.ss15.grp16.entity.exercise.MusclegroupCategory;
import sepm.ss15.grp16.entity.exercise.TrainingsCategory;
import sepm.ss15.grp16.persistence.dao.exercise.CategoryDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;
import sepm.ss15.grp16.service.exercise.CategoryService;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;

import java.util.List;

/**
 * Created by lukas on 12.05.2015.
 */
public class CategoryServiceImpl implements CategoryService {

    private CategoryDAO categoryDAO;

    public CategoryServiceImpl(CategoryDAO categoryDAO) throws ServiceException {
        if (categoryDAO == null) {
            throw new ServiceException("categoryDAO in Service layer null");
        }
        this.categoryDAO = categoryDAO;
    }

    @Override
    public AbsractCategory create(AbsractCategory dto) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    /**
     * no need for all categorys
     *
     * @return
     */
    @Override
    public List<AbsractCategory> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public AbsractCategory update(AbsractCategory dto) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(AbsractCategory dto) throws ServiceException {
        throw new UnsupportedOperationException();

    }

    @Override
    public void validate(AbsractCategory dto) throws ValidationException {
        throw new UnsupportedOperationException();
    }

    /**
     * getting a list of all equipment categorys
     *
     * @return a list of all equipment categorys like, kurzhantel, langhantel, springschnur ...
     * @throws ServiceException
     */
    @Override
    public List<EquipmentCategory> getAllEquipment() throws ServiceException {
        try {
            return categoryDAO.getAllEquipment();
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * getting a list of all muscle groups
     *
     * @return a list of all muslce groups like, bauchmuskeln, oberschenkel, unterschenkel ...
     * @throws ServiceException
     */
    @Override
    public List<MusclegroupCategory> getAllMusclegroup() throws ServiceException {
        try {
            return categoryDAO.getAllMusclegroup();
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * getting a list of all trainingstypes
     *
     * @return a list of all trainingstypes: ausdauer, kraft, balance, flexibilitaet
     * @throws ServiceException
     */
    @Override
    public List<TrainingsCategory> getAllTrainingstype() throws ServiceException {
        try {
            return categoryDAO.getAllTrainingstype();
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * getting exactly one category specified by the given id
     *
     * @param id of the category to search for
     * @return exactely one category
     * @throws ServiceException
     */
    @Override
    public AbsractCategory searchByID(Integer id) throws ServiceException {
        try {
            return categoryDAO.searchByID(id);
        } catch (PersistenceException e) {
            throw new ServiceException(e);
        }
    }
}
