package com.evgenii.my_market.services;

import com.evgenii.my_market.dao.UserDAO;

import com.evgenii.my_market.entity.Role;
import com.evgenii.my_market.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
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
public class UserService implements UserDetailsService{
    private final UserDAO userDAO;
/*    private final RoleRepository roleRepository;*//*
*/
/*
*//*
*/
    private final BCryptPasswordEncoder passwordEncoder;




    public Optional<Users> findByUsername(String username) {
        return userDAO.findByUsername(username);
    }
/*public Optional<User> findByUsernameAndEmail(String name,String email) {
        return userRepository.findByUsernameAndEmail(name,email);
    }*/


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        Collection<Role> roleCollection = new ArrayList<>();
        roleCollection.add(user.getRole());
        return new org.springframework.security.core.userdetails.User(user.getFirstName(), user.getPassword(), mapRolesToAuthorities(roleCollection));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
    }

    public List<Users> getAll() {
        return userDAO.getAllUsers();
    }

    public void save(Users newUsers) {
        newUsers.setPassword(passwordEncoder.encode(newUsers.getPassword()));
        userDAO.saveUser(newUsers);
    }

/*public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        String roleName = "ROLE_USER";
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new ResourceNotFoundException("Unable to find role " + roleName + " add to user"));
        user.setRoles(List.of(role));
        userRepository.save(user);
    }*/

}
