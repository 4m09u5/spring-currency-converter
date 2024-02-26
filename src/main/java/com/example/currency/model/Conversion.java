package com.example.currency.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Conversion {
    @JsonProperty("from")
    private String from;
    @JsonProperty("to")
    private String to;
    @JsonProperty("value")
    private double value;

    public Conversion(String from, String to, double value) {
        this.from = from;
        this.to = to;
        this.value = value;
    }
}
