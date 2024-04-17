package com.currency.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.currency.model.Currency;
import com.example.currency.repository.CurrencyRepository;
import com.example.currency.service.CurrencyService;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CurrencyServiceTest {
  @InjectMocks private CurrencyService currencyService;

  @Mock private CurrencyRepository currencyRepository;

  @BeforeEach
  void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void testGetAllCurrencies() {
    List<Currency> mockBanks = List.of(new Currency(1L, "Super Money", "SMN"));

    when(currencyRepository.findAllByOrderByIdAsc()).thenReturn(mockBanks);

    List<Currency> result = currencyService.getAll();

    assertEquals(mockBanks, result);
  }

  @Test
  void testGetCurrencyById() {
    Long id = 1L;

    Currency expectedCurrency = new Currency(1L, "Super Money", "SMN");

    when(currencyRepository.findById(id)).thenReturn(Optional.of(expectedCurrency));

    Currency result = currencyService.getCurrencyById(id);

    assertEquals(expectedCurrency, result);
  }

  @Test
  void testDeleteCurrecyById() {
    Long id = 1L;

    doNothing().when(currencyRepository).deleteById(id);

    currencyService.deleteCurrency(id);

    verify(currencyRepository).deleteById(id);
  }

  @Test
  void testUpdateCurrency() {
    Long id = 1L;

    Currency oldCurrency = new Currency(1L, "Super Money", "SMN");
    Currency newCurrency = new Currency(1L, "Mega Money", "MMN");

    when(currencyRepository.findById(id)).thenReturn(Optional.of(oldCurrency));
    when(currencyRepository.save(any(Currency.class)))
        .thenAnswer(
            invocation -> {
              Currency currency = invocation.getArgument(0);

              oldCurrency.setName(currency.getName());
              oldCurrency.setAbbreviation(currency.getAbbreviation());

              return oldCurrency;
            });

    Currency result = currencyService.updateCurrency(id, newCurrency);

    assertEquals(newCurrency, result);
  }

  @Test
  void testCreateCurrency() {
    Currency currency = new Currency(1L, "Super Money", "SMN");

    when(currencyRepository.save(currency)).thenReturn(currency);

    currencyService.createCurrency(currency);

    verify(currencyRepository).save(currency);
  }

  @Test
  void testCreateCurrencies() {
    List<Currency> currencies =
        Arrays.asList(
            new Currency(1L, "Super Money", "SMN"),
            new Currency(2L, "Mega Money", "MMN"),
            new Currency(3L, "My Money", "MMN"));

    List<Currency> filteredCurrencies = currencies.stream().filter(c -> c.getId() != 3).toList();

    when(currencyRepository.save(any(Currency.class))).thenAnswer(i -> i.getArguments()[0]);

    currencyService.createCurrencies(currencies);

    filteredCurrencies.stream().map(c -> verify(currencyRepository).save(c));
  }
}
