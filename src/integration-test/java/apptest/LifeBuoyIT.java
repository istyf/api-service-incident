package apptest;

import static apptest.CommonStubs.stubForAccessToken;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.OK;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import se.sundsvall.dept44.test.AbstractAppTest;
import se.sundsvall.dept44.test.annotation.wiremock.WireMockAppTestSuite;
import se.sundsvall.incident.Application;

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
	private static final String EXPECTED_FILE = "expected.json";

	@BeforeEach
	void setup() {
		stubForAccessToken();
	}
	
	@Test
	void test1_successfulLifeBuoyIncident() {
		setupCall()
			.withServicePath(PATH)
			.withHttpMethod(POST)
			.withRequest(REQUEST_FILE)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(EXPECTED_FILE)
			.sendRequestAndVerifyResponse();
	}

	@Test
	void test2_internalServerErrorFromLifebuoy() {
		setupCall()
			.withServicePath(PATH)
			.withHttpMethod(POST)
			.withRequest(REQUEST_FILE)
			.withExpectedResponseStatus(OK)
			.withExpectedResponse(EXPECTED_FILE)
			.sendRequestAndVerifyResponse();

	}
}
