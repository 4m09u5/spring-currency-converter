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

/**
 * This class implements Rate business logic.

 * @author Lemiashonak Dzmitry
 * @since 2024-03-26
 */
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

    /**
     * This method saves given bank branch to database and returns its record id.

     * @param branchIds list of branches that perform exchange with this rate
     * @param fromId 'from' Currency id
     * @param toId 'to' Currency id
     * @param value exchange ratio
     * @param type exchange type
     * @author Lemiashonak Dzmitry
     * @since 2024-03-26
     */
    public Long createRate(List<Long> branchIds, Long fromId, Long toId, Double value, String type) {
        Rate result = new Rate();

        if (value != null) result.setValue(value);
        if (type != null) result.setType(type);
        if (fromId != null) result.setFromCurrency(currencies.getCurrencyById(fromId));
        if (toId != null) result.setToCurrency(currencies.getCurrencyById(toId));

        if (branchIds != null) {
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

    /**
     * This method gets Rate entity with provided id from database.

     * @param id record id to fetch
     * @author Lemiashonak Dzmitry
     * @since 2024-03-26
     */
    public Rate getRateById(Long id) {
        Optional<Rate> cached = cache.getCachedById(id);
        if (cached.isPresent())
            return cached.get();

        Rate rate = rates.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        cache.saveCached(rate.getId(), rate);

        return rate;
    }

    /**
     * This method returns List of all Rates present at database.

     * @author Lemiashonak Dzmitry
     * @since 2024-03-26
     */
    public List<Rate> getAll() {
        List<Rate> all = rates.findAllByOrderByIdAsc();
        for (Rate rate : all) {
            cache.saveCached(rate.getId(), rate);
        }

        return all;
    }

    /**
     * This method updates record with provided id according to given data.

     * @param id record id to modify
     * @param branchIds list of branches that perform exchange with this rate
     * @param fromId 'from' Currency id
     * @param toId 'to' Currency id
     * @param value exchange ratio
     * @param type exchange type
     * @author Lemiashonak Dzmitry
     * @since 2024-03-26
     */
    public Rate updateRate(Long id,
                           List<Long> branchIds,
                           Long fromId, Long toId,
                           Double value,
                           String type) {
        Rate old = getRateById(id);

        if (value != null) {
            old.setValue(value);
        }
        if (type != null) {
            old.setType(type);
        }
        if (fromId != null) {
            old.setFromCurrency(currencies.getCurrencyById(fromId));
        }
        if (toId != null) {
            old.setToCurrency(currencies.getCurrencyById(toId));
        }

        if (branchIds != null) {
            List<BankBranch> newBranches = new ArrayList<>();
            for (Long branchId : branchIds) {
                newBranches.add(branches.getBranchById(branchId));
            }
            old.setBranches(newBranches);
        }

        Rate saved = rates.save(old);
        return cache.saveCached(saved.getId(), saved);
    }

    /**
     * This method removes Rate record with provided id from database.

     * @param id Rate record id to remove
     * @author Lemiashonak Dzmitry
     * @since 2024-03-26
     */
    public void deleteRate(Long id) {
        getRateById(id);
        rates.deleteById(id);
        cache.deleteCachedById(id);
    }
}
