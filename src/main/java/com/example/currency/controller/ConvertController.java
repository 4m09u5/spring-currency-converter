package com.example.currency.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/currency", produces = "application/json")
public class ConvertController {
    @GetMapping("/convert")
    public String convert(@RequestParam("from") String from,
                          @RequestParam("to") String to,
                          @RequestParam("value") double value) {
        return "{\"converted\": 123.45}";
    }
}