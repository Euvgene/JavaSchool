package com.evgenii.my_market.service;

import com.evgenii.my_market.dao.api.UserDAO;
import com.evgenii.my_market.dto.UpdatePasswordDto;
import com.evgenii.my_market.dto.UserDto;
import com.evgenii.my_market.entity.Role;
import com.evgenii.my_market.entity.User;
import com.evgenii.my_market.exception_handling.MarketError;
import com.evgenii.my_market.service.api.ProductService;
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

/**
 * Implementation of {@link ProductService} interface.
 *
 * @author Boznyakov Evgenii
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    /**
     * Creates an instance of this class using constructor-based dependency injection.
     */
    private final UserDAO userDAO;
    private final BCryptPasswordEncoder passwordEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * Find user by user name
     *
     * @param username user name
     * @return optional of  {@linkplain com.evgenii.my_market.entity.User User}
     */
    public Optional<User> findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    /**
     * Find user by user name and email
     *
     * @param username user name
     * @param email    user email
     * @return list of  {@linkplain com.evgenii.my_market.entity.User User}
     */
    public List<User> findByUsernameAndEmail(String username, String email) {
        return userDAO.findByUsernameAndEmail(username, email);
    }

    /**
     * Locates the user based on the username.
     *
     * @param username – the username identifying the user whose data is required.
     * @return a fully populated user record (never null)
     * @throws UsernameNotFoundException – if the user could not be found or the user has no GrantedAuthority
     */
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

    /**
     * Save new user
     *
     * @param newUser {@linkplain com.evgenii.my_market.dto.UserDto}
     */
    @Transactional
    public void save(UserDto newUser) {
        User user = new User(newUser);
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userDAO.saveUser(user);
    }

    /**
     * Check password and update user info if password is valid.
     *
     * @param changedUser {@linkplain com.evgenii.my_market.dto.UserDto}
     * @param oldName     old name of user
     * @return {@linkplain org.springframework.http.ResponseEntity}
     */
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
            LOGGER.info("User {} update user info", user.getFirstName());
            return ResponseEntity.ok(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new MarketError(HttpStatus.CONFLICT.value(), "Wrong password"), HttpStatus.CONFLICT);
        }

    }

    /**
     * Update password if old password is valid.
     *
     * @param passwordDto {@linkplain com.evgenii.my_market.dto.UpdatePasswordDto}
     * @param userName    name of user
     * @return {@linkplain org.springframework.http.ResponseEntity}
     */
    @Transactional
    public ResponseEntity<?> updatePassword(UpdatePasswordDto passwordDto, String userName) {
        User user = userDAO.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", userName)));
        if (passwordEncoder.matches(passwordDto.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(passwordDto.getFirstPassword()));
            userDAO.update(user);
            LOGGER.info("User {} update password", user.getFirstName());
            return ResponseEntity.ok(HttpStatus.ACCEPTED);
        } else
            return new ResponseEntity<>(new MarketError(HttpStatus.CONFLICT.value(), "Wrong old password"), HttpStatus.CONFLICT);
    }

    /**
     * Check if email exist in database
     *
     * @param email email to check
     */
    @Transactional
    public boolean isEmailAlreadyInUse(String email) {
        boolean userInDb = true;
        if (userDAO.getActiveEmail(email) == null) {
            userInDb = false;
        }
        return userInDb;
    }

    /**
     * Check if username exist in database
     *
     * @param name name too check
     */
    @Transactional
    public boolean isNameAlreadyInUse(String name) {
        boolean userInDb = true;
        if (userDAO.getActiveName(name) == null) {
            userInDb = false;
        }
        return userInDb;
    }
}
