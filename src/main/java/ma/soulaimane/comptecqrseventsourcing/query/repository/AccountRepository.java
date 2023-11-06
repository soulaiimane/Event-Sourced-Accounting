package ma.soulaimane.comptecqrseventsourcing.query.repository;

import ma.soulaimane.comptecqrseventsourcing.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,String> {
}
