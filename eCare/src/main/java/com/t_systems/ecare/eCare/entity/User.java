package com.t_systems.ecare.eCare.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "user")
@NamedQueries({@NamedQuery (name="User.findByUer",  query = "SELECT u FROM User u where u.login = :login") })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "blocked")
    boolean blocked=false;
    @ElementCollection(targetClass = Role.class,fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role",joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "roles")
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
 /*   public User() {
      *//*  roles=new HashSet<>();
        roles.add(Role.CUSTOMER);*//*
    }*/

    public void setRole(Role roles) {
        if(this.roles==null)
            this.roles=new HashSet<>();
        this.roles.add(roles);
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
