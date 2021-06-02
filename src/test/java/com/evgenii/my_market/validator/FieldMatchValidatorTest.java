package com.evgenii.my_market.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FieldMatchValidatorTest {
    private static final String VALUE = "12345678";
    @Mock
    private ConstraintValidatorContext context;
    @InjectMocks
    private FieldMatchValidator tested;

    FieldMatchValidator fieldMatch = new FieldMatchValidator();


    @BeforeEach
    void setup() {
//        tested.initialize(new Object()); TODO how to mock it??
    }

    @Test
    void isValid() {

    }
}