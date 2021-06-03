package com.t_systems.ecare.eCare.DAO;

import com.t_systems.ecare.eCare.entity.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PhoneNumberDAO extends GenericDAO<PhoneNumber>{
    @Autowired
    public void setClass() {
        this.setClass(PhoneNumber.class);
    }
}
