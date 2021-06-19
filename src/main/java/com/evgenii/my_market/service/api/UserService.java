package com.evgenii.my_market.service.api;

import com.evgenii.my_market.dto.UpdatePasswordDto;
import com.evgenii.my_market.dto.UserDto;
import com.evgenii.my_market.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * Interface, containing list of required business-logic methods regarding
 * {@linkplain com.evgenii.my_market.entity.User User} entity.
 *
 * @author Boznyakov Evgenii
 */
public interface UserService extends UserDetailsService {

    /**
     * Find user by user name
     *
     * @param username user name
     * @return optional of  {@linkplain com.evgenii.my_market.entity.User User}
     */
    Optional<User> findByUsername(String username);

    /**
     * Find user by user name and email
     *
     * @param username user name
     * @param email    user email
     * @return list of  {@linkplain com.evgenii.my_market.entity.User User}
     */
    List<User> findByUsernameAndEmail(String username, String email);

    /**
     * Locates the user based on the username.
     *
     * @param username – the username identifying the user whose data is required.
     * @return a fully populated user record (never null)
     * @throws UsernameNotFoundException – if the user could not be found or the user has no GrantedAuthority
     */
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    /**
     * Save new user
     *
     * @param newUser {@linkplain com.evgenii.my_market.dto.UserDto}
     */
    void save(UserDto newUser);

    /**
     * Update user
     *
     * @param changedUser {@linkplain com.evgenii.my_market.dto.UserDto}
     * @param oldName     old name of user
     * @return {@linkplain org.springframework.http.ResponseEntity}
     */
    ResponseEntity<?> updateUser(UserDto changedUser, String oldName);

    /**
     * Update password
     *
     * @param passwordDto {@linkplain com.evgenii.my_market.dto.UpdatePasswordDto}
     * @param userName    name of user
     * @return {@linkplain org.springframework.http.ResponseEntity}
     */
    ResponseEntity<?> updatePassword(UpdatePasswordDto passwordDto, String userName);

    /**
     * Check if email exist in database
     *
     * @param email email to check
     */
    boolean isEmailAlreadyInUse(String email);

    /**
     * Check if username exist in database
     *
     * @param name name too check
     */
    boolean isNameAlreadyInUse(String name);
}
