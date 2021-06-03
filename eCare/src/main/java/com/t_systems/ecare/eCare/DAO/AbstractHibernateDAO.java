package com.t_systems.ecare.eCare.DAO;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.Session;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractHibernateDAO< T  >{
    private Class< T > clazz;

    @Autowired
    private SessionFactory sessionFactory;

    public void setClazz(Class< T > clazzToSet) {
        clazz = clazzToSet;
    }

    public T findOne(long id) {
        return (T) getCurrentSession().get( clazz, id );
    }

   /* public T findByName(String name) {
        return (T) getCurrentSession().get( clazz, name );
    }*/

    public List< T > findAll() {
        return getCurrentSession()
                .createQuery( "from " + clazz.getName() ).list();//getResultList()
    }

    public void save(T entity) {
        getCurrentSession().persist( entity );
    }

    public T update(T entity) {
        return (T) getCurrentSession().merge( entity );
    }

    public void delete(T entity) {
        getCurrentSession().delete( entity );
    }
    public void deleteById(long id) {
        final T entity = findOne( id);
        delete( entity );
    }

    protected final Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
}
