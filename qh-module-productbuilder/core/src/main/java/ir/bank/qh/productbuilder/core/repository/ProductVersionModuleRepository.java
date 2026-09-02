package ir.bank.qh.productbuilder.core.repository;

import ir.bank.qh.productbuilder.core.entity.ProductVersionModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for ProductVersionModule (product_version_module).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface ProductVersionModuleRepository extends JpaRepository<ProductVersionModule, Long> {
}
