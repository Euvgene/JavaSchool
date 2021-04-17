package com.evgenii.my_market.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class Users {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "birthday")
    private String birthday;

    @OneToOne()
    @JoinColumn(name = "user_adress")
    private Address userAddress;

    @OneToOne()
    @JoinColumn(name = "user_roles")
    private Role role;

}
