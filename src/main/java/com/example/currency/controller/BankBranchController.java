package com.example.currency.controller;

import com.example.currency.model.BankBranch;
import com.example.currency.service.BankBranchService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/branch")
public class BankBranchController {
    private final BankBranchService branchService;

    public BankBranchController(BankBranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping("/{id}")
    public BankBranch getBranch(@PathVariable Long id) {
        return branchService.getBranchById(id);
    }

    @GetMapping
    public List<BankBranch> getAllBranches() {
        return branchService.getAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Long createBranch(@RequestBody(required = true) BankBranch branch) {
        return branchService.createBranch(branch);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public void updateBranch(@PathVariable Long id, @RequestBody BankBranch branch) {
        branchService.updateBranch(id, branch);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteBranch(@PathVariable Long id) {
        branchService.deleteBranch(id);
    }
}
