package com.example.currency.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
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
 * This class represents BankBranch entity.
 *
 * @author Lemiashonak Dzmitry
 * @since 2024-03-26
 */
@NoArgsConstructor
@AllArgsConstructor
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

  @ToString.Exclude
  @JsonIgnoreProperties("branches")
  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "branch_rate",
      joinColumns = @JoinColumn(name = "branch_id"),
      inverseJoinColumns = @JoinColumn(name = "rate_id"))
  private List<Rate> rates;
}
