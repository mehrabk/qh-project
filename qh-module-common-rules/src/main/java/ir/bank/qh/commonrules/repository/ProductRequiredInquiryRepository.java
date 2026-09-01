package ir.bank.qh.commonrules.repository;

import ir.bank.qh.commonrules.entity.ProductRequiredInquiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for ProductRequiredInquiry (product_required_inquiry).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface ProductRequiredInquiryRepository extends JpaRepository<ProductRequiredInquiry, Long> {
}
