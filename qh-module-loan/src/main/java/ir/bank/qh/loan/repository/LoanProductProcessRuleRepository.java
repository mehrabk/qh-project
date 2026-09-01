package ir.bank.qh.loan.repository;

import ir.bank.qh.loan.entity.LoanProductProcessRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for LoanProductProcessRule (loan_product_process_rule).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface LoanProductProcessRuleRepository extends JpaRepository<LoanProductProcessRule, Long> {
}
