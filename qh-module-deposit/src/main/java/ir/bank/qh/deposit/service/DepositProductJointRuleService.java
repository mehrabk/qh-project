package ir.bank.qh.deposit.service;

import ir.bank.qh.deposit.entity.DepositProductJointRule;
import ir.bank.qh.deposit.repository.DepositProductJointRuleRepository;
import ir.bank.qh.common.service.AbstractCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for DepositProductJointRule. Business-rule checks equivalent to the
 * original model's CHECK constraints can be added by overriding
 * beforeCreate()/beforeUpdate().
 */
@Service
@RequiredArgsConstructor
@Transactional
public class DepositProductJointRuleService extends AbstractCrudService<DepositProductJointRule> {

    private final DepositProductJointRuleRepository repository;

    @Override
    protected JpaRepository<DepositProductJointRule, Long> repository() {
        return repository;
    }

    @Override
    protected String entityName() {
        return "DepositProductJointRule";
    }
}
