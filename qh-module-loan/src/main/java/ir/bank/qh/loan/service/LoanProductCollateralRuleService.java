package ir.bank.qh.loan.service;

import ir.bank.qh.loan.entity.LoanProductCollateralRule;
import ir.bank.qh.loan.repository.LoanProductCollateralRuleRepository;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for LoanProductCollateralRule. Business-rule checks equivalent to the
 * original model's CHECK constraints can be added by overriding
 * beforeCreate()/beforeUpdate().
 */
@Service
@RequiredArgsConstructor
@Transactional("productBuilderTransactionManager")
public class LoanProductCollateralRuleService extends AbstractCrudService<LoanProductCollateralRule> {

    private final LoanProductCollateralRuleRepository repository;

    @Override
    protected JpaRepository<LoanProductCollateralRule, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "LoanProductCollateralRule";
    }
}
