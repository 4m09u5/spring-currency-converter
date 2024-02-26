package com.example.currency.service;

import com.example.currency.model.Conversion;
import org.springframework.stereotype.Service;

@Service
public class ConvertService {

    public Conversion convert(String from, String to, double value) {
        return new Conversion(from, to, value * 2);
    }
}
