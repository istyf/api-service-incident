package se.sundsvall.incident.integration.lifebuoy.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class RequestWrapperTest {

    @Test
    void testBuilderAndSerialization() throws JsonProcessingException {

        LifeBuoyRequestWrapper wrapper = LifeBuoyRequestWrapper.builder()
                .withApiKey("someApiKey")
                .withErrandJsonString(LifebuoyRequest.builder().build())
                .build();


        String serialized;

        serialized = new ObjectMapper().writeValueAsString(wrapper);

        assertThat(serialized).isNotBlank();
    }
}