package com.example.currency.controller;

import com.example.currency.model.Conversion;
import com.example.currency.service.ConvertService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/currency", produces = "application/json")
public class ConvertController {
    private final ConvertService convertService;

    public ConvertController(final ConvertService convertService) {
        this.convertService = convertService;
    }

    @GetMapping("/convert")
    public String convert(@RequestParam("from") String from,
                          @RequestParam("to") String to,
                          @RequestParam("value") double value) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Conversion result = convertService.convert(from, to, value);

        return objectMapper.writeValueAsString(result);
    }
}