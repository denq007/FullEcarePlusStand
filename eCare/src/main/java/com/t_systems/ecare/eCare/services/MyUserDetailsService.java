package com.t_systems.ecare.eCare.services;

import com.t_systems.ecare.eCare.DAO.UserDao;
import com.t_systems.ecare.eCare.entity.Role;
import com.t_systems.ecare.eCare.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserDao userDao;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.getUserByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("User not found by name: " + s);
        }
        List<GrantedAuthority> authorities = buildUserAuthority(user.getRoles());
        return buildUserForAuthentication(user, authorities);
    }

    private org.springframework.security.core.userdetails.User buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> setAuths = new HashSet<>();
        for (Role role : userRoles) {
            setAuths.add(new SimpleGrantedAuthority(role.toString()));
        }
        return new ArrayList<>(setAuths);
    }
}
