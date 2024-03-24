package com.example.currency.service;

import com.example.currency.component.Cache;
import com.example.currency.model.BankBranch;
import com.example.currency.model.Rate;
import com.example.currency.repository.RateRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RateService {
    private final RateRepository rates;
    private final CurrencyService currencies;
    private final BankBranchService branches;
    private final Cache<Rate, Long> cache;

    public RateService(RateRepository rates, CurrencyService currencies, BankBranchService branches) {
        this.rates = rates;
        this.currencies = currencies;
        this.branches = branches;
        this.cache = new Cache<>();
    }
    public Long createRate(List<Long> branchIds, Long fromId, Long toId, Double value, String type) {
        Rate result = new Rate();

        if (value != null) result.setValue(value);
        if (type != null) result.setType(type);
        if (fromId != null) result.setFromCurrency(currencies.getCurrencyById(fromId));
        if (toId != null) result.setToCurrency(currencies.getCurrencyById(toId));

        if(branchIds != null) {
            List<BankBranch> newBranches = new ArrayList<>();
            for (Long branchId : branchIds) {
                newBranches.add(branches.getBranchById(branchId));
            }
            result.setBranches(newBranches);
        }

        Long newRate = rates.save(result).getId();
        cache.saveCached(newRate, result);
        return newRate;
    }
    public Rate getRateById(Long id) {
        Optional<Rate> cached = cache.getCachedById(id);
        if (cached.isPresent())
            return cached.get();

        Rate rate = rates.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        cache.saveCached(rate.getId(), rate);

        return rate;
    }

    public List<Rate> getAll() {
        List<Rate> all = rates.findAllByOrderByIdAsc();
        for (Rate rate : all)
            cache.saveCached(rate.getId(), rate);

        return all;
    }

    public Rate updateValue(Long id, List<Long> branchIds, Long fromId, Long toId, Double value, String type) {
        Rate old = getRateById(id);

        if (value != null) old.setValue(value);
        if (type != null) old.setType(type);
        if (fromId != null) old.setFromCurrency(currencies.getCurrencyById(fromId));
        if (toId != null) old.setToCurrency(currencies.getCurrencyById(toId));

        if(branchIds != null) {
            List<BankBranch> newBranches = new ArrayList<>();
            for (Long branchId : branchIds) {
                newBranches.add(branches.getBranchById(branchId));
            }
            old.setBranches(newBranches);
        }


        Rate saved = rates.save(old);
        return cache.saveCached(saved.getId(), saved);
    }

    public void deleteRate(Long id) {
        getRateById(id);
        rates.deleteById(id);
        cache.deleteCachedById(id);
    }
}
