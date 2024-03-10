package com.example.currency.service;

import com.example.currency.model.Rate;
import com.example.currency.repository.RateRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RateService {
    private RateRepository rates;

    public RateService(RateRepository rates) {
        this.rates = rates;
    }
    public Long createRate(Rate rate) {
        return rates.save(rate).getId();
    }
    public Rate getRateById(Long id) {
        return rates.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Rate> getAll() {
        return rates.findAllByOrderByIdAsc();
    }

    public Rate updateValue(Long id, Rate rate) {
        Rate old = getRateById(id);

        if (rate.getValue() != null) old.setValue(rate.getValue());
        if (rate.getType() != null) old.setType(rate.getType());

        return rates.save(old);
    }

    public void deleteRate(Long id) {
        getRateById(id);
        rates.deleteById(id);
    }
}
