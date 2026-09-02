package ir.bank.qh.reference.service;

import ir.bank.qh.reference.entity.SubOperation;
import ir.bank.qh.reference.repository.SubOperationRepository;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for SubOperation. Business-rule checks equivalent to the
 * original model's CHECK constraints can be added by overriding
 * beforeCreate()/beforeUpdate().
 */
@Service
@RequiredArgsConstructor
@Transactional("productBuilderTransactionManager")
public class SubOperationService extends AbstractCrudService<SubOperation> {

    private final SubOperationRepository repository;

    @Override
    protected JpaRepository<SubOperation, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "SubOperation";
    }
}
