package com.example.currency.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class BankBranch {
    @Id
    @GeneratedValue
    Long id;

    @JsonIgnoreProperties("branches")
    @ManyToOne
    @JoinColumn(name="bank_id", nullable = false)
    Bank bank;

    @Column
    String address;

    @JsonIgnoreProperties("branches")
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(
            name = "branch_rate",
            joinColumns = @JoinColumn(name = "branch_id"),
            inverseJoinColumns = @JoinColumn(name = "rate_id"))
    private List<Rate> rates;
}
