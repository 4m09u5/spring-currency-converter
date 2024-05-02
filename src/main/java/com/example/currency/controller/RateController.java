package com.example.currency.controller;

import com.example.currency.model.Rate;
import com.example.currency.service.RateService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * This class handles connections and initiates necessary business logic for rates.
 *
 * @author Lemiashonak Dzmitry
 * @since 2024-03-26
 */
@CrossOrigin
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
      @RequestParam String from,
      @RequestParam String to,
      @RequestParam Double value,
      @RequestParam String type) {
    return rateService.createRate(branches, from, to, value, type);
  }

  @ResponseStatus(HttpStatus.OK)
  @PatchMapping("/{id}")
  public void updateRate(
      @PathVariable Long id,
      @RequestBody(required = false) List<Long> branches,
      @RequestParam(required = false) String from,
      @RequestParam(required = false) String to,
      @RequestParam(required = false) Double value,
      @RequestParam(required = false) String type) {
    rateService.updateRate(id, branches, from, to, value, type);
  }

  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping("/{id}")
  public void deleteRate(@PathVariable Long id) {
    rateService.deleteRate(id);
  }
}
