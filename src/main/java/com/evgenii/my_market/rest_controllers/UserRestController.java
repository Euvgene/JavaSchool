package com.evgenii.my_market.rest_controllers;

import com.evgenii.my_market.entity.User;
import com.evgenii.my_market.exception_handling.MarketError;
import com.evgenii.my_market.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    @GetMapping()
    public List<User> pagination() {
        return userService.getAll();
    }

    @PostMapping
    public ResponseEntity<?> saveProduct(@RequestBody User newUsers) {
        if (userService.findByUsernameAndEmail(newUsers.getFirstName(), newUsers.getEmail()).size() > 0) {
            return new ResponseEntity<>(new MarketError(HttpStatus.CONFLICT.value(), "This user already exist"), HttpStatus.CONFLICT);
        } else {
            userService.save(newUsers);
            return ResponseEntity.ok(HttpStatus.CREATED);
        }
    }
}
