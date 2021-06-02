package com.evgenii.my_market.service.api;

import com.evgenii.my_market.dto.UpdatePasswordDto;
import com.evgenii.my_market.dto.UserDto;
import com.evgenii.my_market.entity.Role;
import com.evgenii.my_market.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    Optional<User> findByUsername(String username);


    List<User> findByUsernameAndEmail(String username, String email);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    void save(UserDto newUser);

    ResponseEntity<?> updateUser(UserDto changedUser, String oldName);

    public ResponseEntity<?> updatePassword(UpdatePasswordDto passwordDto, String userName);

    public boolean isEmailAlreadyInUse(String email);

    public boolean isNameAlreadyInUse(String name);
}
