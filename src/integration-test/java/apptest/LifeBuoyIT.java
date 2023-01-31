package apptest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import se.sundsvall.dept44.common.validators.annotation.impl.ValidUuidConstraintValidator;
import se.sundsvall.dept44.test.AbstractAppTest;
import se.sundsvall.dept44.test.annotation.wiremock.WireMockAppTestSuite;
import se.sundsvall.incident.Application;
import se.sundsvall.incident.api.model.IncidentSaveResponse;
import se.sundsvall.incident.dto.Status;
import se.sundsvall.incident.integration.db.IncidentRepository;

import static org.assertj.core.api.Assertions.assertThat;

@WireMockAppTestSuite(
        files = "classpath:/LifeBuoyIT/",
        classes = Application.class
)
@Transactional
public class LifeBuoyIT extends AbstractAppTest {

    @Autowired
    private IncidentRepository incidentRepository;

    @Test
    void test1_successfulLifeBuoyIncident() throws Exception {

        var response = setupCall()
                .withServicePath("/api/sendincident")
                .withHttpMethod(HttpMethod.POST)
                .withRequest("request.json")
                .withExpectedResponseStatus(HttpStatus.OK)
                .sendRequestAndVerifyResponse()
                .andReturnBody(IncidentSaveResponse.class);

        assertThat(new ValidUuidConstraintValidator().isValid(response.getIncidentId())).isTrue();
        var incidentId = response.getIncidentId();

        assertThat(incidentRepository.getReferenceById(incidentId)).satisfies(incidentEntity -> {
            assertThat(incidentEntity.getIncidentId()).isEqualTo(incidentId);
            assertThat(incidentEntity.getStatus()).isEqualTo(Status.INSKICKAT);
        });
    }

    @Test
    void test2_internalServerErrorFromLifebuoy() throws Exception {

        var response = setupCall()
                .withServicePath("/api/sendincident")
                .withHttpMethod(HttpMethod.POST)
                .withRequest("request.json")
                .withExpectedResponseStatus(HttpStatus.OK)
                .sendRequestAndVerifyResponse()
                .andReturnBody(IncidentSaveResponse.class);

        assertThat(new ValidUuidConstraintValidator().isValid(response.getIncidentId())).isTrue();
        var incidentId = response.getIncidentId();

        assertThat(incidentRepository.getReferenceById(incidentId)).satisfies(incidentEntity -> {
            assertThat(incidentEntity.getIncidentId()).isEqualTo(incidentId);
            assertThat(incidentEntity.getStatus()).isEqualTo(Status.ERROR);
        });


    }
}
