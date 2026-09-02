package ir.bank.qh.productbuilder.commonrules.repository;

import ir.bank.qh.productbuilder.commonrules.entity.ProductPricingRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for ProductPricingRule (product_pricing_rule).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface ProductPricingRuleRepository extends JpaRepository<ProductPricingRule, Long> {
}
