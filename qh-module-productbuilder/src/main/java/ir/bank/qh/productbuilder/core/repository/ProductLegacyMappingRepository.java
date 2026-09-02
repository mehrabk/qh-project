package ir.bank.qh.productbuilder.core.repository;

import ir.bank.qh.productbuilder.core.entity.ProductLegacyMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for ProductLegacyMapping (product_legacy_mapping).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface ProductLegacyMappingRepository extends JpaRepository<ProductLegacyMapping, Long> {
}
