package com.example.currency.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

/**
 * This class represents Currency entity.
 *
 * @author Lemiashonak Dzmitry
 * @since 2024-03-26
 */
@Data
@Entity
public class Currency {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column private String name;

  @Column private String abbreviation;
}
