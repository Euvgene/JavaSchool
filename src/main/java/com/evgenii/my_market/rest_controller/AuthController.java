package com.evgenii.my_market.rest_controller;

import com.evgenii.my_market.config.JwtTokenUtil;
import com.evgenii.my_market.dto.JwtRequest;
import com.evgenii.my_market.dto.JwtResponse;
import com.evgenii.my_market.exception_handling.MarketError;
import com.evgenii.my_market.service.CartServiceImpl;
import com.evgenii.my_market.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class AuthController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final CartServiceImpl cartService;
    private final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (AuthenticationException ex) {
            return new ResponseEntity<>(new MarketError(HttpStatus.UNAUTHORIZED.value(), "Incorrect username or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        if (!userDetails.getAuthorities().toString().equals("[ROLE_ADMIN]") && !userDetails.getAuthorities().toString().equals("[ROLE_GUEST]")) {
            cartService.getCartForUser(authRequest.getUsername(), authRequest.getCartId());
        }
        LOGGER.info("User " + authRequest.getUsername() + " auth");
        return ResponseEntity.ok(new JwtResponse(token, userDetails.getAuthorities().toString()));
    }
}
