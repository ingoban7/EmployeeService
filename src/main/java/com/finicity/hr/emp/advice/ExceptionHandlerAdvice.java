package com.finicity.hr.emp.advice;

import com.finicity.hr.emp.exception.EmployeeNotFoundException;
import com.finicity.hr.emp.exception.ErrorResponse;
import com.finicity.hr.emp.exception.InvalidInputException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponse> handleInvalidInputException(InvalidInputException iex){
        log.error("Invalid Input Exception");
        ErrorResponse resource = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), iex.getMessage());
        return new ResponseEntity<>(resource, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEmployeeNotFoundException(EmployeeNotFoundException ex){
        return ex.getMessage();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorResponse>  handleException(Exception ex){
        log.error(ex.getMessage());
        ErrorResponse resource = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "Exception has occurred.");
        return new ResponseEntity<>(resource, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
