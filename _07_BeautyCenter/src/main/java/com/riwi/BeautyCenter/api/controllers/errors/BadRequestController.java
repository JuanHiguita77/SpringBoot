package com.riwi.BeautyCenter.api.controllers.errors;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.riwi.BeautyCenter.api.error_handler.BaseErrorResponse;
import com.riwi.BeautyCenter.api.error_handler.ErrorResponse;
import com.riwi.BeautyCenter.api.error_handler.ErrorsResponse;
import com.riwi.BeautyCenter.util.exceptions.IdNotFoundException;

@RestControllerAdvice
//cODIGO DEL ERROR
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestController 
{

    //El manejador de errores que hicimos manual en error_handler sin dependencias
    @ExceptionHandler(IdNotFoundException.class)
    public BaseErrorResponse handleIdNotFound(IdNotFoundException e)
    {
        //Asi se usa el contructor con builder, seria lo mismo a usar una instancia de error y asignarlo con set
        return ErrorResponse.builder()
            .code(HttpStatus.BAD_REQUEST.value())
            .status(HttpStatus.BAD_REQUEST.name())
            .message(e.getMessage()).build();
    }

    //Con la dependencia de spring boot starter validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseErrorResponse handleErrors(MethodArgumentNotValidException e)
    {
        List<String> errors = new ArrayList<>();

        e.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
        
        return ErrorsResponse.builder()
        .code(HttpStatus.BAD_REQUEST.value())
        .status(HttpStatus.BAD_REQUEST.name())
        .errors(errors)
        .build();
    }
}
