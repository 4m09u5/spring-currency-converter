package com.example.currency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * This class boots the project.
 *
 * @author Lemiashonak Dzmitry
 * @since 2024-03-26
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableWebMvc
public class CurrencyApplication {
  public static void main(String[] args) {
    SpringApplication.run(CurrencyApplication.class, args);
  }
}
