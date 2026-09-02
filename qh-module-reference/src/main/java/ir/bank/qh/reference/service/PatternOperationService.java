package ir.bank.qh.reference.service;

import ir.bank.qh.reference.entity.PatternOperation;
import ir.bank.qh.reference.repository.PatternOperationRepository;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for PatternOperation. Business-rule checks equivalent to the
 * original model's CHECK constraints can be added by overriding
 * beforeCreate()/beforeUpdate().
 */
@Service
@RequiredArgsConstructor
@Transactional("productBuilderTransactionManager")
public class PatternOperationService extends AbstractCrudService<PatternOperation> {

    private final PatternOperationRepository repository;

    @Override
    protected JpaRepository<PatternOperation, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "PatternOperation";
    }
}
