package ir.bank.qh.deposit.service;

import ir.bank.qh.deposit.entity.DepositProductTermRule;
import ir.bank.qh.deposit.repository.DepositProductTermRuleRepository;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for DepositProductTermRule. Business-rule checks equivalent to the
 * original model's CHECK constraints can be added by overriding
 * beforeCreate()/beforeUpdate().
 */
@Service
@RequiredArgsConstructor
@Transactional("productBuilderTransactionManager")
public class DepositProductTermRuleService extends AbstractCrudService<DepositProductTermRule> {

    private final DepositProductTermRuleRepository repository;

    @Override
    protected JpaRepository<DepositProductTermRule, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "DepositProductTermRule";
    }
}
