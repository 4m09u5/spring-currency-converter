package com.currency.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.currency.model.Bank;
import com.example.currency.model.BankBranch;
import com.example.currency.model.Currency;
import com.example.currency.model.Rate;
import com.example.currency.repository.BankBranchRepository;
import com.example.currency.repository.BankRepository;
import com.example.currency.service.BankBranchService;

import java.util.*;

import com.example.currency.service.BankService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BankBranchServiceTest {
    @InjectMocks
    private BankBranchService branchService;

    @Mock
    private BankBranchRepository branchRepository;

    @Mock
    private BankService bankService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAllBranches() {
        Bank bank = new Bank(new ArrayList<>(), 1L, "Super bank", "/img/super.svg");

        List<BankBranch> mockBranches =
        Arrays.asList(new BankBranch(1L, bank, "ул. Пушкина, д. Колотушкина", new ArrayList<>()));

        when(branchRepository.findAllByOrderByIdAsc()).thenReturn(mockBranches);

        List<BankBranch> result = branchService.getAll();

        assertEquals(mockBranches, result);
    }

    @Test
    void testGetBranchById() {
        Long id = 1L;

        Bank bank = new Bank(new ArrayList<>(), 1L, "Super bank", "/img/super.svg");

        BankBranch expectedBranch = new BankBranch(1L, bank, "ул. Пушкина, д. Колотушкина", new ArrayList<>());

        when(branchRepository.findById(id)).thenReturn(Optional.of(expectedBranch));

        BankBranch result = branchService.getBranchById(id);

        assertEquals(expectedBranch, result);
    }

    @Test
    void testDeleteBranchById() {
        Long id = 1L;

        doNothing().when(branchRepository).deleteById(id);

        branchService.deleteBranch(id);

        verify(branchRepository).deleteById(id);
    }

    @Test
    void testUpdateBranch() {
        Long branchId = 1L;
        Long bankId = 2L;

        Bank bank = new Bank(new ArrayList<>(), bankId, "Super bank", "/img/super.svg");

        BankBranch oldBranch = new BankBranch(branchId, bank, "ул. Пушкина, д. Толотушкина", new ArrayList<>());
        BankBranch newBranch = new BankBranch(branchId, bank, "ул. Ленина, д. 777", new ArrayList<>());

        when(bankService.getBankById(bankId)).thenReturn(bank);
        when(branchRepository.findById(branchId)).thenReturn(Optional.of(oldBranch));
        when(branchRepository.save(any(BankBranch.class))).thenAnswer(invocation -> {
            BankBranch branch = invocation.getArgument(0);

            oldBranch.setBank(branch.getBank());
            oldBranch.setRates(branch.getRates());
            oldBranch.setAddress(branch.getAddress());

            return oldBranch;
        });

        BankBranch result = branchService.updateBranch(branchId, newBranch, bank.getId());

        assertEquals(newBranch, result);
    }

    @Test
    void testCreateBranch() {
        Bank bank = new Bank(new ArrayList<>(), 1L, "Super bank", "/img/super.svg");

        BankBranch branch = new BankBranch(1L, bank, "ул. Пушкина, д. Толотушкина", new ArrayList<>());

        when(bankService.createBank(bank)).thenReturn(bank.getId());
        when(branchRepository.save(branch)).thenReturn(branch);

        branchService.createBranch(branch, bank.getId());

        verify(branchRepository).save(branch);
    }
}