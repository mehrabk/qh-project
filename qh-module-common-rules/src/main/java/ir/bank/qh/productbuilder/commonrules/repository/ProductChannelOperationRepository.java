package ir.bank.qh.productbuilder.commonrules.repository;

import ir.bank.qh.productbuilder.commonrules.entity.ProductChannelOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for ProductChannelOperation (product_channel_operation).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface ProductChannelOperationRepository extends JpaRepository<ProductChannelOperation, Long> {
}
