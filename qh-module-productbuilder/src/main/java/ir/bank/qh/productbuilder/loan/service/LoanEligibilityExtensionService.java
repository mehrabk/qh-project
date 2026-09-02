package ir.bank.qh.productbuilder.loan.service;

import ir.bank.qh.productbuilder.loan.entity.LoanEligibilityExtension;
import ir.bank.qh.productbuilder.loan.repository.LoanEligibilityExtensionRepository;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for LoanEligibilityExtension. Business-rule checks equivalent to the
 * original model's CHECK constraints can be added by overriding
 * beforeCreate()/beforeUpdate().
 */
@Service
@RequiredArgsConstructor
@Transactional("productBuilderTransactionManager")
public class LoanEligibilityExtensionService extends AbstractCrudService<LoanEligibilityExtension> {

    private final LoanEligibilityExtensionRepository repository;

    @Override
    protected JpaRepository<LoanEligibilityExtension, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "LoanEligibilityExtension";
    }
}
