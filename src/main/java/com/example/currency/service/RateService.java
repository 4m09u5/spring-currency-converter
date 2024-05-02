package com.example.currency.service;

import com.example.currency.model.BankBranch;
import com.example.currency.model.Rate;
import com.example.currency.repository.RateRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * This class implements Rate business logic.
 *
 * @author Lemiashonak Dzmitry
 * @since 2024-03-26
 */
@Service
public class RateService {
  private final RateRepository rates;
  private final CurrencyService currencies;
  private final BankBranchService branches;

  /** RateService constructor. */
  public RateService(RateRepository rates, CurrencyService currencies, BankBranchService branches) {
    this.rates = rates;
    this.currencies = currencies;
    this.branches = branches;
  }

  /**
   * This method saves given bank branch to database and returns its record id.
   *
   * @param branchIds list of branches that perform exchange with this rate
   * @param fromId 'from' Currency id
   * @param toId 'to' Currency id
   * @param value exchange ratio
   * @param type exchange type
   * @author Lemiashonak Dzmitry
   * @since 2024-03-26
   */
  public Long createRate(List<Long> branchIds, String fromId, String toId, Double value, String type) {
    Rate result = new Rate();

    if (value != null) {
      result.setValue(value);
    }
    if (type != null) {
      result.setType(type);
    }
    if (fromId != null) {
      result.setFromCurrency(currencies.getCurrencyByAbbreviation(fromId));
    }
    if (toId != null) {
      result.setToCurrency(currencies.getCurrencyByAbbreviation(toId));
    }

    if (branchIds != null) {
      List<BankBranch> newBranches = new ArrayList<>();
      for (Long branchId : branchIds) {
        newBranches.add(branches.getBranchById(branchId));
      }
      result.setBranches(newBranches);
    }

    return rates.save(result).getId();
  }

  /**
   * This method gets Rate entity with provided id from database.
   *
   * @param id record id to fetch
   * @author Lemiashonak Dzmitry
   * @since 2024-03-26
   */
  public Rate getRateById(Long id) {
    return rates.findById(id).orElseThrow(
            () -> new IllegalArgumentException("Internal error. Rate not found"));
  }

  /**
   * This method provides List of all Rates present at database.
   *
   * @author Lemiashonak Dzmitry
   * @since 2024-03-26
   */
  public List<Rate> getAll() {
    return rates.findAllByOrderByIdAsc();
  }

  /**
   * This method updates record with provided id according to given data.
   *
   * @param id record id to modify
   * @param branchIds list of branches that perform exchange with this rate
   * @param from 'from' Currency abbreviation
   * @param to 'to' Currency abbreviation
   * @param value exchange ratio
   * @param type exchange type
   * @author Lemiashonak Dzmitry
   * @since 2024-03-26
   */
  public Rate updateRate(
      Long id, List<Long> branchIds, String from, String to, Double value, String type) {
    Rate old = getRateById(id);

    if (value != null && value != 0) {
      old.setValue(value);
    }
    if (type != null && !type.isEmpty()) {
      old.setType(type);
    }
    if (from != null && !from.isEmpty()) {
      old.setFromCurrency(currencies.getCurrencyByAbbreviation(from));
    }
    if (to != null && !to.isEmpty()) {
      old.setToCurrency(currencies.getCurrencyByAbbreviation(to));
    }

    if (branchIds != null && !branchIds.isEmpty()) {
      List<BankBranch> newBranches = new ArrayList<>();
      for (Long branchId : branchIds) {
        newBranches.add(branches.getBranchById(branchId));
      }
      old.setBranches(newBranches);
    }

    return rates.save(old);
  }

  /**
   * This method removes Rate record with provided id from database.
   *
   * @param id Rate record id to remove
   * @author Lemiashonak Dzmitry
   * @since 2024-03-26
   */
  public void deleteRate(Long id) {
    if (!rates.existsById(id)) {
      throw new IllegalArgumentException("Курс не найден");
    }
    rates.deleteById(id);
  }
}
