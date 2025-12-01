package com.example.eventsourcingandcqrswithaxonandspringboot.query.service;

import com.example.eventsourcingandcqrswithaxonandspringboot.commonapi.queries.GetAccountQuery;
import com.example.eventsourcingandcqrswithaxonandspringboot.commonapi.queries.GetAllAccountsQuery;
import com.example.eventsourcingandcqrswithaxonandspringboot.query.entities.Account;
import com.example.eventsourcingandcqrswithaxonandspringboot.query.repositories.AccountRepository;
import com.example.eventsourcingandcqrswithaxonandspringboot.query.repositories.OperationRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountQueryService {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;

    public AccountQueryService(AccountRepository accountRepository, OperationRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }

    @QueryHandler
    public List<Account> on(GetAllAccountsQuery query) {
        return accountRepository.findAll();
    }

    @QueryHandler
    public Account on(GetAccountQuery query) {
        return accountRepository.findById(query.getId()).orElse(null);
    }
}
