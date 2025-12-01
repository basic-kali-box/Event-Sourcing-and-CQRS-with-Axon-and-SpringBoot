package com.example.eventsourcingandcqrswithaxonandspringboot.query.controllers;

import com.example.eventsourcingandcqrswithaxonandspringboot.commonapi.queries.GetAccountQuery;
import com.example.eventsourcingandcqrswithaxonandspringboot.commonapi.queries.GetAllAccountsQuery;
import com.example.eventsourcingandcqrswithaxonandspringboot.query.entities.Account;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/query/accounts")
public class AccountQueryController {
    private QueryGateway queryGateway;

    public AccountQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/allAccounts")
    public CompletableFuture<List<Account>> accountList() {
        return queryGateway.query(new GetAllAccountsQuery(), ResponseTypes.multipleInstancesOf(Account.class));
    }

    @GetMapping("/byId/{id}")
    public CompletableFuture<Account> getAccount(@PathVariable String id) {
        return queryGateway.query(new GetAccountQuery(id), ResponseTypes.instanceOf(Account.class));
    }
}
