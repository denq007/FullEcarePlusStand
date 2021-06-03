/*
import com.t_systems.ecare.eCare.configuration.MyConfig;
import com.t_systems.ecare.eCare.DAO.UserDao;
import com.t_systems.ecare.eCare.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
        classes = {MyConfig.class})
@Transactional
public class UserDAOUnitTest {


    @Autowired
    UserDao userDAO = new UserDao();

    */
/**
     * without database, only in session
     *//*


    @Test
    public void userDAOCreatePositivAndDelete() {
        String login = UUID.randomUUID().toString().substring(13) + "@test.de";
        String password = UUID.randomUUID().toString().substring(16);
        User testUser = new User(login, password);
        userDAO.save(testUser);
        log.info("userEntity saved :" + login + password);
        Assertions.assertNotNull(userDAO.getUserByUsername(login));
        userDAO.delete(testUser);
        Assertions.assertNull(userDAO.getUserByUsername(login));
    }

}
*/
