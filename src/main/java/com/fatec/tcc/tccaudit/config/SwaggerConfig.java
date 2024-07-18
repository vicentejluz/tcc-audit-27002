package com.fatec.tcc.tccaudit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

    public static final String SCHEME_NAME = "bearer-key";
    public static final String SCHEME = "bearer";

    @Bean
    public OpenAPI customOpenAPI() {
        var openApi = new OpenAPI().info(this.apiInfo());
        this.addSecurity(openApi);
        return openApi;
    }

    private Info apiInfo() {
        var contact = new Contact();
        var license = new License();
        contact.setEmail("f168.sinfo@fatec.sp.gov.br");
        contact.setName("FATEC São Caetano do Sul");
        contact.setUrl("http://www.fatecsaocaetano.edu.br/");
        license.setName("MIT License");
        license.url("https://github.com/vicentejluz/tcc-audit-27002/blob/main/LICENSE");
        return new Info()
                .title("TCC AUDIT 27002 API")
                .description(
                        """
                                The TCC Audit is a tool developed as part of the Course Completion Project (TCC) at FATEC São Caetano do Sul, specifically within the Information Security field. Its main objective is to help organizations assess their maturity in information security best practices, based on the guidelines outlined in the ISO/IEC 27002:2022 standard.

                                This standard establishes a comprehensive framework consisting of controls, guidelines, and best practices designed to improve information security in organizations. The overall objective is to ensure the confidentiality, integrity, and availability of confidential data.

                                The creation of the TCC Audit means a commitment to applying the practical knowledge acquired throughout the course. Taking advantage of the principles and insights acquired in the Information Security course at FATEC São Caetano do Sul.
                                """)
                .license(license)
                .contact(contact)
                .version("1.1");
    }

    private void addSecurity(OpenAPI openApi) {
        var components = this.createComponents();
        openApi.components(components);
    }

    private Components createComponents() {
        var components = new Components();
        components.addSecuritySchemes(SCHEME_NAME, this.createSecurityScheme());
        return components;
    }

    private SecurityScheme createSecurityScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme(SCHEME).bearerFormat("JWT");
    }
}