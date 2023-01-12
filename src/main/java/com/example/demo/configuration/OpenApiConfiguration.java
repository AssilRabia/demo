package com.example.demo.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenApiConfiguration
 *
 * @author Assil Rabia
 */

@Configuration
public class OpenApiConfiguration {

    /**
     * create customOpenAPI
     *
     * @return OpenAPI
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(apiInfo());
    }

    /**
     * Create apiInfo
     *
     * @return Info
     */
    private Info apiInfo() {
        return new Info()
                .title("Demo")
                .description("API for creating user and get user details by id")
                .version("1.0.0")
                .contact(apiContact());
    }

    /**
     * Create apiContact
     *
     * @return Contact
     */
    private Contact apiContact() {
        return new Contact()
                .name("Assil Rabia")
                .email("assil.rabia@exemple.com");
    }

}
