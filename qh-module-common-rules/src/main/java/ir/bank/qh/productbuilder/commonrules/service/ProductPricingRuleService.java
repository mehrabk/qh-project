package ir.bank.qh.productbuilder.commonrules.service;

import ir.bank.qh.productbuilder.commonrules.entity.ProductPricingRule;
import ir.bank.qh.productbuilder.commonrules.repository.ProductPricingRuleRepository;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for ProductPricingRule. Business-rule checks equivalent to the
 * original model's CHECK constraints can be added by overriding
 * beforeCreate()/beforeUpdate().
 */
@Service
@RequiredArgsConstructor
@Transactional("productBuilderTransactionManager")
public class ProductPricingRuleService extends AbstractCrudService<ProductPricingRule> {

    private final ProductPricingRuleRepository repository;

    @Override
    protected JpaRepository<ProductPricingRule, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "ProductPricingRule";
    }
}
