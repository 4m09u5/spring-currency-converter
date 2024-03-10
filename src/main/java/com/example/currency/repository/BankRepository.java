package com.example.currency.repository;

import com.example.currency.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
    public List<Bank> findAllByOrderByIdAsc();
}