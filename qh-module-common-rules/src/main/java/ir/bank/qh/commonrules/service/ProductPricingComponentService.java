package ir.bank.qh.commonrules.service;

import ir.bank.qh.commonrules.entity.ProductPricingComponent;
import ir.bank.qh.commonrules.repository.ProductPricingComponentRepository;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for ProductPricingComponent. Business-rule checks equivalent to the
 * original model's CHECK constraints can be added by overriding
 * beforeCreate()/beforeUpdate().
 */
@Service
@RequiredArgsConstructor
@Transactional("productBuilderTransactionManager")
public class ProductPricingComponentService extends AbstractCrudService<ProductPricingComponent> {

    private final ProductPricingComponentRepository repository;

    @Override
    protected JpaRepository<ProductPricingComponent, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "ProductPricingComponent";
    }
}
