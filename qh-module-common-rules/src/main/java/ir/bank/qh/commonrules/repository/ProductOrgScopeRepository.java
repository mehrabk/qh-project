package ir.bank.qh.commonrules.repository;

import ir.bank.qh.commonrules.entity.ProductOrgScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for ProductOrgScope (product_org_scope).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface ProductOrgScopeRepository extends JpaRepository<ProductOrgScope, Long> {
}
