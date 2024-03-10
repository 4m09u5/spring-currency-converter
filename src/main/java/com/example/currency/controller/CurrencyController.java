package com.example.currency.controller;

import com.example.currency.model.Currency;
import com.example.currency.service.CurrencyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/currency")
public class CurrencyController {
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/{id}")
    public Currency getCurrency(@PathVariable Long id) {
        return currencyService.getCurrencyById(id);
    }

    @GetMapping
    public List<Currency> getAllCurrencies() {
        return currencyService.getAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Long createCurrency(@RequestBody(required = true) Currency currency) {
        return currencyService.createCurrency(currency);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public void updateCurrency(@PathVariable Long id, @RequestBody Currency currency) {
        currencyService.updateCurrency(id, currency);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteBank(@PathVariable Long id) {
        currencyService.deleteCurrency(id);
    }
}
