package ir.bank.qh.productbuilder.core.service;

import ir.bank.qh.productbuilder.core.entity.ProductLegacyMapping;
import ir.bank.qh.productbuilder.core.repository.ProductLegacyMappingRepository;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for ProductLegacyMapping. Business-rule checks equivalent to the
 * original model's CHECK constraints can be added by overriding
 * beforeCreate()/beforeUpdate().
 */
@Service
@RequiredArgsConstructor
@Transactional("productBuilderTransactionManager")
public class ProductLegacyMappingService extends AbstractCrudService<ProductLegacyMapping> {

    private final ProductLegacyMappingRepository repository;

    @Override
    protected JpaRepository<ProductLegacyMapping, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "ProductLegacyMapping";
    }
}
