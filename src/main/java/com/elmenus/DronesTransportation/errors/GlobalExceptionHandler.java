package com.elmenus.DronesTransportation.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRecordNotFound( RecordNotFoundException ex){
        ErrorResponse response = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ErrorResponse> handleRecordNotFound( DuplicateException ex){
        ErrorResponse response = new ErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
