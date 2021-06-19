package com.evgenii.my_market.entity;

import com.evgenii.my_market.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Entity class for users.
 *
 * @author Boznyakov Evgenii
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {

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

    @OneToOne(targetEntity = Address.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_adress")
    private Address userAddress;

    @ManyToOne()
    @JoinColumn(name = "user_roles")
    private Role role;

    /**
     * Constructor for creating new instance of this class.
     *
     * @param userDto an instance of {@linkplain com.evgenii.my_market.dto.UserDto }
     */
    public User(UserDto userDto) {
        this.firstName = userDto.getFirstName();
        this.lastName = userDto.getLastName();
        this.email = userDto.getEmail();
        this.birthday = userDto.getBirthday();
        this.userAddress = userDto.getUserAddress();
        this.role = userDto.getRole();
    }
}
