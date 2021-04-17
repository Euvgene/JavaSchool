package com.evgenii.my_market.rest_controllers;

import com.evgenii.my_market.beans.JwtTokenUtil;
import com.evgenii.my_market.dto.JwtRequest;
import com.evgenii.my_market.dto.JwtResponse;
import com.evgenii.my_market.exception_handling.MarketError;
import com.evgenii.my_market.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
/*    private final CartService cartService;*/


    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(new MarketError(HttpStatus.UNAUTHORIZED.value(), "Incorrect username or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        System.out.println(userDetails.getAuthorities());
        String token = jwtTokenUtil.generateToken(userDetails);
/*  cartService.getCartForUser(authRequest.getUsername(),authRequest.getCartId() );*/


        return ResponseEntity.ok(new JwtResponse(token,userDetails.getAuthorities().toString()));
    }
}
