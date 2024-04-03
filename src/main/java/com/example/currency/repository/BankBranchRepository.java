package com.example.currency.repository;

import com.example.currency.model.BankBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankBranchRepository extends JpaRepository<BankBranch, Long> {
    List<BankBranch> findAllByOrderByIdAsc();
}