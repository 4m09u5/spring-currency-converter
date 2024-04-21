package com.example.currency.controller;

import com.example.currency.component.RequestCounter;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class handles connections related to counter.
 *
 * @author Lemiashonak Dzmitry
 * @since 2024-03-26
 */
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/counter")
public class CounterController {

  RequestCounter counter;

  @GetMapping
  public Long getCounterValue() {
    return counter.getValue();
  }
}
