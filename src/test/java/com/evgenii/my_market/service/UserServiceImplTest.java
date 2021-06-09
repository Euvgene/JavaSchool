package com.evgenii.my_market.service;

import com.evgenii.my_market.dao.api.UserDAO;
import com.evgenii.my_market.dto.UpdatePasswordDto;
import com.evgenii.my_market.dto.UserDto;
import com.evgenii.my_market.entity.Address;
import com.evgenii.my_market.entity.Role;
import com.evgenii.my_market.entity.User;
import com.evgenii.my_market.exception_handling.MarketError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private static final String USER_NAME = "Bob";
    private static final int USER_ID = 1;
    private static final long ROLE_ID = 1l;
    private static final Long ADDRESS_ID = 1l;
    private static final String OLD_USER_NAME = "JACK";
    private static final String ROLE_NAME = "ROLE_USER";
    private static final String USER_EMAIL = "some@mail.com";
    private static final String USER_PASSWORD = "100";
    private static final int INDEX_OF_FIRST_ITEM = 0;
    private static final ResponseEntity<?> RESPONSE_ENTITY_ACCEPTED = ResponseEntity.ok(HttpStatus.ACCEPTED);
    private static final ResponseEntity<?> RESPONSE_ENTITY_CONFLICT = new ResponseEntity<>(new MarketError(HttpStatus.CONFLICT.value(),
            "Some message"), HttpStatus.CONFLICT);


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

        assertEquals(user.getFirstName(), testUser.getFirstName());
    }


    @Test
    void findByUsernameAndEmail() {
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setFirstName(USER_NAME);
        user.setEmail(USER_EMAIL);
        userList.add(user);
        when(userDAO.findByUsernameAndEmail(USER_NAME, USER_EMAIL)).thenReturn(userList);

        User testUser = tested.findByUsernameAndEmail(USER_NAME, USER_EMAIL).get(INDEX_OF_FIRST_ITEM);

        assertEquals(user.getFirstName(), testUser.getFirstName());
        assertEquals(user.getEmail(), testUser.getEmail());
    }

    @Test
    void loadUserByUsername() {
        User user = new User();
        user.setPassword(USER_PASSWORD);
        user.setFirstName(USER_NAME);
        Role roles = new Role();
        roles.setId(ROLE_ID);
        roles.setRoleName(ROLE_NAME);
        user.setRole(roles);

        Collection<Role> roleCollection = new ArrayList<>();
        roleCollection.add(user.getRole());
        Collection<? extends GrantedAuthority> grantedAuthorities = roleCollection.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());

        when(userDAO.findByUsername(USER_NAME)).thenReturn(Optional.of(user));

        UserDetails testUser = tested.loadUserByUsername(USER_NAME);

        assertEquals(user.getFirstName(), testUser.getUsername());
        assertEquals(user.getPassword(), testUser.getPassword());
        assertEquals(testUser.getAuthorities().iterator().next().getAuthority(), grantedAuthorities.iterator().next().getAuthority());
    }

    @Test
    void save() {
        UserDto userDto = new UserDto();
        userDto.setFirstName(USER_NAME);

        User user = new User(userDto);

        doNothing().when(userDAO).saveUser(user);

        tested.save(userDto);

        assertEquals(USER_NAME, user.getFirstName());
    }

    @Test
    void updateUserTrue() {
        updateUser(true, RESPONSE_ENTITY_ACCEPTED);
    }

    @Test
    void updateUserFalse() {
        updateUser(false, RESPONSE_ENTITY_CONFLICT);
    }

    @Test
    void updatePasswordTrue() {
        updatePassword(true, RESPONSE_ENTITY_ACCEPTED);
    }

    @Test
    void updatePasswordFalse() {
        updatePassword(false, RESPONSE_ENTITY_CONFLICT);
    }


    @Test
    void isEmailAlreadyInUseTrue() {
        User user = new User();
        isEmailAlreadyInUse(user, true);
    }


    @Test
    void isEmailAlreadyInUseFalse() {
        isEmailAlreadyInUse(null, false);
    }

    @Test
    void isNameAlreadyInUseTrue() {
        User user = new User();
        isNameAlreadyInUse(user, true);
    }

    @Test
    void isNameAlreadyInUseFalse() {
        isNameAlreadyInUse(null, false);
    }

    private void isEmailAlreadyInUse(User user, boolean b) {
        when(userDAO.getActiveEmail(USER_EMAIL)).thenReturn(user);

        boolean exist = tested.isEmailAlreadyInUse(USER_EMAIL);

        assertEquals(b, exist);

    }

    private void isNameAlreadyInUse(User user, boolean b) {
        when(userDAO.getActiveName(USER_NAME)).thenReturn(user);

        boolean exist = tested.isNameAlreadyInUse(USER_NAME);

        assertEquals(b, exist);

    }

    private void updatePassword(boolean b, ResponseEntity<?> responseEntity) {
        UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto();
        updatePasswordDto.setFirstPassword(USER_PASSWORD);
        User user = new User();
        user.setPassword(USER_PASSWORD);

        when(userDAO.findByUsername(USER_NAME)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(any(), any())).thenReturn(b);

        ResponseEntity<?> testResponseEntity = tested.updatePassword(updatePasswordDto, USER_NAME);
        assertEquals(responseEntity.getStatusCode(), testResponseEntity.getStatusCode());
    }

    private void updateUser(boolean b, ResponseEntity<?> responseEntity) {
        User user = new User();
        user.setUserId(USER_ID);
        Address address = new Address();
        address.setAddressId(ADDRESS_ID);
        user.setUserAddress(address);
        user.setPassword(USER_PASSWORD);
        Role role = new Role();
        role.setId(ROLE_ID);
        role.setRoleName(ROLE_NAME);
        user.setRole(role);

        UserDto userDto = new UserDto(user);

        when(userDAO.findByUsername(OLD_USER_NAME)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(any(), any())).thenReturn(b);

        ResponseEntity<?> testResponseEntity = tested.updateUser(userDto, OLD_USER_NAME);
        assertEquals(responseEntity.getStatusCode(), testResponseEntity.getStatusCode());
    }
}