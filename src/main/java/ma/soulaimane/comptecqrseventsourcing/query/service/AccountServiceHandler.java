package ma.soulaimane.comptecqrseventsourcing.query.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.soulaimane.comptecqrseventsourcing.commonapi.enums.OperationType;
import ma.soulaimane.comptecqrseventsourcing.commonapi.events.AccountActivatedEvent;
import ma.soulaimane.comptecqrseventsourcing.commonapi.events.AccountCreatedEvent;
import ma.soulaimane.comptecqrseventsourcing.commonapi.events.AccountCreditedEvent;
import ma.soulaimane.comptecqrseventsourcing.commonapi.events.AccountDebitedEvent;
import ma.soulaimane.comptecqrseventsourcing.commonapi.query.GetAccountsByIdQuery;
import ma.soulaimane.comptecqrseventsourcing.commonapi.query.GetAllAccountsQuery;
import ma.soulaimane.comptecqrseventsourcing.query.entities.Account;
import ma.soulaimane.comptecqrseventsourcing.query.entities.Operation;
import ma.soulaimane.comptecqrseventsourcing.query.repository.AccountRepository;
import ma.soulaimane.comptecqrseventsourcing.query.repository.OperationRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class AccountServiceHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;
    @EventHandler
    public void on(AccountCreatedEvent event){
        log.info("***************************");
        log.info("AccountCreatedEvent Received Successfully");
        Account account=new Account();
        account.setId(event.getId());
        account.setCurrency(event.getCurrency());
        account.setBalance(event.getInitialBalance());
        account.setStatus(event.getStatus());
        accountRepository.save(account);


    }
    @EventHandler
    public void on(AccountActivatedEvent event){
        log.info("***************************");
        log.info("AccountActivatedEvent Received Successfully");
        Account account = accountRepository.getById(event.getId());
        account.setStatus(event.getStatus());
        accountRepository.save(account);


    }
    @EventHandler
    public void on(AccountDebitedEvent event){
        log.info("***************************");
        log.info("AccountDebitedEvent Received Successfully");
        Account account = accountRepository.getById(event.getId());
        Operation operation=new Operation();
        operation.setOperationType(OperationType.Debit);
        operation.setAmount(event.getAmount());
        operation.setAccount(account);
        operation.setDate(event.getDate());
        account.setBalance(account.getBalance()-event.getAmount());
        operationRepository.save(operation);
        accountRepository.save(account);


    }
    @EventHandler
    public void on(AccountCreditedEvent event){
        log.info("***************************");
        log.info("AccountCreatedEvent Received Successfully");
        Account account = accountRepository.getById(event.getId());
        Operation operation=new Operation();
        operation.setOperationType(OperationType.Credit);
        operation.setAmount(event.getAmount());
        operation.setAccount(account);
        operation.setDate(event.getDate());
        account.setBalance(account.getBalance()+event.getAmount());
        operationRepository.save(operation);
        accountRepository.save(account);
    }
    @QueryHandler
    public List<Account> on (GetAllAccountsQuery getAllAccountsQuery){
      return accountRepository.findAll();
    }
    @QueryHandler
    public Account on (GetAccountsByIdQuery getAccountsByIdQuery){
        return accountRepository.findById(getAccountsByIdQuery.getAccountId()).get();
    }

}
