package com.juan.GraphQLCrud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import com.juan.GraphQLCrud.interpolator.CustomValidationMessageInterpolator;

import graphql.validation.rules.OnValidationErrorStrategy;
import graphql.validation.rules.ValidationRules;
import graphql.validation.schemawiring.ValidationSchemaWiring;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class GraphQLConfig {
    
    private final CustomValidationMessageInterpolator customValidationMessageInterpolator;

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        ValidationRules validationRules = ValidationRules.newValidationRules()
        .onValidationErrorStrategy(OnValidationErrorStrategy.RETURN_NULL)
        .messageInterpolator(customValidationMessageInterpolator)
        .build();

        ValidationSchemaWiring schemaWiring = new ValidationSchemaWiring(validationRules);

        return builder -> builder.directiveWiring(schemaWiring);
    }
}
