package com.t_systems.ecare.eCare.DAO;
import com.t_systems.ecare.eCare.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;


@Repository
public class UserDao extends GenericDAO<User>{


    public User getUserByUsername(String login) {
     try {
         User user=sessionFactory.getCurrentSession().createQuery("from User u where u.login =: login", User.class)
                 .setParameter("login", login)
                 .getSingleResult();
         return user;
     }catch (NoResultException e)
     {
         User user= null;
         return user;
     }

    }


    @Autowired
    public void setClass() {
        this.setClass(User.class);
    }

}
