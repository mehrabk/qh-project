package ir.bank.qh.deposit.repository;

import ir.bank.qh.deposit.entity.DepositProductHoldRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for DepositProductHoldRule (deposit_product_hold_rule).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface DepositProductHoldRuleRepository extends JpaRepository<DepositProductHoldRule, Long> {
}
