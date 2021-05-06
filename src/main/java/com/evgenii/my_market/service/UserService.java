package com.evgenii.my_market.service;

import com.evgenii.my_market.dao.UserDAO;

import com.evgenii.my_market.dto.UpdatePasswordDto;
import com.evgenii.my_market.dto.UserDto;
import com.evgenii.my_market.entity.Role;
import com.evgenii.my_market.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
public class UserService implements UserDetailsService {
    private final UserDAO userDAO;

    private final BCryptPasswordEncoder passwordEncoder;

    public Optional<User> findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    public Optional<UserDto> findUserDtoByUsername(String username) {
        return userDAO.findByUsername(username).map(UserDto::new);
    }


    public List<User> findByUsernameAndEmail(String username, String email) {
        return userDAO.findByUsernameAndEmail(username, email);
    }

    @Override
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

    public List<User> getAll() {
        return userDAO.getAllUsers();
    }

    @Transactional
    public void save(User newUser) {
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userDAO.saveUser(newUser);
    }

    @Transactional
    public void updateUser(UserDto changedUser, String oldName) {
        User oldUser = userDAO.findByUsername(oldName).get();
        changedUser.getUserAddress().setAddressId(oldUser.getUserAddress().getAddressId());
        User user = new User(changedUser);
        user.setPassword(oldUser.getPassword());
        user.setUserId(oldUser.getUserId());
        user.setRole(oldUser.getRole());


        userDAO.update(user);
    }


    @Transactional
    public String updatePassword(UpdatePasswordDto passwordDto, String userName) {
        User user = userDAO.findByUsername( userName).get();
        if(passwordEncoder.matches(passwordDto.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
            userDAO.update(user);
            return "ok";
        }else return "error";

    }
}
