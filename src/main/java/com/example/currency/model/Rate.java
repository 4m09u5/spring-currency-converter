package com.example.currency.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Currency fromCurrency;

    @ManyToOne
    private Currency toCurrency;

    @Column
    private Double value;

    @Column
    private String type;

    @JsonIgnoreProperties("rates")
    @ManyToMany(mappedBy = "rates")
    private List<BankBranch> branches;
}