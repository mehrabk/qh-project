package ir.bank.qh.commonrules.repository;

import ir.bank.qh.commonrules.entity.ProductChannelRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for ProductChannelRule (product_channel_rule).
 * Add derived-query methods here as new lookups are needed
 * (e.g. findByProductVersionId, findByRecordStatusCode...).
 */
@Repository
public interface ProductChannelRuleRepository extends JpaRepository<ProductChannelRule, Long> {
}
