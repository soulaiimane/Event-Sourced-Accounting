package ma.soulaimane.comptecqrseventsourcing.query.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.soulaimane.comptecqrseventsourcing.commonapi.query.GetAccountsByIdQuery;
import ma.soulaimane.comptecqrseventsourcing.commonapi.query.GetAllAccountsQuery;
import ma.soulaimane.comptecqrseventsourcing.query.entities.Account;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/query")
@AllArgsConstructor
@Slf4j

public class AccountQueryController {
    private QueryGateway queryGateway;
    @GetMapping("/accounts")
    public List<Account> accountList(){
        List<Account> accounts = queryGateway.query(new GetAllAccountsQuery(), ResponseTypes.multipleInstancesOf(Account.class)).join();
        return accounts;
    }
    @GetMapping("/accounts/{accountId}")
    public Account accountList(@PathVariable String accountId ){
        Account account = queryGateway.query(new GetAccountsByIdQuery(accountId), ResponseTypes.instanceOf(Account.class)).join();
        return account;
    }
}
