package ir.bank.qh.reference.service;

import ir.bank.qh.reference.entity.Operation;
import ir.bank.qh.reference.repository.OperationRepository;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for Operation. Business-rule checks equivalent to the
 * original model's CHECK constraints can be added by overriding
 * beforeCreate()/beforeUpdate().
 */
@Service
@RequiredArgsConstructor
@Transactional("productBuilderTransactionManager")
public class OperationService extends AbstractCrudService<Operation> {

    private final OperationRepository repository;

    @Override
    protected JpaRepository<Operation, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "Operation";
    }
}
