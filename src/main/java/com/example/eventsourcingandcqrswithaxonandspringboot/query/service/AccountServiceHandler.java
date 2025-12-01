package com.example.eventsourcingandcqrswithaxonandspringboot.query.service;

import com.example.eventsourcingandcqrswithaxonandspringboot.commonapi.enums.AccountStatus;
import com.example.eventsourcingandcqrswithaxonandspringboot.commonapi.enums.OperationType;
import com.example.eventsourcingandcqrswithaxonandspringboot.commonapi.events.AccountActivatedEvent;
import com.example.eventsourcingandcqrswithaxonandspringboot.commonapi.events.AccountCreatedEvent;
import com.example.eventsourcingandcqrswithaxonandspringboot.commonapi.events.AccountCreditedEvent;
import com.example.eventsourcingandcqrswithaxonandspringboot.commonapi.events.AccountDebitedEvent;
import com.example.eventsourcingandcqrswithaxonandspringboot.query.entities.Account;
import com.example.eventsourcingandcqrswithaxonandspringboot.query.entities.AccountOperation;
import com.example.eventsourcingandcqrswithaxonandspringboot.query.repositories.AccountRepository;
import com.example.eventsourcingandcqrswithaxonandspringboot.query.repositories.OperationRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
@Slf4j
public class AccountServiceHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;

    public AccountServiceHandler(AccountRepository accountRepository, OperationRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }

    @EventHandler
    public void on(AccountCreatedEvent event) {
        log.info("AccountCreatedEvent received");
        Account account = Account.builder()
                .id(event.getId())
                .balance(event.getInitialBalance())
                .currency(event.getCurrency())
                .status(AccountStatus.valueOf(event.getStatus()))
                .build();
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountActivatedEvent event) {
        log.info("AccountActivatedEvent received");
        Account account = accountRepository.findById(event.getId()).orElse(null);
        if (account != null) {
            account.setStatus(AccountStatus.valueOf(event.getStatus()));
            accountRepository.save(account);
        }
    }

    @EventHandler
    public void on(AccountCreditedEvent event) {
        log.info("AccountCreditedEvent received");
        Account account = accountRepository.findById(event.getId()).orElse(null);
        if (account != null) {
            account.setBalance(account.getBalance() + event.getAmount());
            accountRepository.save(account);
            AccountOperation operation = AccountOperation.builder()
                    .amount(event.getAmount())
                    .operationDate(new Date()) // Should ideally come from event metadata
                    .type(OperationType.CREDIT)
                    .account(account)
                    .build();
            operationRepository.save(operation);
        }
    }

    @EventHandler
    public void on(AccountDebitedEvent event) {
        log.info("AccountDebitedEvent received");
        Account account = accountRepository.findById(event.getId()).orElse(null);
        if (account != null) {
            account.setBalance(account.getBalance() - event.getAmount());
            accountRepository.save(account);
            AccountOperation operation = AccountOperation.builder()
                    .amount(event.getAmount())
                    .operationDate(new Date())
                    .type(OperationType.DEBIT)
                    .account(account)
                    .build();
            operationRepository.save(operation);
        }
    }
}
