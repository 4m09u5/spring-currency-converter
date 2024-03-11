package com.example.currency.service;

import com.example.currency.model.BankBranch;
import com.example.currency.model.Rate;
import com.example.currency.repository.RateRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class RateService {
    private RateRepository rates;
    private CurrencyService currencies;
    private BankBranchService branches;

    public RateService(RateRepository rates, CurrencyService currencies, BankBranchService branches) {
        this.rates = rates;
        this.currencies = currencies;
        this.branches = branches;
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

        return rates.save(result).getId();
    }
    public Rate getRateById(Long id) {
        return rates.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Rate> getAll() {
        return rates.findAllByOrderByIdAsc();
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

        return rates.save(old);
    }

    public void deleteRate(Long id) {
        getRateById(id);
        rates.deleteById(id);
    }
}
