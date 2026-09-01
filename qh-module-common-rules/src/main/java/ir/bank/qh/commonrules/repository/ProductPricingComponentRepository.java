package ir.bank.qh.commonrules.repository;

import ir.bank.qh.commonrules.entity.ProductPricingComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for ProductPricingComponent (product_pricing_component).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface ProductPricingComponentRepository extends JpaRepository<ProductPricingComponent, Long> {
}
