package ir.bank.qh.productbuilder.loan.repository;

import ir.bank.qh.productbuilder.loan.entity.LoanProductCollateralRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for LoanProductCollateralRule (loan_product_collateral_rule).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface LoanProductCollateralRuleRepository extends JpaRepository<LoanProductCollateralRule, Long> {
}
