package ir.bank.qh.commonrules.repository;

import ir.bank.qh.commonrules.entity.ProductRateTier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for ProductRateTier (product_rate_tier).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface ProductRateTierRepository extends JpaRepository<ProductRateTier, Long> {
}
