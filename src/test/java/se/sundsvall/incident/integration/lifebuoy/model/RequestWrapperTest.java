package se.sundsvall.incident.integration.lifebuoy.model;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class RequestWrapperTest {

	@Test
	void testBuilderAndSerialization() throws JsonProcessingException {

		var mapper = new ObjectMapper();
		LifeBuoyRequestWrapper wrapper = LifeBuoyRequestWrapper.builder()
			.withApiKey("someApiKey")
			.withErrandJsonString(mapper.writeValueAsString(LifebuoyRequest.builder().build()))
			.build();

		String serialized;

		serialized = new ObjectMapper().writeValueAsString(wrapper);

		assertThat(serialized).isNotBlank();
	}
}