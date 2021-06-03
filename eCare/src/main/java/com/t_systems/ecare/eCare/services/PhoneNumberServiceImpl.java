package com.t_systems.ecare.eCare.services;

import com.t_systems.ecare.eCare.DAO.PhoneNumberDAO;
import com.t_systems.ecare.eCare.entity.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PhoneNumberServiceImpl implements PhoneNumberService{
    @Autowired
    PhoneNumberDAO phoneNumberDAO;

    @Override
    @Transactional
    public long createphoneNumber() {
        PhoneNumber phoneNumber=new PhoneNumber();
        phoneNumberDAO.save1(phoneNumber);
        phoneNumber.setPhoneNumber(phoneNumber.getId());
        phoneNumberDAO.update(phoneNumber);
        return phoneNumber.getPhoneNumber();
    }
}
