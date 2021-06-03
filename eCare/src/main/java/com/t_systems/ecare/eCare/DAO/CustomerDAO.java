package com.t_systems.ecare.eCare.DAO;

import com.t_systems.ecare.eCare.entity.Customer;

public interface CustomerDAO extends IGenericDAO<Customer>{
    public Customer getCustomerByPhoneNumber(String phone);
    public Customer getCustomerIDBYUserID(int userID);
    public Customer getCustomerByPassport(String passportDetails);
}
