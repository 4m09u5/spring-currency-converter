package com.currency.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.currency.model.Bank;
import com.example.currency.model.BankBranch;
import com.example.currency.model.Currency;
import com.example.currency.model.Rate;
import com.example.currency.repository.RateRepository;
import com.example.currency.service.BankBranchService;
import com.example.currency.service.CurrencyService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.currency.service.RateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RateServiceTest {
    @InjectMocks
    private RateService rateService;

    @Mock
    private RateRepository rateRepository;

    @Mock
    private CurrencyService currencyService;

    @Mock
    private BankBranchService branchService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllCurrencies() {
        Long rateId = 1L;

        Currency from = new Currency(1L, "Super Money", "SMN");
        Currency to = new Currency(2L, "Mega Money", "MMN");

        Bank bank = new Bank(new ArrayList<>(), 1L, "Super bank", "/img/super.svg");

        List<BankBranch> branches = Arrays.asList(new BankBranch(1L, bank, "ул. Пушкина, д. Толотушкина", new ArrayList<>()));

        List<Rate> rates = Arrays.asList(new Rate(rateId, from, to, 1.1, "sell", branches));

        when(rateRepository.findAllByOrderByIdAsc()).thenReturn(rates);

        List<Rate> result = rateService.getAll();

        assertEquals(rates, result);
    }

    @Test
    void testGetCurrencyById() {
        Long rateId = 1L;

        Currency from = new Currency(1L, "Super Money", "SMN");
        Currency to = new Currency(2L, "Mega Money", "MMN");

        Bank bank = new Bank(new ArrayList<>(), 1L, "Super bank", "/img/super.svg");

        List<BankBranch> branches = Arrays.asList(new BankBranch(1L, bank, "ул. Пушкина, д. Толотушкина", new ArrayList<>()));

        Rate rate = new Rate(rateId, from, to, 1.1, "sell", branches);

        when(rateRepository.findById(rateId)).thenReturn(Optional.of(rate));

        Rate result = rateService.getRateById(rateId);

        assertEquals(rate, result);
    }

    @Test
    void testDeleteCurrencyById() {
        Long id = 1L;

        doNothing().when(rateRepository).deleteById(id);

        rateService.deleteRate(id);

        verify(rateRepository).deleteById(id);
    }

    @Test
    void testUpdateCurrency() {
        Long rateId = 1L;
        Long branchId = 1L;
        Long fromId = 1L;
        Long toId = 2L;

        Currency from = new Currency(fromId, "Super Money", "SMN");
        Currency to = new Currency(toId, "Mega Money", "MMN");

        Bank bank = new Bank(new ArrayList<>(), 1L, "Super bank", "/img/super.svg");

        List<BankBranch> branches = Arrays.asList(new BankBranch(branchId, bank, "ул. Пушкина, д. Толотушкина", new ArrayList<>()));

        Rate oldRate = new Rate(rateId, from, to, 1.1, "sell", branches);
        Rate newRate = new Rate(rateId, from, to, 1.6, "sell", branches);

        when(branchService.getBranchById(branchId)).thenReturn(branches.get(0));
        when(currencyService.getCurrencyById(fromId)).thenReturn(from);
        when(currencyService.getCurrencyById(toId)).thenReturn(to);
        when(rateRepository.findById(rateId)).thenReturn(Optional.of(oldRate));
        when(rateRepository.save(any(Rate.class))).thenAnswer(invocation -> {
            Rate rate = invocation.getArgument(0);

            oldRate.setFromCurrency(rate.getFromCurrency());
            oldRate.setToCurrency(rate.getToCurrency());
            oldRate.setBranches(rate.getBranches());
            oldRate.setType(rate.getType());
            oldRate.setValue(rate.getValue());

            return oldRate;
        });

        Rate result =
        rateService.updateRate(
            rateId,
                List.of(branchId),
            fromId,
            toId,
            newRate.getValue(),
            newRate.getType());

        assertEquals(newRate, result);
    }

    @Test
    void testCreateRate() {
        Long branchId = 1L;
        Long fromId = 1L;
        Long toId = 2L;

        Currency from = new Currency(fromId, "Super Money", "SMN");
        Currency to = new Currency(toId, "Mega Money", "MMN");

        Bank bank = new Bank(new ArrayList<>(), 1L, "Super bank", "/img/super.svg");

        List<BankBranch> branches = Arrays.asList(new BankBranch(branchId, bank, "ул. Пушкина, д. Толотушкина", new ArrayList<>()));

        Rate rate = new Rate(null, from, to, 1.1, "sell", branches);

        when(branchService.getBranchById(branchId)).thenReturn(branches.get(0));
        when(currencyService.getCurrencyById(fromId)).thenReturn(from);
        when(currencyService.getCurrencyById(toId)).thenReturn(to);
        when(rateRepository.save(rate)).thenReturn(rate);

        rateService.createRate(
                List.of(branchId),
                fromId,
                toId,
                rate.getValue(),
                rate.getType());

        verify(rateRepository).save(rate);
    }
}