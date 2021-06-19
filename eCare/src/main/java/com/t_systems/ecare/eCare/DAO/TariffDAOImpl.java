package com.t_systems.ecare.eCare.DAO;

import com.t_systems.ecare.eCare.entity.Tariff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;
@Repository
public class TariffDAOImpl extends GenericDAO<Tariff> implements TariffDAO{

    @Transactional
    public List<Object[]> getAllTariffNamesAndIds()
    {
        return sessionFactory.getCurrentSession().createQuery("select t.id,t.name from Tariff t", Object[].class).getResultList();
    }

    @Override
    public Tariff findByName(String tariffName) {
        try {
            Tariff tariff=sessionFactory.getCurrentSession().createQuery("from Tariff t where t.name =:name", Tariff.class)
                    .setParameter("name", tariffName)
                    .getSingleResult();
            return tariff;
        }catch (NoResultException e)
        {
            return null;
        }
    }

    @Override
    public boolean isUsed(int id) {
            return !sessionFactory.getCurrentSession()
                    .createQuery("select c.id from Contract c where c.tariffId.id=:id")
                    .setParameter("id",id)
                    .setMaxResults(1)
                    .getResultList().isEmpty();
    }

    @Override
    public List<Tariff> findAll(int page) {
        return sessionFactory.getCurrentSession().createQuery("from Tariff").setFirstResult(10*(page-1)).setMaxResults(10).list();
    }

    @Override
    public int tariffsCount() {
        return sessionFactory.getCurrentSession().createQuery("select count(*) from Tariff",Number.class).getSingleResult().intValue();
    }


    @Autowired
    public void setClass() {
        this.setClass(Tariff.class);
    }
}
