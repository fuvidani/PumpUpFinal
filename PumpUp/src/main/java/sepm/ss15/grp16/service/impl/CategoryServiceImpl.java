package sepm.ss15.grp16.service.impl;

import sepm.ss15.grp16.entity.*;
import sepm.ss15.grp16.persistence.dao.CategoryDAO;
import sepm.ss15.grp16.persistence.exception.PersistenceException;
import sepm.ss15.grp16.service.CategoryService;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;

import java.util.List;

/**
 * Created by lukas on 12.05.2015.
 */
public class CategoryServiceImpl implements CategoryService {

    private CategoryDAO categoryDAO;

    public CategoryServiceImpl(CategoryDAO categoryDAO)throws ServiceException{
        if(categoryDAO==null){
            throw new ServiceException("categoryDAO in Service layer null");
        }
        this.categoryDAO=categoryDAO;
    }

    @Override
    public AbsractCategory create(AbsractCategory dto) throws ServiceException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List findAll() throws ServiceException {
       try{
           return categoryDAO.findAll();
       }catch (PersistenceException e){
           throw new ServiceException(e);
       }
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

    @Override
    public List<EquipmentCategory> getAllEquipment() throws ServiceException {
        try{
            return categoryDAO.getAllEquipment();
        }catch (PersistenceException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public List<MusclegroupCategory> getAllMusclegroup() throws ServiceException {
        try{
           return categoryDAO.getAllMusclegroup();
        }catch (PersistenceException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public List<TrainingsCategory> getAllTrainingstype() throws ServiceException {
       try{
           return categoryDAO.getAllTrainingstype();
       }catch (PersistenceException e){
           throw new ServiceException(e);
       }
    }

    @Override
    public AbsractCategory searchByID(Integer id) throws ServiceException {
        try{
            return categoryDAO.searchByID(id);
        }catch (PersistenceException e){
            throw new ServiceException(e);
        }
    }
}
