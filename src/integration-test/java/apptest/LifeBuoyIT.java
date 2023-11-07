package apptest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.OK;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import se.sundsvall.dept44.common.validators.annotation.impl.ValidUuidConstraintValidator;
import se.sundsvall.dept44.test.AbstractAppTest;
import se.sundsvall.dept44.test.annotation.wiremock.WireMockAppTestSuite;
import se.sundsvall.incident.Application;
import se.sundsvall.incident.api.model.IncidentSaveResponse;

@WireMockAppTestSuite(
	files = "classpath:/LifeBuoyIT/",
	classes = Application.class)
@Sql({
	"/sql/truncate.sql",
	"/sql/testdata.sql"
})
class LifeBuoyIT extends AbstractAppTest {

	private static final String PATH = "/incident";
	private static final String REQUEST_FILE = "request.json";

	@Test
	void test1_successfulLifeBuoyIncident() throws Exception {
		final var response = setupCall()
			.withServicePath(PATH)
			.withHttpMethod(POST)
			.withRequest(REQUEST_FILE)
			.withExpectedResponseStatus(OK)
			.sendRequestAndVerifyResponse()
			.andReturnBody(IncidentSaveResponse.class);

		final var incidentId = response.getIncidentId();
		assertThat(incidentId).isNotNull().isInstanceOf(String.class);
		assertThat(new ValidUuidConstraintValidator().isValid(incidentId)).isTrue();
	}

	@Test
	void test2_internalServerErrorFromLifebuoy() throws Exception {
		final var response = setupCall()
			.withServicePath(PATH)
			.withHttpMethod(POST)
			.withRequest(REQUEST_FILE)
			.withExpectedResponseStatus(OK)
			.sendRequestAndVerifyResponse()
			.andReturnBody(IncidentSaveResponse.class);

		final var incidentId = response.getIncidentId();
		assertThat(incidentId).isNotNull().isInstanceOf(String.class);
		assertThat(new ValidUuidConstraintValidator().isValid(response.getIncidentId())).isTrue();
	}
}
