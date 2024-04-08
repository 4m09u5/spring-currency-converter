package com.example.currency.component;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * This class performs CRUD logging.
 *
 * @author Lemiashonak Dzmitry
 * @since 2024-03-26
 */
@Aspect
@Component
public class CrudLogger {
  private final Logger logger = LoggerFactory.getLogger(CrudLogger.class);

  @Pointcut("execution(* com.example.currency.service.*.create*(..))")
  public void create() {}

  @Pointcut("execution(* com.example.currency.service.*.update*(..))")
  public void update() {}

  @Pointcut("execution(* com.example.currency.service.*.delete*(..)) && args(id,..)")
  public void delete(Long id) {}

  @AfterReturning(pointcut = "create()", returning = "result")
  public void logCreate(Object result) {
    logger.info("New entity created: {}", result);
  }

  @AfterReturning(pointcut = "update()", returning = "result")
  public void logUpdate(Object result) {
    logger.info("Entity updated: {}", result);
  }

  @AfterReturning(pointcut = "delete(id)", returning = "result")
  public void logDelete(Object result, Long id) {
    logger.info("Removed entity: {}", id);
  }
}
