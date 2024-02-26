package com.example.currency.service;

import org.springframework.stereotype.Service;

@Service
public class ConvertService {

    public String convert(String from, String to, double value) {
        return "{\"converted\": 123.45}";
    }
}
