package com.example.currency.error;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * This advice performs exceptions logging.
 *
 * @author Lemiashonak Dzmitry
 * @since 2024-03-26
 */
@Slf4j
@RestControllerAdvice
public class ErrorHandler {
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(RuntimeException.class)
  public ErrorMessage runtimeError(Exception ex) {
    log.error("Runtime exception", ex);
    return new ErrorMessage(500L, ex.getMessage());
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler({ResponseStatusException.class, NoHandlerFoundException.class})
  public ErrorMessage notFoundException(Exception ex) {
    log.error("404 NotFound exception");
    return new ErrorMessage(404L, "Resource not found");
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({
    HttpClientErrorException.class,
    HttpMessageNotReadableException.class,
    MethodArgumentNotValidException.class,
    MissingServletRequestParameterException.class,
    ConstraintViolationException.class
  })
  public ErrorMessage handleBadRequestException(Exception ex) {
    log.error("400 error");
    return new ErrorMessage(400L, "Bad request");
  }

  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ErrorMessage handleMethodNotAllowed(Exception ex) {
    log.error("MethodNotAllowed exception");
    return new ErrorMessage(405L, "Method not allowed");
  }

  /**
   * This record represents exception message.
   *
   * @author Lemiashonak Dzmitry
   * @since 2024-03-26
   */
  public record ErrorMessage(Long code, String message) {}
}
