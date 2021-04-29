package com.evgenii.my_market.rest_controllers;

import com.evgenii.my_market.dto.CartDto;
import com.evgenii.my_market.dto.UpdatePasswordDto;
import com.evgenii.my_market.dto.UserDto;
import com.evgenii.my_market.entity.Cart;
import com.evgenii.my_market.entity.User;
import com.evgenii.my_market.exception_handling.MarketError;
import com.evgenii.my_market.exception_handling.ResourceNotFoundException;
import com.evgenii.my_market.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody User newUsers) {
        if (userService.findByUsernameAndEmail(newUsers.getFirstName(), newUsers.getEmail()).size() > 0) {
            return new ResponseEntity<>(new MarketError(HttpStatus.CONFLICT.value(), "This user already exist"), HttpStatus.CONFLICT);
        } else {
            userService.save(newUsers);
            return ResponseEntity.ok(HttpStatus.CREATED);
        }
    }

    @GetMapping
    public UserDto getCurrentCart(Principal principal) {
        UserDto user = userService.findUserDtoByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException("Unable to find user with name: " + principal.getName()));
        return user;
    }

    @PutMapping
    public void updateUser(@RequestBody UserDto user, Principal principal) {
        userService.updateUser(user, principal.getName());
    }

    @PostMapping("/password")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordDto passwordDto,
                                            Principal principal) {
        if (userService.updatePassword(passwordDto, principal.getName()).equals("ok")) {
            return ResponseEntity.ok(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(new MarketError(HttpStatus.CONFLICT.value(), "Wrong password"), HttpStatus.CONFLICT);
        }
    }
}

