package ir.bank.qh.productbuilder.deposit.repository;

import ir.bank.qh.productbuilder.deposit.entity.DepositProductOpeningRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for DepositProductOpeningRule (deposit_product_opening_rule).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface DepositProductOpeningRuleRepository extends JpaRepository<DepositProductOpeningRule, Long> {
}
