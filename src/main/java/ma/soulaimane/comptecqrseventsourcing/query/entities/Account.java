package ma.soulaimane.comptecqrseventsourcing.query.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ma.soulaimane.comptecqrseventsourcing.commonapi.enums.AccountStatus;

import javax.persistence.*;
import java.util.Collection;
@Entity
@Data
public class Account {
    @Id
    private String id;
    private double balance;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private String currency;
    @OneToMany(mappedBy = "account")
    private Collection<Operation>operations;

}
