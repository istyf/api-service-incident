package se.sundsvall.incident.integration.lifebuoy.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class LifebuoyRequestTest {


    @Test
    void testBuilderAndSerialization() {

        LifebuoyRequest request = LifebuoyRequest.builder()
                .withValue(LifebuoyRequest.TypeAndValue.Type.Property, "source", "B6DD8D87-317A-4CF2-85A1-D01537DD1288")
                .withValue(LifebuoyRequest.TypeAndValue.Type.Property, "personalnumber", "NOT MAPPED")
                .withValue(LifebuoyRequest.TypeAndValue.Type.Property, "name", "Test Testorsson")
                .withValue(LifebuoyRequest.TypeAndValue.Type.Property, "address", "Testgatan 69")
                .withValue(LifebuoyRequest.TypeAndValue.Type.Property, "phonenumber", "0701234567")
                .withValue(LifebuoyRequest.TypeAndValue.Type.Property, "contactmethod", "Email")
                .withValue(LifebuoyRequest.TypeAndValue.Type.Property, "email", "test@testorsson.se")
                .withValue(LifebuoyRequest.TypeAndValue.Type.Property, "category", "livboj")
                .withValue(LifebuoyRequest.TypeAndValue.Type.Property, "description", "Jag är ett automatiskt alarm! Någon har tagit livboje")
                .withValue(LifebuoyRequest.TypeAndValue.Type.GeoProperty, "location", new LifebuoyRequest.Location(new double[]{17.31079,
                        62.389976}))
                .build();

        String serialized;
        try {
            serialized = new ObjectMapper().writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        assertThat(serialized).isEqualTo("{\"address\":{\"type\":\"Property\",\"value\":\"Testgatan 69\"},\"personalnumber\":{\"type\":\"Property\",\"value\":\"NOT MAPPED\"},\"name\":{\"type\":\"Property\",\"value\":\"Test Testorsson\"},\"phonenumber\":{\"type\":\"Property\",\"value\":\"0701234567\"},\"contactmethod\":{\"type\":\"Property\",\"value\":\"Email\"},\"description\":{\"type\":\"Property\",\"value\":\"Jag är ett automatiskt alarm! Någon har tagit livboje\"},\"location\":{\"type\":\"GeoProperty\",\"value\":{\"type\":\"Point\",\"coordinates\":[17.31079,62.389976]}},\"source\":{\"type\":\"Property\",\"value\":\"B6DD8D87-317A-4CF2-85A1-D01537DD1288\"},\"category\":{\"type\":\"Property\",\"value\":\"livboj\"},\"email\":{\"type\":\"Property\",\"value\":\"test@testorsson.se\"}}");
    }

}