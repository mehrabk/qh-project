package ir.bank.qh.commonrules.repository;

import ir.bank.qh.commonrules.entity.ProductEligibilityRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for ProductEligibilityRule (product_eligibility_rule).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface ProductEligibilityRuleRepository extends JpaRepository<ProductEligibilityRule, Long> {
}
