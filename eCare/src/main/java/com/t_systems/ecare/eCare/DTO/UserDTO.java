package com.t_systems.ecare.eCare.DTO;

import com.t_systems.ecare.eCare.entity.Role;
import com.t_systems.ecare.eCare.validation.ValidEmail;
import com.t_systems.ecare.eCare.validation.ValidPassword;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private int userId;
    private String email;
    @ValidEmail
    @NotNull
    @NotEmpty
    @Size(min=9, max = 70)
    private String userLogin;
    @NotNull
    @NotEmpty
    @ValidPassword
    private String userPassword;
    private boolean userBlocked;
    private Set<Role> userRoles;

    public void setUserRole(Role roleCustomer) {
        if(this.userRoles==null)
            this.userRoles=new HashSet<>();
        this.userRoles.add(roleCustomer);
    }

}
