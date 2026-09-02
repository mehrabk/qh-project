package ir.bank.qh.productbuilder.commonrules.service;

import ir.bank.qh.productbuilder.commonrules.entity.ProductChannelOperation;
import ir.bank.qh.productbuilder.commonrules.repository.ProductChannelOperationRepository;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for ProductChannelOperation. Business-rule checks equivalent to the
 * original model's CHECK constraints can be added by overriding
 * beforeCreate()/beforeUpdate().
 */
@Service
@RequiredArgsConstructor
@Transactional("productBuilderTransactionManager")
public class ProductChannelOperationService extends AbstractCrudService<ProductChannelOperation> {

    private final ProductChannelOperationRepository repository;

    @Override
    protected JpaRepository<ProductChannelOperation, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "ProductChannelOperation";
    }
}
