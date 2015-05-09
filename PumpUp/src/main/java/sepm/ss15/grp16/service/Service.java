package sepm.ss15.grp16.service;

import sepm.ss15.grp16.entity.DTO;
import sepm.ss15.grp16.entity.Exercise;
import sepm.ss15.grp16.service.exception.ValidationException;
import sepm.ss15.grp16.service.exception.ServiceException;

import java.util.List;

/**
 * Created by Maximilian on 04.05.2015.
 */
public interface Service<T extends DTO> {

    T create(T dto)throws ServiceException;

    List<T> findAll() throws ServiceException;

    T update(T dto)throws ServiceException;

    void delete(T dto) throws ServiceException;

    void validate(T dto) throws ValidationException;
}
