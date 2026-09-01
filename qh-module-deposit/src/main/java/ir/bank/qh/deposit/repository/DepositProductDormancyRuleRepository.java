package ir.bank.qh.deposit.repository;

import ir.bank.qh.deposit.entity.DepositProductDormancyRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for DepositProductDormancyRule (deposit_product_dormancy_rule).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface DepositProductDormancyRuleRepository extends JpaRepository<DepositProductDormancyRule, Long> {
}
