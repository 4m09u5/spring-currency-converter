package com.example.currency.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;
import lombok.Data;

/**
 * This class represents BankBranch entity.
 *
 * @author Lemiashonak Dzmitry
 * @since 2024-03-26
 */
@Data
@Entity
public class BankBranch {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JsonIgnoreProperties("branches")
  @ManyToOne
  @JoinColumn(name = "bank_id", nullable = false)
  private Bank bank;

  @Column String address;

  @JsonIgnoreProperties("branches")
  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "branch_rate",
      joinColumns = @JoinColumn(name = "branch_id"),
      inverseJoinColumns = @JoinColumn(name = "rate_id"))
  private List<Rate> rates;
}
