package ma.soulaimane.comptecqrseventsourcing.commonapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ma.soulaimane.comptecqrseventsourcing.commonapi.enums.AccountStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequestDtos {
    private double initialBalance;
    private String currency;
    private AccountStatus status;
}
