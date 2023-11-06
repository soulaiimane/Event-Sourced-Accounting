package ma.soulaimane.comptecqrseventsourcing.commonapi.commands;

import lombok.Getter;
import ma.soulaimane.comptecqrseventsourcing.commonapi.enums.AccountStatus;

public class CreatAccountCommand extends BaseCommand<String>{
   @Getter
   private double initialBalance;
   @Getter
    private String currency;


    public CreatAccountCommand(String id, double initialBalance, String currency) {
        super(id);
        this.initialBalance = initialBalance;
        this.currency = currency;
    }
}
