package ir.bank.qh.party.repository;

import ir.bank.qh.party.entity.AuditFieldChangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditFieldChangeEntityRepository extends JpaRepository<AuditFieldChangeEntity, Long> {
}
