package com.t_systems.ecare.eCare.DAO;

import com.t_systems.ecare.eCare.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

@Repository
public class CustomerDAOImpl extends GenericDAO<Customer> implements CustomerDAO{

    public Customer getCustomerByPassport(String passportDetails) {
        try {
            Customer customer=sessionFactory.getCurrentSession().createQuery("from Customer u where u.passportDetails =: passport_details", Customer.class)
                    .setParameter("passport_details", passportDetails)
                    .getSingleResult();
            return customer;
        }catch (NoResultException e)
        {
            return null;
        }
    }

    public Customer getCustomerIDBYUserID(int userID) {
        try {
            Customer customer=sessionFactory.getCurrentSession().createQuery("from Customer u where u.user.id =: user_id", Customer.class)
                    .setParameter("user_id", userID)
                    .getSingleResult();
            return customer;
        }catch (NoResultException e)
        {
            return null;
        }

    }

    @Autowired
    public void setClass() {
        this.setClass(Customer.class);
    }

    public Customer getCustomerByPhoneNumber(String phone) {
        try {
            return sessionFactory.getCurrentSession().createQuery("select c.customerId from Contract c where c.number=:number", Customer.class)
                    .setParameter("number", phone)
                    .getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }
}
