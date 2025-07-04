package com.sirade.SIRADEAPI.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                                "https://id-preview--5341bf68-42ab-4af9-ba52-dd9e41fde1ae.lovable.app",
                                "https://id-preview--2559bef9-c5b5-4b38-b540-94f2dca4981b.lovable.app"
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true); // HABILITAR ENVÍO DE COOKIES O HEADERS COMO AUTH
            }
        };
    }
}
