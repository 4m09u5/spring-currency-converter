package com.example.currency.controller;

import com.example.currency.model.Rate;
import com.example.currency.service.RateService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Rate> getALlRates() {
        return rateService.getAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Long createRate(@RequestBody(required = true) Rate rate) {
        return rateService.createRate(rate);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public void updateRate(@PathVariable Long id, @RequestBody Rate rate) {
        rateService.updateValue(id, rate);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteRate(@PathVariable Long id) {
        rateService.deleteRate(id);
    }
}
