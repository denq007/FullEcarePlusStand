package com.t_systems.ecare.eCare.services;


import com.t_systems.ecare.eCare.DAO.ContractDao;
import com.t_systems.ecare.eCare.DAO.CustomerDAOImpl;
import com.t_systems.ecare.eCare.DAO.UserDao;
import com.t_systems.ecare.eCare.DTO.CustomerDTO;
import com.t_systems.ecare.eCare.entity.Customer;
import com.t_systems.ecare.eCare.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    CustomerDAOImpl customerDAO;
    @Autowired
    UserDao userDao;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UserService userService;
    @Autowired
    ContractDao contractDao;

    /**
     * Transforms the Customer to CustomerDTO
     * @param customer database {@code Customer} object
     * @return сustomerDTO (@code CustomerDTO)
     */
    public CustomerDTO convertToDto(Customer customer) {
        return modelMapper.map(customer, CustomerDTO.class);
    }

    /**
     * Transforms the CustomerDTO to Customer
     * @param customerDTO  data transfer object
     * @return customer (@code Customer)
     */
    public Customer convertToEntity(CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        return customer;
    }

    /**
     * Requests customer by phone number
     * @param phone
     * @return сustomerDTO (@CustomerDTO)
     */
    @Override
    @Transactional
    public CustomerDTO findByPhoneNumber(String phone) {
      Customer customer=customerDAO.getCustomerByPhoneNumber(phone);
        if(customer==null)
            return new CustomerDTO();
        else
        {
        CustomerDTO сustomerDTO=convertToDto(customer);
        return  сustomerDTO;}
    }

    /**
     * Requests customer by user's login
     * @param username
     * @return сustomerDTO (@CustomerDTO)
     */
    @Transactional
    public CustomerDTO getCustomerDTOByEmailUser(String username){
        CustomerDTO сustomerDTO=convertToDto(customerDAO.getCustomerIDBYUserID(
                userDao.getUserByUsername(username).getId()));
        сustomerDTO.setContractIdList(contractDao.getClientContracts(сustomerDTO.getId()));
        return сustomerDTO;
    }

    /**
     * Update all fields in corresponding {@code Customer} with field values from data transfer object
     * @param dto data transfer object contains contract id and properties
     * @return either empty Optional if contract is successfully updated or error message if not
     */
    @Override
    @Transactional
    public Optional<String> update(CustomerDTO dto) {
        Customer customer=customerDAO.findOne(dto.getId());
        User user= customer.getUser();
        customer=convertToEntity(dto);
        customer.setUser(user);
        customerDAO.update(customer);
        return Optional.empty();
    }

    /**
     * Requests customer by id
     * @param id database id of desired {@code Customer} object
     * @return {@code CustomerDTO} object contains contract properties.If the customer is not found it returns null
     */
    @Override
    @Transactional
    public CustomerDTO findById(int id) {
        CustomerDTO customerDTO=new CustomerDTO();
       try {
           Customer customer=  customerDAO.findOne(id);
           customerDTO=convertToDto(customer);
           customerDTO.setUser(userService.convertToDto(customer.getUser()));
           customerDTO.setContractIdList(contractDao.getClientContracts(id));
           return  customerDTO;
       }catch (IllegalArgumentException ex)
       {
          return  null;
       }
    }




}
