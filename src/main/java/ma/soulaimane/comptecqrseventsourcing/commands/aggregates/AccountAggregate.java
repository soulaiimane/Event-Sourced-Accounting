package ma.soulaimane.comptecqrseventsourcing.commands.aggregates;

import ma.soulaimane.comptecqrseventsourcing.commonapi.commands.CreatAccountCommand;
import ma.soulaimane.comptecqrseventsourcing.commonapi.commands.CreditAccountCommand;
import ma.soulaimane.comptecqrseventsourcing.commonapi.commands.DebitAccountCommand;
import ma.soulaimane.comptecqrseventsourcing.commonapi.enums.AccountStatus;
import ma.soulaimane.comptecqrseventsourcing.commonapi.events.AccountActivatedEvent;
import ma.soulaimane.comptecqrseventsourcing.commonapi.events.AccountCreatedEvent;
import ma.soulaimane.comptecqrseventsourcing.commonapi.events.AccountCreditedEvent;
import ma.soulaimane.comptecqrseventsourcing.commonapi.events.AccountDebitedEvent;
import ma.soulaimane.comptecqrseventsourcing.commonapi.exceptions.AmountBiggerThanBalance;
import ma.soulaimane.comptecqrseventsourcing.commonapi.exceptions.AmountEqual0Exception;
import ma.soulaimane.comptecqrseventsourcing.commonapi.exceptions.AmountNegativeException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class AccountAggregate  {
    @AggregateIdentifier
    private String id;
    private double Balance;
    private String currency;
    private AccountStatus status;

    public AccountAggregate() {
        //Required By Axon
    }
    @CommandHandler
    public AccountAggregate(CreatAccountCommand creatAccountCommand) {
        if (creatAccountCommand.getInitialBalance()<0) throw new RuntimeException("Impossible!! You Balance Is Negative");
        if (creatAccountCommand.getInitialBalance()==0) throw new RuntimeException("Impossible!! You Need To Put Money In Your Account");
        //If All is Ok
        AggregateLifecycle.apply(new AccountCreatedEvent(
                creatAccountCommand.getId(),
                creatAccountCommand.getInitialBalance(),
                creatAccountCommand.getCurrency(),
                AccountStatus.Created
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        this.id=event.getId();
        this.Balance=event.getInitialBalance();
        this.currency=event.getCurrency();
        this.status=AccountStatus.Created;
        AggregateLifecycle.apply(new AccountActivatedEvent(
                event.getId(),
                AccountStatus.Activated
        ) );
    }
    @EventSourcingHandler
    public void on(AccountActivatedEvent event){
        this.status=event.getStatus();
    }
    @CommandHandler
    public void CreditCommandHandler(CreditAccountCommand command) throws AmountNegativeException, AmountEqual0Exception {
        if (command.getAmount()<0){
            throw new AmountNegativeException("Amount should be positive !!");
        }
        if (command.getAmount()==0){
            throw new AmountEqual0Exception("Amount should be Bigger Than 0 !!");
        }
        AggregateLifecycle.apply(new AccountCreditedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency(),
                command.getDateCredit()
        ));
    }
    @EventSourcingHandler
    public void on(AccountCreditedEvent event){
        this.Balance+=event.getAmount();
    }
    @CommandHandler
    public void DebitCommandHandler(DebitAccountCommand command) throws AmountBiggerThanBalance,AmountNegativeException {
        if (command.getAmount()>this.Balance){
            throw new AmountBiggerThanBalance("Your Amount Not Enough Please Try Again!!");
        }
        if (command.getAmount()<=0){
            throw new AmountNegativeException("Amount should be positive And Bigger Than 0 !!");
        }
        AggregateLifecycle.apply(new AccountDebitedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency(),
                command.getDateCredit()
        ));
    }
    @EventSourcingHandler
    public void on(AccountDebitedEvent event){
        this.Balance-=event.getAmount();
    }

}
