package com.example.currency.service;

import com.example.currency.model.BankBranch;
import com.example.currency.repository.BankBranchRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BankBranchService {
    private BankBranchRepository branches;
    private BankService banks;

    public BankBranchService(BankBranchRepository branches, BankService banks) {
        this.branches = branches;
        this.banks = banks;
    }
    public Long createBranch(BankBranch branch, Long bankId) {
        BankBranch resultBranch = branch;
        resultBranch.setBank(banks.getBankById(bankId));
        return branches.save(branch).getId();
    }
    public BankBranch getBranchById(Long id) {
        return branches.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<BankBranch> getAll() {
        return branches.findAllByOrderByIdAsc();
    }

    public BankBranch updateBranch(Long id, BankBranch branch, Long bankId) {
        BankBranch old = getBranchById(id);

        if (bankId != null) old.setBank(banks.getBankById(bankId));
        if (branch.getAddress() != null) old.setAddress(branch.getAddress());

        return branches.save(old);
    }

    public void deleteBranch(Long id) {
        getBranchById(id);
        branches.deleteById(id);
    }
}
