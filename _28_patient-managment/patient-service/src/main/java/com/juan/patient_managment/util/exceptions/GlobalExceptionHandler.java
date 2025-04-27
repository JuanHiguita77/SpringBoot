package com.juan.patient_managment.util.exceptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class GlobalExceptionHandler {
    
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<Map<String,String>> errors = new ArrayList<>();

        logger.error("Validation error: {}", ex.getMessage()); //log de advertencia
        ex.getBindingResult().getFieldErrors().forEach(e -> {
            Map<String,String> error = new HashMap<>();
            error.put("error", e.getDefaultMessage()); //agregar al map el error
            error.put("field", e.getField()); //agregar al map en donde ocurrió el error
            errors.add(error);
        });

        return ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value()) //400
                .status(HttpStatus.BAD_REQUEST.name()) //BAD_REQUEST
                .errors(errors) // [ { "field": "mal", "error": "mal"} ]
                .build();
    }

    
    @ExceptionHandler(BadRequestException.class)
    public ErrorResponse handleError(BadRequestException exception){
        List<Map<String,String>> errors = new ArrayList<>();

        Map<String,String> error = new HashMap<>();
        
        logger.error("Error: {}", exception.getMessage()); //log de advertencia
        error.put("Message: ", exception.getMessage());

        errors.add(error);

        
        return ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value()) //400
                .status(HttpStatus.BAD_REQUEST.name()) //BAD_REQUEST
                .errors(errors) // [ { "field": "mal", "error": "mal"} ]
                .build();

    }
}
