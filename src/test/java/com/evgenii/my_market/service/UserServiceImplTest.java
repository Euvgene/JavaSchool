package com.evgenii.my_market.service;

import com.evgenii.my_market.dao.api.UserDAO;
import com.evgenii.my_market.entity.Role;
import com.evgenii.my_market.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private static final String USER_NAME = "Bob";
    private static final String USER_EMAIL = "some@mail.com";
    private static final String USER_PASSWORD = "100";


    @Mock
    private UserDAO userDAO;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private UserServiceImpl tested;

    @BeforeEach
    void setup() {
        tested = new UserServiceImpl(userDAO, passwordEncoder);
    }


    @Test
    void findByUsername() {
        User user = new User();
        user.setFirstName(USER_NAME);

        when(userDAO.findByUsername(USER_NAME)).thenReturn(Optional.of(user));

        User testUser = tested.findByUsername(USER_NAME).get();

        assertEquals(testUser.getFirstName(), user.getFirstName());
    }


    @Test
    void findByUsernameAndEmail() {
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setFirstName(USER_NAME);
        user.setEmail(USER_EMAIL);
        userList.add(user);
        when(userDAO.findByUsernameAndEmail(USER_NAME, USER_EMAIL)).thenReturn(userList);

        User testUser = tested.findByUsernameAndEmail(USER_NAME, USER_EMAIL).get(0);

        assertEquals(testUser.getFirstName(), user.getFirstName());
        assertEquals(testUser.getEmail(), user.getEmail());
    }

    @Test
    void loadUserByUsername() {
        User user = new User();
        user.setPassword(USER_PASSWORD);
        user.setFirstName(USER_NAME);
        Role roles = new Role();
        roles.setId(1L);
        roles.setRoleName("USER");
        user.setRole(roles);

        Collection<Role> roleCollection = new ArrayList<>();
        roleCollection.add(user.getRole());
        Collection<? extends GrantedAuthority> grantedAuthorities = roleCollection.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());

        when(userDAO.findByUsername(USER_NAME)).thenReturn(Optional.of(user));

        UserDetails testUser = tested.loadUserByUsername(USER_NAME);

        assertEquals(testUser.getUsername(), user.getFirstName());
        assertEquals(testUser.getPassword(), user.getPassword());
        assertEquals(testUser.getAuthorities().iterator().next().getAuthority(),grantedAuthorities.iterator().next().getAuthority());
    }

    @Test
    void save() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void updatePassword() {
    }

    @Test
    void isEmailAlreadyInUse() {
    }

    @Test
    void isNameAlreadyInUse() {
    }
}