package ma.soulaimane.comptecqrseventsourcing.commonapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DebitAccountRequestDtos {
    private String accountId;
    private double amount;
    private String currency;
}