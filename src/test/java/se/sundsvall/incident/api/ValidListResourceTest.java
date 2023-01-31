package se.sundsvall.incident.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import se.sundsvall.incident.dto.Category;
import se.sundsvall.incident.dto.Status;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ValidListResourceTest {


    private ValidListResource validListResource;

    @BeforeEach
    void setUp() {
        this.validListResource = new ValidListResource();
    }

    @Test
    void getValidstatuses() {

        var response = validListResource.getValidstatuses();
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(Status.values().length);
        response.getBody()
                .forEach(validStatusResponse -> assertThat(validStatusResponse.getStatus())
                        .isEqualTo(Status.forValue(validStatusResponse.getSTATUS_ID())
                                .getLabel()));


    }

    @Test
    void getValidCategories() {

        var response = validListResource.getValidCategories();
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(Category.values().length);
        response.getBody()
                .forEach(validCategoryResponse -> assertThat(validCategoryResponse.getCategory())
                        .isEqualTo(Category.forValue(validCategoryResponse.getCATEGORY_ID())
                                .getLabel()));


    }

    @Test
    void getValidOepCategories() {

        var response = validListResource.getValidOepCategories();
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).hasSize(Category.values().length);
        response.getBody().forEach(
                validOepCategoryResponse -> assertThat(validOepCategoryResponse.getValue())
                        .isEqualTo(Category.forValue(Integer.parseInt(validOepCategoryResponse.getKey()))
                                .getLabel())
        );


    }
}