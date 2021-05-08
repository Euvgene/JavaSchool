package com.evgenii.my_market.rest_controller;

import com.evgenii.my_market.config.JwtTokenUtil;
import com.evgenii.my_market.dto.UpdatePasswordDto;
import com.evgenii.my_market.dto.UserDto;
import com.evgenii.my_market.dto.UserEmailDto;
import com.evgenii.my_market.dto.UserNameDto;
import com.evgenii.my_market.exception_handling.MarketError;
import com.evgenii.my_market.exception_handling.ResourceNotFoundException;
import com.evgenii.my_market.service.UserService;
import com.evgenii.my_market.validator.UniqueName;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

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
        System.out.println(principal.getName() + "_!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        UserDto user = userService.findUserDtoByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException("Unable to find user with name: " + principal.getName()));
        return user;
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDto user, Principal principal) {
    /*    if (userService.findByUsernameAndEmail(user.getFirstName(), user.getEmail()).size() > 0 ) {
            return new ResponseEntity<>(new MarketError(HttpStatus.CONFLICT.value(), "This username or email already exist"), HttpStatus.CONFLICT);
        } else {*/
        System.out.println(principal.getName() + "__________________________");
        userService.updateUser(user, principal.getName());
        return ResponseEntity.ok(HttpStatus.CREATED);


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

    @PostMapping("/name")
    public ResponseEntity<?> checkUserName(@Valid @RequestBody UserNameDto name) {
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }
    @PostMapping("/email")
    public ResponseEntity<?> checkUserEmail(@Valid @RequestBody UserEmailDto email) {
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }


}

