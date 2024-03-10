package com.example.currency.dto;

import lombok.Data;

import java.util.List;

@Data
public class BankBranchDTO {
    private String address;
    private Long bankId;
    private List<Long> rateIds;
}