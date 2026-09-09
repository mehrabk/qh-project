package ir.bank.qh.party.core.repository;

import ir.bank.qh.party.core.entity.AuditEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditEventEntityRepository extends JpaRepository<AuditEventEntity, Long> {
}
