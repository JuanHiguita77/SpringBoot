package riwi.lastfilter.spring.api.error_handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import riwi.lastfilter.spring.api.dto.errors.BaseErrorResponse;
import riwi.lastfilter.spring.api.dto.errors.ErrorResponse;
import riwi.lastfilter.spring.api.dto.errors.ListErrorsResponse;
import riwi.lastfilter.spring.utils.exceptions.CouponUsedException;
import riwi.lastfilter.spring.utils.exceptions.IdNotFoundException;







// RestControllerAdvice = ErrorController
@RestControllerAdvice
// Error status
@ResponseStatus(code = HttpStatus.BAD_REQUEST )
public class BadRequestController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseErrorResponse handlerBadRequest(MethodArgumentNotValidException exception){
        List<String> errors = new ArrayList<>();
        exception.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
        BaseErrorResponse baseErrorResponse = ListErrorsResponse.builder()
                                            .code(HttpStatus.BAD_REQUEST.value())
                                            .status(HttpStatus.BAD_REQUEST.name())
                                            .errors(errors)
                                            .build();
        return baseErrorResponse;
    }

    @ExceptionHandler(IdNotFoundException.class)
    public BaseErrorResponse handleIdNotFound(IdNotFoundException exception) {
        ErrorResponse error = ErrorResponse.builder()
        .code(HttpStatus.BAD_REQUEST.value())
        .status(HttpStatus.BAD_REQUEST.name())
        .message(exception.getMessage())
        .build();
        return error;
    }

    @ExceptionHandler(CouponUsedException.class)
    public BaseErrorResponse handleIdNotFound(CouponUsedException exception) {
        ErrorResponse error = ErrorResponse.builder()
        .code(HttpStatus.BAD_REQUEST.value())
        .status(HttpStatus.BAD_REQUEST.name())
        .message(exception.getMessage())
        .build();
        return error;
    }

}