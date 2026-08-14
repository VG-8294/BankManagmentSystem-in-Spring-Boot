package com.sevabank.SevaBank.exception;

import com.sevabank.SevaBank.dto.generic.GenericDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public GenericDto<String> handleException(Exception e){
        return new GenericDto<String>(HttpStatus.INTERNAL_SERVER_ERROR, "Some error occured");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public GenericDto<String> resourceNotFound(ResourceNotFoundException e){
        return new GenericDto<String>(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BalanceException.class)
    public GenericDto<String> balanceLessException(BalanceException e){
        return new GenericDto<String>(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidCredentialsException.class)
    public GenericDto<String> invalidCredentialsException(InvalidCredentialsException e){
        return new GenericDto<String>(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public GenericDto<String> userAlreadyExistsException(UserAlreadyExistsException e){
        return new GenericDto<String>(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidAmountException.class)
    private GenericDto<String> invalidAmountException(InvalidAmountException e){
        return new GenericDto<String>(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidAccountTypeException.class)
    private GenericDto<String> invalidAccountTypeException(InvalidAccountTypeException e){
        return new GenericDto<String>(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidAgeException.class)
    private GenericDto<String> invalidAgeException(InvalidAgeException e){
        return new GenericDto<String>(HttpStatus.BAD_REQUEST, e.getMessage());
    }

}
