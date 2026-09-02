package ir.bank.qh.productbuilder.deposit.repository;

import ir.bank.qh.productbuilder.deposit.entity.DepositProductTransactionRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for DepositProductTransactionRule (deposit_product_transaction_rule).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface DepositProductTransactionRuleRepository extends JpaRepository<DepositProductTransactionRule, Long> {
}
