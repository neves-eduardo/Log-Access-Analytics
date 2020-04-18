package com.neves_eduardo.logaccesssanalytics.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.neves_eduardo.logaccesssanalytics")
public class AppConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
