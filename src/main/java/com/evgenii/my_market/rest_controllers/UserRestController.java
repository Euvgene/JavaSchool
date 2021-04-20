package com.evgenii.my_market.rest_controllers;

import com.evgenii.my_market.entity.Category;
import com.evgenii.my_market.entity.Product;
import com.evgenii.my_market.entity.Users;
import com.evgenii.my_market.services.CategoryService;
import com.evgenii.my_market.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    @GetMapping()
    public List<Users> pagination() {
        return userService.getAll();
    }

    @PostMapping
    /*  @ResponseStatus(HttpStatus.CREATED)*/
    public void saveProduct(@RequestBody Users newUsers) {
        userService.save(newUsers);
    }
}
