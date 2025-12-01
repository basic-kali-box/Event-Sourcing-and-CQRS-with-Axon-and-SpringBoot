package com.example.eventsourcingandcqrswithaxonandspringboot.commonapi.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAccountQuery {
    private String id;
}
