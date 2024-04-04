package com.example.currency.component;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CrudLogger {
  private final Logger logger = LoggerFactory.getLogger(CrudLogger.class);

  @Pointcut("execution(* com.example.currency.service.*.create*(..))")
  public void create() {}

  @Pointcut("execution(* com.example.currency.service.*.update*(..))")
  public void update() {}

  @Pointcut("execution(* com.example.currency.service.*.delete*(..))")
  public void delete() {}

  @AfterReturning(pointcut = "create()", returning = "result")
  public void logCreate(Object result) {
    logger.info("Created: {}", result);
  }

  @AfterReturning(pointcut = "update()", returning = "result")
  public void logUpdate(Object result) {
    logger.info("Updated: {}", result);
  }

  @AfterReturning(pointcut = "delete()", returning = "result")
  public void logDelete(Object result) {
    logger.info("Deleted: {}", result);
  }
}
