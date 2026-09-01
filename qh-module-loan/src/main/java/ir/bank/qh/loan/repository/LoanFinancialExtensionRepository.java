package ir.bank.qh.loan.repository;

import ir.bank.qh.loan.entity.LoanFinancialExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for LoanFinancialExtension (loan_financial_extension).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface LoanFinancialExtensionRepository extends JpaRepository<LoanFinancialExtension, Long> {
}
