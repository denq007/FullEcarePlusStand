package com.t_systems.ecare.eCare.services;

import com.t_systems.ecare.eCare.DAO.ContractDao;
import com.t_systems.ecare.eCare.DAO.CustomerDAOImpl;
import com.t_systems.ecare.eCare.DAO.UserDao;
import com.t_systems.ecare.eCare.DTO.UserDTO;
import com.t_systems.ecare.eCare.entity.Contract;
import com.t_systems.ecare.eCare.entity.Customer;
import com.t_systems.ecare.eCare.entity.Role;
import com.t_systems.ecare.eCare.entity.User;
import org.apache.log4j.Logger;
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
    private static final Logger logger = org.apache.log4j.Logger.getLogger(UserServiceImpl.class);
    /**
     * Transforms the User to UserDTO
     *
     * @param user database {@code User} object
     * @return userDTO (@code UserDTO)
     */
    public UserDTO convertToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    /**
     * Transforms the UserDTO to User
     *
     * @param userDto data transfer object
     * @return
     */
    public User convertToEntity(UserDTO userDto) {
        User userEntity = modelMapper.map(userDto, User.class);
        userEntity.setPassword(passwordEncoder.encode(userDto.getUserPassword()));
        return userEntity;
    }

    /**
     * Set block property of specific contract to true with role EMPLOYEE
     *
     * @param id id of {@code Contract} to block
     * @return id of {@code Contract} to block
     */
    @Override
    public int blockByEmployee(int id) {
        Contract contract = contractDao.findOne(id);
        contract.setBlockedByAdmin(true);
        contractDao.update(contract);
        return contract.getCustomerId().getId();
    }

    /**
     * Set block property of specific contract to false with role EMPLOYEE
     *
     * @param id id of {@code Contract} to unblock
     * @return id of {@code Contract} to unblock
     */
    @Override
    public int unblockByEmployee(int id) {
        Contract contract = contractDao.findOne(id);
        contract.setBlockedByAdmin(false);
        contractDao.update(contract);
        return contract.getCustomerId().getId();
    }

    /**
     * Set block property of specific contract to true with role CUSTOMER;
     *
     * @param id id of {@code Contract} to block
     */
    @Override
    public void blockByCustomer(int id) {
        Contract contract = contractDao.findOne(id);
        contract.setBlockedByUser(true);
        contractDao.update(contract);
    }

    /**
     * Set block property of specific contract to false with role CUSTOMER;
     *
     * @param id id of {@code Contract}  to unblock
     */
    @Override
    public void unblockByCustomer(int id) {
        Contract contract = contractDao.findOne(id);
        contract.setBlockedByUser(false);
        contractDao.update(contract);
    }


    /**
     * Save new {@code User} based on dto properties with role CUSTOMER.User must be unique.
     *
     * @param user dto data transfer object contains required properties
     * @return empty Optional if user is successfully created or error message if not
     */
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

    /**
     * User Search based user
     *
     * @param user
     */
    public void findUserByName(User user) {
        userDao.getUserByUsername(user.getLogin());
    }

    /**
     * User authentication using the method - HttpServletRequest.login()
     *
     * @param request
     * @param username
     * @param password
     */
    public void authWithHttpServletRequest(HttpServletRequest request, String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException e) {
            logger.info("Error while login " + e.getMessage());
        }
    }
}
