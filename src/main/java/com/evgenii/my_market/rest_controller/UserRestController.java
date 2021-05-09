package com.evgenii.my_market.rest_controller;

import com.evgenii.my_market.config.JwtTokenUtil;
import com.evgenii.my_market.dto.UpdatePasswordDto;
import com.evgenii.my_market.dto.UserDto;
import com.evgenii.my_market.dto.UserEmailDto;
import com.evgenii.my_market.dto.UserNameDto;
import com.evgenii.my_market.exception_handling.MarketError;
import com.evgenii.my_market.exception_handling.ResourceNotFoundException;
import com.evgenii.my_market.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserDto newUsers) {
        if (userService.findByUsernameAndEmail(newUsers.getFirstName(), newUsers.getEmail()).size() > 0) {
            return new ResponseEntity<>(new MarketError(HttpStatus.CONFLICT.value(), "This username or email already exist"), HttpStatus.CONFLICT);
        } else {
            userService.save(newUsers);
            return ResponseEntity.ok(HttpStatus.CREATED);
        }
    }

    @GetMapping
    public UserDto getCurrentUser(Principal principal) {
        UserDto user = userService.findUserDtoByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException("Unable to find user with name: " + principal.getName()));
        return user;
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDto user, Principal principal) {
       return  userService.updateUser(user, principal.getName());
    }

    @PostMapping("/password")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody UpdatePasswordDto passwordDto,
                                            Principal principal) {
        return userService.updatePassword(passwordDto, principal.getName());
    }

    @PostMapping("/name")
    public ResponseEntity<?> checkUserName(@Valid @RequestBody UserNameDto name) {
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }
    @PostMapping("/email")
    public ResponseEntity<?> checkUserEmail(@Valid @RequestBody UserEmailDto email) {
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }


}

