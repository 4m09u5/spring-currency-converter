package com.example.currency.controller;

import com.example.currency.model.Bank;
import com.example.currency.service.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bank")
public class BankController {
    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping("/{id}")
    public Bank getBank(@PathVariable Long id) {
        return bankService.getBankById(id);
    }

    @GetMapping
    public List<Bank> getAllBanks() {
        return bankService.getAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Long createBank(@RequestBody(required = true) Bank bank) {
        return bankService.createBank(bank);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public void updateBank(@PathVariable Long id, @RequestBody Bank bank) {
        bankService.updateBank(id, bank);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteBank(@PathVariable Long id) {
        bankService.deleteBank(id);
    }
}
