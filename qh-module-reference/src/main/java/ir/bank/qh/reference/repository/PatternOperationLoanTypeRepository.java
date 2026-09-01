package ir.bank.qh.reference.repository;

import ir.bank.qh.reference.entity.PatternOperationLoanType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for PatternOperationLoanType (pattern_operation_loan_type).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface PatternOperationLoanTypeRepository extends JpaRepository<PatternOperationLoanType, Long> {
}
