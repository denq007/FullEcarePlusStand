/*
package com.t_systems.ecare.eCare.Converter;

import com.t_systems.ecare.eCare.DTO.UserDTO;
import com.t_systems.ecare.eCare.entity.User;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@NoArgsConstructor
public class UserConverterImpl implements UserConverter{


    @Override
    public User converterUSERDTOToUser(UserDTO userDTO) {
      User user=new User();
      user.setBlocked(userDTO.isUserBlocked());
      user.setLogin(userDTO.getUserLogin());
      user.setPassword(userDTO.getUserPassword());
      user.setRoles(userDTO.getUserRoles());
      user.setId(userDTO.getUserId());

        return user;
    }

    @Override
    public UserDTO converterUserToUserDTO(User user) {
        UserDTO userDTO=new UserDTO();
        userDTO.setUserId(user.getId());
        userDTO.setUserLogin(user.getLogin());
        userDTO.setUserBlocked(user.isBlocked());
        userDTO.setUserRoles(user.getRoles());
        userDTO.setUserPassword(user.getPassword());
        return userDTO;
    }
}
*/
