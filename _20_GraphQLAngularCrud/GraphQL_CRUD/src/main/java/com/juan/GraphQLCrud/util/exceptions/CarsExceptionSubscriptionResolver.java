package com.juan.GraphQLCrud.util.exceptions;

import java.util.List;

import org.springframework.graphql.execution.SubscriptionExceptionResolverAdapter;
import org.springframework.stereotype.Controller;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

@Controller
public class CarsExceptionSubscriptionResolver extends SubscriptionExceptionResolverAdapter{
    @Override
    protected GraphQLError resolveToSingleError(Throwable ex) {
        return new GraphQLError(){
            @Override
            public String getMessage() {
                return ex.getMessage();
            }

            @Override
            public ErrorClassification getErrorType() {
                return null;
            }

            @Override
            public List<SourceLocation> getLocations() {
                return null;
            }
        };
    }
}
