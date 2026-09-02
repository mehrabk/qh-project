package ir.bank.qh.productbuilder.commonrules.service;

import ir.bank.qh.productbuilder.commonrules.entity.ProductOrgScope;
import ir.bank.qh.productbuilder.commonrules.repository.ProductOrgScopeRepository;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for ProductOrgScope. Business-rule checks equivalent to the
 * original model's CHECK constraints can be added by overriding
 * beforeCreate()/beforeUpdate().
 */
@Service
@RequiredArgsConstructor
@Transactional("productBuilderTransactionManager")
public class ProductOrgScopeService extends AbstractCrudService<ProductOrgScope> {

    private final ProductOrgScopeRepository repository;

    @Override
    protected JpaRepository<ProductOrgScope, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "ProductOrgScope";
    }
}
