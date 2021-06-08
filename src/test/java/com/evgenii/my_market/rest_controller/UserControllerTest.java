package com.evgenii.my_market.rest_controller;

import com.evgenii.my_market.config.SpringConfig;
import com.evgenii.my_market.dto.UpdatePasswordDto;
import com.evgenii.my_market.dto.UserDto;
import com.evgenii.my_market.dto.UserEmailDto;
import com.evgenii.my_market.dto.UserNameDto;
import com.evgenii.my_market.entity.Address;
import com.evgenii.my_market.entity.Role;
import com.evgenii.my_market.entity.User;
import com.evgenii.my_market.exception_handling.MarketError;
import com.evgenii.my_market.service.api.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringConfig.class})
class UserControllerTest {

    private static final String USER_NAME = "Evgenii";
    private static final String NEW_USER_NAME = "Evgenii123";
    private static final String USER_EMAIL = "Deny@mail.com";
    private static final String NEW_USER_EMAIL = "someemail@mail.com";
    private static final ResponseEntity RESPONSE_ENTITY_ACCEPTED = ResponseEntity.ok(HttpStatus.ACCEPTED);

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @Mock
    UserService userService;

    private UserController tested;

    @BeforeEach
    public void setup() {
        tested = new UserController(userService);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void saveUserSuccess() {
        UserDto userDto = new UserDto();
        userDto.setFirstName(USER_NAME);
        userDto.setEmail(USER_EMAIL);
        List<User> users = new ArrayList<>();

        when(userService.findByUsernameAndEmail(USER_NAME, USER_EMAIL)).thenReturn(users);
        doNothing().when(userService).save(userDto);

        ResponseEntity<?> testResponse = tested.saveUser(userDto);

        assertEquals(testResponse.getStatusCode(), HttpStatus.OK);
    }

    @SneakyThrows
    @Test
    void saveUserAlreadyExist() {
        UserDto userDto = creatUserDto("ROLE_USER");

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(userDto);

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isConflict());
    }

    @SneakyThrows
    @Test
    void saveUserInvalidUser() {
        UserDto userDto = creatUserDto("ROLE_ADMIN");

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(userDto);

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getCurrentUser() {
        User expectedUser = new User();
        expectedUser.setFirstName(USER_NAME);

        when(userService.findByUsername(USER_NAME)).thenReturn(Optional.of(expectedUser));

        UserDto testUser = tested.getCurrentUser(() -> USER_NAME);

        assertEquals(testUser.getFirstName(), expectedUser.getFirstName());

    }

    @Test
    void updateUser() {
        UserDto userDto = new UserDto();

        when(userService.updateUser(userDto, USER_NAME)).thenReturn(RESPONSE_ENTITY_ACCEPTED);

        ResponseEntity<?> test = tested.updateUser(userDto, () -> USER_NAME);

        assertEquals(test.getStatusCode(), RESPONSE_ENTITY_ACCEPTED.getStatusCode());
    }

    @Test
    void updatePasswordSuccess() {
        UpdatePasswordDto passwordDto = new UpdatePasswordDto();

        when(userService.updatePassword(passwordDto, USER_NAME)).thenReturn(RESPONSE_ENTITY_ACCEPTED);

        ResponseEntity<?> test = tested.updatePassword(passwordDto, () -> USER_NAME);

        assertEquals(test.getStatusCode(), RESPONSE_ENTITY_ACCEPTED.getStatusCode());
    }

    @SneakyThrows
    @Test
    void updatePasswordInValidPassword() {
        UpdatePasswordDto passwordDto = new UpdatePasswordDto();
        passwordDto.setOldPassword("12345678");
        passwordDto.setFirstPassword("12345678");
        passwordDto.setSecondPassword("12369985");

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(passwordDto);

        mockMvc.perform(post("/api/v1/users/password")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest());
    }

    @SneakyThrows
    @Test
    void checkUserNameExist() {
        UserNameDto userNameDto = new UserNameDto();
        userNameDto.setValue(USER_NAME);

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(userNameDto);

        mockMvc.perform(post("/api/v1/users/name")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest());
    }

    @SneakyThrows
    @Test
    void checkUserNameNotExist() {
        UserNameDto userNameDto = new UserNameDto();
        userNameDto.setValue(NEW_USER_NAME);

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(userNameDto);

        mockMvc.perform(post("/api/v1/users/name")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void checkUserEmailExist() {

        UserEmailDto userEmailDto = new UserEmailDto();
        userEmailDto.setValue(USER_EMAIL);

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(userEmailDto);

        mockMvc.perform(post("/api/v1/users/email")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest());

    }

    @SneakyThrows
    @Test
    void checkUserEmailNotExist() {

        UserEmailDto userEmailDto = new UserEmailDto();
        userEmailDto.setValue(NEW_USER_EMAIL);

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(userEmailDto);

        mockMvc.perform(post("/api/v1/users/email")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());

    }

    @SneakyThrows
    private UserDto creatUserDto(String role) {
        UserDto userDto = new UserDto();
        Address address = creatAddress();
        Role userRole = new Role();
        userRole.setId(1L);
        userRole.setRoleName(role);

        userDto.setFirstName(USER_NAME);
        userDto.setLastName(USER_NAME);
        userDto.setEmail(USER_EMAIL);
        userDto.setPassword("12345678");
        userDto.setRole(userRole);
        userDto.setUserAddress(address);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        Date date = simpleDateFormat.parse("2018-09-09");

        userDto.setBirthday(date);
        return userDto;
    }

    private Address creatAddress() {
        Address address = new Address();
        address.setAddressId(999L);
        address.setCity("Sin");
        address.setStreetName("Nevskii");
        address.setCountry("country");
        address.setFlatNumber((byte) 1);
        address.setHouseNumber(1);
        address.setPostalCode("1234567");
        return address;
    }
}