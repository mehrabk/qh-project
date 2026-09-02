package ir.bank.qh.productbuilder.core.service;

import ir.bank.qh.productbuilder.core.entity.ProductVersionModule;
import ir.bank.qh.productbuilder.core.repository.ProductVersionModuleRepository;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for ProductVersionModule. Business-rule checks equivalent to the
 * original model's CHECK constraints can be added by overriding
 * beforeCreate()/beforeUpdate().
 */
@Service
@RequiredArgsConstructor
@Transactional("productBuilderTransactionManager")
public class ProductVersionModuleService extends AbstractCrudService<ProductVersionModule> {

    private final ProductVersionModuleRepository repository;

    @Override
    protected JpaRepository<ProductVersionModule, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "ProductVersionModule";
    }
}
