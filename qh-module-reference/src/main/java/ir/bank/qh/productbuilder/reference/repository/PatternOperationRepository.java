package ir.bank.qh.productbuilder.reference.repository;

import ir.bank.qh.productbuilder.reference.entity.PatternOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for PatternOperation (pattern_operation).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface PatternOperationRepository extends JpaRepository<PatternOperation, Long> {
}
