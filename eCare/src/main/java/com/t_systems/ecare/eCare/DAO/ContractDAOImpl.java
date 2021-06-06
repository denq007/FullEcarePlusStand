package com.t_systems.ecare.eCare.DAO;

import com.t_systems.ecare.eCare.DTO.ContractDTO;
import com.t_systems.ecare.eCare.entity.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ContractDAOImpl extends GenericDAO<Contract> implements ContractDao {

    @Autowired
    public void setClass() {
        this.setClass(Contract.class);
    }

    @Override    //("from Customer u where u.user.id =: user_id", Customer.class)
    public List<Contract> getClientContracts(int customerid) {
        return sessionFactory.getCurrentSession().createQuery(
                "from Contract c where c.customerId.id=:customer_id", Contract.class)
                .setParameter("customer_id", customerid)
                .getResultList();

    }


}
