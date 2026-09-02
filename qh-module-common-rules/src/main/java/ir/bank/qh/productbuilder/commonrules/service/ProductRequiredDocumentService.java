package ir.bank.qh.productbuilder.commonrules.service;

import ir.bank.qh.productbuilder.commonrules.entity.ProductRequiredDocument;
import ir.bank.qh.productbuilder.commonrules.repository.ProductRequiredDocumentRepository;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for ProductRequiredDocument. Business-rule checks equivalent to the
 * original model's CHECK constraints can be added by overriding
 * beforeCreate()/beforeUpdate().
 */
@Service
@RequiredArgsConstructor
@Transactional("productBuilderTransactionManager")
public class ProductRequiredDocumentService extends AbstractCrudService<ProductRequiredDocument> {

    private final ProductRequiredDocumentRepository repository;

    @Override
    protected JpaRepository<ProductRequiredDocument, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "ProductRequiredDocument";
    }
}
