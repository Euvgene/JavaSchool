package com.evgenii.my_market.rest_controller;

import com.evgenii.my_market.config.SpringConfig;
import com.evgenii.my_market.dto.UpdatePasswordDto;
import com.evgenii.my_market.dto.UserDto;
import com.evgenii.my_market.dto.UserEmailDto;
import com.evgenii.my_market.dto.UserNameDto;
import com.evgenii.my_market.entity.Address;
import com.evgenii.my_market.entity.Role;
import com.evgenii.my_market.entity.User;
import com.evgenii.my_market.service.api.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
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
    private static final String USER_ROLE = "ROLE_USER";
    private static final long USER_ROLE_ID = 1L;
    private static final String ADMIN_ROLE = "ROLE_ADMIN";
    private static final String USER_PASSWORD = "12345678";
    private static final String NEW_PASSWORD = "11111111";
    private static final String NEW_PASSWORD_NOT_MATCH = "22222222";
    private static final String BIRTH_DATE = "1990-11-11";
    private static final String CITY = "Sin";
    private static final String STREET_NAME = "Nevskii";
    private static final String COUNTRY_NAME = "Russia";
    private static final byte FLAT_NUMBER = 1;
    private static final int HOUSE_NUMBER = 1;
    private static final String ZIP = "666666";
    private static final long ADDRESS_ID = 999L;

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

        assertEquals(HttpStatus.OK, testResponse.getStatusCode());
    }

    @SneakyThrows
    @Test
    void saveUserAlreadyExist() {
        UserDto userDto = creatUserDto(USER_ROLE);

        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(userDto);

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isConflict());
    }

    @SneakyThrows
    @Test
    void saveUserInvalidUser() {
        UserDto userDto = creatUserDto(ADMIN_ROLE);

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

        assertEquals(expectedUser.getFirstName(), testUser.getFirstName());

    }

    @Test
    void updateUser() {
        UserDto userDto = new UserDto();

        when(userService.updateUser(userDto, USER_NAME)).thenReturn(RESPONSE_ENTITY_ACCEPTED);

        ResponseEntity<?> test = tested.updateUser(userDto, () -> USER_NAME);

        assertEquals(RESPONSE_ENTITY_ACCEPTED.getStatusCode(), test.getStatusCode());
    }

    @Test
    void updatePasswordSuccess() {
        UpdatePasswordDto passwordDto = new UpdatePasswordDto();

        when(userService.updatePassword(passwordDto, USER_NAME)).thenReturn(RESPONSE_ENTITY_ACCEPTED);

        ResponseEntity<?> test = tested.updatePassword(passwordDto, () -> USER_NAME);

        assertEquals(RESPONSE_ENTITY_ACCEPTED.getStatusCode(), test.getStatusCode());
    }

    @SneakyThrows
    @Test
    void updatePasswordInValidPassword() {
        UpdatePasswordDto passwordDto = new UpdatePasswordDto();
        passwordDto.setOldPassword(USER_PASSWORD);
        passwordDto.setFirstPassword(NEW_PASSWORD);
        passwordDto.setSecondPassword(NEW_PASSWORD_NOT_MATCH);

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


    private UserDto creatUserDto(String role) throws ParseException {
        UserDto userDto = new UserDto();
        Address address = creatAddress();
        Role userRole = new Role();
        userRole.setId(USER_ROLE_ID);
        userRole.setRoleName(role);

        userDto.setFirstName(USER_NAME);
        userDto.setLastName(USER_NAME);
        userDto.setEmail(USER_EMAIL);
        userDto.setPassword(USER_PASSWORD);
        userDto.setRole(userRole);
        userDto.setUserAddress(address);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        Date date = simpleDateFormat.parse(BIRTH_DATE);

        userDto.setBirthday(date);
        return userDto;
    }

    private Address creatAddress() {
        Address address = new Address();
        address.setAddressId(ADDRESS_ID);
        address.setCity(CITY);
        address.setStreetName(STREET_NAME);
        address.setCountry(COUNTRY_NAME);
        address.setFlatNumber(FLAT_NUMBER);
        address.setHouseNumber(HOUSE_NUMBER);
        address.setPostalCode(ZIP);
        return address;
    }

}