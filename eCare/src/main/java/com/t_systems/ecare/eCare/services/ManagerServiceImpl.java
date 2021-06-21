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

    /**
     * Requests all customers in (@link CustomerDAO)
     * @return list of {@code CustomerDTO}
     */
    @Transactional
    public List<CustomerDTO> getAllCustomer()
    {
        List<Customer>list= customerDAO.findAll();
        return list.stream().map(s->customerService.convertToDto(s)).collect(Collectors.toList());
    }

    /**
     * Create new {@code Customer} based on dto properties
     * @param customerDTO
     * @return empty Optional if contract is successfully created or error message if not
     */
    @Transactional
    public Optional<String> saveCustomer(CustomerDTO customerDTO)
    {
        if (customerDAO.getCustomerByPassport(customerDTO.getPassportDetails()) != null)
            return Optional.of("passportId is reserved");
        Customer customer= customerService.convertToEntity(customerDTO);
        customerDAO.save( customer);
        return Optional.empty();
    }
}
