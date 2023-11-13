package se.sundsvall.incident.integration.lifebuoy.configuration;

import org.springframework.cloud.openfeign.FeignBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import se.sundsvall.dept44.configuration.feign.FeignConfiguration;
import se.sundsvall.dept44.configuration.feign.FeignMultiCustomizer;
import se.sundsvall.dept44.configuration.feign.decoder.ProblemErrorDecoder;

@Import(FeignConfiguration.class)
public class LifeBuoyConfiguration {

	public static final String REGISTRATION_ID = "lifebuoy";

	@Bean
	FeignBuilderCustomizer feignBuilderCustomizer(final LifeBuoyProperties lifeBuoyProperties, final ClientRegistrationRepository clientRegistrationRepository) {
		return FeignMultiCustomizer.create()
			.withErrorDecoder(new ProblemErrorDecoder(REGISTRATION_ID))
			.withRequestTimeoutsInSeconds(lifeBuoyProperties.connectTimeout(), lifeBuoyProperties.readTimeout())
			.withRetryableOAuth2InterceptorForClientRegistration(clientRegistrationRepository.findByRegistrationId(REGISTRATION_ID))
			.composeCustomizersToOne();
	}

}
