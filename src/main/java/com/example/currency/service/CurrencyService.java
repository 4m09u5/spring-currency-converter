package com.example.currency.service;

import com.example.currency.model.Currency;
import com.example.currency.repository.CurrencyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CurrencyService {
    private CurrencyRepository currencies;

    public CurrencyService(CurrencyRepository currencies) {
        this.currencies = currencies;
    }
    public Long createCurrency(Currency currency) {
        return currencies.save(currency).getId();
    }
    public Currency getCurrencyById(Long id) {
        return currencies.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Currency> getAll() {
        return currencies.findAllByOrderByIdAsc();
    }

    public Currency updateCurrency(Long id, Currency currency) {
        Currency old = getCurrencyById(id);

        if (currency.getName() != null) old.setName(currency.getName());
        if (currency.getAbbreviation() != null) old.setAbbreviation(currency.getAbbreviation());

        return currencies.save(old);
    }

    public void deleteCurrency(Long id) {
        getCurrencyById(id);
        currencies.deleteById(id);
    }
}
