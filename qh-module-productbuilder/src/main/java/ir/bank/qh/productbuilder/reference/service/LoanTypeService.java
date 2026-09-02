package ir.bank.qh.productbuilder.reference.service;

import ir.bank.qh.productbuilder.reference.entity.LoanType;
import ir.bank.qh.productbuilder.reference.repository.LoanTypeRepository;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for LoanType. Business-rule checks equivalent to the
 * original model's CHECK constraints can be added by overriding
 * beforeCreate()/beforeUpdate().
 */
@Service
@RequiredArgsConstructor
@Transactional("productBuilderTransactionManager")
public class LoanTypeService extends AbstractCrudService<LoanType> {

    private final LoanTypeRepository repository;

    @Override
    protected JpaRepository<LoanType, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "LoanType";
    }
}
