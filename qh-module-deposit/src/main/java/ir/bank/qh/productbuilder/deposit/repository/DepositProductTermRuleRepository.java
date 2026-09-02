package ir.bank.qh.productbuilder.deposit.repository;

import ir.bank.qh.productbuilder.deposit.entity.DepositProductTermRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for DepositProductTermRule (deposit_product_term_rule).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface DepositProductTermRuleRepository extends JpaRepository<DepositProductTermRule, Long> {
}
