package com.example.currency.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(RuntimeException.class)
  public ErrorMessage runtimeError(Exception ex) {
    log.error("Runtime exception", ex);
    return new ErrorMessage("Internal server error");
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler({ResponseStatusException.class, MethodArgumentTypeMismatchException.class, NoHandlerFoundException.class})
  public ErrorMessage notFoundException(Exception ex) {
    log.error("404 NotFound exception");
    return new ErrorMessage("Resource not found");
  }

  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ErrorMessage handleMethodNotAllowed(Exception ex) {
    log.error("MethodNotAllowed exception");
    return new ErrorMessage("Method not allowed");
  }

  public record ErrorMessage(String message) {}
}
