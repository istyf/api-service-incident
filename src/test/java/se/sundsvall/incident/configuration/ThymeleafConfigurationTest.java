package se.sundsvall.incident.configuration;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@ExtendWith(MockitoExtension.class)
class ThymeleafConfigurationTest {

	@InjectMocks
	private ThymeleafConfiguration thymeleafConfiguration;

	@Test
	void thymeleafTemplateResolverTest() {
		ClassLoaderTemplateResolver resolver = (ClassLoaderTemplateResolver) thymeleafConfiguration.thymeleafTemplateResolver();

		assertThat(resolver.getPrefix()).isEqualTo("mail-templates/");
		assertThat(resolver.getSuffix()).isEqualTo(".html");
		assertThat(resolver.getTemplateMode()).isEqualTo(TemplateMode.HTML);
		assertThat(resolver.getCharacterEncoding()).isEqualTo("UTF-8");
	}

	@Test
	void thymeleafTemplateEngineTest() {
		var resolver = thymeleafConfiguration.thymeleafTemplateResolver();
		SpringTemplateEngine engine = thymeleafConfiguration.thymeleafTemplateEngine(resolver);

		assertThat(engine.getTemplateResolvers()).contains(resolver);
	}

}
