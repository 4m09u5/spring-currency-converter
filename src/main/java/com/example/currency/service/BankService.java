package com.example.currency.service;

import com.example.currency.model.Bank;
import com.example.currency.model.Rate;
import com.example.currency.repository.BankRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * This class implements Bank business logic.
 *
 * @author Lemiashonak Dzmitry
 * @since 2024-03-26
 */
@Service
public class BankService {
  private final BankRepository bankRepository;

  public BankService(BankRepository banks) {
    this.bankRepository = banks;
  }

  /**
   * This method saves given Bank entity to database and returns its record id.
   *
   * @param bank bank entity to save
   * @author Lemiashonak Dzmitry
   * @since 2024-03-26
   */
  public Long createBank(Bank bank) {
    return bankRepository.save(bank).getId();
  }

  /**
   * This method gets Bank entity with provided id from database.
   *
   * @param id record id to fetch
   * @author Lemiashonak Dzmitry
   * @since 2024-03-26
   */
  public Bank getBankById(Long id) {
    return bankRepository
        .findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Internal error. Bank not found"));
  }

  /**
   * This method provides List of all Banks present at database.
   *
   * @author Lemiashonak Dzmitry
   * @since 2024-03-26
   */
  public List<Bank> getAll() {
    return bankRepository.findAllByOrderByIdAsc();
  }

  /**
   * This method updates record with provided id according to given entity.
   *
   * @param id record id to modify
   * @param bank entity with new data
   * @author Lemiashonak Dzmitry
   * @since 2024-03-26
   */
  public Bank updateBank(Long id, Bank bank) {
    Bank old = getBankById(id);

    if (bank.getName() != null) {
      old.setName(bank.getName());
    }
    if (bank.getImage() != null) {
      old.setImage(bank.getImage());
    }

    return bankRepository.save(old);
  }

  /**
   * This method removes Bank record with provided id from database.
   *
   * @param id Bank record id to remove
   * @author Lemiashonak Dzmitry
   * @since 2024-03-26
   */
  public void deleteBank(Long id) {
    bankRepository.deleteById(id);
  }

  /**
   * This method provides best Rates for a Bank according to given criteria.
   *
   * @param id Bank id
   * @param from acronym for 'from' currency
   * @param to acronym for 'to' currency
   * @param type type desired of exchange
   * @author Lemiashonak Dzmitry
   * @since 2024-03-26
   */
  public Rate getBestBankRate(Long id, String from, String to, String type) {
    List<Rate> rates = bankRepository.getBestBankRate(id, from, to, type);
    if (rates.isEmpty()) {
      throw new IllegalArgumentException("Internal error. Bank not found");
    }
    return rates.iterator().next();
  }
}
