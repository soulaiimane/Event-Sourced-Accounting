package ma.soulaimane.comptecqrseventsourcing.commonapi.events;

import lombok.Getter;
import ma.soulaimane.comptecqrseventsourcing.commonapi.enums.AccountStatus;

public class AccountActivatedEvent extends BaseEvent<String>{
    @Getter
    private AccountStatus status;

    public AccountActivatedEvent(String id, AccountStatus status) {
        super(id);
        this.status = status;
    }
}
