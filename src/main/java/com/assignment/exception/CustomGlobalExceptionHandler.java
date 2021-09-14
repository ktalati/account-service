package com.assignment.exception;

import com.assignment.model.CustomErrorResponse;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.ServiceUnavailableException;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Optional;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({BadRequestException.class, CustomerNotFoundException.class, APIException.class, Exception.class})
    public ResponseEntity<CustomErrorResponse> handleExceptions(Exception e, HttpServletResponse httpServletResponse){
        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setTimestamp(LocalDateTime.now());
        errors.setMessage(e.getMessage());
        if(e instanceof BadRequestException){
            errors.setStatus(HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }else if(e instanceof CustomerNotFoundException){
            errors.setStatus(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
        }else if(e instanceof ServiceUnavailableException){
            errors.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
            return new ResponseEntity<>(errors, HttpStatus.SERVICE_UNAVAILABLE);
        }else if(e instanceof FeignException){
            HttpStatus status = Optional.ofNullable(HttpStatus.resolve(((FeignException) e).status())).orElse(HttpStatus.SERVICE_UNAVAILABLE);
            errors.setStatus(status.value());
            return new ResponseEntity<>(errors, status);
        }else{
            errors.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
