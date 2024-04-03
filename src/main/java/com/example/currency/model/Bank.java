package com.example.currency.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.Data;

/**
 * This class represents Bank entity.
 *
 * @author Lemiashonak Dzmitry
 * @since 2024-03-26
 */
@Data
@Entity
public class Bank {
  @JsonIgnoreProperties("bank")
  @OneToMany(mappedBy = "bank")
  private List<BankBranch> branches;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column private String name;
  @Column private String image;
}
