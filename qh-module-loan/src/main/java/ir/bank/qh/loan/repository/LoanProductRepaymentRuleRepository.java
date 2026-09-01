package ir.bank.qh.loan.repository;

import ir.bank.qh.loan.entity.LoanProductRepaymentRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for LoanProductRepaymentRule (loan_product_repayment_rule).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface LoanProductRepaymentRuleRepository extends JpaRepository<LoanProductRepaymentRule, Long> {
}
