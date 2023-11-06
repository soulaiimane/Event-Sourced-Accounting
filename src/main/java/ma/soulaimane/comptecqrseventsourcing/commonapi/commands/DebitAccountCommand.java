package ma.soulaimane.comptecqrseventsourcing.commonapi.commands;

import lombok.Getter;

import java.util.Date;

public class DebitAccountCommand extends BaseCommand<String>{
    @Getter private Date dateCredit;
    @Getter private double amount;
    @Getter private String currency;

    public DebitAccountCommand(String id, Date dateCredit, double amount, String currency) {
        super(id);
        this.dateCredit = dateCredit;
        this.amount = amount;
        this.currency = currency;
    }
}
