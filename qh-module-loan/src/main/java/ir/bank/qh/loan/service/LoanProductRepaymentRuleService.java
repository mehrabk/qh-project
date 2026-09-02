package ir.bank.qh.loan.service;

import ir.bank.qh.loan.entity.LoanProductRepaymentRule;
import ir.bank.qh.loan.repository.LoanProductRepaymentRuleRepository;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for LoanProductRepaymentRule. Business-rule checks equivalent to the
 * original model's CHECK constraints can be added by overriding
 * beforeCreate()/beforeUpdate().
 */
@Service
@RequiredArgsConstructor
@Transactional("productBuilderTransactionManager")
public class LoanProductRepaymentRuleService extends AbstractCrudService<LoanProductRepaymentRule> {

    private final LoanProductRepaymentRuleRepository repository;

    @Override
    protected JpaRepository<LoanProductRepaymentRule, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "LoanProductRepaymentRule";
    }
}
