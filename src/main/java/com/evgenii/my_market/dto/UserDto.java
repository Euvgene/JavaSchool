package com.evgenii.my_market.dto;

import com.evgenii.my_market.entity.Address;
import com.evgenii.my_market.entity.Order;
import com.evgenii.my_market.entity.StateEnum;
import com.evgenii.my_market.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;


@NoArgsConstructor
@Data
public class UserDto {
    private String firstName;
    private String lastName;
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthday;
    private Address userAddress;

    public UserDto(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email= user.getEmail();
        this.birthday = user.getBirthday();
        this.userAddress = user.getUserAddress();

    }
}
