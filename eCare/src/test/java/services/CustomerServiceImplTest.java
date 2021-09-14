package services;

import com.t_systems.ecare.eCare.DAO.ContractDao;
import com.t_systems.ecare.eCare.DAO.CustomerDAOImpl;
import com.t_systems.ecare.eCare.DAO.UserDao;
import com.t_systems.ecare.eCare.DTO.CustomerDTO;
import com.t_systems.ecare.eCare.DTO.UserDTO;
import com.t_systems.ecare.eCare.entity.Contract;
import com.t_systems.ecare.eCare.entity.Customer;
import com.t_systems.ecare.eCare.entity.User;
import com.t_systems.ecare.eCare.services.CustomerServiceImpl;
import com.t_systems.ecare.eCare.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
@RunWith(MockitoJUnitRunner.class)
class CustomerServiceImplTest {
    @InjectMocks
    CustomerServiceImpl customerService;
    @Mock
    CustomerDAOImpl customerDAO;
    @Mock
    Customer customer;
    @Mock
    ModelMapper modelMapper;
    @Mock
    CustomerDTO customerDTO;
    @Mock
    UserDao userDao;
    @Mock
    ContractDao contractDao;
    @Mock
    User user;
    @Mock
    UserService userService;
    @Mock
    UserDTO userDTO;
    private List<Contract> contractList=new ArrayList<>();
    @BeforeEach
    void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findByPhoneNumberPositive() {
        when(customerDAO.getCustomerByPhoneNumber("9210010101")).thenReturn(customer);
        when(customerService.convertToDto(customer)).thenReturn(customerDTO);
        assertEquals(customerService.findByPhoneNumber("9210010101"), customerDTO);
        }
    @Test
    void findByPhoneNumberNegative() {
        when(customerDAO.getCustomerByPhoneNumber("9210010101")).thenReturn(null);
        assertEquals(customerService.findByPhoneNumber("9210010101").getName(), null);
    }

    @Test
    void getCustomerDTOByEmailUser() {
        String username="Vasya";
        when(userDao.getUserByUsername(username)).thenReturn(user);
        when(customerDAO.getCustomerIDBYUserID(user.getId())).thenReturn(customer);
        when(customerService.convertToDto(customer)).thenReturn(customerDTO);
        assertEquals(customerService.getCustomerDTOByEmailUser(username),customerDTO);
    }

    @Test
    void update() {
        when(customerDAO.findOne(customerDTO.getId())).thenReturn(customer);
        when(customer.getUser()).thenReturn(user);
        when(customerService.convertToEntity(customerDTO)).thenReturn(customer);
        customerService.update(customerDTO);
        verify(customerDAO).update(customer);
    }

    @Test
    void findByIdPositive() {
        int id=1;
        when(customerDAO.findOne(id)).thenReturn(customer);
        when(customerService.convertToDto(customer)).thenReturn(customerDTO);
        when(userService.convertToDto(customer.getUser())).thenReturn(userDTO);
        when(contractDao.getClientContracts(id)).thenReturn(contractList);
       assertEquals(customerService.findById(1),customerDTO);
    }
    @Test
    void findByIDNegative()
    {   int id=1;
        when(customerDAO.findOne(id)).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> customerDAO.findOne(id));
    }
}