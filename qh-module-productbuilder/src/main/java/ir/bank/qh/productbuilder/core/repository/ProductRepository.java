package ir.bank.qh.productbuilder.core.repository;

import ir.bank.qh.productbuilder.core.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for Product (product).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
