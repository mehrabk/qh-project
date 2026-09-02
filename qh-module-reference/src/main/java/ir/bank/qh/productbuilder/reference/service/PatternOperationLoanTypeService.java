package ir.bank.qh.productbuilder.reference.service;

import ir.bank.qh.productbuilder.reference.entity.PatternOperationLoanType;
import ir.bank.qh.productbuilder.reference.repository.PatternOperationLoanTypeRepository;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for PatternOperationLoanType. Business-rule checks equivalent to the
 * original model's CHECK constraints can be added by overriding
 * beforeCreate()/beforeUpdate().
 */
@Service
@RequiredArgsConstructor
@Transactional("productBuilderTransactionManager")
public class PatternOperationLoanTypeService extends AbstractCrudService<PatternOperationLoanType> {

    private final PatternOperationLoanTypeRepository repository;

    @Override
    protected JpaRepository<PatternOperationLoanType, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "PatternOperationLoanType";
    }
}
