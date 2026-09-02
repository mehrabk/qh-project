package ir.bank.qh.productbuilder.reference.service;

import ir.bank.qh.productbuilder.reference.entity.PatternOperationDetail;
import ir.bank.qh.productbuilder.reference.repository.PatternOperationDetailRepository;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for PatternOperationDetail. Business-rule checks equivalent to the
 * original model's CHECK constraints can be added by overriding
 * beforeCreate()/beforeUpdate().
 */
@Service
@RequiredArgsConstructor
@Transactional("productBuilderTransactionManager")
public class PatternOperationDetailService extends AbstractCrudService<PatternOperationDetail> {

    private final PatternOperationDetailRepository repository;

    @Override
    protected JpaRepository<PatternOperationDetail, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "PatternOperationDetail";
    }
}
