package ma.soulaimane.comptecqrseventsourcing.query.repository;

import ma.soulaimane.comptecqrseventsourcing.query.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation,Long> {
}
