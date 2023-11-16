package se.sundsvall.incident.integration.lifebuoy.configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.cloud.openfeign.FeignBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import se.sundsvall.dept44.configuration.feign.FeignConfiguration;
import se.sundsvall.dept44.configuration.feign.FeignMultiCustomizer;
import se.sundsvall.dept44.configuration.feign.decoder.ProblemErrorDecoder;

import feign.Request;
import feign.codec.ErrorDecoder;

@Import(FeignConfiguration.class)
public class LifeBuoyConfiguration {

	public static final String REGISTRATION_ID = "lifebuoy";
	private final LifeBuoyProperties properties;

	LifeBuoyConfiguration(final LifeBuoyProperties properties) {
		this.properties = properties;
	}

	@Bean
	FeignBuilderCustomizer customizer() {
		return FeignMultiCustomizer.create()
			.withErrorDecoder(errorDecoder())
			.withRequestOptions(requestOptions())
			.composeCustomizersToOne();
	}

	Request.Options requestOptions() {
		return new Request.Options(
			properties.connectTimeout(), TimeUnit.SECONDS,
			properties.readTimeout(), TimeUnit.SECONDS,
			true);

	}

	ErrorDecoder errorDecoder() {
		return new ProblemErrorDecoder(REGISTRATION_ID);
	}

}
