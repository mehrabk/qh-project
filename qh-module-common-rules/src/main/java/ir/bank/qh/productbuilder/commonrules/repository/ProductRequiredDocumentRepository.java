package ir.bank.qh.productbuilder.commonrules.repository;

import ir.bank.qh.productbuilder.commonrules.entity.ProductRequiredDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for ProductRequiredDocument (product_required_document).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface ProductRequiredDocumentRepository extends JpaRepository<ProductRequiredDocument, Long> {
}
