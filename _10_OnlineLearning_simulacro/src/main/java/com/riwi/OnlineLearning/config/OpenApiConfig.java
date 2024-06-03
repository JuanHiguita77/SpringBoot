package com.riwi.OnlineLearning.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(info = @Info(
    title = "Online Learning Platform",
    version = "1.0",
    description = "API for Riwi Courses platform"
))
public class OpenApiConfig {
    
}
