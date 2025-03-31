package com.juan.GraphQLCrud.util.exceptions;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.graphql.execution.ErrorType;
import org.springframework.http.HttpStatus;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CustomValidationQLException extends RuntimeException implements GraphQLError {

    private String message;

    @Override
    public Map<String, Object> getExtensions() {
        Map<String, Object> attr = new LinkedHashMap<>();

        attr.put("Status", HttpStatus.BAD_REQUEST.value());

        return attr;
    }

    @Override
    public ErrorClassification getErrorType() {
        return ErrorType.BAD_REQUEST;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }
    
}
