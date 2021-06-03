package com.t_systems.ecare.eCare.DAO;

import com.t_systems.ecare.eCare.entity.RequiredOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RequiredOptionDAOImpl extends GenericDAO<RequiredOption> implements RequiredOptionDAO{
    @Autowired
    public void setClass() {
        this.setClass(RequiredOption.class);
    }

  /*  @Override
    @Transactional
    public List<Object[]> getAllOptionNamesAndGroupIds() {
        return sessionFactory.getCurrentSession().createQuery("select o.groupId,o.name from RequiredOption o", Object[].class).getResultList();
    }*/
}
