package com.example.currency.service;

import com.example.currency.model.BankBranch;
import com.example.currency.repository.BankBranchRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * This class implements BankBranch business logic.
 *
 * @author Lemiashonak Dzmitry
 * @since 2024-03-26
 */
@Service
public class BankBranchService {
  private final BankBranchRepository branches;
  private final BankService banks;

  public BankBranchService(BankBranchRepository branches, BankService banks) {
    this.branches = branches;
    this.banks = banks;
  }

  /**
   * This method saves given bank branch to database and returns its record id.
   *
   * @param branch bank branch entity to save
   * @param bankId owner bank id
   * @author Lemiashonak Dzmitry
   * @since 2024-03-26
   */
  public Long createBranch(BankBranch branch, Long bankId) {
    BankBranch resultBranch = branch;
    resultBranch.setBank(banks.getBankById(bankId));
    return branches.save(branch).getId();
  }

  /**
   * This method gets BankBranch entity with provided id from database.
   *
   * @param id record id to fetch
   * @author Lemiashonak Dzmitry
   * @since 2024-03-26
   */
  public BankBranch getBranchById(Long id) {
    return branches
        .findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Internal error. Branch not found"));
  }

  /**
   * This method provides List of all BankBranches present at database.
   *
   * @author Lemiashonak Dzmitry
   * @since 2024-03-26
   */
  public List<BankBranch> getAll() {
    return branches.findAllByOrderByIdAsc();
  }

  /**
   * This method updates record with provided id according to given entity.
   *
   * @param id record id to modify
   * @param branch entity with new data
   * @param bankId owner bank id
   * @author Lemiashonak Dzmitry
   * @since 2024-03-26
   */
  public BankBranch updateBranch(Long id, BankBranch branch, Long bankId) {
    BankBranch old = getBranchById(id);

    if (bankId != null) {
      old.setBank(banks.getBankById(bankId));
    }
    if (branch.getAddress() != null) {
      old.setAddress(branch.getAddress());
    }

    return branches.save(old);
  }

  /**
   * This method removes BankBranch record with provided id from database.
   *
   * @param id BankBranch record id to remove
   * @author Lemiashonak Dzmitry
   * @since 2024-03-26
   */
  public void deleteBranch(Long id) {
    getBranchById(id);
    branches.deleteById(id);
  }
}
