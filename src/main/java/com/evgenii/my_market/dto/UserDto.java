package com.evgenii.my_market.dto;

import com.evgenii.my_market.entity.Address;
import com.evgenii.my_market.entity.Role;
import com.evgenii.my_market.entity.User;
import com.evgenii.my_market.validator.ValidRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Date;

/**
 * DTO for entity {@linkplain com.evgenii.my_market.entity.User User}.
 * @author Boznyakov Evgenii
 *
 */
@NoArgsConstructor
@Data
public class UserDto {
    @NotEmpty(message = "Please provide a name")
    private String firstName;

    @NotEmpty(message = "Please provide a lastname")
    private String lastName;

    @NotEmpty(message = "Please provide a email")
    @Email(message = "invalid email format")
    private String email;

    @NotEmpty(message = "Please provide a password")
    @Size(min = 8, max = 61, message = "Password size must be higher than 8 letters or numbers")
    private String password;

    @Past(message = "Must be a past date")
    @NotNull(message = "Please provide a birth date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthday;

    @Valid
    private Address userAddress;

    @ValidRole
    private Role role;

    /**
     * Constructor for creating new instance of this class.
     * @param user an instance of {@linkplain com.evgenii.my_market.entity.User User}
     */
    public UserDto(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.birthday = user.getBirthday();
        this.userAddress = user.getUserAddress();
        this.role = user.getRole();
        this.password = user.getPassword();
    }
}
