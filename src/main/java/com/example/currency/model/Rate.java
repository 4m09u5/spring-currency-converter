package com.example.currency.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;
import lombok.Data;
import lombok.ToString;

/**
 * This class represents Rate entity.
 *
 * @author Lemiashonak Dzmitry
 * @since 2024-03-26
 */
@Data
@Entity
public class Rate {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne private Currency fromCurrency;

  @ManyToOne private Currency toCurrency;

  @Column private Double value;

  @Column private String type;

  @ToString.Exclude
  @JsonIgnoreProperties("rates")
  @ManyToMany
  @JoinTable(
      name = "branch_rate",
      joinColumns = @JoinColumn(name = "rate_id"),
      inverseJoinColumns = @JoinColumn(name = "branch_id"))
  private List<BankBranch> branches;
}
