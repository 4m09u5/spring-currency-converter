package com.example.currency.service;

import com.example.currency.model.Currency;
import com.example.currency.repository.CurrencyRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * This class implements Currency business logic.
 *
 * @author Lemiashonak Dzmitry
 * @since 2024-03-26
 */
@Service
public class CurrencyService {
  private final CurrencyRepository currencies;

  public CurrencyService(CurrencyRepository currencies) {
    this.currencies = currencies;
  }

  /**
   * This method saves given currency to database and returns its record id.
   *
   * @param currency currency entity to save
   * @author Lemiashonak Dzmitry
   * @since 2024-03-26
   */
  public Long createCurrency(Currency currency) {
    return currencies.save(currency).getId();
  }

  /**
   * This method gets Currency entity with provided id from database.
   *
   * @param id record id to fetch
   * @author Lemiashonak Dzmitry
   * @since 2024-03-26
   */
  public Currency getCurrencyById(Long id) {
    return currencies
        .findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  /**
   * This method provides List of all Currencies present at database.
   *
   * @author Lemiashonak Dzmitry
   * @since 2024-03-26
   */
  public List<Currency> getAll() {
    return currencies.findAllByOrderByIdAsc();
  }

  /**
   * This method updates record with provided id according to given entity.
   *
   * @param id record id to modify
   * @param currency entity with new data
   * @author Lemiashonak Dzmitry
   * @since 2024-03-26
   */
  public Currency updateCurrency(Long id, Currency currency) {
    Currency old = getCurrencyById(id);

    if (currency.getName() != null) {
      old.setName(currency.getName());
    }
    if (currency.getAbbreviation() != null) {
      old.setAbbreviation(currency.getAbbreviation());
    }

    return currencies.save(old);
  }

  /**
   * This method removes Currency record with provided id from database.
   *
   * @param id Currency record id to remove
   * @author Lemiashonak Dzmitry
   * @since 2024-03-26
   */
  public void deleteCurrency(Long id) {
    getCurrencyById(id);
    currencies.deleteById(id);
  }
}
