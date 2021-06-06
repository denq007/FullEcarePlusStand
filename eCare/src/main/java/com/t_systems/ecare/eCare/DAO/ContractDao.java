package com.t_systems.ecare.eCare.DAO;

import com.t_systems.ecare.eCare.DTO.ContractDTO;
import com.t_systems.ecare.eCare.entity.Contract;

import java.util.List;

public interface ContractDao extends IGenericDAO<Contract>{
    public List<Contract> getClientContracts(int customerid);


}
