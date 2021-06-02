package com.evgenii.my_market.service;

import com.evgenii.my_market.dao.UserDAOImpl;

import com.evgenii.my_market.dao.api.UserDAO;
import com.evgenii.my_market.dto.UpdatePasswordDto;
import com.evgenii.my_market.dto.UserDto;
import com.evgenii.my_market.entity.Role;
import com.evgenii.my_market.entity.User;
import com.evgenii.my_market.exception_handling.MarketError;
import com.evgenii.my_market.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public Optional<User> findByUsername(String username) {
        return userDAO.findByUsername(username);
    }


    public List<User> findByUsernameAndEmail(String username, String email) {
        return userDAO.findByUsernameAndEmail(username, email);
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        Collection<Role> roleCollection = new ArrayList<>();
        roleCollection.add(user.getRole());
        return new org.springframework.security.core.userdetails.User(user.getFirstName(), user.getPassword(), mapRolesToAuthorities(roleCollection));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(UserDto newUser) {
        User user = new User(newUser);
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userDAO.saveUser(user);
    }

    @Transactional
    public ResponseEntity<?> updateUser(UserDto changedUser, String oldName) {
        User oldUser = userDAO.findByUsername(oldName).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", oldName)));
        if (passwordEncoder.matches(changedUser.getPassword(), oldUser.getPassword())) {
            changedUser.getUserAddress().setAddressId(oldUser.getUserAddress().getAddressId());
            User user = new User(changedUser);
            user.setPassword(oldUser.getPassword());
            user.setUserId(oldUser.getUserId());
            user.setRole(oldUser.getRole());
            userDAO.update(user);
            LOGGER.info("User " + user.getFirstName() + " update user info");
            return ResponseEntity.ok(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new MarketError(HttpStatus.CONFLICT.value(), "Wrong password"), HttpStatus.CONFLICT);
        }

    }


    @Transactional
    public ResponseEntity<?> updatePassword(UpdatePasswordDto passwordDto, String userName) {
        User user = userDAO.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", userName)));
        if (passwordEncoder.matches(passwordDto.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(passwordDto.getFirstPassword()));
            userDAO.update(user);
            LOGGER.info("User " + user.getFirstName() + " update password");
            return ResponseEntity.ok(HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>(new MarketError(HttpStatus.CONFLICT.value(), "Wrong old password"), HttpStatus.CONFLICT);

    }

    @Transactional
    public boolean isEmailAlreadyInUse(String email) {
        boolean userInDb = true;
        if (userDAO.getActiveEmail(email) == null) userInDb = false;
        return userInDb;
    }

    @Transactional
    public boolean isNameAlreadyInUse(String name) {
        boolean userInDb = true;
        if (userDAO.getActiveName(name) == null) userInDb = false;
        return userInDb;
    }
}
