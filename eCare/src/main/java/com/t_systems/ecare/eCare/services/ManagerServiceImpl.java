package com.t_systems.ecare.eCare.services;


import com.t_systems.ecare.eCare.DAO.CustomerDAOImpl;
import com.t_systems.ecare.eCare.DTO.CustomerDTO;
import com.t_systems.ecare.eCare.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ManagerServiceImpl implements ManagerService{
    @Autowired
    CustomerDAOImpl customerDAO;
    @Autowired
    CustomerService customerService;


    @Transactional
    public List<CustomerDTO> getAllCustomer()
    {
        List<Customer>list= customerDAO.findAll();
        return list.stream().map(s->customerService.convertToDto(s)).collect(Collectors.toList());
    }

    @Transactional
    public Optional<String> saveCustomer(CustomerDTO customerDTO)
    {
        if (customerDAO.getCustomerByPassport(customerDTO.getPassportDetails()) != null)
            return Optional.of("passportId is reserved");
        Customer customer= customerService.convertToEntity(customerDTO);
        customerDAO.save( customer);
        return Optional.empty();
    }
    public void ubdateData(Customer customer,CustomerDTO customerDTO)
    {
        customer.setName(customerDTO.getName());
        customer.setSurname(customerDTO.getSurname());
        customer.setAddress(customerDTO.getAddress());
        customer.setBirthDate(customerDTO.getBirthDate());
        customer.setPassportDetails(customerDTO.getPassportDetails());

    }
}
