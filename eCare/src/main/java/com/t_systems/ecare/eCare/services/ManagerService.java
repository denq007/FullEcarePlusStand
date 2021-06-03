package com.t_systems.ecare.eCare.services;

import com.t_systems.ecare.eCare.DTO.CustomerDTO;

import java.util.List;
import java.util.Optional;

public interface ManagerService {
    public List<CustomerDTO> getAllCustomer();
    public Optional<String> saveCustomer(CustomerDTO customerDTO);

}
