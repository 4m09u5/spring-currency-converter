package com.currency.api.service;

import com.example.currency.model.Bank;
import com.example.currency.model.Currency;
import com.example.currency.model.Rate;
import com.example.currency.repository.BankRepository;
import com.example.currency.service.BankService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BankServiceTest {
    @InjectMocks
    private BankService bankService;

    @Mock
    private BankRepository bankRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllBanks() {
        List<Bank> mockBanks = Arrays.asList(new Bank(new ArrayList<>(), 1L, "Super bank", "/img/super.svg"));

        when(bankRepository.findAllByOrderByIdAsc()).thenReturn(mockBanks);

        List<Bank> result = bankService.getAll();

        assertEquals(mockBanks, result);
    }

    @Test
    void testGetBankById() {
        Long id = 1L;

        Bank expectedBank = new Bank(new ArrayList<>(), id, "Super bank", "/img/super.svg");

        when(bankRepository.findById(id)).thenReturn(Optional.of(expectedBank));

        Bank result = bankService.getBankById(id);

        assertEquals(expectedBank, result);
    }

    @Test
    void testDeleteBankById() {
        Long id = 1L;

        doNothing().when(bankRepository).deleteById(id);

        bankService.deleteBank(id);

        verify(bankRepository).deleteById(id);
    }

    @Test
    void testUpdateBank() {
        Long id = 1L;

        Bank oldBank = new Bank(new ArrayList<>(), id, "Super bank", "/img/super.svg");
        Bank newBank = new Bank(new ArrayList<>(), id, "Mega bank", "/img/mega.svg");

        when(bankRepository.findById(id)).thenReturn(Optional.of(oldBank));
        when(bankRepository.save(any(Bank.class))).thenAnswer(invocation -> {
            Bank bank = invocation.getArgument(0);

            oldBank.setName(bank.getName());
            oldBank.setBranches(bank.getBranches());
            oldBank.setImage(bank.getImage());

            return oldBank;
        });

        Bank result = bankService.updateBank(id, newBank);

        assertEquals(newBank, result);
    }

    @Test
    void testCreateBank() {
        Bank bank = new Bank(new ArrayList<>(), 0L, "Super bank", "/img/super.svg");

        when(bankRepository.save(bank)).thenReturn(bank);

        bankService.createBank(bank);

        verify(bankRepository).save(bank);
    }

    @Test
    void testGetBestBankRate() {
        Currency byn = new Currency(1L, "Belarusian rouble", "BYN");
        Currency usd = new Currency(2L, "United States dollar", "USD");

        Rate bestRate = new Rate(2L, usd, byn, 1.0, "buy", new ArrayList<>());

        Long id = 1L;
        String from = usd.getAbbreviation();
        String to = byn.getAbbreviation();
        String type = "sell";

        List<Rate> rates = Arrays.asList(bestRate);

        when(bankRepository.getBestBankRate(id, from, to, type)).thenReturn(rates);

        Rate best = bankService.getBestBankRate(id, from, to, type);

        assertEquals(best, bestRate);
    }
}