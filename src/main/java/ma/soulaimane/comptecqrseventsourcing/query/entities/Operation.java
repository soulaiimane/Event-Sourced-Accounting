package ma.soulaimane.comptecqrseventsourcing.query.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ma.soulaimane.comptecqrseventsourcing.commonapi.enums.OperationType;

import javax.persistence.*;
import java.util.Date;
@Entity
@Data
public class Operation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private double amount;
    @Enumerated(EnumType.STRING)
    private OperationType operationType;
    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Account account;
}
