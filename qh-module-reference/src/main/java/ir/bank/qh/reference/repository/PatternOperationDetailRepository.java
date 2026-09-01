package ir.bank.qh.reference.repository;

import ir.bank.qh.reference.entity.PatternOperationDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for PatternOperationDetail (pattern_operation_detail).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface PatternOperationDetailRepository extends JpaRepository<PatternOperationDetail, Long> {
}
