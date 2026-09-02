package ir.bank.qh.reference.service;

import ir.bank.qh.reference.entity.LoanTypePlanType;
import ir.bank.qh.reference.repository.LoanTypePlanTypeRepository;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for LoanTypePlanType. Business-rule checks equivalent to the
 * original model's CHECK constraints can be added by overriding
 * beforeCreate()/beforeUpdate().
 */
@Service
@RequiredArgsConstructor
@Transactional("productBuilderTransactionManager")
public class LoanTypePlanTypeService extends AbstractCrudService<LoanTypePlanType> {

    private final LoanTypePlanTypeRepository repository;

    @Override
    protected JpaRepository<LoanTypePlanType, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "LoanTypePlanType";
    }
}
