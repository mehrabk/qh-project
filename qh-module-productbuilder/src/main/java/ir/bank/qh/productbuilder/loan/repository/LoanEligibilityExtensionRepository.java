package ir.bank.qh.productbuilder.loan.repository;

import ir.bank.qh.productbuilder.loan.entity.LoanEligibilityExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for LoanEligibilityExtension (loan_eligibility_extension).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface LoanEligibilityExtensionRepository extends JpaRepository<LoanEligibilityExtension, Long> {
}
