package ir.bank.qh.productbuilder.core.repository;

import ir.bank.qh.productbuilder.core.entity.ProductRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for ProductRelationship (product_relationship).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface ProductRelationshipRepository extends JpaRepository<ProductRelationship, Long> {
}
