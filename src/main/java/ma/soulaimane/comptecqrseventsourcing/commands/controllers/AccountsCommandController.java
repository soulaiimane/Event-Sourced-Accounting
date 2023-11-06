package ma.soulaimane.comptecqrseventsourcing.commands.controllers;

import lombok.AllArgsConstructor;
import ma.soulaimane.comptecqrseventsourcing.commonapi.commands.CreatAccountCommand;
import ma.soulaimane.comptecqrseventsourcing.commonapi.commands.CreditAccountCommand;
import ma.soulaimane.comptecqrseventsourcing.commonapi.commands.DebitAccountCommand;
import ma.soulaimane.comptecqrseventsourcing.commonapi.dtos.CreateAccountRequestDtos;
import ma.soulaimane.comptecqrseventsourcing.commonapi.dtos.CreditAccountRequestDtos;
import ma.soulaimane.comptecqrseventsourcing.commonapi.dtos.DebitAccountRequestDtos;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = "/commands/account")
@AllArgsConstructor
public class AccountsCommandController {
    private CommandGateway commandGateway;
    private EventStore eventStore;
    @PostMapping(path = "/create")
    public CompletableFuture<String> createNewAccount(@RequestBody CreateAccountRequestDtos requestDtos){
        CompletableFuture<String> commandResponse = commandGateway.send(new CreatAccountCommand(
                UUID.randomUUID().toString(),
                requestDtos.getInitialBalance(),
                requestDtos.getCurrency()
        ));
        return commandResponse;
    }
    @PutMapping(path = "/credit")
    public CompletableFuture<String> creditNewAccount(@RequestBody CreditAccountRequestDtos requestDtos){
        CompletableFuture<String> commandResponse = commandGateway.send(new CreditAccountCommand(
                requestDtos.getAccountId(),
                new Date(),
                requestDtos.getAmount(),
                requestDtos.getCurrency()
        ));
        return commandResponse;
    }
    @PutMapping(path = "/debit")
    public CompletableFuture<String> debitNewAccount(@RequestBody DebitAccountRequestDtos requestDtos){
        CompletableFuture<String> commandResponse = commandGateway.send(new DebitAccountCommand(
                requestDtos.getAccountId(),
                new Date(),
                requestDtos.getAmount(),
                requestDtos.getCurrency()
        ));
        return commandResponse;
    }
    @ExceptionHandler
    public ResponseEntity<String> exceptionHandler(Exception exception){
        ResponseEntity<String> entity=new ResponseEntity<>(
                exception.getMessage(),
        HttpStatus.INTERNAL_SERVER_ERROR
        );
        return entity;

    }
    @GetMapping("/eventStore/{accountId}")
    public Stream AllEventForAccount(@PathVariable String accountId){
        return eventStore.readEvents(accountId).asStream();
    }
}
