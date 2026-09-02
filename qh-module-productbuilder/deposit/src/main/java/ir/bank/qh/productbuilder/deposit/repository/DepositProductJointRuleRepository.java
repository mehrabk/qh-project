package ir.bank.qh.productbuilder.deposit.repository;

import ir.bank.qh.productbuilder.deposit.entity.DepositProductJointRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for DepositProductJointRule (deposit_product_joint_rule).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface DepositProductJointRuleRepository extends JpaRepository<DepositProductJointRule, Long> {
}
