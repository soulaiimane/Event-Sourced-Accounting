package ma.soulaimane.comptecqrseventsourcing.commonapi.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAccountsByIdQuery {
    private String accountId;

}
