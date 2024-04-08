package com.example.currency.controller;

import com.example.currency.model.BankBranch;
import com.example.currency.service.BankBranchService;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToMany;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class handles connections and initiates necessary business logic for bank branches.
 *
 * @author Lemiashonak Dzmitry
 * @since 2024-03-26
 */
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
  @PostMapping("/{bankId}")
  public Long createBranch(@RequestBody BankBranch branch, @PathVariable Long bankId) {
    return branchService.createBranch(branch, bankId);
  }

  @ResponseStatus(HttpStatus.OK)
  @PatchMapping("/{id}")
  public void updateBranch(
      @PathVariable Long id,
      @RequestBody BankBranch branch,
      @RequestParam(required = false) Long bankId) {
    branchService.updateBranch(id, branch, bankId);
  }

  @ManyToMany(cascade = CascadeType.PERSIST)
  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping("/{id}")
  public void deleteBranch(@PathVariable Long id) {
    branchService.deleteBranch(id);
  }
}
