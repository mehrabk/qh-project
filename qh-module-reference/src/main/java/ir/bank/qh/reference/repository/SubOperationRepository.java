package ir.bank.qh.reference.repository;

import ir.bank.qh.reference.entity.SubOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for SubOperation (sub_operation).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface SubOperationRepository extends JpaRepository<SubOperation, Long> {
}
