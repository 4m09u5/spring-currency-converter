package com.example.currency.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * This class represents Rate entity.
 *
 * @author Lemiashonak Dzmitry
 * @since 2024-03-26
 */
@NoArgsConstructor
@AllArgsConstructor
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
