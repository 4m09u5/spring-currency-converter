package com.example.currency.service;

import com.example.currency.model.Currency;
import com.example.currency.repository.CurrencyRepository;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import org.springframework.stereotype.Service;

/**
 * This class implements Currency business logic.
 *
 * @author Lemiashonak Dzmitry
 * @since 2024-03-26
 */
@Service
public class CurrencyService {
  private final CurrencyRepository currencyRepository;

  public CurrencyService(CurrencyRepository currencies) {
    this.currencyRepository = currencies;
  }

  /**
   * This method saves given currency to database and returns its record id.
   *
   * @param currency currency entity to save
   * @author Lemiashonak Dzmitry
   * @since 2024-03-26
   */
  public Long createCurrency(Currency currency) {
    return currencyRepository.save(currency).getId();
  }

  private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
    Set<Object> seen = ConcurrentHashMap.newKeySet();
    return t -> seen.add(keyExtractor.apply(t));
  }

  /**
   * This method saves given currencies to database and returns their record id.
   *
   * @param currencies List of entities to save
   * @author Lemiashonak Dzmitry
   * @since 2024-03-26
   */
  public List<Long> createCurrencies(List<Currency> currencies) {
    return currencies.stream()
            .filter(distinctByKey(p -> p.getAbbreviation()))
            .map(this::createCurrency)
            .toList();
  }


  /**
   * This method gets Currency entity with provided id from database.
   *
   * @param id record id to fetch
   * @author Lemiashonak Dzmitry
   * @since 2024-03-26
   */
  public Currency getCurrencyById(Long id) {
    return currencyRepository
        .findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Internal error. Currency not found"));
  }

  /**
   * This method provides List of all Currencies present at database.
   *
   * @author Lemiashonak Dzmitry
   * @since 2024-03-26
   */
  public List<Currency> getAll() {
    return currencyRepository.findAllByOrderByIdAsc();
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

    return currencyRepository.save(old);
  }

  /**
   * This method removes Currency record with provided id from database.
   *
   * @param id Currency record id to remove
   * @author Lemiashonak Dzmitry
   * @since 2024-03-26
   */
  public void deleteCurrency(Long id) {
    currencyRepository.deleteById(id);
  }
}
