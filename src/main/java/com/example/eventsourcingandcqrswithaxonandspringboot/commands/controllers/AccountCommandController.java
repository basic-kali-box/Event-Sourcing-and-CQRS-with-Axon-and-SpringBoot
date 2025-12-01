package com.example.eventsourcingandcqrswithaxonandspringboot.commands.controllers;

import com.example.eventsourcingandcqrswithaxonandspringboot.commonapi.commands.CreateAccountCommand;
import com.example.eventsourcingandcqrswithaxonandspringboot.commonapi.commands.CreditAccountCommand;
import com.example.eventsourcingandcqrswithaxonandspringboot.commonapi.commands.DebitAccountCommand;
import com.example.eventsourcingandcqrswithaxonandspringboot.commonapi.dtos.CreateAccountRequestDTO;
import com.example.eventsourcingandcqrswithaxonandspringboot.commonapi.dtos.CreditAccountRequestDTO;
import com.example.eventsourcingandcqrswithaxonandspringboot.commonapi.dtos.DebitAccountRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(path = "/commands/account")
public class AccountCommandController {
    private CommandGateway commandGateway;

    public AccountCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping(path = "/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountRequestDTO request) {
        CompletableFuture<String> commandResponse = commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                request.getInitialBalance(),
                request.getCurrency()
        ));
        return commandResponse;
    }

    @PutMapping(path = "/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDTO request) {
        CompletableFuture<String> commandResponse = commandGateway.send(new CreditAccountCommand(
                request.getAccountId(),
                request.getAmount(),
                request.getCurrency()
        ));
        return commandResponse;
    }

    @PutMapping(path = "/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDTO request) {
        CompletableFuture<String> commandResponse = commandGateway.send(new DebitAccountCommand(
                request.getAccountId(),
                request.getAmount(),
                request.getCurrency()
        ));
        return commandResponse;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
