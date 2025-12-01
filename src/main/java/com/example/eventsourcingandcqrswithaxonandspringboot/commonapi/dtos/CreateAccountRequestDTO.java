package com.example.eventsourcingandcqrswithaxonandspringboot.commonapi.dtos;

import lombok.Data;

@Data
public class CreateAccountRequestDTO {
    private double initialBalance;
    private String currency;
}
