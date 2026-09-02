package ir.bank.qh.productbuilder.reference.repository;

import ir.bank.qh.productbuilder.reference.entity.LoanUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for LoanUsage (loan_usage).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface LoanUsageRepository extends JpaRepository<LoanUsage, Long> {
}
