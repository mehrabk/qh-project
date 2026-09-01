package ir.bank.qh.loan.service;

import ir.bank.qh.loan.entity.LoanProductProcessRule;
import ir.bank.qh.loan.repository.LoanProductProcessRuleRepository;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for LoanProductProcessRule. Business-rule checks equivalent to the
 * original model's CHECK constraints can be added by overriding
 * beforeCreate()/beforeUpdate().
 */
@Service
@RequiredArgsConstructor
@Transactional
public class LoanProductProcessRuleService extends AbstractCrudService<LoanProductProcessRule> {

    private final LoanProductProcessRuleRepository repository;

    @Override
    protected JpaRepository<LoanProductProcessRule, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "LoanProductProcessRule";
    }
}
