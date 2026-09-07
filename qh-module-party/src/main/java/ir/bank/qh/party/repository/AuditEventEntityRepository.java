package ir.bank.qh.party.repository;

import ir.bank.qh.party.entity.AuditEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditEventEntityRepository extends JpaRepository<AuditEventEntity, Long> {
}
