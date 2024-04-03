package com.example.currency.repository;

import com.example.currency.model.Bank;
import com.example.currency.model.Rate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * This interface defines desired methods for database communication relative to Bank entity.
 *
 * @author Lemiashonak Dzmitry
 * @since 2024-03-26
 */
@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
  List<Bank> findAllByOrderByIdAsc();

  @Query(
      "SELECT r FROM Rate r "
          + "JOIN r.branches b "
          + "ON b.bank.id = :id "
          + "WHERE r.fromCurrency.abbreviation = :from "
          + "AND r.toCurrency.abbreviation = :to "
          + "AND r.type = :type "
          + "ORDER BY r.value ASC")
  List<Rate> getBestBankRate(Long id, String from, String to, String type);
}
