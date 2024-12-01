package com.riwi.librosYa.api.error_handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.riwi.librosYa.api.dto.errors.BaseErrorResp;
import com.riwi.librosYa.api.dto.errors.ErrorsResp;
import com.riwi.librosYa.util.exceptions.BadRequestException;

@RestControllerAdvice
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestController {

    //Manejar excepciones que ocurren cuando una validación de argumentos falla, como al usar anotaciones de validación (@NotNull, @Size, etc.) en un DTO.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseErrorResp handleBadRequest(MethodArgumentNotValidException exception)
    {
        List<Map<String, String>> errors = new ArrayList<>();

        //Construye una lista de errores
        exception.getBindingResult().getFieldErrors().forEach(e ->
        {
            Map<String, String> error = new HashMap<>();

            error.put("error", e.getDefaultMessage());
            error.put("field", e.getField());
            errors.add(error);
        });

        return ErrorsResp.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .status(HttpStatus.BAD_REQUEST.name())
                        .errors(errors)
                        .build();
    }

    //Manejar excepciones personalizadas (BadRequestException) lanzadas manualmente en la aplicación para errores específicos de negocio.
    @ExceptionHandler(BadRequestException.class)
    public BaseErrorResp handleError(BadRequestException exception)
    {
        List<Map<String, String>> errors = new ArrayList<>();

        Map<String, String> error = new HashMap<>();

        //Devuelve un mensaje estructurado personalizado con el error
        error.put("id", exception.getMessage());

        errors.add(error);

        return ErrorsResp.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .status(HttpStatus.BAD_REQUEST.name())
                        .errors(errors)
                        .build();
    }
}
