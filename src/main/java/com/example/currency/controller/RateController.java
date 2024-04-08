package com.example.currency.controller;

import com.example.currency.model.Rate;
import com.example.currency.service.RateService;
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
 * This class handles connections and initiates necessary business logic for rates.
 *
 * @author Lemiashonak Dzmitry
 * @since 2024-03-26
 */
@RestController
@RequestMapping("/api/v1/rate")
public class RateController {
  private final RateService rateService;

  public RateController(RateService rateService) {
    this.rateService = rateService;
  }

  @GetMapping("/{id}")
  public Rate getRate(@PathVariable Long id) {
    return rateService.getRateById(id);
  }

  @GetMapping
  public List<Rate> getAllRates() {
    return rateService.getAll();
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public Long createRate(
      @RequestBody(required = false) List<Long> branches,
      @RequestParam(required = false) Long fromId,
      @RequestParam(required = false) Long toId,
      @RequestParam(required = false) Double value,
      @RequestParam(required = false) String type) {
    return rateService.createRate(branches, fromId, toId, value, type);
  }

  @ResponseStatus(HttpStatus.OK)
  @PatchMapping("/{id}")
  public void updateRate(
      @PathVariable Long id,
      @RequestBody(required = false) List<Long> branches,
      @RequestParam(required = false) Long fromId,
      @RequestParam(required = false) Long toId,
      @RequestParam(required = false) Double value,
      @RequestParam(required = false) String type) {
    rateService.updateRate(id, branches, fromId, toId, value, type);
  }

  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping("/{id}")
  public void deleteRate(@PathVariable Long id) {
    rateService.deleteRate(id);
  }
}
