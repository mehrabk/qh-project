package ir.bank.qh.deposit.repository;

import ir.bank.qh.deposit.entity.DepositProductClosureRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for DepositProductClosureRule (deposit_product_closure_rule).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface DepositProductClosureRuleRepository extends JpaRepository<DepositProductClosureRule, Long> {
}
