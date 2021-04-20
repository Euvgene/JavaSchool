package com.evgenii.my_market.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthday;

    @OneToOne()
    @JoinColumn(name = "user_adress")
    private Address userAddress;

    @OneToOne()
    @JoinColumn(name = "user_roles")
    private Role role;

}
