package services;

import com.t_systems.ecare.eCare.DAO.CustomerDAO;
import com.t_systems.ecare.eCare.DAO.CustomerDAOImpl;
import com.t_systems.ecare.eCare.DTO.CustomerDTO;
import com.t_systems.ecare.eCare.entity.Customer;
import com.t_systems.ecare.eCare.services.CustomerService;
import com.t_systems.ecare.eCare.services.ManagerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class ManagerServiceImplTest {

    @InjectMocks
    ManagerServiceImpl managerService;
    @Mock
    CustomerDAOImpl customerDAO;
    @Mock
    CustomerService customerService;
    @Mock
    Customer customer;
    @Mock
    CustomerDTO customerDTO;
    private List<Customer> list = new ArrayList<>();
    private List<CustomerDTO> customerDTOList = new ArrayList<>();

 /*   @Test
    void getAllCustomer() {
        list.add(new Customer());
        when(customerDAO.findAll()).thenReturn(list);
        when(customerService.convertToDto(customer)).thenReturn(customerDTO);
        *//*when(list.stream().map(s -> customerService.convertToDto(s)).collect(Collectors.toList())).thenAnswer(customerDTOList);*//*
        managerService.getAllCustomer();
        assertEquals(managerService.getAllCustomer(), customerDTOList);
        assertDoesNotThrow(() -> managerService.getAllCustomer());
    }*/

    @Test
    void saveCustomerPositive() {
        when(customerDTO.getPassportDetails()).thenReturn(null);
        when(customerDAO.getCustomerByPassport(null)).thenReturn(null);
        when(customerService.convertToEntity(customerDTO)).thenReturn(customer);
        managerService.saveCustomer(customerDTO);
        verify(customerDAO, times(1)).save(customer);

    }
    @Test
    void saveCustomerNegative() {
        when(customerDTO.getPassportDetails()).thenReturn("4002076234");
        when(customerDAO.getCustomerByPassport("4002076234")).thenReturn(customer);
        assertEquals(managerService.saveCustomer(customerDTO),Optional.of("passportId is reserved"));
    }
}