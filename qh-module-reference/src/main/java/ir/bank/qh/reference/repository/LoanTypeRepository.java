package ir.bank.qh.reference.repository;

import ir.bank.qh.reference.entity.LoanType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for LoanType (loan_type).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface LoanTypeRepository extends JpaRepository<LoanType, Long> {
}
