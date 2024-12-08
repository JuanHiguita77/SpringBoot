package com.juan.GraphQLCrud.interpolator;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.juan.GraphQLCrud.util.exceptions.CustomValidationQLException;

import graphql.GraphQLError;
import graphql.validation.interpolation.MessageInterpolator;
import graphql.validation.rules.ValidationEnvironment;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class CustomValidationMessageInterpolator implements MessageInterpolator {

    @Override
    public GraphQLError interpolate(String arg0, Map<String, Object> arg1, ValidationEnvironment arg2) {
        String argumentName = arg2.getArgument().getName();

        String message = String.format(arg0, argumentName);

        return new CustomValidationQLException(message);
    }
    
}
