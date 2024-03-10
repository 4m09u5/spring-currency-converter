package com.example.currency.service;

import com.example.currency.model.Bank;
import com.example.currency.repository.BankRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BankService {
    private BankRepository banks;

    public BankService(BankRepository banks) {
        this.banks = banks;
    }
    public Long createBank(Bank bank) {
        return banks.save(bank).getId();
    }
    public Bank getBankById(Long id) {
        return banks.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Bank> getAll() {
        return banks.findAllByOrderByIdAsc();
    }

    public Bank updateBank(Long id, Bank bank) {
        Bank old = getBankById(id);

        if (bank.getName() != null) old.setName(bank.getName());
        if (bank.getImage() != null) old.setImage(bank.getImage());

        return banks.save(old);
    }

    public void deleteBank(Long id) {
        getBankById(id);
        banks.deleteById(id);
    }
}
