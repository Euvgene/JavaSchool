package com.evgenii.my_market.rest_controller;

import com.evgenii.my_market.dto.UpdatePasswordDto;
import com.evgenii.my_market.dto.UserDto;
import com.evgenii.my_market.dto.UserEmailDto;
import com.evgenii.my_market.dto.UserNameDto;
import com.evgenii.my_market.exception_handling.MarketError;
import com.evgenii.my_market.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * Rest controller for all possible actions with user.
 *
 * @author Boznyakov Evgenii
 */
@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    /**
     * Method responsible for save new user
     *
     * @param newUsers {@linkplain com.evgenii.my_market.dto.UserDto}
     * @return {@linkplain org.springframework.http.ResponseEntity}
     */
    @PostMapping
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserDto newUsers) {
        if (!userService.findByUsernameAndEmail(newUsers.getFirstName(), newUsers.getEmail()).isEmpty()) {
            return new ResponseEntity<>(new MarketError(HttpStatus.CONFLICT.value(), "This username or email already exist"), HttpStatus.CONFLICT);
        } else {
            userService.save(newUsers);
            LOGGER.info("Registration new user {} ", newUsers.getFirstName());
            return ResponseEntity.ok(HttpStatus.CREATED);
        }
    }

    /**
     * Method responsible for getting current user
     *
     * @param principal {@linkplain java.security.Principal}
     * @return {@linkplain  com.evgenii.my_market.dto.UserDto}
     */
    @GetMapping
    public UserDto getCurrentUser(Principal principal) {
        return new UserDto(userService.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", principal.getName()))));
    }

    /**
     * Method responsible for updating  user
     *
     * @param user      {@linkplain com.evgenii.my_market.dto.UserDto}
     * @param principal {@linkplain java.security.Principal}
     * @return {@linkplain org.springframework.http.ResponseEntity}
     */
    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDto user, Principal principal) {
        return userService.updateUser(user, principal.getName());
    }

    /**
     * Method responsible for updating  password
     *
     * @param passwordDto {@linkplain com.evgenii.my_market.dto.UpdatePasswordDto}
     * @param principal   {@linkplain java.security.Principal}
     * @return {@linkplain org.springframework.http.ResponseEntity}
     */
    @PostMapping("/password")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody UpdatePasswordDto passwordDto,
                                            Principal principal) {
        return userService.updatePassword(passwordDto, principal.getName());
    }

    /**
     * Method responsible for checking if username exist in database
     *
     * @param name {@linkplain com.evgenii.my_market.dto.UserNameDto}
     * @return {@linkplain org.springframework.http.ResponseEntity}
     */
    @PostMapping("/name")
    public ResponseEntity<?> checkUserName(@Valid @RequestBody UserNameDto name) {
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    /**
     * Method responsible for checking if email exist in database
     *
     * @param email {@linkplain com.evgenii.my_market.dto.UserEmailDto}
     * @return {@linkplain org.springframework.http.ResponseEntity}
     */
    @PostMapping("/email")
    public ResponseEntity<?> checkUserEmail(@Valid @RequestBody UserEmailDto email) {
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }


}

