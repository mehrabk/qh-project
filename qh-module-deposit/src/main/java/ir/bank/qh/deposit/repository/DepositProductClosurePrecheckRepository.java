package ir.bank.qh.deposit.repository;

import ir.bank.qh.deposit.entity.DepositProductClosurePrecheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for DepositProductClosurePrecheck (deposit_product_closure_precheck).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface DepositProductClosurePrecheckRepository extends JpaRepository<DepositProductClosurePrecheck, Long> {
}
