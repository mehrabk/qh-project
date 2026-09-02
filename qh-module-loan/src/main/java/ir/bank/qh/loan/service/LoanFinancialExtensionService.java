package ir.bank.qh.loan.service;

import ir.bank.qh.loan.entity.LoanFinancialExtension;
import ir.bank.qh.loan.repository.LoanFinancialExtensionRepository;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for LoanFinancialExtension. Business-rule checks equivalent to the
 * original model's CHECK constraints can be added by overriding
 * beforeCreate()/beforeUpdate().
 */
@Service
@RequiredArgsConstructor
@Transactional("productBuilderTransactionManager")
public class LoanFinancialExtensionService extends AbstractCrudService<LoanFinancialExtension> {

    private final LoanFinancialExtensionRepository repository;

    @Override
    protected JpaRepository<LoanFinancialExtension, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "LoanFinancialExtension";
    }
}
