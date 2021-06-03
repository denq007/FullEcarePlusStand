package com.t_systems.ecare.eCare.DAO;

import com.t_systems.ecare.eCare.entity.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class OptionDAOImp extends GenericDAO<Option> implements OptionDAO {

    @Override
    @Transactional
    public List<Object[]> getAllOptionNamesAndIds() {
        return sessionFactory.getCurrentSession().createQuery("select o.id,o.name from Option o", Object[].class).getResultList();
    }

    @Override
    public Option findByName(String optionName) {
        try {
            Option option = sessionFactory.getCurrentSession().createQuery("from Option o where o.name =:name", Option.class)
                    .setParameter("name", optionName).getSingleResult();
            return option;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Autowired
    public void setClass() {
        this.setClass(Option.class);
    }
}
