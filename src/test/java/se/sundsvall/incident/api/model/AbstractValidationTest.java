package se.sundsvall.incident.api.model;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;

abstract class AbstractValidationTest {

    protected Validator validator;

    @BeforeEach
    void setUp() {

        validator = Validation.buildDefaultValidatorFactory().getValidator();

    }
}

