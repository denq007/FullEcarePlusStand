package com.t_systems.ecare.eCare.DAO;

import java.util.List;

public interface IGenericDAO <T>{
    T findOne(final int id);

    List<T> findAll();

    List<T> allInRange(int offset, int limit);

    List<T> getLast(int limit);

    int save(final T entity);

    void save1(final T entity);

    void update(final T entity);

    void delete(final T entity);

    void deleteById(final int entityId);

    void setClass(Class<T> clazzToSet);

    Long count();
}
