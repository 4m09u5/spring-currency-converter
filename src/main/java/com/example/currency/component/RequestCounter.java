package com.example.currency.component;

import java.util.concurrent.atomic.AtomicLong;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * This class counts requests to controllers.
 *
 * @author Lemiashonak Dzmitry
 * @since 2024-03-26
 */
@Aspect
@Component
public class RequestCounter {
  private final AtomicLong counter = new AtomicLong(0L);

  public Long getValue() {
    return counter.longValue();
  }

  @Pointcut("execution(* com.example.currency.controller.*.*(..))")
  public void request() {}

  @Before("request()")
  public void increment() {
    counter.incrementAndGet();
  }
}
