package com.manoelalmeidaio.rinhadebackendjavaspring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler
  public ResponseEntity<String> handleException(MethodArgumentNotValidException ex) {
    return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler
  public ResponseEntity<String> handleException(HttpMessageNotReadableException ex) {
    return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
  }
}
