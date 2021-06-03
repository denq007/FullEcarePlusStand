package com.t_systems.ecare.eCare.services;

import com.t_systems.ecare.eCare.DAO.ContractDao;
import com.t_systems.ecare.eCare.DAO.CustomerDAOImpl;
import com.t_systems.ecare.eCare.DAO.UserDao;
import com.t_systems.ecare.eCare.DTO.UserDTO;
import com.t_systems.ecare.eCare.entity.Contract;
import com.t_systems.ecare.eCare.entity.Customer;
import com.t_systems.ecare.eCare.entity.Role;
import com.t_systems.ecare.eCare.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    CustomerDAOImpl customerDAO;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ContractDao contractDao;

    public UserDTO convertToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User convertToEntity(UserDTO userDto) {
        User userEntity = modelMapper.map(userDto, User.class);
        userEntity.setPassword(passwordEncoder.encode(userDto.getUserPassword()));
        return userEntity;
    }

    @Override
    public int blockByEmployee(int id) {
        Contract contract=contractDao.findOne(id);
        contract.setBlockedByAdmin(true);
        contractDao.update(contract);
        return contract.getCustomerId().getId();
    }

    @Override
    public int unblockByEmployee(int id) {
        Contract contract=contractDao.findOne(id);
        contract.setBlockedByAdmin(false);
        contractDao.update(contract);
        return contract.getCustomerId().getId();
    }

    @Override
    public void blockByCustomer(int id) {
        Contract contract=contractDao.findOne(id);
        contract.setBlockedByUser(true);
        contractDao.update(contract);
    }

    @Override
    public void unblockByCustomer(int id) {
        Contract contract=contractDao.findOne(id);
        contract.setBlockedByUser(false);
        contractDao.update(contract);
    }


    @Transactional
    public Optional<String> saveUser(UserDTO user) {
        User fromDB = userDao.getUserByUsername(user.getUserLogin());
        if (fromDB != null) {
            return Optional.of("This user is already registered");
        }
        fromDB = new User();
        fromDB.setRole(Role.ROLE_CUSTOMER);
        fromDB.setPassword(passwordEncoder.encode(user.getUserPassword()));
        fromDB.setLogin(user.getUserLogin());
        user.setUserId(fromDB.getId());
        user.setUserRole(Role.ROLE_CUSTOMER);
        Customer customer = new Customer();
        customer.setUser(fromDB);
        customerDAO.save(customer);
        return Optional.empty();
    }

    public void findUserByName(User user) {
        User fromDB = userDao.getUserByUsername(user.getLogin());
    }

    public void authWithHttpServletRequest(HttpServletRequest request, String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException e) {
            System.out.println("Error while login " + e.getMessage());
        }
    }
}
