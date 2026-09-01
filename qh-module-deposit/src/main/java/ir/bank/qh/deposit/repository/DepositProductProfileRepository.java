package ir.bank.qh.deposit.repository;

import ir.bank.qh.deposit.entity.DepositProductProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for DepositProductProfile (deposit_product_profile).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface DepositProductProfileRepository extends JpaRepository<DepositProductProfile, Long> {
}
